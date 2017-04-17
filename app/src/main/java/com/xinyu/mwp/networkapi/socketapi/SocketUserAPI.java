package com.xinyu.mwp.networkapi.socketapi;

import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.constant.SocketAPIConstant;
import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.entity.LoginVerifyCodeEntry;
import com.xinyu.mwp.entity.RegisterReturnEntity;
import com.xinyu.mwp.entity.RegisterVerifyCodeEntry;
import com.xinyu.mwp.entity.UserinfoEntity;
import com.xinyu.mwp.entity.VerifyCodeReturnEntry;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.UserAPI;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketUserAPI extends SocketBaseAPI implements UserAPI {
    @Override
    public void login(String phone, String password, String deviceId, OnAPIListener<LoginReturnEntity> listener) {
        LogUtil.d("开始登录");
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", password);
        map.put("source", 2);
        map.put("deviceId",  MyApplication.getApplication().getAndroidId());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Login,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, LoginReturnEntity.class, listener);
    }

    @Override
    public void register(String phone, String password, String vCode, long memberId, String agentId, String recommend, OnAPIListener<RegisterReturnEntity> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", password);
        map.put("vCode", vCode);
        map.put("timeStamp", RegisterVerifyCodeEntry.timeStamp);
        map.put("vToken", RegisterVerifyCodeEntry.vToken);
        map.put("memberId", memberId);
        map.put("agentId", agentId);
        map.put("recommend", recommend);
        map.put("deviceId",  MyApplication.getApplication().getAndroidId());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Register,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, RegisterReturnEntity.class, listener);
    }

    @Override
    public void verifyCode(String phone, int verifyType, OnAPIListener<VerifyCodeReturnEntry> listener) {
        LogUtil.d("负责加入网络请求---获取短信验证码--------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        map.put("verifyType", verifyType);  //0-注册 1-登录 2-更新服务（暂用 1）
        map.put("phone", phone);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.VerifyCode,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, VerifyCodeReturnEntry.class, listener);
    }

    @Override
    public void resetDealPwd(String phone, String pwd, String vCode, int type, OnAPIListener<Object> listener) {
        LogUtil.d("修改用户密码-----------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("phone", phone);
        map.put("pwd", pwd);
        map.put("type", type);  //0：登录密码 1：交易密码，提现密码
        map.put("timestamp", LoginVerifyCodeEntry.timestamp);
        map.put("vToken", LoginVerifyCodeEntry.vToken);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.DealPwd,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, VerifyCodeReturnEntry.class, listener);
    }

    @Override
    public void test(int testID, OnAPIListener<Object> listener) {
        LogUtil.d("心跳包-----------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", testID);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Test,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, VerifyCodeReturnEntry.class, listener);
    }

    @Override
    public void loginWithToken(OnAPIListener<LoginReturnEntity> listener) {
        LogUtil.d("用token登录");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Token,
                SocketAPIConstant.ReqeutType.User, map);
        requestEntity(socketDataPacket, LoginReturnEntity.class, listener);
    }

    @Override
    public void balance(OnAPIListener<BalanceInfoEntity> listener) {
        LogUtil.d("请求余额信息");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", NetworkAPIFactoryImpl.getConfig().getUserId());
        map.put("token", NetworkAPIFactoryImpl.getConfig().getUserToken());
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Balance,
                SocketAPIConstant.ReqeutType.Verify, map);
        requestEntity(socketDataPacket, BalanceInfoEntity.class, listener);
    }
}
