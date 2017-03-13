package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.DisplayUtil;

import org.xutils.view.annotation.ViewInject;

public class CellView extends BaseFrameLayout {

    @ViewInject(R.id.icon)
    private ImageView icon;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.content)
    private TextView content;
    @ViewInject(R.id.contentLeft)
    private TextView contentLeft;
    @ViewInject(R.id.arrow)
    private ImageView arrow;
    @ViewInject(R.id.contentRightImage)
    private ImageView contentRightImage;

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
            if (typedArray.hasValue(R.styleable.CellView_cell_name_size))
                name.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.CellView_cell_name_size, DisplayUtil.dip2px(15, context)));
            if (typedArray.hasValue(R.styleable.CellView_cell_name_color))
                name.setTextColor(typedArray.getColor(R.styleable.CellView_cell_name_color, getResources().getColor(R.color.font_333)));

            if (typedArray.hasValue(R.styleable.CellView_cell_content))
                content.setText(typedArray.getString(R.styleable.CellView_cell_content));
            if (typedArray.hasValue(R.styleable.CellView_cell_content_size))
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.CellView_cell_content_size, DisplayUtil.dip2px(12, context)));
            if (typedArray.hasValue(R.styleable.CellView_cell_content_color))
                content.setTextColor(typedArray.getColor(R.styleable.CellView_cell_content_color, getResources().getColor(R.color.font_333)));

            if (typedArray.hasValue(R.styleable.CellView_cell_contentleft))
                contentLeft.setText(typedArray.getString(R.styleable.CellView_cell_contentleft));
            if (typedArray.hasValue(R.styleable.CellView_cell_contentleft_size))
                contentLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.CellView_cell_contentleft_size, DisplayUtil.dip2px(12, context)));
            if (typedArray.hasValue(R.styleable.CellView_cell_contentleft_color))
                contentLeft.setTextColor(typedArray.getColor(R.styleable.CellView_cell_contentleft_color, getResources().getColor(R.color.font_333)));

            if (typedArray.hasValue(R.styleable.CellView_cell_icon_visible)) {
                boolean iconVisible = typedArray.getBoolean(R.styleable.CellView_cell_icon_visible, true);
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) name.getLayoutParams();
                if (iconVisible) {
                    icon.setVisibility(VISIBLE);
                    rl.leftMargin = DisplayUtil.dip2px(12, context);
                } else {
                    icon.setVisibility(GONE);
                    rl.leftMargin = 0;
                }
                name.setLayoutParams(rl);

            }

            if (typedArray.hasValue(R.styleable.CellView_cell_arrow_visible)) {
                boolean arrowVisible = typedArray.getBoolean(R.styleable.CellView_cell_arrow_visible, true);
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) content.getLayoutParams();
                if (arrowVisible) {
                    arrow.setVisibility(VISIBLE);
                    rl.rightMargin = DisplayUtil.dip2px(17, context);
                } else {
                    arrow.setVisibility(GONE);
                    rl.rightMargin = 0;
                }
                content.setLayoutParams(rl);
            }

            if (typedArray.hasValue(R.styleable.CellView_cell_rightimage_visible)) {
                boolean arrowVisible = typedArray.getBoolean(R.styleable.CellView_cell_rightimage_visible, false);
                contentRightImage.setVisibility(arrowVisible ? VISIBLE : GONE);
            }

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

    public void updateContentLeft(String content) {
        this.contentLeft.setText(content);
    }

    public void updateContentRightImage(String url) {
        ImageLoader.getInstance().displayImage(url, contentRightImage, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
    }

    public void updateNameAndContent(String name, String content) {
        updateName(name);
        updateContent(content);
    }
    public void updateContentLeftImage(int id) {
        icon.setImageResource(id);
    }
}
