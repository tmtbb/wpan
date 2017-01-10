package com.xinyu.mwp.emptyview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.constant.ActionConstant;
import com.xinyu.mwp.entity.EmptyViewEntity;
import com.xinyu.mwp.view.BaseDataFrameLayout;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-04-13 11:48
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DefaultEmptyView extends BaseDataFrameLayout<EmptyViewEntity> {

    @ViewInject(R.id.emptyIcon)
    protected ImageView emptyIcon;
    @ViewInject(R.id.emptyContent)
    protected TextView emptyContent;
    @ViewInject(R.id.emptyButton)
    protected Button emptyButton;

    public DefaultEmptyView(Context context) {
        super(context);
    }

    public DefaultEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(EmptyViewEntity data) {
        if (data != null) {

            if (!TextUtils.isEmpty(data.getButtonText())) {
                setEmptyButtonText(data.getButtonText());
            }

            if (!TextUtils.isEmpty(data.getContentText())) {
                setEmptyContent(data.getContentText());
            }

            if (data.getIcon() != 0) {
                setEmptyIcon(data.getIcon());
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_defaultemptyview;
    }

    @Override
    protected void initListener() {
        super.initListener();
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildViewClick(v, ActionConstant.Action.EMPTY_BUTTON);
            }
        });
    }

    protected void setEmptyIcon(int resId) {
        if (emptyIcon != null) {
            emptyIcon.setImageResource(resId);
        }
    }

    public void setEmptyContent(String content) {
        if (emptyContent != null) {
            emptyContent.setText(content);
        }
    }

    public void setEmptyContent(int resId) {
        setEmptyContent(getContext().getString(resId));
    }

    public void setEmptyButtonText(String text) {
        if (emptyButton != null) {
            emptyButton.setVisibility(VISIBLE);
            emptyButton.setText(text);
        }
    }

    public void setEmptyButtonText(int resId) {
        setEmptyButtonText(getContext().getString(resId));
    }
}
