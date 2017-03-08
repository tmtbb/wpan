package com.xinyu.mwp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.unionpay.tsmservice.request.GetTransRecordRequestParams;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseMultiFragmentActivity;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.entity.LoginReturnEntity;
import com.xinyu.mwp.fragment.DealFragment;
import com.xinyu.mwp.fragment.IndexFragment;
import com.xinyu.mwp.fragment.LeftFragment;
import com.xinyu.mwp.fragment.ShareOrderExpectFragment;
import com.xinyu.mwp.fragment.ShareOrderFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.user.OnUserUpdateListener;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.ActivityUtil;
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
        // fragments.add(new ShareOrderFragment());  //隐藏晒单界面
        fragments.add(new ShareOrderExpectFragment());
        leftFragment = new LeftFragment();


        pushFragmentToContainer(R.id.leftContainer, leftFragment);

        pushFragmentToBackStack(0);
    }

    @Override
    public void pushFragmentToBackStack(int selectIndex) {
        super.pushFragmentToBackStack(selectIndex);
        bottombar.getChildAt(selectIndex).setSelected(true);
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
        leftFragment.setLeftClickListener(new LeftFragment.LeftClickListener() {
            @Override
            public void click(View v, int action, Object obj) {
                toggleDrawer(false);
                switch (action) {
//                    case R.id.icon:
//                        next(UserSettingActivity.class);
//                        break;
                    case R.id.login:
                        ActivityUtil.nextLogin(context);
                        break;
                    case R.id.register:
                        ActivityUtil.nextRegister(context);
                        break;
//                    case R.id.user_balance:
                    //  next(UserAssetsActivity.class);
//                        break;
//                    case R.id.myScoreLayout:
//                        break;
//                    case R.id.myAttention:
//                        next(MyAttentionActivity.class);
//                        break;
//                    case R.id.myPushOrder:
//                        next(MyPushOrderActivity.class);
//                        break;
//                    case R.id.myShareOrder:
//                        next(MyShareOrderActivity.class);
//                        break;
                    case R.id.dealDetail:
                        if (UserManager.getInstance().isLogin()) {
                            next(DealDetailFragmentActivity.class);
                        } else {
                            ToastUtils.show(context, "请先登录");
                            ActivityUtil.nextLogin(context);
                        }
                        break;
//                    case R.id.feedback:
//                        next(RechargeRecordActivity.class);
//                        break;
//                    case R.id.score:
//                        next(CheckPhoneNumberActivity.class);
//                        break;
//                    case R.id.about:
//                        next(AddBankInfoActivity.class);
//                        break;
                    case R.id.myCashOut:
                        if (UserManager.getInstance().isLogin()) {
                            ToastUtils.show(context, "提现");
                            next(CashActivity.class);
                        } else {
                            ToastUtils.show(context, "请先登录");
                            ActivityUtil.nextLogin(context);
                        }
                        break;
                    case R.id.myRecharge:
                        if (UserManager.getInstance().isLogin()) {
                            ToastUtils.show(context, "充值");
                            next(RechargeActivity.class);
                        } else {
                            ToastUtils.show(context, "请先登录");
                            ActivityUtil.nextLogin(context);
                        }
                        break;
                    case R.id.logout:
                        UserManager.getInstance().logout();
                        ToastUtils.show(context, "退出登录");
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
        SocketAPINettyBootstrap.getInstance().setOnConnectListener(new SocketAPINettyBootstrap.OnConnectListener() {
            @Override
            public void onExist() {
            }

            @Override
            public void onSuccess() {
                judgeIsLogin();
            }

            @Override
            public void onFailure() {
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

    @Override
    protected void onResume() {
        super.onResume();
        if (UserManager.getInstance().isLogin()) {
            leftFragment.userUpdate(true);
        }
    }

    private void judgeIsLogin() {
        LogUtil.d("校验token登录是否失效");
        //判断是否登录
        if (UserManager.getInstance().isLogin()) {
            //利用token登录
            String phone = UserManager.getInstance().getUserEntity().getName();
            String token = UserManager.getInstance().getUserEntity().getToken();

            NetworkAPIFactoryImpl.getUserAPI().loginWithToken(phone, token, new OnAPIListener<LoginReturnEntity>() {
                @Override
                public void onError(Throwable ex) {
                    ex.printStackTrace();
                    LogUtil.d("登录失败.token已经失效");
                    UserManager.getInstance().logout();
                    next(LoginActivity.class);
                }

                @Override
                public void onSuccess(LoginReturnEntity loginReturnEntity) {
                    LogUtil.d("登陆成功,token登录用户信息登陆成功" + loginReturnEntity.toString());
                }
            });
        }
    }
}
