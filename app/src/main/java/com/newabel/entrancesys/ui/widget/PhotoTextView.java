package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.newabel.entrancesys.ui.utils.UIUtils;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/22 0022.
 */

public class PhotoTextView extends android.support.v7.widget.AppCompatTextView {
    private Context context;
    private Paint mPaint;
    private int distance;

    public PhotoTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PhotoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PhotoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setTextSize(getTextSize());
        distance = UIUtils.dip2Px(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width,height;
        int[] intArr = getTextSizes(getText().toString());
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY){
            width = MeasureSpec.getSize(widthMeasureSpec);
        }else{
            width = getPaddingLeft() + intArr[0] + distance + (int)(intArr[1]*0.75f + 0.5) + getPaddingRight();
        }

        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY){
            height = MeasureSpec.getSize(heightMeasureSpec);
        }else{
            height = getPaddingTop() + intArr[1] + getPaddingBottom();
        }
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int[] intArr = getTextSizes(getText().toString());
        mPaint.setColor(getCurrentTextColor());
        mPaint.setTextSize(getTextSize());
        mPaint.setAntiAlias(true);
        canvas.drawText(getText().toString(),getPaddingLeft(),(getHeight() + intArr[1])/2,mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(getPaddingLeft() + intArr[0] + distance,(getHeight() + intArr[1])*0.5f);
        path.lineTo(getPaddingLeft() + intArr[0] + distance + intArr[1]*0.75f,(getHeight() + intArr[1])*0.5f);
        path.lineTo(getPaddingLeft() + intArr[0] + distance + intArr[1]*0.75f,getHeight()*0.5f - intArr[1]*0.25f);
        path.close();

        canvas.drawPath(path,mPaint);
    }

    private int[] getTextSizes(String text){
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        int[] intArr = new int[2];
        intArr[0] = rect.width();
        intArr[1] = rect.height();
        return intArr;
    }
}
