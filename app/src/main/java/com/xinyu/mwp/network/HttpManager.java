/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.xinyu.mwp.network;

import android.content.Context;
import android.util.Log;

import com.xinyu.mwp.base.BaseApplication;
import com.xinyu.mwp.bean.HttpErrorMassge;
import com.xinyu.mwp.bean.Parameter;
import com.xinyu.mwp.utils.MD5Util;
import com.xinyu.mwp.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTP请求管理类
 *
 * @author Lemon
 * @use HttpManager.getInstance().get(...)或HttpManager.getInstance().post(...)  > 在回调方法onHttpRequestSuccess和onHttpRequestError处理HTTP请求结果
 * @must 解决getToken，getResponseCode，getResponseData中的TODO
 */
public class HttpManager {
    private static final String TAG = "HttpManager";
    private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
    private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");
    private UploadManager mUpdateClient;
    protected Context context;

    /**
     * 网络请求回调接口
     */
    public interface OnHttpResponseListener {
        /**
         * @param requestPath 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
         * @param resultCode  服务器返回结果码
         * @param resultData  服务器返回的Json串
         */
        void onHttpRequestSuccess(String requestPath, int resultCode, String resultData);

        /**
         * @param requestPath 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
         * @param exception   OKHTTP中请求异常
         */
        void onHttpRequestError(String requestPath, Exception exception);

        /**
         * @param requestPath 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
         * @param filePath    文件路径
         */
        void onHttpFileSuccess(String requestPath, String filePath);
    }

    private static HttpManager instance;// 单例
    private static SSLSocketFactory socketFactory;// 单例
    protected OkHttpClient.Builder mOkHttpClientBuilder;
    private OkHttpClient mOkHttpClient;

    private HttpManager(Context context) {
        try {
            this.context = context;
            //TODO 初始化自签名，demo.cer（这里demo.cer是空文件）为服务器生成的自签名证书，存放于assets目录下，如果不需要自签名可删除
            mOkHttpClientBuilder = new OkHttpClient.Builder();
            mOkHttpClientBuilder.cookieJar(new HttpHead());
            mOkHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
            mOkHttpClientBuilder.writeTimeout(10, TimeUnit.SECONDS);
            mOkHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS);
            this.mUpdateClient = new UploadManager(mOkHttpClientBuilder);
            this.mOkHttpClient = mOkHttpClientBuilder.build();
        } catch (Exception e) {
            Log.e(TAG, "HttpManager  try {" +
                    "  socketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open(\"demo.cer\"));\n" +
                    "\t\t} catch (Exception e) {\n" + e.getMessage());
        }
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager(BaseApplication.getInstance());
                }
            }
        }
        return instance;
    }

    /**
     * 列表首页页码。有些服务器设置为1，即列表页码从1开始
     */
    public static final int PAGE_NUM_0 = 0;

    /**
     * 异步请求
     *
     * @param paramList 请求参数列表，（可以一个键对应多个值）
     * @param url       接口url
     *                  在activity中可以以requestCode来区分各个请求，serverResultCode是服务器返回的状态码，
     *                  json是数据json，可能为 空字符串
     * @param listener  接口回调
     */
    public void postAsynOKHttp(String requestName, List<Parameter> paramList, String url, OnHttpResponseListener listener) {
        //添加信任https证书,用于自签名,不需要可删除
        if (url.startsWith(StringUtil.URL_PREFIXs) && socketFactory != null) {
            mOkHttpClientBuilder.sslSocketFactory(socketFactory);
        }
        FormBody.Builder fBuilder = new FormBody.Builder();
        StringBuffer sb = new StringBuffer();
        if (paramList != null) {
            for (Parameter p : paramList) {
                fBuilder.add(StringUtil.getTrimedString(p.key), StringUtil.getTrimedString(p.value));
                sb.append(StringUtil.getTrimedString(p.key));
                sb.append("=");
                sb.append(StringUtil.getTrimedString(p.value));
                sb.append("&");
            }
        }
        RequestBody body = fBuilder.build();
        Log.i(TAG, url + "?" + sb.toString());

        Request request = new Request.Builder()
                .url(StringUtil.getNoBlankString(url))
                .post(body).build();
        requestStart(requestName, request, listener);
    }

    /**
     * 同步请求
     *
     * @param url      请求地址
     * @param listener
     * @param tag
     * @throws IOException
     */
    public Response postOKHttp(String url, OnHttpResponseListener listener, Object tag) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_STRING, url);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        if (tag != null) {
            builder.tag(tag);
        }
        Request request = builder.build();
        okhttp3.Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    /**
     * @param paramList 请求参数列表，（可以一个键对应多个值）
     * @param url       接口url
     *                  在activity中可以以requestCode来区分各个请求，serverResultCode是服务器返回的状态码，
     *                  json是数据json，可能为 空字符串
     * @param listener
     */
    public void getAsynOKHttp(String requestName, List<Parameter> paramList, String url, OnHttpResponseListener listener) {
        //添加信任https证书,用于自签名,不需要可删除
        if (url.startsWith(StringUtil.URL_PREFIXs) && socketFactory != null) {
            mOkHttpClientBuilder.sslSocketFactory(socketFactory);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.getNoBlankString(url));
        if (paramList != null) {
            Parameter parameter;
            for (int i = 0; i < paramList.size(); i++) {
                parameter = paramList.get(i);
                sb.append(i <= 0 ? "?" : "&");
                sb.append(StringUtil.getTrimedString(parameter.key));
                sb.append("=");
                sb.append(StringUtil.getTrimedString(parameter.value));
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString()).build();
        Log.i(TAG, sb.toString());
        requestStart(requestName, request, listener);
    }

    private void requestStart(final String requestName, Request request, final OnHttpResponseListener listener) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                listener.onHttpRequestError(requestName, e);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                int statusCode = response.code();
                Log.i(TAG, response.body().toString());
                try {
                    switch (statusCode) {
                        case 200:
                            listener.onHttpRequestSuccess(requestName, statusCode, response.body().string());
                            break;
                        case 300://重定向错误
                            listener.onHttpRequestError(requestName, new Exception(HttpErrorMassge.ABHORMAL_LINKE_REDIRECT));
                            break;
                        case 400:
                        case 404://参数错误
                            listener.onHttpRequestError(requestName, new Exception(HttpErrorMassge.ABHORMAL_LINKE_ERROR));
                            break;
                        case 405:
                            listener.onHttpRequestError(requestName, new Exception(HttpErrorMassge.ABHORMAL_REQUEST_ERROR));
                            break;
                        case 500:
                        case 505://服务端错误
                            listener.onHttpRequestError(requestName, new Exception(HttpErrorMassge.ABHORMAL_SERVER_ERROR));
                            break;
                    }
                } catch (Exception e) {
                    listener.onHttpRequestError(requestName, e);
                }
            }
        });
    }

    /**
     * @param paramList
     * @return
     * @must demo_***改为服务器设定值
     */
    public String getToken(List<Parameter> paramList) {
        if (paramList == null) {
            return "";
        }

        String token = "";
        Parameter p;
        for (int i = 0; i < paramList.size(); i++) {
            if (i > 0) {
                token += "&";
            }
            p = paramList.get(i);
            token += (p.key + "=" + p.value);
        }
        token += "demo_***";//TODO 这里的demo_***改为你自己服务器的设定值
        return MD5Util.MD5(token);
    }

    private static final String KEY_COOKIE = "cookie";

    /**
     * @return
     */
    public String getCookie() {
        return context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE).getString(KEY_COOKIE, "");
    }

    /**
     * @param value
     */
    public void saveCookie(String value) {
        context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE).edit().putString(KEY_COOKIE, value).commit();
    }

    public class HttpHead implements CookieJar {

        public HttpHead() {
        }

        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url, cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }

    public void uploadFile(String url, String key, File file, OnHttpResponseListener listener) {
        mUpdateClient.postAsyn(url, key, file, listener, null);
    }

    public void uploadFile(String url, String key, File file, List<Parameter> paramList, OnHttpResponseListener listener) {
        mUpdateClient.postAsyn(url, key, file, paramList, listener, null);
    }
}