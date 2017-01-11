package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;

import org.xutils.view.annotation.ViewInject;

public class CellView extends BaseFrameLayout {

    @ViewInject(R.id.icon)
    private ImageView icon;
    @ViewInject(R.id.name)
    private TextView name;

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

            if (typedArray.hasValue(R.styleable.CellView_cell_name))
                name.setText(typedArray.getString(R.styleable.CellView_cell_name));

            if (typedArray.hasValue(R.styleable.CellView_cell_text_color))
                name.setTextColor(typedArray.getColor(R.styleable.CellView_cell_text_color, getResources().getColor(R.color.font_333)));
            typedArray.recycle();
            typedArray = null;
        }
    }
}
