package com.xinyu.mwp.activity;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.fragment.DealFragment;
import com.xinyu.mwp.fragment.IndexFragment;
import com.xinyu.mwp.fragment.LeftFragment;
import com.xinyu.mwp.fragment.ShareOrderFragment;
import com.xinyu.mwp.user.OnUserUpdateListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by Benjamin
 * @email : samwong.greatstone@gmail.com
 */
public class MainFragmentActivity extends BaseMultiFragmentActivity implements OnUserUpdateListener {
    private static final String[] TITLES = {"首页", "交易", "晒单"};

    @ViewInject(R.id.drawer)
    protected DrawerLayout drawer;
    @ViewInject(R.id.bottomLayout)
    private LinearLayout bottombar;
    @ViewInject(R.id.title)
    private TextView title;
    protected LeftFragment leftFragment;
    private long exitNow;
    private long first = 0;

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

        fragments.add(new IndexFragment());
        fragments.add(new DealFragment());
        fragments.add(new ShareOrderFragment());

        leftFragment = new LeftFragment();
        pushFragmentToContainer(R.id.leftContainer, leftFragment);
        pushFragmentToBackStack(0);

    }

    @Override
    public void pushFragmentToBackStack(int selectIndex) {
        super.pushFragmentToBackStack(selectIndex);
        bottombar.getChildAt(selectIndex).setSelected(true);
        toggleTitleBar();
    }

    @Override
    protected void initView() {
        super.initView();
        UserManager.getInstance().registerUserUpdateListener(this);
        setSwipeBackEnable(false);
    }

    @Event(value = {R.id.icon, R.id.login, R.id.register
            , R.id.myAttention, R.id.myPushOrder, R.id.myShareOrder, R.id.dealDetail
            , R.id.feedback, R.id.score, R.id.about})
    @Override
    protected void initListener() {
        super.initListener();

        leftFragment.setLeftClickListener(new LeftFragment.LeftClickListener() {
            @Override
            public void click(View v, int action, Object obj) {
                toggleDrawer(false);
                ToastUtils.show(context, action + "");
                switch (action) {
                    case R.id.icon:
                        break;
                    case R.id.login:
                        break;
                    case R.id.register:
                        break;
                    case R.id.myAttention:
                        next(MyAttentionActivity.class);
                        break;
                    case R.id.myPushOrder:
                        break;
                    case R.id.myShareOrder:
                        break;
                    case R.id.dealDetail:
                        break;
                    case R.id.feedback:
                        break;
                    case R.id.score:
                        break;
                    case R.id.about:
                        break;
                }
            }
        });

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
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

    public void postNext(final Class clazz) {
        drawer.closeDrawers();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next(clazz);
            }
        }, 300);
    }

    private void toggleTitleBar() {
        title.setText(TITLES[selectIndex]);
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

    public void onClickSelect(View view) {
        if (System.currentTimeMillis() - first > 350) {
            if (selectIndex >= 0) {
                bottombar.getChildAt(selectIndex).setSelected(false);
            }
            try {
                for (int i = 0; i < bottombar.getChildCount(); ++i) {
                    View childView = bottombar.getChildAt(i);
                    if (view == childView) {
                        pushFragmentToBackStack(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        first = System.currentTimeMillis();
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
        LogUtil.e("test");
    }
}
