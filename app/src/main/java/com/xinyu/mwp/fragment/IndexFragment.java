package com.xinyu.mwp.fragment;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.ImageUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;
import com.xinyu.mwp.view.IndexItemView;
import com.xinyu.mwp.view.banner.IndexBannerView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Benjamin on 17/1/10.
 */

public class IndexFragment extends BaseRefreshFragment {

    @ViewInject(R.id.refreshFrameLayout)
    private PtrFrameLayout refreshFrameLayout;
    @ViewInject(R.id.bannerView)
    private IndexBannerView bannerView;
    @ViewInject(R.id.itemLayout)
    private LinearLayout itemLayout;
    @ViewInject(R.id.bottomImageView)
    private ImageView bottomImageView;
    @ViewInject(R.id.leftImage)
    private ImageView leftImage;
    @ViewInject(R.id.titleText)
    private TextView titleText;
    //    @ViewInject(R.id.marqueeView)
//    private MarqueeView marqueeView;
    @ViewInject(R.id.container)
    private RelativeLayout tittleBar;
    private List<ProductEntity> symbolProductList;
    private HashMap<String, String> symbolMap;
    private List<ProductEntity> productList;  //商品列表集合--大集合
    private List<CurrentPriceReturnEntity> entitys;
    @ViewInject(R.id.rl_error_message)
    private RelativeLayout errorMessage;
    private static final int REQUEST_ERROR = 0;
    private static final int REQUEST_SUCCESS = 1;
    private int requestState = REQUEST_ERROR;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_index;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reuqestData();
            handler.postDelayed(this, 3000);
        }
    };

    //设置网络状态的监听
    public interface OnRequestDataStateListener {
        void onError();

        void onSuccess();
    }

    private OnRequestDataStateListener onRequestDataStateListener;

    public void setOnRequestDataStateListener(OnRequestDataStateListener onRequestDataStateListener) {
        this.onRequestDataStateListener = onRequestDataStateListener;
    }

    @Override
    protected void initView() {
        super.initView();
        initStatusBar();
        titleText.setText("微盘");
        leftImage.setImageResource(R.mipmap.icon_head);
        reuqestData();  //商品列表
        isShow = true;
        bannerView.centerDot();
        bannerView.setRefreshLayout(refreshFrameLayout);
        bannerView.update(TestDataUtil.getIndexBannersInfo());
        ImageLoader.getInstance().displayImage(ImageUtil.getRandomUrl(), bottomImageView, DisplayImageOptionsUtil.getInstance().getBannerOptions());
        bottomImageView.setVisibility(View.GONE);
        // initMarqueeView();  //今日头滚动效果
        showLoader();
        processErrorMessage();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("onResume执行方法,sss此时的fragment显示状态是:" + isShow);
        //如果当前是显示状态的话,执行;如果当前是隐藏状态的话,不执行了
        if (isShow) {
            handler.postDelayed(runnable, 3000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("onPause方法执行,关掉网络请求sss");
        handler.removeCallbacks(runnable);
    }

    //初始化的时候 isTrue
    private static boolean isShow = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LogUtil.d("界面被隐藏了sss");
            isShow = false;
            handler.removeCallbacks(runnable);
        } else {
            LogUtil.d("界面显示了sss");
            isShow = true;
            handler.postDelayed(runnable, 3000);
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        LogUtil.d("此时的界面是fou可见的dddddddddddsssssssss:"+isVisibleToUser);
//         super.setUserVisibleHint(isVisibleToUser);
//        LogUtil.d("此时的界面是fou可见的sssssssss:"+isVisibleToUser);
//        if (isVisibleToUser){
//            LogUtil.d("此时的界面是fou可见的sssssssss:"+isVisibleToUser);
//            if (UserManager.getInstance().isLogin()){
//                handler.postDelayed(runnable, 3000);
//            }else{
//                handler.removeCallbacks(runnable);
//            }
//        }else{
//            LogUtil.d("此时的界面是fou可见的sssssssss:"+isVisibleToUser);
//        }
//
//    }

    /**
     * 网络请求商品列表数据
     */
    private void reuqestData() {
        LogUtil.d("网络的状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
        NetworkAPIFactoryImpl.getDealAPI().products(new OnAPIListener<List<ProductEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                if (onRequestDataStateListener != null) {
                    onRequestDataStateListener.onError();
                }
            }

            @Override
            public void onSuccess(List<ProductEntity> productEntities) {
                productList = productEntities;
                if (onRequestDataStateListener != null) {
                    onRequestDataStateListener.onSuccess();
                }
                processData();
            }
        });
    }

    private void processData() {
        HashSet<String> setList = new HashSet<>();
        symbolProductList = new ArrayList<>();
        for (ProductEntity productEntity : productList) {
            if (setList.add(productEntity.getShowSymbol())) {
                symbolProductList.add(productEntity);
            }
        }
        symbolMap = new HashMap();
        for (ProductEntity productEntity : symbolProductList) {
            symbolMap.put(productEntity.getSymbol(), productEntity.getShowSymbol());
        }
        initProductPrice();//请求报价
    }

    private ArrayList<SymbolInfosEntity> symbolInfos = new ArrayList<>();

    //请求商品报价数据
    private void initProductPrice() {
        LogUtil.d("请求报价");
        if (symbolInfos.size() > 0) {
            symbolInfos.clear();
        }
        for (ProductEntity productEntity : symbolProductList) {
            SymbolInfosEntity entity = new SymbolInfosEntity();
            entity.setAType(4);
            entity.setSymbol(productEntity.getSymbol());
            entity.setPlatformName(productEntity.getPlatformName());
            entity.setExchangeName(productEntity.getExchangeName());
            symbolInfos.add(entity);
        }
        NetworkAPIFactoryImpl.getDealAPI().currentPrice(symbolInfos, new OnAPIListener<List<CurrentPriceReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<CurrentPriceReturnEntity> currentPriceReturnEntities) {
                entitys = currentPriceReturnEntities;
                doRefresh();

            }
        });
    }

//头条滚动效果
//    private void initMarqueeView() {
//        List<CharSequence> list = new ArrayList<>();
//        SpannableString ss1 = new SpannableString("用户001买涨 【上海-东京】赚999999999999999元");
//        ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.default_red)), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        list.add(ss1);
//        SpannableString ss2 = new SpannableString("用户002买跌 【上海-东京】赚1元");
//        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.default_green)), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        list.add(ss2);
//        SpannableString ss3 = new SpannableString("用户003买涨 【上海-东京】赚100000000元");
//        ss3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.default_red)), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        list.add(ss3);
//        SpannableString ss4 = new SpannableString("用户014买跌 【上海-东京】赚9999999999999999999999999999元");
//        ss4.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.default_green)), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        list.add(ss4);
//        marqueeView.startWithList(list);
//        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, TextView textView) {
//                ToastUtils.show(context, textView.getText() + "");
//            }
//        });
//    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });

        setOnRequestDataStateListener(new OnRequestDataStateListener() {
            @Override
            public void onError() {
                LogUtil.d("请求数据错误");
                if (requestState != REQUEST_ERROR) {
                    requestState = REQUEST_ERROR;
                    processErrorMessage();
                }
            }

            @Override
            public void onSuccess() {
                LogUtil.d("请求数据成功");
                if (requestState != REQUEST_SUCCESS) {
                    requestState = REQUEST_SUCCESS;
                    processErrorMessage();
                }
            }
        });
    }

    private void processErrorMessage() {
        if (requestState == REQUEST_ERROR) {
            LogUtil.d("数据为空,展示错误界面");
            errorMessage.setVisibility(View.VISIBLE);
            bannerView.setVisibility(View.INVISIBLE);
        } else {
            LogUtil.d("数据不为空,展示正常的界面");
            errorMessage.setVisibility(View.GONE);
            bannerView.setVisibility(View.VISIBLE);
        }
    }

    private void doRefresh() {
        itemLayout.removeAllViews();
        if (entitys == null) {
            return;
        }
        for (CurrentPriceReturnEntity entity : entitys) {
            if (symbolMap.containsKey(entity.getSymbol())) {
                entity.setShowSymbol(symbolMap.get(entity.getSymbol()));
            }
            IndexItemView indexItemView = new IndexItemView(context);
            indexItemView.update(entity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(96, context));
            params.topMargin = DisplayUtil.dip2px(1, context);
            itemLayout.addView(indexItemView, params);
        }
        getRefreshController().refreshComplete();
    }

    @Event(value = {R.id.followView, R.id.leftImage})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.leftImage:
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer);
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.followView:
                //跟单,判断是否登录,需要判断被点击的是买涨,买跌,然后跟单
                showDialog(Constant.TYPE_BUY_MINUS);
                break;
            default:
                break;
        }
    }

    private void showDialog(int type) {
        String buyType = null;
        if (type == Constant.TYPE_BUY_MINUS) {
            buyType = "买跌";
        } else if (type == Constant.TYPE_BUY_PLUS) {
            buyType = "买涨";
        }
        CustomDialog.Builder builder = new CustomDialog.Builder(context, type);
        builder.setPositiveButton(buyType, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //买涨点击后操作
                ToastUtils.show(context, "买跌");
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //取消点击后操作
                        ToastUtils.show(context, "取消");
                    }
                });
        builder.create().show();
    }

    @Override
    public void initStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(getActivity(),
                (DrawerLayout) getActivity().findViewById(R.id.drawer),
                getResources().getColor(R.color.default_main_color), 0);
    }
}
