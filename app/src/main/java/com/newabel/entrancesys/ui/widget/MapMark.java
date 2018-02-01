package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/26 0026.
 */

public class MapMark extends View {
    private float mapX;
    private float mapY;
    private Paint mPaint;
    private Drawable mDrawable;

    public MapMark(Context context) {
        super(context);
        init(null);
    }

    public MapMark(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        float mDensity = getContext().getResources().getDisplayMetrics().density;
        int padding = (int) (5 * mDensity + 0.5);
        setPadding(padding, padding, padding, padding);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MapMark);
            this.mDrawable = ta.getDrawable(R.styleable.MapMark_mm_src);
            ta.recycle();
        }
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY) {
            w = getPaddingLeft() + getPaddingBottom() + (mDrawable == null ? 0 : mDrawable.getIntrinsicWidth());
        }
        if (hMode != MeasureSpec.EXACTLY) {
            h = getPaddingTop() + getPaddingBottom() + (mDrawable == null ? 0 : mDrawable.getIntrinsicHeight());
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.mDrawable != null) {
            canvas.drawBitmap(((BitmapDrawable) mDrawable).getBitmap(), getPaddingLeft(), getPaddingTop(), mPaint);
        }
    }

    public float getMapX() {
        return mapX;
    }

    public void setMapX(float mapX) {
        this.mapX = mapX;
    }

    public float getMapY() {
        return mapY;
    }

    public void setMapY(float mapY) {
        this.mapY = mapY;
    }

    public void setBitmap(int resId) {
        this.mDrawable = ContextCompat.getDrawable(getContext(), resId);
        requestLayout();
    }
}
