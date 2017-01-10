package com.xinyu.mwp.activity;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.fragment.LeftFragment;
import com.xinyu.mwp.user.OnUserUpdateListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.DisplayUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by Benjamin
 * @email : samwong.greatstone@gmail.com
 * @date : 2016/1/8
 */
@Deprecated
public class MainFragmentActivity extends BaseMultiFragmentActivity implements OnUserUpdateListener {


    @ViewInject(R.id.drawer)
    protected DrawerLayout drawer;
    protected LeftFragment leftFragment;
    private long exitNow;


    @Override
    public int getFragmentContainerId() {
        return R.id.contentContainer;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_mainfragment;
    }

    @Override
    public void createFragmentsToBackStack() {

//        fragments.add(new HomeFragment());
//        fragments.add(new SociatyFragment());
//        fragments.add(new GiftFragment());

        leftFragment = new LeftFragment();
        pushFragmentToContainer(R.id.leftContainer, leftFragment);
        pushFragmentToBackStack(0);

    }

    @Override
    protected void initView() {
        super.initView();
        UserManager.getInstance().registerUserUpdateListener(this);
        setSwipeBackEnable(false);
    }

    @Override
    protected void initListener() {
        super.initListener();

//        leftFragment.setListener(new OnItemChildViewClickListener() {
//            @Override
//            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
//                toggleDrawer(false);
//            }
//        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drawer.getChildAt(0);
                ViewCompat.setTranslationX(mContent, DisplayUtil.dip2px(242, MainFragmentActivity.this) * slideOffset);
                mContent.invalidate();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void doLeftHeader(Object obj) {
    }

    public void postNext(final Class clazz) {
        drawer.closeDrawers();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next(clazz);
            }
        }, 300);
    }

    public void doLeftList(int leftPosition) {
        doLeftList(leftPosition, -1);
    }

    public void doLeftList(final int leftPosition, final int innerPosition) {
    }

    private void doLeftFooter() {
    }

    public void toggleDrawer(boolean open) {
        if (open) {
            drawer.openDrawer(Gravity.LEFT);
        } else {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onUserUpdate(boolean isLogin) {
        leftFragment.userUpdate(isLogin);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)) {
            if ((System.currentTimeMillis() - exitNow) > 2000) {
                Toast.makeText(this, String.format(getString(R.string.confirm_exit_app), getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
                exitNow = System.currentTimeMillis();
            } else if ((System.currentTimeMillis() - exitNow) > 0) {
                MyApplication.getApplication().exitApp(this);
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserManager.getInstance().unregisterUserUpdateListener(this);
    }
}
