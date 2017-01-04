package com.xinyu.mwp.utils;

import com.xinyu.mwp.bean.RealmBaseBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Don on 2016/12/28.
 * Describe ${TODO}
 * Modified ${TODO}
 */
public class RealmHelper {
    public static final String DB_NAME = "wpanRealm.realm";
    private Realm mRealm;
    private Class<? extends RealmBaseBean> clazz;

    public RealmHelper(Class<? extends RealmBaseBean> clazz) {
        this.clazz = clazz;
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * add （增）
     */
    public void addT(RealmBaseBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }

    /**
     * delete （删）
     */
    public void deleteT(String id) {
        RealmBaseBean bean = mRealm.where(clazz).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        bean.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * update （改）
     */
    public void updateT(String id, String newName) {
        RealmBaseBean bean = mRealm.where(clazz).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        bean.setName(newName);
        mRealm.commitTransaction();
    }

    /**
     * query （查询所有）
     */
    public List<? extends RealmBaseBean> queryAllT() {
        RealmResults<? extends RealmBaseBean> objects = mRealm.where(clazz).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        objects = objects.sort("id");
        //降序排列
        //objects = objects.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(objects);
    }

    /**
     * query （根据Id（主键）查）
     */
    public RealmBaseBean queryTById(String id) {
        RealmBaseBean bean = mRealm.where(clazz).equalTo("id", id).findFirst();
        return bean;
    }

    public boolean isTExist(String id) {
        RealmBaseBean bean = mRealm.where(clazz).equalTo("id", id).findFirst();
        if (bean == null) {
            return false;
        } else {
            return true;
        }
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void close() {
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
