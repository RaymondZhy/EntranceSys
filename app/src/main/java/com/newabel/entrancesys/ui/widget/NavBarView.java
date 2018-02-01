package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.utils.UIUtils;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/14 0014.
 */

public class NavBarView extends View {

    private int nbv_bg_color;
    private int nbv_arrow_color;
    private int nbv_text_color;
    private float nbv_text_size;
    private float nbv_arrow_width;
    private float nbv_text_padding;

    private Path mPath;
    private Paint mPaint;
    private String mContent;
    private int nbv_text_selected_color;

    public NavBarView(Context context) {
        super(context);
    }

    public NavBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavBarView);
        nbv_bg_color = ta.getColor(R.styleable.NavBarView_nbv_bg_color, ContextCompat.getColor(context, R.color.gray));
        nbv_arrow_color = ta.getColor(R.styleable.NavBarView_nbv_arrow_color, ContextCompat.getColor(context, R.color.text_content));
        nbv_arrow_width = ta.getDimension(R.styleable.NavBarView_nbv_arrow_width, UIUtils.dip2Px(1));
        nbv_text_color = ta.getColor(R.styleable.NavBarView_nbv_text_color, ContextCompat.getColor(context, R.color.white));
        nbv_text_size = ta.getDimension(R.styleable.NavBarView_nbv_text_size, UIUtils.dip2Px(14));
        nbv_text_padding = ta.getDimension(R.styleable.NavBarView_nbv_text_padding,UIUtils.dip2Px(5));
        nbv_text_selected_color = ta.getColor(R.styleable.NavBarView_nbv_text_selected_color,ContextCompat.getColor(context, R.color.colorPrimary));
        mContent = ta.getString(R.styleable.NavBarView_nbv_text);
        mContent = TextUtils.isEmpty(mContent) ? "" : mContent;
        ta.recycle();

        setPadding((int)(nbv_text_padding +0.5), 0, (int) (nbv_arrow_width / 2 + 0.5), 0);

        mPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int width; //= MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (hMode != MeasureSpec.EXACTLY) {
            height = (int) (getTextHeight() + getPaddingTop() + getPaddingBottom() + 0.5);
        }
        width = (int) (getTextWidth(mContent) + getPaddingLeft() * 2 + getTextHeight(mContent) / 2 + getPaddingRight() + 0.5);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        mPaint.setColor(nbv_bg_color);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        //画文字
        if(isSelected() || isSelected()) {
            mPaint.setColor(nbv_text_selected_color);
        }else {
            mPaint.setColor(nbv_text_color);
        }
        mPaint.setTextSize(nbv_text_size);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseline = (getHeight() + (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.bottom-UIUtils.dip2Px(1);
        canvas.drawText(mContent, getPaddingLeft(), baseline, mPaint);

        //画箭头
        float th = getTextHeight(mContent);
        mPath.reset();
        mPath.moveTo(getWidth() - getPaddingRight() - th / 2, (getHeight() - th) / 2);
        mPath.lineTo(getWidth() - getPaddingRight(), getHeight() / 2);
        mPath.lineTo(getWidth() - getPaddingRight() - th / 2, (getHeight() + th) / 2);
        if(isSelected() || isPressed()) {
            mPaint.setColor(nbv_arrow_color);
        }else {
            mPaint.setColor(nbv_arrow_color);
        }
        mPaint.setStrokeWidth(nbv_arrow_width);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.drawPath(mPath, mPaint);
    }

    public void setText(String text) {
        this.mContent = text;
        requestLayout();
    }

    public float getTextWidth(String str) {
        mPaint.setColor(nbv_text_color);
        mPaint.setTextSize(nbv_text_size);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        Rect bounds = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.width();
    }

    public float getTextHeight() {
        mPaint.setColor(nbv_text_color);
        mPaint.setTextSize(nbv_text_size);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    public float getTextHeight(String str) {
        mPaint.setColor(nbv_text_color);
        mPaint.setTextSize(nbv_text_size);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        Rect bounds = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.height();
    }
}
