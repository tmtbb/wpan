package com.xinyu.mwp.activity.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.xinyu.mwp.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-06-17 10:55
 * @describe : BaseFragmentActivity
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseMultiFragmentActivity extends BaseFragmentActivity {

    public int selectIndex = -1;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    @IdRes
    public abstract int getFragmentContainerId();

    public abstract void createFragmentsToBackStack();

    @Override
    protected void initView() {
        super.initView();
        createFragmentsToBackStack();
    }

    public void pushFragmentToBackStack(int selectIndex) {
        try {
            FragmentTransaction addTransaction = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments() == null || (fragments.size() > selectIndex && !fragmentManager.getFragments().contains(fragments.get(selectIndex)))) {
                addTransaction.add(getFragmentContainerId(), fragments.get(selectIndex), String.format("fragment_%s", selectIndex));
                addTransaction.commitAllowingStateLoss();
            }
            showFragmentIndex(selectIndex);
        }catch (Exception e){

        }
    }

    private void showFragmentIndex(int selectIndex) {
        if (fragments != null && fragments.size() > selectIndex) {
            if (this.selectIndex != selectIndex) {
                this.selectIndex = selectIndex;
                FragmentTransaction showTranscation = fragmentManager.beginTransaction();
                for (Fragment fragment : fragments) {
                    showTranscation.hide(fragment);
                }
                showTranscation.show(fragments.get(selectIndex));
                showTranscation.commitAllowingStateLoss();
            }
        }
    }

    protected void pushFragmentToContainer(int resId, BaseFragment fragment) {
        if (resId > 0 && fragment != null) {
            FragmentTransaction addTransaction = fragmentManager.beginTransaction();
            addTransaction.add(resId, fragment);
            addTransaction.commitAllowingStateLoss();
        }
    }


}
