package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.util.DisplayUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class CellView extends BaseFrameLayout {

    @ViewInject(R.id.icon)
    private ImageView icon;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.content)
    private TextView content;

    public CellView(Context context) {
        super(context);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_cell;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if (attrs != null) {

            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CellView);

            if (typedArray.hasValue(R.styleable.CellView_cell_icon))
                icon.setImageResource(typedArray.getResourceId(R.styleable.CellView_cell_icon, 0));

            if (typedArray.hasValue(R.styleable.CellView_cell_icon_width)) {
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) icon.getLayoutParams();
                rl.width = typedArray.getResourceId(R.styleable.CellView_cell_icon_width, DisplayUtil.dip2px(18, context));
                icon.setLayoutParams(rl);
            }

            if (typedArray.hasValue(R.styleable.CellView_cell_icon_height)) {
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) icon.getLayoutParams();
                rl.height = typedArray.getResourceId(R.styleable.CellView_cell_icon_height, DisplayUtil.dip2px(18, context));
                icon.setLayoutParams(rl);
            }

            if (typedArray.hasValue(R.styleable.CellView_cell_name))
                name.setText(typedArray.getString(R.styleable.CellView_cell_name));

            if (typedArray.hasValue(R.styleable.CellView_cell_name_color))
                name.setTextColor(typedArray.getColor(R.styleable.CellView_cell_name_color, getResources().getColor(R.color.font_333)));

            if (typedArray.hasValue(R.styleable.CellView_cell_content))
                name.setText(typedArray.getString(R.styleable.CellView_cell_content));

            if (typedArray.hasValue(R.styleable.CellView_cell_content_color))
                name.setTextColor(typedArray.getColor(R.styleable.CellView_cell_content_color, getResources().getColor(R.color.font_333)));
            typedArray.recycle();
            typedArray = null;
        }
    }

    public void updateName(String name) {
        this.name.setText(name);
    }

    public void updateContent(String content) {
        this.content.setText(content);
    }

    public void updateNameAndContent(String name, String content) {
        updateName(name);
        updateContent(content);
    }
}
