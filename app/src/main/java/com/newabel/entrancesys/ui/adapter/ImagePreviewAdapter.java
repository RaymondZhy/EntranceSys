package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.newabel.entrancesys.service.helper.Glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/15 0015.
 */

public class ImagePreviewAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mList;
    private List<String> list;

    public ImagePreviewAdapter(Context mContext, List<ImageView> mList, List<String> list) {
        this.mContext = mContext;
        if (mList == null) {
            mList = new ArrayList<>();
        }
        this.mList = mList;
        this.list = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = mList.get(position);
        String url = list.get(position);
        if (isResID(url)) {
            GlideHelper.showImage(mContext, Integer.valueOf(url), iv);
        } else {
            GlideHelper.showImage(mContext, url, iv);
        }
        container.addView(iv);
        return iv;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    private boolean isResID(String url) {
        String regex = "\\d+";
        return Pattern.compile(regex).matcher(url).matches();
    }
}
