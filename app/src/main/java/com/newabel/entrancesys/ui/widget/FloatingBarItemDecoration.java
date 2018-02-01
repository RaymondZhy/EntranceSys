/**
 * Copyright 2017 ChenHao Dendi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public class FloatingBarItemDecoration extends RecyclerView.ItemDecoration {

    private final String TAG = FloatingBarItemDecoration.class.getSimpleName();

    /**
     * 水平分割线模式
     */
    public final static int MODE_HORIZONTAL_LINE = 1;

    /**
     * 浮动字母模式
     */
    public final static int MODE_LETTER = 2;

    /**
     * 水平或者垂直分割线模式 待扩展
     */
    public final static int MODE_MIXED_LINE = 3;

    private Context mContext;
    private int mTitleHeight;
    private int mBackground;
    private int mFontColor;
    private int mFontSize;

    public int mMode; //三种模式中的当前模式

    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    private int mTextHeight;
    private int mTextBaselineOffset;
    private int mTextStartMargin;
    /**
     * Integer means the related position of the Recyclerview#getViewAdapterPosition()
     * (the position of the view in original adapter's list)
     * String means the title to be drawn
     */
    private Map<Integer, String> mList;

    public FloatingBarItemDecoration(Builder builder){
        this.mContext = builder.context;
        this.mList = builder.mList;
        if(mList == null){
            this.mList = new LinkedHashMap<>();
        }

        this.mTitleHeight = builder.mTitleHeight;
        this.mBackground = builder.mBackground;
        this.mFontColor = builder.mFontColor;
        this.mFontSize = builder.mFontSize;
        this.mTextStartMargin = builder.mTextStartMargin;
        this.mMode = builder.mMode;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackground);

        mTextPaint = new Paint();
        mTextPaint.setColor(mFontColor);
        mTextPaint.setTextSize(mFontSize);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = (int) (fm.bottom - fm.top);
        mTextBaselineOffset = (int) fm.bottom;

    }

//    private FloatingBarItemDecoration(Context context, Map<Integer, String> list) {
//        this.mContext = context;
//        Resources resources = mContext.getResources();
//        this.mList = list;
//        this.mTitleHeight = resources.getDimensionPixelSize(R.dimen.item_decoration_title_height);
//
//        mBackgroundPaint = new Paint();
//        mBackgroundPaint.setColor(ContextCompat.getColor(mContext, R.color.item_decoration_title_background));
//
//        mTextPaint = new Paint();
//        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.item_decoration_title_fontcolor));
//        mTextPaint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.item_decoration_title_fontsize));
//
//        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//        mTextHeight = (int) (fm.bottom - fm.top);
//        mTextBaselineOffset = (int) fm.bottom;
//        mTextStartMargin = resources.getDimensionPixelOffset(R.dimen.item_decoration_title_start_margin);
//    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        outRect.set(0, mList.containsKey(position) ? mTitleHeight : 0, 0, 0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewAdapterPosition();
            switch (mMode) {
                case MODE_HORIZONTAL_LINE:
//                    drawLine(c,left,right,child);
                    int rectBottom = child.getBottom();
                    c.drawRect(left, rectBottom - mTitleHeight, right,
                            rectBottom, mBackgroundPaint);
                    break;
                case MODE_LETTER:
                    if (!mList.containsKey(position)) {
                        continue;
                    }
                    drawTitleArea(c, left, right, child, params, position);
                    break;
                case MODE_MIXED_LINE:

                    break;
            }
        }
    }

    private void drawLine(Canvas c, int left, int right, View child){
        c.drawRect(new RectF(left,child.getTop(),right,child.getBottom()),mBackgroundPaint);
    }

    private void drawTitleArea(Canvas c, int left, int right, View child,
                               RecyclerView.LayoutParams params, int position) {
        final int rectBottom = child.getTop() - params.topMargin;
        c.drawRect(left, rectBottom - mTitleHeight, right,
                rectBottom, mBackgroundPaint);
        c.drawText(mList.get(position), child.getPaddingLeft() + mTextStartMargin,
                rectBottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset, mTextPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        final int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        View child = parent.findViewHolderForAdapterPosition(position).itemView;
        String initial = getTag(position);
        if (initial == null) {
            return;
        }

        boolean flag = false;
        if (getTag(position + 1) != null && !initial.equals(getTag(position + 1))) {
            if (child.getHeight() + child.getTop() < mTitleHeight) {
                c.save();
                flag = true;
                c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
            }
        }

        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(),
                parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mBackgroundPaint);
        c.drawText(initial, child.getPaddingLeft() + mTextStartMargin,
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset, mTextPaint);

        if (flag) {
            c.restore();
        }
    }

    private String getTag(int position) {
        while (position >= 0) {
            if (mList.containsKey(position)) {
                return mList.get(position);
            }
            position--;
        }
        return null;
    }

    public static class Builder{
        private Context context;
        private Map<Integer, String> mList;
        private int mTitleHeight;     //浮动分割线的高
        private int mBackground; //浮动分割线的背景
        private int mFontColor;       //字体颜色
        private int mFontSize;        //字体大小
        private int mTextStartMargin;     //浮动分割线据左的距离
        private int mMode;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setList(Map<Integer, String> mList) {
            this.mList = mList;
            return this;
        }

        public Builder setTitleHeight(int mTitleHeight) {
            this.mTitleHeight = mTitleHeight;
            return this;
        }

        public Builder setBackground(int mBackground) {
            this.mBackground = mBackground;
            return this;
        }

        public Builder setFontColor(int mFontColor) {
            this.mFontColor = mFontColor;
            return this;
        }

        public Builder setFontSize(int mFontSize) {
            this.mFontSize = mFontSize;
            return this;
        }

        public Builder setTextStartMargin(int mTextStartMargin) {
            this.mTextStartMargin = mTextStartMargin;
            return this;
        }

        public Builder setMode(int mMode){
            this.mMode = mMode;
            return this;
        }

        public FloatingBarItemDecoration builder(){
            return new FloatingBarItemDecoration(this);
        }


    }

}

