package com.xinyu.mwp.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.BalanceInfoEntity;
import com.xinyu.mwp.entity.TotalDealInfoEntity;
import com.xinyu.mwp.fragment.base.BaseControllerFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.LogUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/10.
 */

public class LeftFragment extends BaseControllerFragment {
    @ViewInject(R.id.unLoginLayout)
    private View unLoginLayout;
    @ViewInject(R.id.user_balance)
    private TextView balance;
    @ViewInject(R.id.user_balance_text)
    private TextView userBalanceText;
    @ViewInject(R.id.logout)
    private Button logout;
    @ViewInject(R.id.iv_member)
    private ImageView memberIcon;

    private LeftClickListener leftClickListener;

    @Override
    protected int getLayoutID() {
//        return R.layout.fragment_left;
        return R.layout.fragment_left_new;
    }


    @Event(value = {R.id.login, R.id.register, R.id.user_balance
            , R.id.myCashOut, R.id.myRecharge, R.id.dealDetail, R.id.logout})
    private void click(View v) {
        if (null != leftClickListener) {
            leftClickListener.click(v, v.getId(), null);
        }
    }

    public void update() {
        balance.setText(UserManager.getInstance().getUserEntity().getBalance() + "");
    }

    public void userUpdate() {
        requestUserBalance();//请求余额信息
//        requestUserDealInfo();//请求总单数,总手数
    }

    private void requestUserDealInfo() {
        NetworkAPIFactoryImpl.getDealAPI().totalDealInfo(new OnAPIListener<TotalDealInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(TotalDealInfoEntity totalDealInfoEntities) {
                LogUtil.d("交易总概览请求数据成功:" + totalDealInfoEntities.toString());
            }
        });
    }

    private void requestUserBalance() {
        NetworkAPIFactoryImpl.getUserAPI().balance(new OnAPIListener<BalanceInfoEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("余额请求失败");
            }

            @Override
            public void onSuccess(BalanceInfoEntity balanceInfoEntity) {
                LogUtil.d("余额请求信息成功:" + balanceInfoEntity.toString());
                UserManager.getInstance().getUserEntity().setBalance(balanceInfoEntity.getBalance());
                update();
            }
        });
    }

    public void setLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public interface LeftClickListener {
        void click(View v, int action, Object obj);
    }

    @Override
    protected void initView() {
        super.initView();
        LogUtil.d("左边的fragment初始化了");

        initLoginLayout(true);
//        initLoginLayout(true);
//        userUpdate();
    }

    private void initLoginLayout(boolean isLogin) {
        if (isLogin) {
            userBalanceText.setVisibility(View.VISIBLE);
            unLoginLayout.setVisibility(View.GONE);
            balance.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);

        } else {
            userBalanceText.setVisibility(View.INVISIBLE);
            unLoginLayout.setVisibility(View.VISIBLE);
            balance.setVisibility(View.GONE);
            logout.setVisibility(View.INVISIBLE);
        }
        if (UserManager.getInstance().getUserEntity() != null) {
            if (UserManager.getInstance().getUserEntity().getUserType() == 0) {
                memberIcon.setVisibility(View.INVISIBLE);
            } else {
                memberIcon.setVisibility(View.VISIBLE);
            }
        }
    }
}
