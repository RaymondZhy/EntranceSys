package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.utils.UIUtils;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/17 0017.
 */

public class TabLayout extends android.support.design.widget.TabLayout {

    private int mLastSelectedTabPosition;
    private int mTabSelectedTextColor;
    private int mTabTextSize;
    private int mTabTextColor;

    public TabLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, android.support.design.R.styleable.TabLayout,
                    defStyleAttr, android.support.design.R.style.Widget_Design_TabLayout);
            //Tab文字选中颜色
            try {
                mTabSelectedTextColor = a.getColor(android.support.design.R.styleable.TabLayout_tabSelectedTextColor, ContextCompat.getColor(getContext(), R.color.colorPrimary));
                mTabTextColor = a.getColor(android.support.design.R.styleable.TabLayout_tabTextColor, 0);
                int tabTextAppearance = a.getResourceId(android.support.design.R.styleable.TabLayout_tabTextAppearance, android.support.design.R.style.TextAppearance_Design_Tab);
                final TypedArray ta = getContext().obtainStyledAttributes(tabTextAppearance, android.support.v7.appcompat.R.styleable.TextAppearance);
                try {
                    //Tab字体大小
                    mTabTextSize = ta.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, 0);
                    //Tab文字颜色
                    if (mTabTextColor == 0) {
                        mTabTextColor = ta.getColor(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor, ContextCompat.getColor(getContext(), R.color.text_tittle));
                    }
                } finally {
                    ta.recycle();
                }

            } finally {
                a.recycle();
            }
        }
    }


    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        View view = getTabView();
        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(param);
        tab.setCustomView(view);
        super.addTab(tab, position, setSelected);
    }

    public void setSelected(int position) {
        setUnSelected(mLastSelectedTabPosition);
        TextView tv = (TextView) ((ViewGroup) getTabAt(position).getCustomView()).getChildAt(0);
        tv.setTextColor(mTabSelectedTextColor);
        this.mLastSelectedTabPosition = position;
    }

    public void setUnSelected(int position) {
        TextView tv = (TextView) ((ViewGroup) getTabAt(position).getCustomView()).getChildAt(0);
        tv.setTextColor(mTabTextColor);
    }


    public void setMessageCount(int position, int count) {
        TextView tv = (TextView) ((ViewGroup) getTabAt(position).getCustomView()).getChildAt(1);
        if (count <= 0) {
            tv.setText(String.valueOf(count));
            tv.setVisibility(INVISIBLE);
        } else if (count > 99) {
            tv.setText(99 + "+");
            tv.setVisibility(VISIBLE);
        } else {
            tv.setText(String.valueOf(count));
            tv.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        PagerAdapter adapter = viewPager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            ((TextView) ((ViewGroup) getTabAt(i).getCustomView()).getChildAt(0)).setText(adapter.getPageTitle(i));
        }
        int position = getSelectedTabPosition();
        position = position >= 0 ? position : 0;
        setSelected(position);
        mLastSelectedTabPosition = position;
    }

    public RelativeLayout getTabView() {
        RelativeLayout rl = new RelativeLayout(getContext());
        TextView tv_item = new TextView(getContext());
        tv_item.setGravity(Gravity.CENTER);
        tv_item.setTextColor(mTabTextColor);
        tv_item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_item.setLayoutParams(param);
        rl.addView(tv_item);

        TextView tv_count = new TextView(getContext());
        tv_count.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        tv_count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tv_count.setBackgroundResource(R.drawable.shape_messge_num);
        tv_count.setVisibility(INVISIBLE);
        tv_count.setMinWidth(UIUtils.dip2Px(18));
        tv_count.setMinHeight(UIUtils.dip2Px(18));
        param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
        param.setMargins(0, UIUtils.dip2Px(2), UIUtils.dip2Px(2), 0);
        tv_count.setLayoutParams(param);
        tv_count.setGravity(Gravity.CENTER);
        rl.addView(tv_count);
        return rl;
    }
}
