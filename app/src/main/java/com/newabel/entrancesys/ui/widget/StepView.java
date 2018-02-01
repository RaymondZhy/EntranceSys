package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/22 0022.
 */

public class StepView extends View {
    private float mOuterRadius;
    private float mInnerRadius;
    private float mOuterLineWidth;
    private int mBgColor;
    private Paint mPaint;
    private int mSelectedColor;
    private float mRectWidth;
    private float mRectTopHeight;
    private float mRectBottomHeight;
    private boolean isRectTopVisible;
    private boolean isRectBottomVisible;
    private float mMarginLeft;
    private float mTextSize;
    private int mTextColor;
    private String mText;
    private List<Integer> rList;
    private String mDate;
    private float mDividerWidth;
    private int mDividerBgColor;
    private boolean isDividerVisible;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.StepView);
        try {
            mOuterRadius = ta.getDimension(R.styleable.StepView_sv_outer_r, UIUtils.dip2Px(10));
            mInnerRadius = ta.getDimension(R.styleable.StepView_sv_inner_r, UIUtils.dip2Px(7));
            mOuterLineWidth = ta.getDimension(R.styleable.StepView_sv_outer_Line_w, UIUtils.dip2Px(2));
            mBgColor = ta.getColor(R.styleable.StepView_sv_bg_color, ContextCompat.getColor(getContext(), R.color.text_content));
            mSelectedColor = ta.getColor(R.styleable.StepView_sv_selected_color, ContextCompat.getColor(getContext(), R.color.green));
            mRectWidth = ta.getDimension(R.styleable.StepView_sv_rect_width, UIUtils.dip2Px(2));
            mRectTopHeight = ta.getDimension(R.styleable.StepView_sv_rect_top_height, UIUtils.dip2Px(20));
            mRectBottomHeight = ta.getDimension(R.styleable.StepView_sv_rect_bottom_height, UIUtils.dip2Px(20));
            isRectTopVisible = ta.getBoolean(R.styleable.StepView_sv_is_rect_top_visible, true);
            isRectBottomVisible = ta.getBoolean(R.styleable.StepView_sv_is_rect_bottom_visible, true);
            mMarginLeft = ta.getDimension(R.styleable.StepView_sv_margin_text_left, UIUtils.dip2Px(10));
            mTextSize = ta.getDimension(R.styleable.StepView_sv_text_size, UIUtils.dip2Px(14));
            mTextColor = ta.getColor(R.styleable.StepView_sv_text_color, ContextCompat.getColor(getContext(), R.color.text_content));
            mText = ta.getString(R.styleable.StepView_sv_text);
            mText = mText == null ? "" : mText;
            mDate = ta.getString(R.styleable.StepView_sv_date);
            mDate = mDate == null ? "" : mDate;
            mDividerWidth = ta.getDimension(R.styleable.StepView_sv_divider_width, UIUtils.dip2Px(0));
            mDividerBgColor = ta.getColor(R.styleable.StepView_sv_divider_bg_color, ContextCompat.getColor(getContext(), R.color.common_app_bg));
        } finally {
            ta.recycle();
        }
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setSelected(true);

        isDividerVisible = false;

        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float w = MeasureSpec.getSize(widthMeasureSpec);

        float cx = getPaddingLeft() + mOuterLineWidth / 2 + mOuterRadius;
        float x = cx + getRadius() + mMarginLeft;
        float leftLength = w - x - getPaddingRight();
        float th = getTextHeight(mText);
        float r = getRadius();
        int start = 0;
        if (rList != null) {
            rList.clear();
        } else {
            rList = new ArrayList<>();
        }
        rList.add(0);
        getList(start, leftLength);

        //+ mOuterLineWidth / 2;
//        float h = MeasureSpec.getSize(heightMeasureSpec);
//        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
//            float h = mRectTopHeight + mRectBottomHeight + getRadius() * 2 + getPaddingBottom() + getPaddingTop();
//        }
        mRectBottomHeight = (rList.size() - 1) * th - r / 2 - th / 2 + th + 2 * mRectTopHeight;
        float h = mRectTopHeight + mRectBottomHeight + r * 2 + getPaddingBottom() + getPaddingTop();

        setMeasuredDimension((int) w, (int) h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mRadius = getRadius();
        float cx = getPaddingLeft() + mOuterLineWidth / 2 + mOuterRadius;
        float cy = getPaddingTop() + mRectTopHeight + mRadius;
        if (isSelected()) {
            mPaint.setColor(mSelectedColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mOuterLineWidth);
            mPaint.setAntiAlias(true);
            canvas.drawCircle(cx, cy, mOuterRadius, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, mInnerRadius, mPaint);
        } else {
            mPaint.setColor(mBgColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            canvas.drawCircle(cx, cy, mInnerRadius, mPaint);
        }

        mPaint.setColor(mBgColor);
        if (isRectTopVisible) {
            canvas.drawRect(cx - mRectWidth / 2, getPaddingTop(), cx + mRectWidth / 2, getPaddingTop() + mRectTopHeight, mPaint);
        }
        if (isRectBottomVisible) {
            float top = isSelected() ? (cy + mOuterRadius + mOuterLineWidth / 2) : (cy + mInnerRadius);
            canvas.drawRect(cx - mRectWidth / 2, top, cx + mRectWidth / 2, top + mRectBottomHeight, mPaint);
        }

        float x = cx + mRadius + mMarginLeft;
        float leftLength = getWidth() - x - getPaddingRight();
        float textHeight = getTextHeight(mText);
        float y = cy + textHeight / 2;
        for (int i = 0; i < rList.size() - 1; i++) {
            x = cx + mRadius + mMarginLeft;
            String str = mText.substring(rList.get(i), rList.get(i + 1));
            float tw = getTextWidth(str);
            float interval = (leftLength - tw) / (str.length() - 1);
            char[] charArr = str.toCharArray();
            for (int j = 0; j < charArr.length; j++) {
                if (j > 0) {
                    x = x + (rList.size() - i <= 2 ? 0 : interval) + getTextWidth(String.valueOf(charArr[j - 1]));
                }
                if (isSelected()) {
                    mPaint.setColor(mSelectedColor);
                } else {
                    mPaint.setColor(mTextColor);
                }
                canvas.drawText(charArr, j, 1, x, y + i * textHeight, mPaint);
            }
        }

        x = cx + mRadius + mMarginLeft;
        float yy = cy + (rList.size() - 1) * textHeight - textHeight / 2;
        float dy = yy + (getHeight() - getPaddingBottom() - yy - mDividerWidth) / 2 + textHeight / 2;
        canvas.drawText(mDate, x, dy, mPaint);

        if (isDividerVisible) {
            mPaint.setColor(mDividerBgColor);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x, getHeight() - mDividerWidth, getWidth(), getHeight(), mPaint);
        }
    }


    private List<Integer> getList(int start, float leftLength) {
        if (rList == null) {
            rList = new ArrayList<>();
        }
        int end = mText.length() - 1;
        float tw = getTextWidth(mText.substring(start, end + 1));
        if (tw <= leftLength) {
            rList.add(mText.length());
            return rList;
        } else {
            int temp = start;
            while (temp <= end) {
                int var1 = (temp + end) / 2;
                int var2 = var1 + 1;
                float tw1 = getTextWidth(mText.substring(start, var1));
                float tw2 = getTextWidth(mText.substring(start, var2));
                if (getTextWidth(mText.substring(start, var1)) == leftLength) {
                    rList.add(var1);
                    start = var2;
                    getList(start, leftLength);
                    break;
                } else if (tw1 < leftLength && tw2 > leftLength) {
                    rList.add(var1);
                    start = var2;
                    getList(start, leftLength);
                    break;
                } else if (tw1 < leftLength && tw2 <= leftLength) {
                    temp = var2;
                } else if (tw1 > leftLength && tw2 > leftLength) {
                    end = var1;
                }
            }
        }
        return rList;
    }

    private float getRadius() {
        float mRadius = isSelected() ? mOuterLineWidth / 2 + mOuterRadius : mInnerRadius;
//        float mRadius = mOuterLineWidth / 2 + mOuterRadius;
        return mRadius;
    }


    private float getTextHeight(String content) {
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        Rect bounds = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), bounds);
        return bounds.bottom - bounds.top;
    }

    private float getTextWidth(String content) {
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

//        Rect bounds = new Rect();
//        mPaint.getTextBounds(content, 0, content.length(), bounds);
//        return bounds.width();
        return mPaint.measureText(content);
    }

    public void setText(String text) {
        this.mText = text;
        invalidate();
    }

    public void setDate(String date) {
        this.mDate = date;
        invalidate();
    }

    public void setRectTopVisible(boolean bl) {
        this.isRectTopVisible = bl;
        invalidate();
    }

    public void setRectBottomVisible(boolean bl) {
        this.isRectBottomVisible = bl;
        invalidate();
    }

    public void setDividerVisible(boolean bl) {
        this.isDividerVisible = bl;
        invalidate();
    }

}
