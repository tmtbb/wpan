package com.xinyu.mwp.user;

import android.content.Context;
import android.text.TextUtils;

import com.xinyu.mwp.entity.UserEntity;
import com.xinyu.mwp.util.JSONEntityUtil;
import com.xinyu.mwp.util.SPUtils;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-08-22 22:14
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class UserManager extends UserObservable {

    private static final String USER_ENTITY = "user_entity";
    public static UserManager sInstance;
    private UserEntity userEntity;
    private boolean login = false;
    private Context context;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                if (sInstance == null) {
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    private void setUserEntity(UserEntity userEntity) {
        setUserEntity(userEntity, true);
    }

    private void setUserEntity(UserEntity userEntity, boolean isNotify) {
        this.userEntity = userEntity;
        if (isNotify)
            notifyObservers(login);
    }

    public void saveUserEntity(UserEntity userEntity) {
        saveUserEntity(userEntity, true);
    }

    /**
     * 控制是否需要刷新
     * @param userEntity
     * @param isNotify
     */
    public void saveUserEntity(UserEntity userEntity, boolean isNotify) {
        login = true;
        setUserEntity(userEntity, isNotify);
        SPUtils.putString(USER_ENTITY, JSONEntityUtil.EntityToJSON(userEntity).toString());
    }

    public void logout() {
        login = false;
        setUserEntity(null);
        SPUtils.putString(USER_ENTITY, "");
    }

    public void initUser(Context context) {
        this.context = context;
        String result = SPUtils.getString(USER_ENTITY, "");
        login = false;
        if (!TextUtils.isEmpty(result)) {
            userEntity = JSONEntityUtil.JSONToEntity(UserEntity.class, result);
            login = true;
            setUserEntity(userEntity);
        }
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isSelf(String userId) {
        return login && String.valueOf(userEntity.getId()).equals(userId);
    }

    public boolean isSelf(int userId) {
        return isSelf(String.valueOf(userId));
    }

    private Object readResolve() {
        return sInstance;
    }
}
