package com.xinyu.mwp.fragment;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.MainFragmentActivity;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.IndexItemEntity;
import com.xinyu.mwp.fragment.base.BaseControllerFragment;
import com.xinyu.mwp.fragment.base.BaseRefreshFragment;
import com.xinyu.mwp.listener.OnRefreshListener;
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

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView() {
        super.initView();
        titleText.setText("微盘");
        leftImage.setImageResource(R.mipmap.icon_head);

        bannerView.centerDot();
        bannerView.setRefreshLayout(refreshFrameLayout);
        bannerView.update(TestDataUtil.getIndexBanners(3));
        ImageLoader.getInstance().displayImage(ImageUtil.getRandomUrl(), bottomImageView, DisplayImageOptionsUtil.getInstance().getBannerOptions());
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemLayout.removeAllViews();
                        for (int i = 0; i < 3; i++) {
                            IndexItemEntity indexItemEntity = new IndexItemEntity();
                            if (i == 0) {
                                indexItemEntity.setTitle("上海-法兰克福");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("-0.47%");
                                indexItemEntity.setPriceChange("0.5659");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            } else if (i == 1) {
                                indexItemEntity.setTitle("上海-纽约");
                                indexItemEntity.setPriceChange("0.1235");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("+0.47%");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            } else {
                                indexItemEntity.setTitle("上海-东京");
                                indexItemEntity.setPriceChange("0.5454");
                                indexItemEntity.setPrice("3780");
                                indexItemEntity.setPercent("-0.47%");
                                indexItemEntity.setHighPrice("3900");
                                indexItemEntity.setLowPrice("3650");
                                indexItemEntity.setTodayPrice("3755");
                                indexItemEntity.setYesterdayPrice("3600");
                            }

                            IndexItemView indexItemView = new IndexItemView(context);
                            indexItemView.update(indexItemEntity);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(96, context));
                            params.topMargin = DisplayUtil.dip2px(1, context);
                            itemLayout.addView(indexItemView, params);
                        }
                        getRefreshController().refreshComplete();
                    }
                }, 500);
            }
        });
    }

    @Event(value = R.id.followView)
    private void click(View v) {
        //跟单,需要判断被点击的是买涨,买跌,然后跟单
        showDialog(Constant.TYPE_BUY_MINUS);
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
}
