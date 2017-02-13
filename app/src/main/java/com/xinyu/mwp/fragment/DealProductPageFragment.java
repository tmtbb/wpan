package com.xinyu.mwp.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
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
import com.xinyu.mwp.adapter.DealProductPageAdapter;
import com.xinyu.mwp.adapter.LoopPagerAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.DealProductPageEntity;

import com.xinyu.mwp.entity.Model;
import com.xinyu.mwp.entity.StockListBean;
import com.xinyu.mwp.fragment.base.BaseRefreshAbsListControllerFragment;
import com.xinyu.mwp.listener.OnRefreshPageListener;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CustomDialog;
import com.xinyu.mwp.view.MyTransformation;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @describe : 交易界面 白银/原油/咖啡 详情
 */
public class DealProductPageFragment extends BaseRefreshAbsListControllerFragment<DealProductPageEntity> implements View.OnClickListener {

    private DealProductPageAdapter adapter;

    private List<Fragment> chartFragments = new ArrayList<>();
    private RadioGroup radioGroupChart;

    private CombinedChart mChart;

    private int itemcount;
    private LineData lineData;
    private CandleData candleData;
    private CombinedData combinedData;
    private ArrayList<String> xVals;
    private List<CandleEntry> candleEntries = new ArrayList<>();
    private int colorHomeBg;
    private int colorLine;
    private int colorText;
    private int colorMa5;
    private int colorMa10;
    private int colorMa20;
    private ListView listView;
    private ViewPager mViewPager;
    private RelativeLayout mViewPagerContainer;
    private int halfScreenWidth;

    public static final int TYPE_BUY_MINUS = 0; //买跌
    public static final int TYPE_BUY_PLUS = 1; //买跌

    @Override
    protected int getLayoutID() {
        return R.layout.ly_listview;
    }

    @Override
    protected IListAdapter<DealProductPageEntity> createAdapter() {
        return adapter = new DealProductPageAdapter(context);
    }

    protected void setData(String title) {
        LogUtil.d("得到的:" + title);
        // new DealProductPageAdapter(context).notifyDataSetChanged();
        listView.setSelection(0); //自动跳转到顶部
    }

    private List<View> mViewList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        View view;
        for (int i = 0; i < 10; i++) {
            view = LayoutInflater.from(context).inflate(R.layout.ll_trade_time_type, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_trade_min60);
            tv.setText(i + "分时");
            mViewList.add(view);
        }

        View headView = LayoutInflater.from(context).inflate(R.layout.ly_tab_deal_order_head, null);

        listView = (ListView) getRefreshController().getContentView();
        listView.addHeaderView(headView);

        LinearLayout historyRecord = (LinearLayout) headView.findViewById(R.id.ll_history_record);  //持仓盈亏,历史记录

        TextView buyPlus = (TextView) headView.findViewById(R.id.tv_exchange_buy_plus);
        TextView buyMinus = (TextView) headView.findViewById(R.id.tv_exchange_buy_minus);

        radioGroupChart = (RadioGroup) headView.findViewById(R.id.radiogroup_chart);
        mChart = (CombinedChart) headView.findViewById(R.id.chart);

        RadioButton rbMinHour = (RadioButton) headView.findViewById(R.id.rb_min_hour); //分时线
        RadioButton rbMinHour5 = (RadioButton) headView.findViewById(R.id.rb_min_5); //分时线
        RadioButton rbMinHour15 = (RadioButton) headView.findViewById(R.id.rb_min_15); //15分时线
        RadioButton rbMinHour60 = (RadioButton) headView.findViewById(R.id.rb_min_60); //60分时线
        RadioButton rbMinHourDay = (RadioButton) headView.findViewById(R.id.rb_day_hour); //日时线

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

        initViewPager();

        //设置K线图属性
        initChart();
        loadChartData();
    }


    private void initViewPager() {
        halfScreenWidth = DisplayUtil.getScreenWidth(context) / 2;

        //设置ViewPager切换效果
        mViewPager.setPageTransformer(true, new MyTransformation());
        //为ViewPager设置PagerAdapter
        mViewPager.setAdapter(new LoopPagerAdapter(mViewList));

        //设置每页之间的左右间隔
        mViewPager.setPageMargin(0);

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
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间
    }

    @Override
    protected void initListener() {
        super.initListener();

        setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<DealProductPageEntity> list = new ArrayList<>();
                        for (int i = 0; i < 30; i++) {
                            DealProductPageEntity entity = new DealProductPageEntity();
                            entity.setProductName("石油" + (i + 1));
                            entity.setOpenPrice(6666);
                            entity.setGrossProfit(8888);
                            list.add(entity);
                        }
                        getRefreshController().refreshComplete(list);
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_history_record:  //仓位历史记录
                next(PositionHistoryActivity.class);
                LogUtil.d("仓位历史记录");
                break;

            case R.id.tv_exchange_buy_plus:
                ToastUtils.show(context, "买涨");
                showDialog(TYPE_BUY_PLUS);
                break;
            case R.id.tv_exchange_buy_minus:
                ToastUtils.show(context, "买跌");
                showDialog(TYPE_BUY_MINUS);
                break;

            case R.id.rb_min_hour:
                ToastUtils.show(context, "分时线,加载数据");
                loadChartData();
                break;
            case R.id.rb_min_5:
                ToastUtils.show(context, "5分线,加载数据");
                loadChartData();
                break;
            case R.id.rb_min_15:
                ToastUtils.show(context, "15分时线,加载数据");
                loadChartData();
                break;
            case R.id.rb_min_60:
                ToastUtils.show(context, "60分时线,加载数据");
                loadChartData();
                break;
            case R.id.rb_day_hour:
                ToastUtils.show(context, "日时线,加载数据");
                loadChartData();
                break;
        }
    }

    private void showDialog(int type) {
        String buyType = null;
        if (type == TYPE_BUY_MINUS) {
            buyType = "买跌";
        } else if (type == TYPE_BUY_PLUS) {
            buyType = "买涨";
        }
        CustomDialog.Builder builder = new CustomDialog.Builder(context, type);

        builder.setPositiveButton(buyType, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //买涨点击后操作
                ToastUtils.show(context, "买涨");
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

    private void initChart() {
        colorHomeBg = getResources().getColor(R.color.white); //背景色
        colorLine = getResources().getColor(R.color.white);//分割线
        colorText = getResources().getColor(R.color.color_666666);
        colorMa5 = getResources().getColor(R.color.yellow);//条目1
        colorMa10 = getResources().getColor(R.color.red);//条目2
        colorMa20 = getResources().getColor(R.color.color_f05f46);//条目3

        mChart.setDescription("");//描述信息
        mChart.setDrawGridBackground(false); //是否显示表格颜色
        mChart.setBackgroundColor(colorHomeBg);
        mChart.setGridBackgroundColor(colorHomeBg);
        mChart.setScaleYEnabled(true);  //Y轴激活
        mChart.setPinchZoom(true); //如果禁用,扩展可以在x轴和y轴分别完成
        mChart.setDrawValueAboveBar(false);
        mChart.setNoDataText("加载中...");
        mChart.setAutoScaleMinMaxEnabled(false);
        mChart.setDragEnabled(false); //可以拖拽
        mChart.setScaleEnabled(false);
        //设置绘制顺序
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);// 轴刻度间的宽度，默认值是4

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(6, false);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawAxisLine(true);
        rightAxis.setGridColor(colorLine);
        rightAxis.setTextColor(colorText);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);

        int[] colors = {colorMa5, colorMa10, colorMa20};
        String[] labels = {"MA5", "MA10", "MA20"};
        Legend legend = mChart.getLegend();
        legend.setCustom(colors, labels);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        for (int color : colors) {
            legend.setTextColor(color); //设置标签的颜色
        }

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                CandleEntry candleEntry = (CandleEntry) entry;
                float change = (candleEntry.getClose() - candleEntry.getOpen()) / candleEntry.getOpen();
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));
                LogUtil.d("qqq", "最高" + candleEntry.getHigh() + " 最低" + candleEntry.getLow() +
                        " 开盘" + candleEntry.getOpen() + " 收盘" + candleEntry.getClose() +
                        " 涨跌幅" + changePercentage);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    protected void loadChartData() {
        mChart.resetTracking();

        Random r = new Random();  //模拟随机数

        candleEntries = Model.getCandleEntries();

        itemcount = candleEntries.size();
        List<StockListBean.StockBean> stockBeans = Model.getData();
        xVals = new ArrayList<>();
        for (int i = 0; i < itemcount; i++) {
            int num = r.nextInt(itemcount);
            xVals.add(stockBeans.get(num).getDate());  //模拟的日期
        }

        combinedData = new CombinedData(xVals);

        /*k line*/
        candleData = generateCandleData();
        combinedData.setData(candleData);

        /*ma5*/
        ArrayList<Entry> ma5Entries = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            ma5Entries.add(new Entry(stockBeans.get(index).getMa5(), index));
        }
        /*ma10*/
        ArrayList<Entry> ma10Entries = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            ma10Entries.add(new Entry(stockBeans.get(index).getMa10(), index));
        }
        /*ma20*/
        ArrayList<Entry> ma20Entries = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            ma20Entries.add(new Entry(stockBeans.get(index).getMa20(), index));
        }

        lineData = generateMultiLineData(   //包含三种折线图的LineData
                generateLineDataSet(ma5Entries, colorMa5, "ma5"),
                generateLineDataSet(ma10Entries, colorMa10, "ma10"),
                generateLineDataSet(ma20Entries, colorMa20, "ma20"));

        combinedData.setData(lineData); //加入MA5\MA10\MA20三种折线图数据
        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        mChart.invalidate();


    }

    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label) {
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        set.setDrawCubic(true);//圆滑曲线
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }

    //折线图
    private LineData generateMultiLineData(LineDataSet... lineDataSets) {
        List<LineDataSet> dataSets = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < lineDataSets.length; i++) {
            dataSets.add(lineDataSets[i]);
        }

        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < itemcount; i++) {
            xVals.add("" + (r.nextInt(1990) + i));
        }

        LineData data = new LineData(xVals, dataSets);

        return data;
    }

    private CandleData generateCandleData() {

        CandleDataSet set = new CandleDataSet(candleEntries, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.RED);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.GREEN);
        set.setIncreasingPaintStyle(Paint.Style.STROKE);
        //set.setNeutralColor(Color.RED);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(0.5f);
        set.setHighLightColor(Color.WHITE);
        set.setDrawValues(false);

        CandleData candleData = new CandleData(xVals);
        candleData.addDataSet(set);

        return candleData;
    }
}

