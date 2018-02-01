package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.utils.UIUtils;


/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/22 0022.
 */

public class PhotoCheckBox extends android.support.v7.widget.AppCompatCheckBox {
    private float cb_stroke_size;
    private int cb_stroke_color;
    private float cb_radius;
    private int cb_solid_color;
    private int cb_inner_shape;
    private int cb_inner_shape_color;
    private float cb_hook_stroke_size;
    private float cb_dot_radius;
    private Paint mPaint;
    private Context context;
    public PhotoCheckBox(Context context) {
        super(context);
        this.context = context;
    }

    public PhotoCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initValues(attrs);
    }



    public PhotoCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initValues(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width,height;
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY){
            width = MeasureSpec.getSize(widthMeasureSpec);
        }else{
            width = UIUtils.dip2Px(35);
        }

        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY){
            height = MeasureSpec.getSize(heightMeasureSpec);
        }else{
            height =  UIUtils.dip2Px(35);
        }
        int size = Math.min(width,height);
        setMeasuredDimension(size,size);
    }

    private void initValues(AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PhotoCheckBox);
        cb_stroke_size = ta.getDimension(R.styleable.PhotoCheckBox_cb_stroke_size, UIUtils.dip2Px(2));
        cb_stroke_color = ta.getColor(R.styleable.PhotoCheckBox_cb_stroke_color, ContextCompat.getColor(context,R.color.white));
        cb_radius = ta.getDimension(R.styleable.PhotoCheckBox_cb_radius,UIUtils.dip2Px(3));
        cb_solid_color = ta.getColor(R.styleable.PhotoCheckBox_cb_solid_color, ContextCompat.getColor(context,R.color.green));
        cb_inner_shape = ta.getInt(R.styleable.PhotoCheckBox_cb_inner_shape,1);
        cb_inner_shape_color = ta.getColor(R.styleable.PhotoCheckBox_cb_inner_shape_color, ContextCompat.getColor(context,R.color.white));
        cb_hook_stroke_size = ta.getDimension(R.styleable.PhotoCheckBox_cb_hook_stroke_size,UIUtils.dip2Px(2));
        cb_dot_radius = ta.getDimension(R.styleable.PhotoCheckBox_cb_dot_radius,UIUtils.dip2Px(7));
        ta.recycle();

        int p = UIUtils.dip2Px(5);
        setPadding(p,p,p,p);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        boolean isChecked = isChecked();
        if(isChecked){
            drawCheckedBg(canvas);
            switch (cb_inner_shape){
                case 0:
                    drawHook(canvas);
                    break;
                case 1:
                    drawDot(canvas);
                    break;
            }
        }else{
            drawUnChecked(canvas);
        }

    }

    private void drawCheckedBg(Canvas canvas) {


        switch (cb_inner_shape){
            case 0:
                mPaint.setColor(cb_solid_color);
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.FILL);
                RectF rectF = new RectF(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());
                canvas.drawRoundRect(rectF,cb_radius,cb_radius,mPaint);
                break;
            case 1:
                mPaint.setColor(cb_solid_color);
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(cb_stroke_size);

                float drawWidth = getDrawWidth();
                float drawHeight = getDrawHeight();
                float cx = getPaddingLeft() + drawWidth/2;
                float cy = getPaddingTop() + drawHeight/2;
                canvas.drawCircle(cx,cy,Math.min(drawHeight,drawWidth)/2,mPaint);
                break;
        }
    }

    private void drawUnChecked(Canvas canvas) {
        mPaint.setColor(cb_stroke_color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(cb_stroke_size);
        switch (cb_inner_shape){
            case 0:
                RectF rectF = new RectF(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());
                canvas.drawRoundRect(rectF,cb_radius,cb_radius,mPaint);
                break;
            case 1:
                float drawWidth = getDrawWidth();
                float drawHeight = getDrawHeight();
                float cx = getPaddingLeft() + drawWidth/2;
                float cy = getPaddingTop() + drawHeight/2;
                canvas.drawCircle(cx,cy,Math.min(drawHeight,drawWidth)/2,mPaint);
                break;
        }

    }

    private void drawHook(Canvas canvas) {
        mPaint.setColor(cb_inner_shape_color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(cb_hook_stroke_size);
        mPaint.setAntiAlias(true);

        Path path = new Path();
        float drawWidth = getDrawWidth();
        float drawHeight = getDrawHeight();
        path.moveTo(getPaddingLeft() + drawWidth*0.1f,getPaddingTop()+ drawHeight * 0.2f+drawHeight*0.3f);
        path.lineTo(getPaddingLeft() + drawWidth*0.42f,getHeight()-getPaddingBottom() - drawHeight * 0.2f);
        path.lineTo(getWidth() - getPaddingRight() - drawWidth*0.1f,getPaddingTop()+ drawHeight * 0.2f);
        canvas.drawPath(path,mPaint);
    }

    private void drawDot(Canvas canvas) {
        float drawWidth = getDrawWidth();
        float drawHeight = getDrawHeight();

        float cx = getPaddingLeft() + drawWidth * 0.5f;
        float cy = getPaddingTop() + drawHeight * 0.5f;

        mPaint.setColor(cb_inner_shape_color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);


        canvas.drawCircle(cx,cy,cb_dot_radius,mPaint);
    }

    private float getDrawHeight(){
        return  getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private float getDrawWidth(){
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }
}
