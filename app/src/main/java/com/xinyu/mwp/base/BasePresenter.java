package com.xinyu.mwp.base;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.xinyu.mwp.bean.Parameter;
import com.xinyu.mwp.interfaces.IBaseView;
import com.xinyu.mwp.manager.WeakHandler;
import com.xinyu.mwp.network.HttpManager;
import com.xinyu.mwp.utils.SharePerferenceUtil;
import com.xinyu.mwp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 2016/11/11 18:24.
 * Describe：${Presenter基类}
 * Modified：${TODO}
 */

public class BasePresenter<T> implements HttpManager.OnHttpResponseListener {
    private static final String TAG = "BasePresenter";
    private BaseModel mModel;
    protected IBaseView mView;
    protected List<Parameter> paramList;
    public Context context;
    private WeakHandler mHandler;

    public BasePresenter(IBaseView mView, Context context) {
        this.mView = mView;
        this.context = context;
        mHandler = new WeakHandler(Looper.myLooper());
    }

    public List<Parameter> addToken() {
        if (paramList != null) {
            paramList.clear();
        } else {
            paramList = new ArrayList<>();
        }
        paramList.add(new Parameter("token", SharePerferenceUtil.getToken()));
        return paramList;
    }

    public BaseModel getmModel() {
        return mModel;
    }

    public void setmModel(BaseModel mModel) {
        this.mModel = mModel;
    }

    public void initData(T data) {

    }

    public void runUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public void runThread(String runName, Runnable runnable) {
        ((BaseActivity) context).runThread(runName, runnable);
    }

    @Override
    public void onHttpRequestSuccess(String requestPath, final int resultCode, final String resultData) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (resultCode != 200) {
                    ToastUtil.showToastOkPic(resultData);
                    return;
                }
            }
        });
    }

    @Override
    public void onHttpRequestError(String requestPath, final Exception exception) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastOkPic(exception.getMessage());
            }
        });
    }

    @Override
    public void onHttpFileSuccess(String requestPath, String filePath) {
    }

    /**
     * @param json
     * @return
     * @throws Exception
     */
    public Object[] checkRespone(String json) throws Exception {
        if (TextUtils.isEmpty(json)) {
            throw (new Exception("json 为null"));
        } else {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject.containsKey("code") && jsonObject.getIntValue("code") == 200) {
                if (jsonObject.containsKey("data")) {
                    return new Object[]{jsonObject.getString("data"), jsonObject.getIntValue("code")};
                } else if (jsonObject.containsKey("msg")) {
                    return new Object[]{jsonObject.getString("msg"), jsonObject.getIntValue("code")};
                } else {
                    return new Object[]{"", jsonObject.getIntValue("code")};
                }
            } else if (jsonObject.containsKey("code")) {
                if (jsonObject.containsKey("msg")) {
                    return new Object[]{jsonObject.getString("msg"), jsonObject.getIntValue("code")};
                } else {
                    throw (new Exception("缺少msg"));
                }
            } else {
                throw (new Exception("json 格式异常"));
            }
        }
    }
}
