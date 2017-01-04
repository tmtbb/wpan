package com.xinyu.mwp.network;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xinyu.mwp.network.progress.ProgressResponseBody;
import com.xinyu.mwp.network.progress.listener.ProgressListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载相关的模块
 */
public class DownloadManager {

    private static final String TAG = "DownloadManager";
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    public static final String FILE_ROOT = SDCARD_ROOT + "Download/";
    private OkHttpClient mOkHttpClient;

    public DownloadManager(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    public void downloadAsyn(final String url, final String destFileDir, final HttpManager.OnHttpResponseListener callback, Object tag) {
        final Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestPath = call.request().url().toString();
                callback.onHttpRequestError(requestPath, e);
            }

            //TODO 下载的回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requestPath = response.request().url().toString();
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();

                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        Log.i(TAG, "onResponse: " + buf + "leng" + len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    callback.onHttpFileSuccess(requestPath, file.getAbsolutePath());

                } catch (IOException e) {
                    callback.onHttpRequestError(requestPath, e);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {

                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {

                    }
                }
            }

            String requestPath;
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    public void downloadAsyn(final String url, final String destFileDir, final HttpManager.OnHttpResponseListener callback) {
        downloadAsyn(url, destFileDir, callback, null);
    }


    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public void addProgressResponseListener(final ProgressListener progressListener, final String url, final Handler callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //增加拦截器
        mOkHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            //TODO 下载的回调
            @Override
            public void onFailure(Call call, IOException e) {
                msg.what = -1;
                msg.obj = e.getMessage();
                callback.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File dir = new File(FILE_ROOT);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        //如果下载文件成功，第一个参数为文件的绝对路径
                        Log.i(TAG, "onResponse: " + buf + "leng" + len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    msg.what = 1;
                    msg.obj = file.getAbsolutePath();
                    callback.sendMessage(msg);
                } catch (IOException e) {
                    msg.what = -1;
                    msg.obj = e.getMessage();
                    callback.sendMessage(msg);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            Message msg = new Message();
        });
    }
}