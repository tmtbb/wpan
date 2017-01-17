package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.listener.OnTextChangeListener;
import com.xinyu.mwp.util.DisplayUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/12.
 */

public class CellEditView extends BaseFrameLayout implements OnTextChangeListener {
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.edit)
    private EditText edit;
    @ViewInject(R.id.cashAll)
    private TextView cashAll;
    @ViewInject(R.id.rightImage)
    private ImageView rightImage;

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
            if (typedArray.hasValue(R.styleable.CellEditView_celledit_name_size))
                name.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.CellEditView_celledit_name_size, DisplayUtil.dip2px(15, context)));
            if (typedArray.hasValue(R.styleable.CellEditView_celledit_name_color))
                name.setTextColor(typedArray.getColor(R.styleable.CellEditView_celledit_name_color, getResources().getColor(R.color.font_333)));

            if (typedArray.hasValue(R.styleable.CellEditView_celledit_hint))
                edit.setHint(typedArray.getString(R.styleable.CellEditView_celledit_hint));

            if (typedArray.hasValue(R.styleable.CellEditView_celledit_cashall))
                cashAll.setVisibility(typedArray.getBoolean(R.styleable.CellEditView_celledit_cashall, false) ? VISIBLE : GONE);

            if (typedArray.hasValue(R.styleable.CellEditView_celledit_right_visible)) {
                boolean rightVisible = typedArray.getBoolean(R.styleable.CellEditView_celledit_right_visible, false);
                rightImage.setVisibility(rightVisible ? VISIBLE : GONE);
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) edit.getLayoutParams();
                if (rightVisible) {
                    rl.addRule(RelativeLayout.LEFT_OF, rightImage.getId());
                } else {
                    rl.addRule(RelativeLayout.LEFT_OF, cashAll.getId());
                }
                edit.setLayoutParams(rl);
            }
            typedArray.recycle();
            typedArray = null;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_celledit;
    }

    @Event(value = {R.id.cashAll, R.id.rightImage})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.cashAll:
                onChildViewClick(v, 99);
                break;
            case R.id.rightImage:
                onChildViewClick(v, 98);
                break;
        }
    }

    @Override
    public void addITextChangedListener(TextWatcher textWatcher) {
        if (edit != null)
            edit.addTextChangedListener(textWatcher);
    }

    @Override
    public String getEditTextString() {
        if (edit != null) {
            return edit.getText().toString().trim();
        }
        return "";
    }

    public void setEditTextString(String str) {
        if (edit != null && !TextUtils.isEmpty(str)) {
            edit.setText(str);
        }
    }
}
