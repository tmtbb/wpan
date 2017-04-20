package com.xinyu.mwp.fragment;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.PositionHistoryActivity;
import com.xinyu.mwp.activity.RechargeActivity;
import com.xinyu.mwp.adapter.DealProductPageAdapter;
import com.xinyu.mwp.adapter.GalleryAdapter;
import com.xinyu.mwp.adapter.LoopPagerAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.CurrentPositionEntity;
import com.xinyu.mwp.entity.CurrentPositionListReturnEntity;
import com.xinyu.mwp.entity.CurrentPriceReturnEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.entity.ProductEntity;
import com.xinyu.mwp.entity.SymbolInfosEntity;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.networkapi.NetworkAPIFactoryImpl;
import com.xinyu.mwp.user.UserManager;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TestDataUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;
import com.xinyu.mwp.view.MyTransformation;

import org.xutils.view.annotation.ViewInject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 * @describe : 交易界面 商品详情
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
    private List<ProductEntity> mUnitViewList = new ArrayList<>();  //单价信息
    private List<ProductEntity> mNewUnitList;  //单价信息
    private TextView currentPrice;
    private TextView changePercent;
    private TextView changePrice;
    private TextView highPrice;
    private TextView lowPrice;
    private TextView openingTodayPrice;
    private TextView closedYesterdayPrice;
    private List<CurrentPriceReturnEntity> entitys;
    private ArrayList<SymbolInfosEntity> symbolInfos = new ArrayList<>();//商品信息集合
    private List<CurrentPositionListReturnEntity> currentPositionList; //仓位列表集合
    private List<String> mTitleList;
    private GalleryAdapter mAdapter;
    private List<ProductEntity> productList;  //商品列表集合--大集合
    private List<List<ProductEntity>> products; //集合的集合
    private RecyclerView mRecyclerView;
    private boolean isRequestFail = true;
    private List<CurrentTimeLineReturnEntity> currentTimeLineEntities; //当前分时数据
    private KChartFragment kChartFragment;
    private static int currentType = Constant.MIN_LINE1;
    private static long count = 0;
    private static int start = 1;
    private int requestCount = 30;  //请求仓位列表的个数
    private int currentIndex = 0;
    private double turnoverPrice;
    private double earnestMoney;
    private int length = 0;
    private int newItemIndex = 0;
    private CurrentPositionEntity currentPositionEntity;
    private TextView tvExchangeAssets;

    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reuqestData();  //商品列表
            handler.postDelayed(this, 3000);
        }
    };
    private LinearLayout priceInfo;

    public DealProductPageFragment() {
        isShow = true;
    }

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
        initCurrentPosition();//请求当前仓位信息
        mAdapter.setOnItemClick(new GalleryAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                mUnitViewList = products.get(position); //通多点击的位置,获取不同unit的集合
                count = 0;
                initProductPrice();  //点击后请求报价
                initCurrentPosition();//请求当前仓位信息
                mAdapter.setPosition(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean OnLongClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                return false;
            }
        });
    }

    /**
     * 请求当前仓位信息
     */
    private void initCurrentPosition() {
        if (mUnitViewList.size() == 0) {
            mUnitViewList = products.get(0);
        }
        double id = mUnitViewList.get(0).getId();
        NetworkAPIFactoryImpl.getDealAPI().currentPosition(id, new OnAPIListener<CurrentPositionEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(CurrentPositionEntity currentPosition) {
                currentPositionEntity = currentPosition;
                currentPositionEntity.setCurrentPositionName(mUnitViewList.get(0).getShowSymbol());
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        reuqestData();  //商品列表
        handler.postDelayed(runnable, 3000);//每两秒执行一次runnable.
        initCurrentPositionList(start, requestCount);  //请求仓位列表数据
        getRefreshController().setPullDownRefreshEnabled(false);
        initHeadView();  //初始化头布局
    }

    /**
     * 请求当前仓位列表
     */
    private void initCurrentPositionList(int num, int requestCount) {
        NetworkAPIFactoryImpl.getDealAPI().currentPositionList(num, requestCount, new OnAPIListener<List<CurrentPositionListReturnEntity>>() {
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
                getOrderList(); //仓位列表按照时间排序

                processPositionList();
            }
        });
    }

    /**
     * 按照建仓时间进行排序
     */
    private void getOrderList() {
        Collections.sort(currentPositionList, new Comparator<CurrentPositionListReturnEntity>() {
            @Override
            public int compare(CurrentPositionListReturnEntity c1, CurrentPositionListReturnEntity c2) {
                long i = c2.getPositionTime() - c1.getPositionTime();
                return (int) i;
            }
        });
        LogUtil.d("排序之后的currentPositionList集合:" + currentPositionList.toString());
    }

    private void proofCountdown() {
        long curTime = System.currentTimeMillis();
        Iterator<CurrentPositionListReturnEntity> iterator = currentPositionList.iterator();
        while (iterator.hasNext()) {
            CurrentPositionListReturnEntity entity = iterator.next();
            if (entity.getInterval() == 0) {   //删除已经平仓但是仍然在持仓列表中的条目
                iterator.remove();
            }
            entity.setEndTime((long) (entity.getInterval() * 1000 + curTime));
        }
    }

    /**
     * 仓位列表数据显示
     */
    private void processPositionList() {
        proofCountdown();// 校对倒计时
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
        priceInfo = (LinearLayout) headView.findViewById(R.id.ll_deal_info_modify);
        currentPrice = (TextView) headView.findViewById(R.id.tv_current_price);
        changePercent = (TextView) headView.findViewById(R.id.tv_change_percent);
        changePrice = (TextView) headView.findViewById(R.id.tv_change_price);
        highPrice = (TextView) headView.findViewById(R.id.tv_high_price);
        lowPrice = (TextView) headView.findViewById(R.id.tv_low_price);
        openingTodayPrice = (TextView) headView.findViewById(R.id.tv_today_price);
        closedYesterdayPrice = (TextView) headView.findViewById(R.id.tv_yesterday_price);

        //总资产数目
        tvExchangeAssets = (TextView) headView.findViewById(R.id.tv_exchange_assets);
        ImageView ivAssetsAdd = (ImageView) headView.findViewById(R.id.iv_exchange_assets_add);//加号按钮

        RadioButton rbMinHour = (RadioButton) headView.findViewById(R.id.rb_min_hour); //分时线
        RadioButton rbMinHour5 = (RadioButton) headView.findViewById(R.id.rb_min_5); //分时线
        RadioButton rbMinHour15 = (RadioButton) headView.findViewById(R.id.rb_min_15); //15分时线
        RadioButton rbMinHour60 = (RadioButton) headView.findViewById(R.id.rb_min_60); //60分时线
        RadioButton rbMinHourDay = (RadioButton) headView.findViewById(R.id.rb_day_hour); //日时线

        refreshBalance();
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

    private void refreshBalance() {
        if (UserManager.getInstance().getUserEntity() != null) {
            tvExchangeAssets.setText(NumberUtils.halfAdjust2(UserManager.getInstance().getUserEntity().getBalance()));
        }
    }

    //请求商品报价数据
    private void initProductPrice() {
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
                entitys = currentPriceReturnEntities;
                initViewPager();
                processPriceInfo();
//                mViewPager.setCurrentItem(0);//默认在中间
                refreshChartData();  //请求报价成功后,根据currentType刷新chart数据
            }
        });
    }

    /**
     * 根据currentType刷新chart数据
     */
    private void refreshChartData() {
        switch (currentType) {
            case Constant.MIN_LINE1:  //加载分时图
                if (count % 5 == 0) {
                    processTimeLine();
                }
                break;
            case Constant.MIN_LINE5:  //5分时K线图
                if (count % 25 == 0) {
                    processKChartData(Constant.MIN_LINE5);
                }
                break;
            case Constant.MIN_LINE15:  //15分时K线图
                if (count % 100 == 0) {
                    processKChartData(Constant.MIN_LINE15);
                }
                break;
            case Constant.MIN_LINE30:  //30分时K线图   10分钟更新一次
                if (count % 200 == 0) {
                    processKChartData(Constant.MIN_LINE30);
                }
                break;

            case Constant.MIN_LINE60:  //60分时K线图,20分钟更新一次
                if (count % 1200 == 0) {
                    processKChartData(Constant.MIN_LINE60);
                }
                break;
        }
        count++;
    }

    /**
     * 加载分时数据
     */
    private void processTimeLine() {
        if (symbolInfos.size() == 0) {
            ToastUtils.show(context, "请求数据失败,请检查网络连接");
            return;
        }
        SymbolInfosEntity symbolInfosentity = symbolInfos.get(newItemIndex);
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
                kChartFragment.loadChartData(currentTimeLineEntities, 0);
            }
        });
    }

    /**
     * 加载价格详情
     */
    private void processPriceInfo() {
        if (priceInfo.getVisibility() == View.INVISIBLE) {
            priceInfo.setVisibility(View.VISIBLE);
        }
        String resultPrice = NumberUtils.halfAdjust4(entitys.get(newItemIndex).getCurrentPrice());
        if (entitys.get(newItemIndex).getChange() < 0) {
            currentPrice.setTextColor(getResources().getColor(R.color.default_green));
        } else {
            currentPrice.setTextColor(getResources().getColor(R.color.default_red));
        }
        currentPrice.setText(resultPrice);
        String resultChange = NumberUtils.halfAdjust5(entitys.get(newItemIndex).getChange());
        changePrice.setText(resultChange);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        String resultPercent = numberFormat.format(entitys.get(newItemIndex).getChange() / entitys.get(newItemIndex).getCurrentPrice() * 100);
        changePercent.setText(resultPercent);
        highPrice.setText(NumberUtils.halfAdjust5(entitys.get(newItemIndex).getHighPrice()));
        lowPrice.setText(NumberUtils.halfAdjust5(entitys.get(newItemIndex).getLowPrice()));
        openingTodayPrice.setText(NumberUtils.halfAdjust5(entitys.get(newItemIndex).getOpeningTodayPrice()));
        closedYesterdayPrice.setText(NumberUtils.halfAdjust5(entitys.get(newItemIndex).getClosedYesterdayPrice()));
    }

    private void initViewPager() {
        setNewUnitList();  //设置新的价格 集合   size+2 实现循环滑动

        if (loopPagerAdapter != null) {
            loopPagerAdapter.updateItemsData(mNewUnitList);
            LogUtil.d("viewpager不为空,刷新mUnitViewList长度是:" + mUnitViewList.size());
        } else {
            halfScreenWidth = DisplayUtil.getScreenWidth(context) / 2;
            mViewPager.setOffscreenPageLimit(3);

//            mViewPager.setNestedpParent((ViewGroup)mViewPager.getParent());
            loopPagerAdapter = new LoopPagerAdapter(context, mNewUnitList);
            mViewPager.setAdapter(loopPagerAdapter);
            mViewPager.setPageTransformer(true, new MyTransformation()); //设置切换效果
            mViewPager.setPageMargin(0);   //设置每页之间的左右间隔
        }
//        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间
    }

    /**
     * 设置新的价格集合
     */
    private void setNewUnitList() {
        for (ProductEntity productEntity : mUnitViewList) {
            for (CurrentPriceReturnEntity entity : entitys) {
                if (entity.getSymbol().equals(productEntity.getSymbol())) {
                    productEntity.setCurrentPrice(entity.getCurrentPrice());
                }
            }
        }
        length = mUnitViewList.size() + 2;
        mNewUnitList = new ArrayList<>();
        mNewUnitList.add(0, mUnitViewList.get(mUnitViewList.size() - 1));  //第0个设成 最后一个
        mNewUnitList.addAll(1, mUnitViewList);
        mNewUnitList.add(length - 1, mUnitViewList.get(0));
    }

    @Override
    protected void initListener() {
        super.initListener();
        getRefreshController().setAutoPullDownRefresh(false);
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0.0) {
                    if (position == 0) {
                        mViewPager.setCurrentItem(length - 2, false);
                    } else if (position == length - 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if (currentIndex == mNewUnitList.size() - 1) {
                    newItemIndex = 0;
                } else if (currentIndex == 0) {
                    newItemIndex = mUnitViewList.size() - 1;
                } else {
                    newItemIndex = currentIndex - 1;
                }

//                newItemIndex = currentIndex - 2;
//                if (newItemIndex < 0) {
//                    newItemIndex = mUnitViewList.size() - 1;
//                } else if (newItemIndex == mNewUnitList.size() - 1) {
//                    newItemIndex = 0;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //将容器的触摸事件反馈给ViewPager
        mViewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        //倒计时结束的回调
        adapter.setTimeFinishLitener(new DealProductPageAdapter.TimeFinishLitener() {
            @Override
            public void refreshData() {
                initCurrentPositionList(start, requestCount);//刷新仓位列表
            }
        });
    }

    public void doRefresh() {
        initCurrentPositionList(start, requestCount);  //请求仓位列表数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPositionList == null) {
                    ToastUtils.show(context, "没有更多数据");
                }
                getRefreshController().refreshComplete(currentPositionList);
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exchange_assets_add:
                showToast("充值");
                next(RechargeActivity.class);
                break;
            case R.id.tv_exchange_assets:
//                showToast("个人资产");
//                next(UserAssetsActivity.class);
                break;
            case R.id.ll_history_record:  //仓位历史记录
                next(PositionHistoryActivity.class);
                break;
            case R.id.tv_exchange_buy_plus:
                showDialog(Constant.TYPE_BUY_PLUS);
                break;
            case R.id.tv_exchange_buy_minus:
                showDialog(Constant.TYPE_BUY_MINUS);
                break;
            case R.id.rb_min_hour:
                currentType = Constant.MIN_LINE1;
                processTimeLine();//请求分时数据
                break;
            case R.id.rb_min_5:
                processKChartData(Constant.MIN_LINE5);
                break;
            case R.id.rb_min_15:
                processKChartData(Constant.MIN_LINE15); //请求K线数据
                break;
            case R.id.rb_min_60:
                processKChartData(Constant.MIN_LINE30); //请求K线数据
                break;
            case R.id.rb_day_hour:
                processKChartData(Constant.MIN_LINE60);   //请求K线数据
                break;
        }
    }

    /**
     * 请求K线数据
     */
    private void processKChartData(int chartType) {
        currentType = chartType;
        if (symbolInfos.size() == 0) {
            ToastUtils.show(context, "请求网络数据失败");
            return;
        }
        SymbolInfosEntity symbolInfosentity = symbolInfos.get(newItemIndex);
        int aType = symbolInfosentity.getAType();
        String exchangeName = symbolInfosentity.getExchangeName();
        String platformName = symbolInfosentity.getPlatformName();
        String symbol = symbolInfosentity.getSymbol();
        NetworkAPIFactoryImpl.getDealAPI().kchart(exchangeName, platformName,
                symbol, aType, currentType, new OnAPIListener<List<CurrentTimeLineReturnEntity>>() {
                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                    }

                    @Override
                    public void onSuccess(List<CurrentTimeLineReturnEntity> currentTimeLineReturnEntities) {
                        kChartFragment.loadChartData(currentTimeLineReturnEntities, currentType);
                    }
                });
    }

    private void showDialog(int type) {
        if (mUnitViewList.size() == 0 || mViewPager == null || entitys == null) {
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
        }).setPositiveButton(buyType, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int progress = CustomDialog.mSeekBar.getProgress();
                final double amount = progress + 1;
                final int codeId = mUnitViewList.get(newItemIndex).getId();
                final boolean deferred;
                if (mUnitViewList.get(newItemIndex).getDeferred() == 1) {
                    deferred = true;                        //是否过滤
                } else {
                    deferred = false;
                }
                if (earnestMoney > UserManager.getInstance().getUserEntity().getBalance()) {
                    showRechargeDialog();//余额不足,提示弹窗
                } else {
                    showLoader("正在提交订单...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                requestOpenPosition(codeId, finalBuySell, amount, deferred);    //买涨点击后操作
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ToastUtils.show(context, "取消");
                    }
                }).create().show();
        initDialogData(1);
    }

    private void showRechargeDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(context, Constant.TYPE_INSUFFICIENT_BALANCE);
        builder.setTitle(getResources().getString(R.string.insufficient_balance))
                .setMessage(getResources().getString(R.string.recharge_prompt))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        next(RechargeActivity.class);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    /**
     * 设置买张买跌数据
     *
     * @param amount 当前手数
     */
    private void initDialogData(int amount) {
//        if (mUnitViewList.size() == 0 || mViewPager == null) {
//            ToastUtils.show(context, "请求网络失败,数据为空");
//            return;
//        }
        double unit = getCurrentUnit();  //获取当前商品 的单价
        CustomDialog.mCurrentCount.setText(amount + "");
        earnestMoney = Double.parseDouble(NumberUtils.halfAdjust2(unit * amount)); //定金-实际付的钱
//        CustomDialog.mEarnestMoney.setText("¥ " + earnestMoney); //定金
        double chargeFree = mUnitViewList.get(newItemIndex).getOpenChargeFee() * unit * amount;//手续费
        CustomDialog.mServiceCharge.setText(NumberUtils.halfAdjust2(chargeFree));
        turnoverPrice = Double.parseDouble(NumberUtils.halfAdjust2(unit * amount - chargeFree));
        CustomDialog.mTurnoverMoney.setText("¥ " + turnoverPrice);//成交额
        CustomDialog.mCurrentPosition.setText(String.format(currentPositionEntity.getCurrentPositionName() + "%s", currentPositionEntity.getName()));//当前仓位
    }

    private double getCurrentUnit() {
        int currentItem = newItemIndex;
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
    private void requestOpenPosition(int codeId, int finalBuySell, double amount, boolean deferred) {
        NetworkAPIFactoryImpl.getDealAPI().openPosition(codeId, finalBuySell, amount, turnoverPrice, deferred, new OnAPIListener<CurrentPositionListReturnEntity>() {
            @Override
            public void onError(Throwable ex) {
                ex.printStackTrace();
                closeLoader();
                ToastUtils.show(context, "建仓失败");
            }

            @Override
            public void onSuccess(CurrentPositionListReturnEntity openPositionReturnEntity) {
                LogUtil.d("建仓请求网络成功" + openPositionReturnEntity.toString());
                closeLoader();
                ToastUtils.show(context, "交易成功");


                initCurrentPositionList(start, requestCount);  //刷新当前建仓列表数据
                TestDataUtil.requestBalance();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshBalance();  //更新余额
                    }
                }, 500);

//                listView.setSelection(0);  //跳到顶部
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("onResume执行方法,sss此时的fragment显示状态是:" + isShow);
        //如果当前是显示状态的话,执行;如果当前是隐藏状态的话,不执行了
//        if (isShow) {
        handler.postDelayed(runnable, 3000);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("onPause方法执行,关掉网络请求sss");
        handler.removeCallbacks(runnable);
        count = 0;
    }

    //初始化的时候
    private static boolean isShow = false;

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            LogUtil.d("界面被隐藏了sss");
//            isShow = false;
//            handler.removeCallbacks(runnable);
//        } else {
//            LogUtil.d("界面显示了sss");
//            isShow = true;
//            handler.postDelayed(runnable, 3000);
//        }
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d("这个设置隐藏显示的方法执行了" + isVisibleToUser);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

