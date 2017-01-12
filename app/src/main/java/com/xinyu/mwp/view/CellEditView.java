package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinyu.mwp.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CellEditView extends BaseFrameLayout {
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.edit)
    private EditText edit;
    @ViewInject(R.id.cashAll)
    private TextView cashAll;

    public CellEditView(Context context) {
        super(context);
    }

    public CellEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CellEditView);
            if (typedArray.hasValue(R.styleable.CellEditView_celledit_name))
                name.setText(typedArray.getString(R.styleable.CellEditView_celledit_name));

            if (typedArray.hasValue(R.styleable.CellEditView_celledit_hint))
                edit.setHint(typedArray.getString(R.styleable.CellEditView_celledit_hint));

            if (typedArray.hasValue(R.styleable.CellEditView_celledit_cashall))
                cashAll.setVisibility(typedArray.getBoolean(R.styleable.CellEditView_celledit_cashall, false) ? VISIBLE : GONE);

            typedArray.recycle();
            typedArray = null;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_celledit;
    }

    @Event(value = R.id.cashAll)
    private void click(View v) {
        onChildViewClick(v, 99);
    }
}
