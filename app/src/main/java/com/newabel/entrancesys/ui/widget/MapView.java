package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/25 0025.
 */

public class MapView extends ViewGroup implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private String TAG = MapView.class.getSimpleName();

    private ImageView mImageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private Scroller mScroller;
    private float startX;
    private float startY;
    private int mPointerCount;
    private int mTouchSlop;
    private Matrix mMatrix;
    private float mMaxScale = 4.0f;
    private float mMinScale = 0.25f;
    private long mClickBeginTime;
    private boolean isEditMode = true;
    private OnMarkWatcher mOnMarkWatcher;
    private VelocityTracker mVelocityTracker;

    public MapView(Context context) {
        super(context);
        init(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mImageView = new ImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MapView);
            Drawable drawable = ta.getDrawable(R.styleable.MapView_mv_src);

            if (drawable != null) {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                mImageView.setLayoutParams(params);
                setImageDrawable(drawable);
            }
            ta.recycle();
        }
        mImageView.setBackgroundResource(R.color.white);
        mImageView.setOnTouchListener(this);
        addView(mImageView);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int w;
        int h;
        int minWidth = 0;
        int minHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            measureChild(v, widthMeasureSpec, heightMeasureSpec);
            minWidth = v.getMeasuredWidth() > minWidth ? v.getMeasuredWidth() : minWidth;
            minHeight = v.getMeasuredHeight() > minHeight ? v.getMeasuredHeight() : minHeight;
        }

        if (wMode == MeasureSpec.EXACTLY) {
            w = MeasureSpec.getSize(widthMeasureSpec);

        } else {
            w = minWidth;
        }

        if (hMode == MeasureSpec.EXACTLY) {
            h = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            h = minHeight;
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            int w = getWidth();
            int mw = v.getMeasuredWidth();
            int h = getHeight();
            int mh = v.getMeasuredHeight();
            if (v instanceof ImageView) {
                v.layout((w - mw) / 2, (h - mh) / 2, (w + mw) / 2, (h + mh) / 2);
            } else if (v instanceof MapMark) {
                float scale = getScale();
                float transX = getTransX();
                float transY = getTransY();
                MapMark mark = (MapMark) v;

                RectF rectF = getMatrixRectF();

                int left = toInt(mark.getMapX() * scale + transX - v.getMeasuredWidth() / 2.0f);
                int top = toInt(mark.getMapY() * scale + transY - v.getMeasuredHeight() + v.getPaddingBottom());
                int right = toInt(mark.getMapX() * scale + transX + v.getMeasuredWidth() / 2.0f);
                int bottom = toInt(mark.getMapY() * scale + transY + v.getPaddingBottom());
                v.layout(left, top, right, bottom);
            } else {
                throw new RuntimeException("Child view must be ImageView or MarkView");
            }
        }
    }

    private int toInt(float value) {
        return (int) (value + 0.5);
    }

    public void setImageResource(int resId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        setImageDrawable(drawable);
    }

    public void setImageDrawable(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bm) {
        mImageView.setImageBitmap(bm);
    }

    public void setImageMatrix(Matrix matrix) {
        mImageView.setImageMatrix(matrix);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        setImageMatrix(detector);
        return true;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    private void setImageMatrix(ScaleGestureDetector detector) {//
        float mScaleFactor = detector.getScaleFactor();
        float mScale = getScale();
        mScaleFactor = getScaleFactor(mScaleFactor, mScale);
        mMatrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
        centerDrawable();
        setImageMatrix(mMatrix);
        requestLayout();
    }

    private void centerDrawable() {//
        RectF mRectF = getMatrixRectF();
        float offsetX = 0;
        float offsetY = 0;
        if (mRectF.width() >= mImageView.getWidth()) {
            if (mRectF.left > 0) {
                offsetX = -mRectF.left;
            }

            if (mRectF.right < mImageView.getWidth()) {
                offsetX = mImageView.getWidth() - mRectF.right;
            }
        }

        if (mRectF.height() >= mImageView.getHeight()) {
            if (mRectF.top > 0) {
                offsetY = -mRectF.top;
            }

            if (mRectF.bottom < mImageView.getHeight()) {
                offsetY = mImageView.getHeight() - mRectF.bottom;
            }
        }

        if (mRectF.width() < mImageView.getWidth()) {
            offsetX = mRectF.width() / 2 - (mRectF.right - mImageView.getWidth() / 2);
        }

        if (mRectF.height() < mImageView.getHeight()) {
            offsetY = mRectF.height() / 2 - (mRectF.bottom - mImageView.getHeight() / 2);
        }
        mMatrix.postTranslate(offsetX, offsetY);


    }

    private RectF getMatrixRectF() {//
        RectF rectF = new RectF();
        Drawable drawable = mImageView.getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mMatrix.mapRect(rectF);
        }
        return rectF;
    }

    private float getScaleFactor(float mScaleFactor, float mScale) {
        mMaxScale = getMaxScale();
        mMinScale = getMinScale();

        if (mScaleFactor <= 1 && mScale * mScaleFactor < mMinScale) {
            mScaleFactor = mMinScale / mScale;
        }

        if (mScaleFactor >= 1 && mScale * mScaleFactor > mMaxScale) {
            mScaleFactor = mMaxScale / mScale;
        }
        return mScaleFactor;
    }

    private float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    private float getTransY() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MTRANS_Y];
    }

    private float getTransX() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MTRANS_X];
    }

    private float getMaxScale() {
        return mMaxScale;
    }

    private float getMinScale() {
        Drawable drawable = mImageView.getDrawable();
        if (drawable != null) {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            float scale1 = 1.0f * getWidth() / w;
            float scale2 = 1.0f * getHeight() / h;
            mMinScale = scale2 > scale1 ? scale2 : scale1;
        }
        return mMinScale;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void addView(MapMark mark) {
        super.addView(mark);
    }


    private void initTracker(MotionEvent event){
        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
            mVelocityTracker.addMovement(event);
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof MapMark) {
//            initTracker(event);
//            MapMark mark = (MapMark)v;
//            switch (event.getAction()){
//                case event.
//            }
        } else{

            mScaleGestureDetector.onTouchEvent(event);
            int pointerCount = event.getPointerCount();
            float x = 0;
            float y = 0;
            for (int i = 0; i < pointerCount; i++) {
                x += event.getX(i);
                y += event.getY(i);
            }
            x /= pointerCount;
            y /= pointerCount;

            if (mPointerCount != pointerCount) {
                mPointerCount = pointerCount;
                startX = x;
                startY = y;
            } else {
                startX = startX == 0 ? x : startX;
                startY = startY == 0 ? y : startY;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mClickBeginTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float mOffsetX = x - startX;
                    float mOffsetY = y - startY;
                    if (isDrag(mOffsetX, mOffsetY)) {
                        RectF mRectF = getMatrixRectF();
                        if (mRectF.width() < getWidth()) {
                            mOffsetX = 0;
                        }
                        if (mRectF.height() < getHeight()) {
                            mOffsetY = 0;
                        }
                        mMatrix.postTranslate(mOffsetX, mOffsetY);
                        centerDrawable(mOffsetX, mOffsetY);
                        setImageMatrix(mMatrix);
                        requestLayout();
                    }
                    startX = x;
                    startY = y;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (System.currentTimeMillis() - mClickBeginTime < 200 && !isDrag(x - startX, y - startY)) {
                        if (isEditMode) {
                            RectF rectF = getMatrixRectF();
                            float scale = getScale();
                            mOnMarkWatcher.onAddMark(this, (startX - getTransX())/scale, (startY - getTransY())/scale);
                        }
                    }
                    startX = 0;
                    startY = 0;
                    mPointerCount = 0;
                    mClickBeginTime = 0;
            }
        }
        return true;
    }

    private boolean isDrag(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) >= mTouchSlop;
    }

    private void centerDrawable(float mOffsetX, float mOffsetY) {
        RectF mRectF = getMatrixRectF();
        float dx = 0;
        float dy = 0;
        if (mRectF.left > 0 && mOffsetX != 0) {
            dx = -mRectF.left;
        }

        if (mRectF.right < getWidth() && mOffsetX != 0) {
            dx = getWidth() - mRectF.right;
        }

        if (mRectF.top > 0 && mOffsetY != 0) {
            dy = -mRectF.top;
        }

        if (mRectF.bottom < getHeight() && mOffsetY != 0) {
            dy = getHeight() - mRectF.bottom;
        }
        mMatrix.postTranslate(dx, dy);
    }

    public interface OnMarkWatcher {
        void onAddMark(View v, float x, float y);
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public OnMarkWatcher getmOnMarkWatcher() {
        return mOnMarkWatcher;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void setOnAddMarkListener(OnMarkWatcher mOnMarkWatcher) {
        this.mOnMarkWatcher = mOnMarkWatcher;
    }

    public void removeMark(MapMark mark) {
        removeView(mark);
    }
}

