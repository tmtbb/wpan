package com.xinyu.mwp.networkapi.http;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnErrorListener;
import com.xinyu.mwp.listener.OnProgressListener;
import com.xinyu.mwp.listener.OnSuccessListener;
import com.xinyu.mwp.networkapi.NetworkAPIConstant;
import com.xinyu.mwp.networkapi.NetworkAPIException;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class XUtilsHttpReqeustImpl extends BaseReqeustAbstract {


    class JsonRequestCallBack<T> implements Callback.ProgressCallback<String> {

        private OnErrorListener onErrorListener;
        private OnSuccessListener<T> onSuccessListener;
        private OnProgressListener onProgressListener;

        public JsonRequestCallBack() {

        }

        public JsonRequestCallBack(OnSuccessListener<T> onSuccessListener, OnErrorListener onErrorListener) {
            this.onErrorListener = onErrorListener;
            this.onSuccessListener = onSuccessListener;
        }

        public void setOnErrorListener(OnErrorListener onErrorListener) {
            this.onErrorListener = onErrorListener;
        }

        public void setOnSuccessListener(OnSuccessListener<T> onSuccessListener) {
            this.onSuccessListener = onSuccessListener;
        }

        public void setOnProgressListener(OnProgressListener onProgressListener) {
            this.onProgressListener = onProgressListener;
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            if (onProgressListener != null && isUploading) {
                onProgressListener.onProgress(total, current);
            }
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onSuccess(String s) {
            try {
                LogUtil.e("onSuccess--->" + s);
                JSONObject jsonObject = new JSONObject(s);
                onJSONObjectSuccess(jsonObject, onSuccessListener, onErrorListener);

            } catch (JSONException e) {
                XUtilsHttpReqeustImpl.this.onError(NetworkAPIException.JSON_ERROR, e, onErrorListener);
            }
        }

        @Override
        public void onCancelled(CancelledException e) {

        }

        @Override
        public void onError(Throwable throwable, boolean b) {
            XUtilsHttpReqeustImpl.this.onError(throwable.getCause(), onErrorListener);
        }

        @Override
        public void onFinished() {

        }
    }

    @Override
    protected void postRequest(String url,
                               HashMap<String, Object> map,
                               OnSuccessListener<?> onSuccessListener,
                               OnErrorListener onErrorListener) {
        postRequest(url, map, onSuccessListener, onErrorListener, null);
    }


    protected void postRequest(String url,
                               HashMap<String, Object> map,
                               OnSuccessListener<?> onSuccessListener,
                               OnErrorListener onErrorListener, OnProgressListener onProgressListener) {
        int timeout = isUpfileload(map) ? NetworkHttpAPIFactoryImpl.getInstance().getConfig().getUpfileloadTimeout()
                : NetworkHttpAPIFactoryImpl.getInstance().getConfig().getTimeout();
        JsonRequestCallBack jsonRequestCallBack = new JsonRequestCallBack();
        jsonRequestCallBack.setOnErrorListener(onErrorListener);
        jsonRequestCallBack.setOnSuccessListener(onSuccessListener);
        jsonRequestCallBack.setOnProgressListener(onProgressListener);
        RequestParams requestParams = mapToRequestParams(url, map);
        requestParams.setConnectTimeout(timeout);
        x.http().post(requestParams, jsonRequestCallBack);
    }


    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String sign(String string) {
        return MD5(string);
    }

    /**
     * 解析参数
     *
     * @param map
     * @return
     */
    private RequestParams mapToRequestParams(String url, HashMap<String, Object> map) {

        final RequestParams requestParams = new RequestParams(url);
        StringBuffer signBuffer = new StringBuffer();
        signBuffer.append("POST");
        signBuffer.append(url);
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put("timestamp", new Date().getTime() / 1000);//秒
        map.put("ostype", NetworkAPIConstant.OSTYPE_ANDROID);
        map.put("appver", Utils.getVersion());
        map.put("appname", Utils.getPackageName());
        map.put("sdkver", Utils.getSdkVersion());
        map.put("deviceId", MyApplication.getApplication().getAndroidId());
        map.put("channelCode", Utils.getChannel(MyApplication.getApplication()));//快速打包方式设置渠道
        String imei = ((TelephonyManager) MyApplication.getApplication().getSystemService(MyApplication.getApplication().TELEPHONY_SERVICE)).getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = "";
        }
        map.put("imei", imei);

        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        Object value = null;
        for (Object key : keys) {
            value = map.get(key);
            if (value != null) {
                if (value instanceof File) {
                    requestParams.addBodyParameter(key.toString(), (File) map.get(key));
                } else {
                    signBuffer.append(key.toString());
                    signBuffer.append("=");
                    signBuffer.append(map.get(key).toString());
                    requestParams.addBodyParameter(key.toString(), map.get(key).toString());
                }
            }
        }
        LogUtil.e(url + "-->" + map.toString());
        signBuffer.append(MD5(Utils.getPackageName()));
        try {
            requestParams.addBodyParameter("sign", sign(URLEncoder.encode(signBuffer.toString(), "utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return requestParams;
    }

    private boolean isUpfileload(HashMap<String, Object> map) {
        for (String key : map.keySet()) {
            if (map.get(key) != null && map.get(key) instanceof File)
                return true;
        }
        return false;
    }


    protected void getRequest(String url, HashMap<String, String> map, final OnAPIListener<JSONObject> listener) {
        int timeout = NetworkHttpAPIFactoryImpl.getInstance().getConfig().getTimeout();
        RequestParams requestParams = mapToGetRequestParams(url, map);
        requestParams.setConnectTimeout(timeout);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    listener.onSuccess(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                listener.onError(throwable);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private RequestParams mapToGetRequestParams(String url, HashMap<String, String> map) {
        final RequestParams requestParams = new RequestParams(url);
        for (String key : map.keySet()) {
            requestParams.addQueryStringParameter(key, map.get(key));
        }
        return requestParams;
    }
}
