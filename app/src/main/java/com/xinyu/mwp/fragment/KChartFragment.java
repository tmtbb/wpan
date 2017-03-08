package com.xinyu.mwp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

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

import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.CurrentTimeLineReturnEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.BaseFrameLayout;

import java.util.ArrayList;
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
        mChart.setPinchZoom(true); //如果禁用,扩展可以在x轴和y轴分别完成
        mChart.setNoDataText("加载中...");
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setDragEnabled(false); //可以拖拽
        mChart.setScaleEnabled(false);  //放大缩小
        mChart.getLegend().setEnabled(false);//图例
        //设置绘制顺序
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);// 轴刻度间的宽度，默认值是4
        xAxis.setEnabled(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(7, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(colorLine);
        rightAxis.setTextColor(colorText);
        rightAxis.setDrawGridLines(false);
        rightAxis.setStartAtZero(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);

//        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry entry, int i, Highlight highlight) {
//                CandleEntry candleEntry = (CandleEntry) entry;
//                float change = (candleEntry.getClose() - candleEntry.getOpen()) / candleEntry.getOpen();
//                NumberFormat nf = NumberFormat.getPercentInstance();
//                nf.setMaximumFractionDigits(2);
//                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));
//                LogUtil.d("qqq", "最高" + candleEntry.getHigh() + " 最低" + candleEntry.getLow() +
//                        " 开盘" + candleEntry.getOpen() + " 收盘" + candleEntry.getClose() +
//                        " 涨跌幅" + changePercentage);
//            }
//            @Override
//            public void onNothingSelected() {
//            }
//        });
    }

    private List<CurrentTimeLineReturnEntity> currentTimeLineEntities;

    public void loadChartData(List<CurrentTimeLineReturnEntity> currentTimeLineEntities, int chartType) {
        mChart.resetTracking();
        this.currentTimeLineEntities = currentTimeLineEntities;
        if (currentTimeLineEntities == null) {
            ToastUtils.show(context, "当前数据为空");
            return;
        }
        candleEntries = getCandleEntries(currentTimeLineEntities, 0);//获取处理过的集合
        itemcount = currentTimeLineEntities.size();
        xVals = new ArrayList<>();  //X集合
        for (int i = 0; i < itemcount; i++) {
            double time = currentTimeLineEntities.get(i).getPriceTime() * 1000;
            String dateAndTime = TimeUtil.getDateAndTime((long) time);
            xVals.add(dateAndTime);  //日期集合
        }
        combinedData = new CombinedData(xVals);
        if (chartType == 0) {
            //分时图
            LogUtil.d("分时图");
        } else {
               /*k line*/
            candleData = generateCandleData();
            combinedData.setData(candleData);
        }

         /*ma10*/
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int index = 0; index < itemcount; index++) {
            float currentPrice = (float) currentTimeLineEntities.get(index).getCurrentPrice();
            yVals.add(new Entry((currentPrice), index));
        }
        LineDataSet lineDataSet = generateLineDataSet(yVals, colorLine, "");
        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(xVals, dataSets);
        combinedData.setData(lineData); //加入MA5\MA10\MA20三种折线图数据
        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        mChart.invalidate();
    }

    private List<CandleEntry> candleEntries;
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
        return set;
    }

    private CandleData generateCandleData() {
        CandleDataSet set = new CandleDataSet(candleEntries, "");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
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

    /**
     * 获取处理过的  数据集合
     *
     * @param rawData
     * @param startIndex
     * @return
     */
    public static List<CandleEntry> getCandleEntries(List<CurrentTimeLineReturnEntity> rawData, int startIndex) {
        startIndex = 0;
        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < rawData.size(); i++) {
            CurrentTimeLineReturnEntity stock = rawData.get(i);
            if (stock == null) {
                Log.e("xxx", "第" + i + "StockBean==null");
                continue;
            }
            CandleEntry entry = new CandleEntry(startIndex + i, (float) stock.getHighPrice(), (float) stock.getLowPrice(), (float) stock.getOpeningTodayPrice(),
                    (float) stock.getClosedYesterdayPrice());
            entries.add(entry);
        }
        return entries;
    }

}