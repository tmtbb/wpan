package com.xinyu.mwp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.util.NumberUtils;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsAdapter extends BaseListViewAdapter<HistoryPositionListReturnEntity> {

    private static final int DEAL_DATE = 2; //日期
    private static final int DEAL_DETAIL = 1; //交易详情

    private List<HistoryPositionListReturnEntity> historyPositionListReturnEntities;
    private static HashSet<Integer> positionLists = new HashSet<>();


    public DealAllGoodsAdapter(Context context) {
        super(context);
        positionLists.clear();
    }

    /**
     * 自定义itemType
     *
     * @param position 条目位置
     * @return type类型   2种类型,一种是日期 + 条目  一种是条目
     */
    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (positionLists.contains(position)) {
            viewType = DEAL_DATE;
        } else {
            viewType = DEAL_DETAIL;
        }
        return viewType;
    }

    @Override
    public void notifyDataSetChanged() {
        setList(getProcessData());
        super.notifyDataSetChanged();
    }

    private List<HistoryPositionListReturnEntity> getProcessData() {
        String preTime = "";
        if (list.size() == 0) {
            return null;
        }
        List<HistoryPositionListReturnEntity> newList = new ArrayList();   //list去除重复
        Set<Long> set = new HashSet<Long>();     //去除相同closeTime的数据 ---有风险
        for (int i = 0; i < list.size(); i++) {
            if (set.add(list.get(i).getCloseTime())) {
                newList.add(list.get(i));
            }
        }

        for (int i = 0; i < newList.size(); i++) {
            String formatTime = TimeUtil.getWeekAndYearDate(newList.get(i).getCloseTime() * 1000);
            if (formatTime.equals(preTime)) {
                                                //这是一个第二种类型的数据
            } else {
                preTime = formatTime;
                positionLists.add(i);   //第一种类型,日期  吧position存入集合里面
            }
        }

        return newList;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    protected BaseViewHolder<HistoryPositionListReturnEntity> getViewHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == DEAL_DATE) {
            return new DealAllGoodsViewHolder(context);
        } else {
            return new DealDetailsViewHolder(context);
        }
    }

    class DealAllGoodsViewHolder extends BaseViewHolder<HistoryPositionListReturnEntity> {
        @ViewInject(R.id.name)
        private TextView dateMonth;
        @ViewInject(R.id.buy_type)
        private CellView buyType;
        private HistoryPositionListReturnEntity historyData;

        public DealAllGoodsViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_date;
        }

        @Event(value = {R.id.buy_type})
        private void click(View v) {
            switch (v.getId()) {
                case R.id.buy_type:
                    onItemChildViewClick(v, 0, historyData);
                    break;
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
            OnChildViewClickListener listener = new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView, action, obj);
                }
            };
            buyType.setOnChildViewClickListener(listener);
        }


        @Override
        protected void update(HistoryPositionListReturnEntity data) {
            historyData = data;
            String weekAndDate = TimeUtil.getWeekAndDate(historyData.getCloseTime() * 1000);
            dateMonth.setText(weekAndDate);

            String type = "";
            if (historyData.getBuySell() == 1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buyup);
                type = "买入";
            } else if (historyData.getBuySell() == -1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buydown);
                type = "卖出";
            }
            StringBuffer sb = new StringBuffer();
            sb.append(type);
            sb.append("(");
            sb.append(getTimeType(historyData.getName()));
            sb.append(")");
            buyType.updateContent(NumberUtils.halfAdjust2(historyData.getOpenCost()));  //成交额
            buyType.updateName(sb.toString());
        }
    }

    private class DealDetailsViewHolder extends BaseViewHolder<HistoryPositionListReturnEntity> {
        @ViewInject(R.id.buy_type)
        private CellView buyType;
        private HistoryPositionListReturnEntity historyData;

        public DealDetailsViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_deal_details;
        }

        @Override
        protected void update(HistoryPositionListReturnEntity data) {
            historyData = data;
            String type = "";
            if (historyData.getBuySell() == 1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buyup);
                type = "买入";
            } else if (historyData.getBuySell() == -1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buydown);
                type = "卖出";
            }
            StringBuffer sb = new StringBuffer();
            sb.append(type);
            sb.append("(");
            sb.append(getTimeType(historyData.getName()));
            sb.append(")");
            buyType.updateContent(NumberUtils.halfAdjust2(historyData.getOpenCost()));  //成交额
            buyType.updateName(sb.toString());
        }

        @Event(value = {R.id.buy_type})
        private void click(View v) {
            switch (v.getId()) {
                case R.id.buy_type:
                    onItemChildViewClick(v, 0, historyData);
                    break;
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
            OnChildViewClickListener listener = new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView, action, obj);
                }
            };
            buyType.setOnChildViewClickListener(listener);
        }
    }

    private String getTimeType(String productName) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(productName);
        String midNumber = m.replaceAll("").trim();
        int index = productName.indexOf(midNumber);
        String substring = productName.substring(index, productName.length());
        return substring;
    }
}
