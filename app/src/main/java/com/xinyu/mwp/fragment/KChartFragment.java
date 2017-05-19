package com.xinyu.mwp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
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
import com.xinyu.mwp.entity.CurrentTimeLineEntity;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.view.BaseFrameLayout;
import com.xinyu.mwp.view.LineMarkerView;
import com.xinyu.mwp.view.NewMarkerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class KChartFragment extends BaseFrameLayout {

    private int itemcount;
    private LineData lineData;
    private CandleData candleData;
    private CombinedData combinedData;
    private ArrayList<String> xVals;

    private int colorHomeBg;
    private int colorDivide;
    private int colorText;
    private int colorLine;
    private CombinedChart mChart;
    private NewMarkerView mvK;
    private LineMarkerView mvLine;
    private List<CurrentTimeLineReturnEntity> newCurrentTimeLineEntities;
    private int chartType = 0;
    private static int preChartType = 0;
    private List<CandleEntry> candleEntries;

    public KChartFragment(Context context) {
        super(context);
    }

    public KChartFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_deal_kchart;
    }

    @Override
    protected void initView() {
        super.initView();
        mChart = (CombinedChart) findViewById(R.id.chart);
        initChart();
    }

    public void initChart() {
        colorHomeBg = getResources().getColor(R.color.white); //背景色
        colorDivide = getResources().getColor(R.color.white);//分割线
        colorText = getResources().getColor(R.color.color_666666);
        colorLine = getResources().getColor(R.color.red);//条目

        mChart.setDescription("");//描述信息
        mChart.setDrawGridBackground(false); //是否显示表格颜色
        mChart.setBackgroundColor(colorHomeBg);
        mChart.setGridBackgroundColor(colorHomeBg);
        mChart.setScaleYEnabled(true);  //Y轴激活
        mChart.setPinchZoom(false); //如果禁用,扩展可以在x轴和y轴分别完成
        mChart.setNoDataText("加载中...");
//        mChart.setAutoScaleMinMaxEnabled(true);
//        mChart.setDragEnabled(false); //可以拖拽
        mChart.setScaleEnabled(false);  //放大缩小
        mChart.getLegend().setEnabled(false);//图例
        mChart.setTouchEnabled(true);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});  //设置绘制顺序

//        mChart.requestDisallowInterceptTouchEvent(true);
        mChart.setExtraLeftOffset(10);
        mChart.setExtraRightOffset(10);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);// 轴刻度间的宽度，默认值是4
        xAxis.setEnabled(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(6, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(colorLine);
        rightAxis.setTextColor(colorText);
        rightAxis.setDrawGridLines(false);
        rightAxis.setStartAtZero(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        refreshMarkerView(chartType);
    }

    public void loadChartData(List<CurrentTimeLineReturnEntity> currentTimeLineEntities, int chartType) {
        this.chartType = chartType;
        mChart.resetTracking();

        Collections.reverse(currentTimeLineEntities);
        if (currentTimeLineEntities.size() == 0) {
            return;
        }

        //为上海到东京的集合  特殊处理  v-symbol : "fx_sjpycnh"  上海-东京 分时图才做特殊处理  第一条单独拿出来处理
        if (currentTimeLineEntities.get(0).getSymbol().equals("fx_sjpycnh") && chartType == 0) {
            List<CurrentTimeLineReturnEntity> newList = new ArrayList<>();
            int j = 0;
            if (currentTimeLineEntities.size() > 7) {    //第一条单独拿出来,取倒数后7条
                j = currentTimeLineEntities.size() - 7;
            }

            for (int i = j; i < currentTimeLineEntities.size() - 1; i++) {  //取倒数后6条数据
                CurrentTimeLineReturnEntity entity = currentTimeLineEntities.get(i);
                for (int k = 0; k < 5; k++) {
                    CurrentTimeLineReturnEntity tpEntity = new CurrentTimeLineReturnEntity();
                    tpEntity.setPriceTime(entity.getPriceTime());
                    tpEntity.setCurrentPrice(entity.getCurrentPrice());
                    newList.add(tpEntity);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                long priceTime = newList.get(i).getPriceTime();
                if (i % 5 == 0) {
                    newList.get(i).setPriceTime(priceTime - 240);
                } else if (i % 5 == 1) {
                    newList.get(i).setPriceTime(priceTime - 180);
                } else if (i % 5 == 2) {
                    newList.get(i).setPriceTime(priceTime - 120);
                } else if (i % 5 == 3) {
                    newList.get(i).setPriceTime(priceTime - 60);
                } else if (i % 5 == 4) {
                    newList.get(i).setPriceTime(priceTime);
                }
//                LogUtil.d("当前索引:" + i + "得到的newList的priceTime:" + priceTime);
            }
            newList.add(currentTimeLineEntities.get(currentTimeLineEntities.size() - 1));
            this.newCurrentTimeLineEntities = newList;
        } else {
            List<CurrentTimeLineReturnEntity> newTimeLineList = new ArrayList<>();
            int j = 0;
            if (currentTimeLineEntities.size() > 30) {
                j = currentTimeLineEntities.size() - 30;
            }
            for (int i = j; i < currentTimeLineEntities.size(); i++) {  //取倒数前30条数据
                newTimeLineList.add(currentTimeLineEntities.get(i));
            }
            this.newCurrentTimeLineEntities = newTimeLineList;
        }
        if (preChartType != 0 && chartType == 0) {
            preChartType = 0;
            refreshMarkerView(0);
        } else if (preChartType == 0 && chartType != 0) {
            preChartType = 1;
            refreshMarkerView(1);
        }
//        LogUtil.d("这边接收到的集合数据ssssssssss:" + newCurrentTimeLineEntities.toString());

        candleEntries = getCandleEntries(newCurrentTimeLineEntities, 0);//获取处理过的集合
        itemcount = newCurrentTimeLineEntities.size();
        xVals = new ArrayList<>();  //X集合
        for (int i = 0; i < itemcount; i++) {
            double time = newCurrentTimeLineEntities.get(i).getPriceTime() * 1000;
            String dateAndTime = TimeUtil.getDateAndTime((long) time);
            xVals.add(dateAndTime);  //日期集合
        }
        combinedData = new CombinedData(xVals);
        if (chartType == 0) {   //分时图
            ArrayList<Entry> yVals = new ArrayList<Entry>();
            for (int index = 0; index < itemcount; index++) {
                float currentPrice = (float) newCurrentTimeLineEntities.get(index).getCurrentPrice();
                yVals.add(new Entry((currentPrice), index));
            }
            LineDataSet lineDataSet = generateLineDataSet(yVals, colorLine, "");
            List<LineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            LineData lineData = new LineData(xVals, dataSets);
            combinedData.setData(lineData); //加入MA5\MA10\MA20三种折线图数据
        } else {   /*k line*/
            candleData = generateCandleData();
            combinedData.setData(candleData);
        }
        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        mChart.setVisibleXRange(30, 100);
        mChart.invalidate();
    }

    public void refreshMarkerView(int type) {
        mvK = new NewMarkerView(context, R.layout.ly_marker_view);
        mvLine = new LineMarkerView(context, R.layout.ly_marker_line);
        if (type == 0) {
            mChart.setMarkerView(mvLine);
        } else {
            mChart.setMarkerView(mvK);
        }
    }

    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label) {
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        set.setDrawCubic(true);//圆滑曲线
        set.setDrawCircles(false);//设置有圆点
        set.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set.setFillColor(R.color.color_cccccc);
        set.setCubicIntensity(0.2f);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setHighLightColor(R.color.color_666666);
        return set;
    }

    private CandleData generateCandleData() {
        CandleDataSet set = new CandleDataSet(candleEntries, "");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.GREEN);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.RED);
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(0.5f);
        set.setHighLightColor(R.color.font_9e9e9e);
        set.setDrawValues(false);
        CandleData candleData = new CandleData(xVals);
        candleData.addDataSet(set);
        return candleData;
    }

    /**
     * 获取处理过的  数据集合
     *
     * @param rawData
     * @param startIndex
     * @return
     */
    public static List<CandleEntry> getCandleEntries(List<CurrentTimeLineReturnEntity> rawData, int startIndex) {
        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < rawData.size(); i++) {
            CurrentTimeLineReturnEntity stock = rawData.get(i);
            if (stock == null) {
                continue;
            }
            CandleEntry entry = new CandleEntry(startIndex + i, (float) stock.getHighPrice(), (float) stock.getLowPrice(), (float) stock.getOpeningTodayPrice(),
                    (float) stock.getClosedYesterdayPrice());
            entries.add(entry);
        }
        return entries;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                long time = newCurrentTimeLineEntities.get(entry.getXIndex()).getPriceTime() * 1000;
                entry.setData(TimeUtil.getHourAndMin(time));
                if (chartType != 0) {
                    mvK.refreshContent(entry, highlight);
                } else {
                    mvLine.refreshContent(entry, highlight);
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }
}