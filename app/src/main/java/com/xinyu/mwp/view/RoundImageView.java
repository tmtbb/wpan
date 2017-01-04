package com.xinyu.mwp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xinyu.mwp.R;


/**
 * Created by Don on 2016/12/5 10:55.
 * Describe：${带圆角的imageview}
 * Modified：${TODO}
 */
public class RoundImageView extends ImageView {

    private Paint mPaint;
    private Paint mPaint2;
    private int corners;
    private boolean mAll;
    private boolean mTop;

    public void setCorners(int corners) {
        this.corners = corners;
    }

    public void setAll(boolean all) {
        mAll = all;
    }

    public void setTop(boolean top) {
        mTop = top;
    }

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            corners = (int) a.getDimension(R.styleable.RoundImageView_corners, corners);
            mAll = a.getBoolean(R.styleable.RoundImageView_round_all, false);
            mTop = a.getBoolean(R.styleable.RoundImageView_round_top, false);
        } else {
            float density = getResources().getDisplayMetrics().density;
            corners = (int) (corners * density);
            mAll = false;
            mTop = false;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);//set this color => backgroundColor
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        mPaint2 = new Paint();
        mPaint2.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        if (mAll) {
            drawLeftDown(c);
            drawLeftUp(c);
            drawRightUp(c);
            drawRightDown(c);
        }
        if (mTop) {
            drawLeftUp(c);
            drawRightUp(c);
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint2);
        bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, corners);
        path.lineTo(0, 0);
        path.lineTo(corners, 0);
        path.arcTo(new RectF(0, 0, corners * 2, corners * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), corners);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - corners, 0);
        path.arcTo(new RectF(getWidth() - corners * 2, 0, getWidth(), 0 + corners * 2), -90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - corners);
        path.lineTo(0, getHeight());
        path.lineTo(corners, getHeight());
        path.arcTo(new RectF(0, getHeight() - corners * 2, corners * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - corners, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - corners);
        path.arcTo(new RectF(getWidth() - corners * 2, getHeight() - corners * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}