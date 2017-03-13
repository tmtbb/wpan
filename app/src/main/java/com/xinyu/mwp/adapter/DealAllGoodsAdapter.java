package com.xinyu.mwp.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.adapter.base.BaseListViewAdapter;
import com.xinyu.mwp.adapter.viewholder.BaseViewHolder;
import com.xinyu.mwp.entity.DealAllGoodsItemEntity;
import com.xinyu.mwp.entity.HistoryPositionListReturnEntity;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.TimeUtil;
import com.xinyu.mwp.util.ToastUtils;
import com.xinyu.mwp.view.CellView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin on 17/1/12.
 */

public class DealAllGoodsAdapter extends BaseListViewAdapter<HistoryPositionListReturnEntity> {

    private static final int DEAL_DATE = 2; //日期
    private static final int DEAL_DETAIL = 1; //交易详情

    private List<HistoryPositionListReturnEntity> historyPositionListReturnEntities;
    private HashSet<String> dateSet = new HashSet<>();
    private HashSet<Integer> positionLists = new HashSet<>();
    private int prePosition = 0;


    public DealAllGoodsAdapter(Context context) {
        super(context);
        dateSet.clear();
        positionLists.clear();
        prePosition = 0;
    }

    public void setProductDealList(List<HistoryPositionListReturnEntity> dealDetailList) {
        historyPositionListReturnEntities = dealDetailList;
        initData();
    }

    private void initData() {
        if (historyPositionListReturnEntities == null) {
            return;
        }
        for (int i = 0; i < historyPositionListReturnEntities.size(); i++) {
            String formatTime = TimeUtil.getWeekAndYearDate(historyPositionListReturnEntities.get(i).getCloseTime() * 1000);
            if (dateSet.add(formatTime)) {
                positionLists.add(i + prePosition);
            }
        }
        prePosition = prePosition + historyPositionListReturnEntities.size();
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
        if (position == 0) {
            viewType = DEAL_DATE;
        }
        return viewType;
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
                    onItemChildViewClick(v,0,historyData.getPositionId());
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
                type = "买涨";
            } else if (historyData.getBuySell() == -1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buydown);
                type = "买跌";
            }
            StringBuffer sb = new StringBuffer();
            sb.append(type);
            sb.append("(");
            sb.append(getTimeType(historyData.getName()));
            sb.append(")");
            buyType.updateContent(historyData.getOpenPrice() + "");  //成交额
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
                type = "买涨";
            } else if (historyData.getBuySell() == -1) {
                buyType.updateContentLeftImage(R.mipmap.icon_buydown);
                type = "买跌";
            }
            StringBuffer sb = new StringBuffer();
            sb.append(type);
            sb.append("(");
            sb.append(getTimeType(historyData.getName()));
            sb.append(")");
            buyType.updateContent(historyData.getOpenPrice() + "");  //成交额
            buyType.updateName(sb.toString());
        }

        @Event(value = {R.id.buy_type})
        private void click(View v) {
            switch (v.getId()) {
                case R.id.buy_type:
//                    onItemChildViewClick(v, (int) historyData.getPositionId());
                    onItemChildViewClick(v,0,historyData.getPositionId());
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
