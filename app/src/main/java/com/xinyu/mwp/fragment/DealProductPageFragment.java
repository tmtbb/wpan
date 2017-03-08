package com.xinyu.mwp.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.PositionHistoryActivity;
import com.xinyu.mwp.activity.RechargeActivity;
import com.xinyu.mwp.activity.UserAssetsActivity;
import com.xinyu.mwp.adapter.DealProductPageAdapter;
import com.xinyu.mwp.adapter.GalleryAdapter;
import com.xinyu.mwp.adapter.LoopPagerAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.OpenPositionReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPINettyBootstrap;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;
import com.xinyu.mwp.view.MyTransformation;

import org.xutils.view.annotation.ViewInject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


/**
 * @describe : 交易界面 白银/原油/咖啡 详情
 */
public class DealProductPageFragment extends BaseRefreshAbsListControllerFragment<CurrentPositionListReturnEntity> implements View.OnClickListener {
    private DealProductPageAdapter adapter;

    private RadioGroup radioGroupChart;
    private FrameLayout mChartContainer;

    private ListView listView;
    private ViewPager mViewPager;
    private RelativeLayout mViewPagerContainer;
    private int halfScreenWidth;

    private LoopPagerAdapter loopPagerAdapter;
    private List<ProductEntity> mUnitViewList = new ArrayList<>();
    private TextView currentPrice;
    private TextView changePercent;
    private TextView changePrice;
    private TextView highPrice;
    private TextView lowPrice;
    private TextView openingTodayPrice;
    private TextView closedYesterdayPrice;
    private List<CurrentPriceReturnEntity> entitys;
    private ArrayList<SymbolInfosEntity> symbolInfos = new ArrayList<>();
    ;  //商品信息集合
    private List<CurrentPositionListReturnEntity> currentPositionList; //仓位列表集合

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LogUtil.d("每3秒执行一次");
            reuqestData();  //商品列表
            handler.postDelayed(this, 3000);
        }
    };
    private List<String> mTitleList;
    private GalleryAdapter mAdapter;
    private List<ProductEntity> productList;  //商品列表集合--大集合
    private List<List<ProductEntity>> products; //集合的集合
    private RecyclerView mRecyclerView;
    private boolean isRequestFail = true;
    private List<CurrentTimeLineReturnEntity> currentTimeLineEntities; //当前分时数据
    private KChartFragment kChartFragment;

    public DealProductPageFragment() {
    }

    private CurrentPriceReturnEntity priceReturnEntity;//价格信息

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected IListAdapter<CurrentPositionListReturnEntity> createAdapter() {
        return adapter = new DealProductPageAdapter(context);
    }

    /**
     * 网络请求商品列表数据
     */
    private void reuqestData() {
        LogUtil.d("网络的状态是:" + SocketAPINettyBootstrap.getInstance().isOpen());
        NetworkAPIFactoryImpl.getDealAPI().products(new OnAPIListener<List<ProductEntity>>() {
            @Override
            public void onError(Throwable ex) {
                isRequestFail = true;
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(List<ProductEntity> productEntities) {
                isRequestFail = false;
                if (productList != null) {
                    productList.clear();
                }
                productList = productEntities;
                LogUtil.d("商品列表--请求成功的信息是:" + productEntities.toString());
                processData();
            }
        });
    }

    private void processData() {
        mTitleList = new ArrayList<>();
        HashSet<String> setList = new HashSet<>();
        for (ProductEntity productEntity : productList) {
            setList.add(productEntity.getShowSymbol());
        }
        List<ProductEntity> newProducts = null;
        products = new ArrayList<List<ProductEntity>>();
        for (String s : setList) {
            newProducts = new ArrayList<>();
            for (ProductEntity productEntity : productList) {
                if (productEntity.getShowSymbol().equals(s)) {
                    newProducts.add(productEntity);
                }
            }
            products.add(newProducts);
            mTitleList.add(s);
        }
        //加载标题
        initProductName();
    }

    private void initProductName() {
        if (mTitleList == null) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            mAdapter = new GalleryAdapter(context, mTitleList);
            mRecyclerView.setAdapter(mAdapter);
        }
        initProductPrice();//请求报价
        mAdapter.setOnItemClick(new GalleryAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                mUnitViewList = products.get(position); //通多点击的位置,获取不同unit的集合
                LogUtil.d("点击的位置是:" + position + "当前获取的vp集合长度:" + mUnitViewList.size());
                ToastUtils.show(context, mTitleList.get(position) + "刷新数据");
                mAdapter.setPosition(position);
                mAdapter.notifyDataSetChanged();
                initProductPrice();  //点击后请求报价
            }

            @Override
            public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                return false;
            }
        });

    }

    @Override
    protected void initView() {
        super.initView();
        reuqestData();  //商品列表
        handler.postDelayed(runnable, 3000);//每两秒执行一次runnable.
        initCurrentPositionList();  //请求仓位列表数据
        initHeadView();  //初始化头布局
    }

    /**
     * 请求当前仓位列表
     */
    private void initCurrentPositionList() {
        NetworkAPIFactoryImpl.getDealAPI().currentPositionList(new OnAPIListener<List<CurrentPositionListReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("仓位列表,请求网络失败" + ex.getMessage());
                currentPositionList = new ArrayList<CurrentPositionListReturnEntity>();
                processPositionList();
            }

            @Override
            public void onSuccess(List<CurrentPositionListReturnEntity> currentPositionListReturnEntities) {
                LogUtil.d("仓位列表,请求网络成功" + currentPositionListReturnEntities.toString());
                if (currentPositionList != null) {
                    currentPositionList.clear();
                }
                currentPositionList = currentPositionListReturnEntities;
                processPositionList();
            }
        });
    }

    /**
     * 仓位列表数据显示
     */
    private void processPositionList() {
        adapter.setList(currentPositionList);
        adapter.notifyDataSetChanged();
    }

    private void initHeadView() {
        View headView = LayoutInflater.from(context).inflate(R.layout.ly_tab_deal_order_head, null);
        listView = (ListView) getRefreshController().getContentView();
        listView.addHeaderView(headView);
        mRecyclerView = (RecyclerView) headView.findViewById(R.id.rv_title);
        LinearLayout historyRecord = (LinearLayout) headView.findViewById(R.id.ll_history_record);  //持仓盈亏,历史记录
        TextView buyPlus = (TextView) headView.findViewById(R.id.tv_exchange_buy_plus);
        TextView buyMinus = (TextView) headView.findViewById(R.id.tv_exchange_buy_minus);

        radioGroupChart = (RadioGroup) headView.findViewById(R.id.radiogroup_chart);
        mChartContainer = (FrameLayout) headView.findViewById(R.id.chart_container);
        kChartFragment = new KChartFragment(context);
        mChartContainer.addView(kChartFragment);

        currentPrice = (TextView) headView.findViewById(R.id.tv_current_price);
        changePercent = (TextView) headView.findViewById(R.id.tv_change_percent);
        changePrice = (TextView) headView.findViewById(R.id.tv_change_price);
        highPrice = (TextView) headView.findViewById(R.id.tv_high_price);
        lowPrice = (TextView) headView.findViewById(R.id.tv_low_price);
        openingTodayPrice = (TextView) headView.findViewById(R.id.tv_today_price);
        closedYesterdayPrice = (TextView) headView.findViewById(R.id.tv_yesterday_price);

        TextView tvExchangeAssets = (TextView) headView.findViewById(R.id.tv_exchange_assets); //总资产数目
        ImageView ivAssetsAdd = (ImageView) headView.findViewById(R.id.iv_exchange_assets_add);//加号按钮

        RadioButton rbMinHour = (RadioButton) headView.findViewById(R.id.rb_min_hour); //分时线
        RadioButton rbMinHour5 = (RadioButton) headView.findViewById(R.id.rb_min_5); //分时线
        RadioButton rbMinHour15 = (RadioButton) headView.findViewById(R.id.rb_min_15); //15分时线
        RadioButton rbMinHour60 = (RadioButton) headView.findViewById(R.id.rb_min_60); //60分时线
        RadioButton rbMinHourDay = (RadioButton) headView.findViewById(R.id.rb_day_hour); //日时线

        tvExchangeAssets.setOnClickListener(this);
        ivAssetsAdd.setOnClickListener(this);
        buyMinus.setOnClickListener(this);
        buyPlus.setOnClickListener(this);
        rbMinHour.setOnClickListener(this);
        rbMinHour5.setOnClickListener(this);
        rbMinHour15.setOnClickListener(this);
        rbMinHour60.setOnClickListener(this);
        rbMinHourDay.setOnClickListener(this);
        historyRecord.setOnClickListener(this);

        mViewPager = (ViewPager) headView.findViewById(R.id.vp_trade_time_mins);
        mViewPagerContainer = (RelativeLayout) headView.findViewById(R.id.rl_viewPagerContainer);
    }


    //请求商品报价数据
    private void initProductPrice() {
        LogUtil.d("请求报价");


        if (mUnitViewList.size() == 0) {
            mUnitViewList = products.get(0);
        }
        if (symbolInfos.size() > 0) {
            symbolInfos.clear();
        }
        for (ProductEntity productEntity : mUnitViewList) {
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
                LogUtil.d("当前报价======请求网络错误");
            }

            @Override
            public void onSuccess(List<CurrentPriceReturnEntity> currentPriceReturnEntities) {
                LogUtil.d("============当前报价,请求网络成功:" + currentPriceReturnEntities.toString());
                entitys = currentPriceReturnEntities;
                initViewPager();
                processPriceInfo();
//                mViewPager.setCurrentItem(0);//默认在中间

                processTimeLine();//加载分时图
            }
        });
    }

    /**
     * 加载分时数据
     */
    private void processTimeLine() {
        if (!isTimeLine) {
            return;   //如果为false,不用刷新请求数据
        }
        if (symbolInfos.size() == 0) {
            ToastUtils.show(context, "请求数据失败,请检查网络连接");
            return;
        }
        SymbolInfosEntity symbolInfosentity = symbolInfos.get(mViewPager.getCurrentItem());
        int aType = symbolInfosentity.getAType();
        String exchangeName = symbolInfosentity.getExchangeName();
        String platformName = symbolInfosentity.getPlatformName();
        String symbol = symbolInfosentity.getSymbol();
        NetworkAPIFactoryImpl.getDealAPI().timeline(exchangeName, platformName, symbol, aType, new OnAPIListener<List<CurrentTimeLineReturnEntity>>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                LogUtil.d("分时请求网络错误---");
            }

            @Override
            public void onSuccess(List<CurrentTimeLineReturnEntity> currentTimeLineReturnEntities) {
                LogUtil.d("分时请求网络成功" + currentTimeLineReturnEntities.toString());
                currentTimeLineEntities = currentTimeLineReturnEntities;
//                kChartFragment.initChart();
                kChartFragment.loadChartData(currentTimeLineEntities, 0);
            }
        });


    }

    /**
     * 加载价格详情
     */
    private void processPriceInfo() {
        String resultPrice = NumberUtils.halfAdjust4(entitys.get(mViewPager.getCurrentItem()).getCurrentPrice());
        currentPrice.setText(resultPrice);
        String resultChange = NumberUtils.halfAdjust5(entitys.get(mViewPager.getCurrentItem()).getChange());
        changePrice.setText(resultChange);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String resultPercent = numberFormat.format(entitys.get(mViewPager.getCurrentItem()).getChange() / entitys.get(mViewPager.getCurrentItem()).getCurrentPrice() * 100);
        changePercent.setText(resultPercent);
        highPrice.setText(NumberUtils.halfAdjust5(entitys.get(mViewPager.getCurrentItem()).getHighPrice()));
        lowPrice.setText(NumberUtils.halfAdjust5(entitys.get(mViewPager.getCurrentItem()).getLowPrice()));
        openingTodayPrice.setText(NumberUtils.halfAdjust5(entitys.get(mViewPager.getCurrentItem()).getOpeningTodayPrice()));
        closedYesterdayPrice.setText(NumberUtils.halfAdjust5(entitys.get(mViewPager.getCurrentItem()).getClosedYesterdayPrice()));
    }

    private void initViewPager() {
        if (loopPagerAdapter != null) {
            loopPagerAdapter.updateItemsData(mUnitViewList, entitys);
            LogUtil.d("viewpager不为空,刷新mUnitViewList长度是:" + mUnitViewList.size());
        } else {
            halfScreenWidth = DisplayUtil.getScreenWidth(context) / 2;
            loopPagerAdapter = new LoopPagerAdapter(context, mUnitViewList, entitys);
            mViewPager.setPageTransformer(true, new MyTransformation()); //设置切换效果
            mViewPager.setAdapter(loopPagerAdapter);
            mViewPager.setPageMargin(0);   //设置每页之间的左右间隔
        }
        //将容器的触摸事件反馈给ViewPager
        mViewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int ex = (int) event.getX();
                LogUtil.d("点击的X轴坐标" + ex + "当前条目的位置:" + mViewPager.getCurrentItem());
                if (ex < halfScreenWidth) {
                    //左侧点击
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
                if (ex > halfScreenWidth) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                return mViewPager.dispatchTouchEvent(event);
            }
        });
//        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间
    }


    @Override
    protected void initListener() {
        super.initListener();
        getRefreshController().setAutoPullDownRefresh(false);
        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                if (isRequestFail) {
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initCurrentPositionList();  //请求仓位列表数据
                        getRefreshController().refreshComplete(currentPositionList);
                        ToastUtils.show(context, "没有更多数据");
                    }
                }, 500);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        adapter.setTimeFinishLitener(new DealProductPageAdapter.TimeFinishLitener() {
            @Override
            public void refreshData() {
                initCurrentPositionList();//刷新仓位列表
                LogUtil.d("刷新仓位列表了---------");
            }
        });
    }

    private boolean isTimeLine = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exchange_assets_add:
                showToast("充值");
                next(RechargeActivity.class);
                break;
            case R.id.tv_exchange_assets:
                showToast("个人资产");
                next(UserAssetsActivity.class);
                break;

            case R.id.ll_history_record:  //仓位历史记录
                next(PositionHistoryActivity.class);
                break;

            case R.id.tv_exchange_buy_plus:
                ToastUtils.show(context, "买涨");
                showDialog(Constant.TYPE_BUY_PLUS);
                break;
            case R.id.tv_exchange_buy_minus:
                ToastUtils.show(context, "买跌");
                showDialog(Constant.TYPE_BUY_MINUS);
                break;
            case R.id.rb_min_hour:
                ToastUtils.show(context, "分时线,加载数据");
                if (symbolInfos == null) {
                    processTimeLine();//请求分时数据

                }
                isTimeLine = true;
                break;
            case R.id.rb_min_5:
                ToastUtils.show(context, "5分时线");
                processKChartData(MIN_LINE5);
                break;
            case R.id.rb_min_15:
                ToastUtils.show(context, "15分时线,加载数据");
                processKChartData(MIN_LINE15); //请求K线数据
                break;
            case R.id.rb_min_60:
                ToastUtils.show(context, "30分时线,加载数据");
                processKChartData(MIN_LINE30); //请求K线数据
                break;
            case R.id.rb_day_hour:
                ToastUtils.show(context, "60分时线,加载数据");
                processKChartData(MIN_LINE60);   //请求K线数据
                break;
        }
    }

    //    60-1分钟K线，300-5分K线，900-15分K线，1800-30分K线，3600-60分K线，5-日K线
//    private static int MIN_LINE1 = 60;
    private static int MIN_LINE5 = 300;
    private static int MIN_LINE15 = 900;
    private static int MIN_LINE30 = 1800;
    private static int MIN_LINE60 = 3600;


    /**
     * 请求K线数据
     */
    private void processKChartData(final int chartType) {
        isTimeLine = false;
        LogUtil.d("模拟加载K线数据");
        if (symbolInfos == null) {
            ToastUtils.show(context, "请求网络数据失败");
            return;
        }

        SymbolInfosEntity symbolInfosentity = symbolInfos.get(mViewPager.getCurrentItem());
        int aType = symbolInfosentity.getAType();
        String exchangeName = symbolInfosentity.getExchangeName();
        String platformName = symbolInfosentity.getPlatformName();
        String symbol = symbolInfosentity.getSymbol();
        NetworkAPIFactoryImpl.getDealAPI().kchart(exchangeName, platformName,
                symbol, aType, chartType, new OnAPIListener<List<CurrentTimeLineReturnEntity>>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                        LogUtil.d("K线请求网络错了");
                    }

                    @Override
                    public void onSuccess(List<CurrentTimeLineReturnEntity> currentTimeLineReturnEntities) {
                        LogUtil.d("K线数据,请求成功:" + currentTimeLineReturnEntities.toString());
//                kChartFragment.initChart();
                        kChartFragment.loadChartData(currentTimeLineReturnEntities, chartType);
                    }
                });
    }

    private void showDialog(int type) {
        if (mUnitViewList.size() == 0) {
            ToastUtils.show(context, "请求数据失败");
            return;
        }
        String buyType = null;
        int buySell = 1;
        if (type == Constant.TYPE_BUY_MINUS) {
            buyType = "买跌";
            buySell = -1;
        } else if (type == Constant.TYPE_BUY_PLUS) {
            buyType = "买涨";
            buySell = 1;
        }

        final CustomDialog.Builder builder = new CustomDialog.Builder(context, type);
        final int finalBuySell = buySell;
        final String finalBuyType = buyType;
        builder.setProgressChangeListener(new CustomDialog.Builder.ProgressChangeListener() {
            @Override
            public void processData(int amount) {
                initDialogData(amount);
            }
        });
        builder.setPositiveButton(buyType, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                int progress = CustomDialog.mSeekBar.getProgress();
                double amount = progress + 1;
                int codeId = mUnitViewList.get(mViewPager.getCurrentItem()).getId();
                int deferred = 1; //是否过滤
                if (mUnitViewList.get(mViewPager.getCurrentItem()).getDeferred() != 0) {
                    deferred = (int) mUnitViewList.get(mViewPager.getCurrentItem()).getDeferred();
                }
                ToastUtils.show(context, finalBuyType);
                requestOpenPosition(codeId, finalBuySell, amount, deferred);    //买涨点击后操作
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
        initDialogData(1);
    }

    /**
     * 设置买张买跌数据
     *
     * @param amount 当前手数
     */
    private void initDialogData(int amount) {
        if (mUnitViewList.size() == 0 || mViewPager == null) {
            ToastUtils.show(context, "集合为空或者viewpager为空");
            return;
        }
        double unit = getCurrentUnit();  //获取当前商品 的单价
        CustomDialog.mEarnestMoney.setText("¥ " + NumberUtils.halfAdjust2(unit * amount)); //定金
        double chargeFree = mUnitViewList.get(mViewPager.getCurrentItem()).getOpenChargeFee() * unit * amount;//手续费
        CustomDialog.mServiceCharge.setText(NumberUtils.halfAdjust2(chargeFree) + "");
        CustomDialog.mTurnoverMoney.setText("¥ " + NumberUtils.halfAdjust2(unit * amount - chargeFree));//成交额
    }

    private double getCurrentUnit() {
        int currentItem = mViewPager.getCurrentItem();
        double unitPrice = entitys.get(currentItem).getCurrentPrice() * mUnitViewList.get(currentItem).getDepositFee(); //单价
        return unitPrice;
    }

    /**
     * 建仓请求数据
     *
     * @param codeId       商品ID
     * @param finalBuySell 建仓方向
     * @param amount       建仓手数
     * @param deferred     是否过滤
     */
    private void requestOpenPosition(int codeId, int finalBuySell, double amount, int deferred) {
        NetworkAPIFactoryImpl.getDealAPI().openPosition(codeId, finalBuySell, amount, deferred, new OnAPIListener<OpenPositionReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(OpenPositionReturnEntity openPositionReturnEntity) {
                LogUtil.d("建仓请求网络成功" + openPositionReturnEntity.toString());
                //刷新当前建仓列表数据
                initCurrentPositionList();

                processPositionList();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}

