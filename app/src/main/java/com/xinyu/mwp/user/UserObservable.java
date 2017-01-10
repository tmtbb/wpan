package com.xinyu.mwp.user;

import java.util.ArrayList;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-08-22 22:21
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public abstract class UserObservable {

    protected ArrayList<OnUserUpdateListener> mObservers = new ArrayList<OnUserUpdateListener>();

    public void registerUserUpdateListener(OnUserUpdateListener observer) {
        mObservers.add(observer);
    }


    public void unregisterUserUpdateListener(OnUserUpdateListener observer) {
        //mObservers.remove(observer);
        int i = mObservers.indexOf(observer);
        if (i >= 0) {
            mObservers.remove(i);
        }
    }

    public void notifyObservers(boolean isLogin) {
        for (OnUserUpdateListener observer : mObservers) {
            observer.onUserUpdate(isLogin);
        }
    }
}
