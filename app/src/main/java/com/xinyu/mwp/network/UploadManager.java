package com.xinyu.mwp.network;

import com.xinyu.mwp.bean.Parameter;
import com.xinyu.mwp.network.progress.ProgressRequestBody;
import com.xinyu.mwp.network.progress.listener.ProgressListener;
import com.xinyu.mwp.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传相关的模块
 */
public class UploadManager {

    private final OkHttpClient mOkHttpClient;

    public UploadManager(OkHttpClient.Builder mOkHttpClientBuilder) {
        mOkHttpClient = mOkHttpClientBuilder.build();
    }

    /**
     * 同步基于post的文件上传:上传单个文件
     */
    public Response post(String url, String fileKey, File file, Object tag) throws IOException {
        return post(url, new String[]{fileKey}, new File[]{file}, null, tag);
    }

    /**
     * 同步基于post的文件上传:上传多个文件以及携带key-value对：主方法
     */
    public Response post(String url, String[] fileKeys, File[] files, List<Parameter> paramList, Object tag) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, paramList, tag);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 同步单文件上传
     */
    public Response post(String url, String fileKey, File file, List<Parameter> paramList, Object tag) throws IOException {
        return post(url, new String[]{fileKey}, new File[]{file}, paramList, tag);
    }

    /**
     * 异步基于post的文件上传:主方法
     */
    public void postAsyn(String url, String[] fileKeys, File[] files, List<Parameter> paramList, final HttpManager.OnHttpResponseListener callback, Object tag) {
        final Request request = buildMultipartFormRequest(url, files, fileKeys, paramList, tag);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                resultUrl = call.request().url().toString();
                callback.onHttpRequestError(resultUrl, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                resultUrl = response.request().toString();
                callback.onHttpFileSuccess(resultUrl, response.body().toString());
            }

            String resultUrl;
        });
    }

    /**
     * 异步基于post的文件上传:单文件不带参数上传
     */
    public void postAsyn(String url, String fileKey, File file, final HttpManager.OnHttpResponseListener callback, Object tag) {
        postAsyn(url, new String[]{fileKey}, new File[]{file}, null, callback, tag);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     */
    public void postAsyn(String url, String fileKey, File file, List<Parameter> paramList, final HttpManager.OnHttpResponseListener callback, Object tag) {
        postAsyn(url, new String[]{fileKey}, new File[]{file}, paramList, callback, tag);
    }

    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, List<Parameter> paramList, Object tag) {
        //        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.getNoBlankString(url));//去掉空格
        if (paramList != null) {
            Parameter parameter;
            for (int i = 0; i < paramList.size(); i++) {
                parameter = paramList.get(i);
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + StringUtil.getTrimedString(parameter.key) + "\""),
                        RequestBody.create(null, StringUtil.getTrimedString(parameter.value)));
            }
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(tag)
                .build();
    }
    //=============便利的访问方式结束===============


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 包装请求体用于上传文件的回调
     *
     * @param requestBody             请求体RequestBody
     * @param progressRequestListener 进度回调接口
     * @return 包装后的进度回调请求体
     */
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, ProgressListener progressRequestListener) {
        //包装请求体
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }
}
