package com.xinyu.mwp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xinyu.mwp.R;
import com.xinyu.mwp.util.LogUtil;

/**
 * 自定义View，矩形,柱状图
 * Created by Administrator on 2017/2/4.
 */
public class CustomView extends View {

    private int height;
    private Rect rect;
    private Paint paint;
    private int color;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = R.color.red;
    }

    public CustomView(Context context) {
        super(context);
       color = R.color.red;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(color);
        // 画矩形(Rect)
        rect = new Rect(0, 0, R.dimen.dp_21, height);
        canvas.drawRect(rect, paint);

    }

    //设置高度的百分比
    public void setPercentage(int i, int flag) {
        height = i;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = i;
        setLayoutParams(layoutParams);

        if (flag == 1 || flag == 4) {
            color = getResources().getColor(R.color.color_009944);
        } else if (flag == 2 || flag == 3) {
            color = getResources().getColor(R.color.color_ef8455);
        } else if (flag == 5){
            color = getResources().getColor(R.color.default_red);
        }

    }
}
