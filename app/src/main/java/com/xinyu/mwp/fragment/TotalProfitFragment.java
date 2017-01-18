package com.xinyu.mwp.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseFragment;
import com.xinyu.mwp.util.DisplayUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/17 0017
 * @describe : com.xinyu.mwp.fragment
 */
public class TotalProfitFragment extends BaseFragment {

    @ViewInject(R.id.pieChartView)
    private PieChart pieChartView;
    @ViewInject(R.id.pieDescriptionLayout)
    private LinearLayout pieDescriptionLayout;
    @ViewInject(R.id.barChartView)
    private BarChart barChartView;
    
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_total_profit;
    }

    @Override
    protected void initView() {
        super.initView();
        initPieChart();
        initPieDescription();
    }
    
    private void initPieChart(){
        pieChartView.setUsePercentValues(false);
        pieChartView.setDescription("");
        pieChartView.setExtraOffsets(5, 10, 5, 5);
        pieChartView.setDragDecelerationFrictionCoef(0.95f);
        pieChartView.setDrawHoleEnabled(true);
        pieChartView.setHoleColor(Color.WHITE);

        pieChartView.setTransparentCircleColor(Color.WHITE);
        pieChartView.setTransparentCircleAlpha(110);

        pieChartView.setHoleRadius(58f);
        pieChartView.setTransparentCircleRadius(61f);

        pieChartView.setDrawCenterText(false);

        pieChartView.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChartView.setRotationEnabled(true);
        pieChartView.setHighlightPerTapEnabled(true);
        Legend l = pieChartView.getLegend();
        l.setEnabled(false);
        setPieChartData();
        pieChartView.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setPieChartData(){
        ArrayList<Entry> entries = new ArrayList<Entry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new Entry(15.05f, 0));
        entries.add(new Entry(12.00f, 1));
        entries.add(new Entry(101.20f, 2));

        PieDataSet dataSet = new PieDataSet(entries, "");
//        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#3fbcf6"));
        colors.add(Color.parseColor("#f6de3f"));
        colors.add(Color.parseColor("#f07a46"));
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        List<String> xVals = new ArrayList<>();
        xVals.add("");
        xVals.add("");
        xVals.add("");
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        data.setDrawValues(false);
        pieChartView.setData(data);

        // undo all highlights
        pieChartView.highlightValues(null);

        pieChartView.invalidate();
    }

    private void initPieDescription(){
        for (int i = 0; i < 3; i++){
            TextView textView = new TextView(context);
            textView.setTextColor(getResources().getColor(R.color.font_333));
            textView.setTextSize(16);
            if(i == 0){
                textView.setText("总收益    15.05%");
            } else if(i == 1){
                textView.setText("本月收益    12.00%");
            } else if(i == 2){
                textView.setText("本周收益    101.20%");
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            if(i > 0){
                params.topMargin = DisplayUtil.dip2px(5, context);
            }
            pieDescriptionLayout.addView(textView, params);
        }
    }

}
