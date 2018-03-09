package com.newabel.entrancesys.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.helper.Glide.GlideHelper;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/10 0010.
 */

public class TestAdapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder> {

    public TestAdapter(int layoutResId, List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {

        //iv_head tv_name tv_message tv_time
        GlideHelper.showImage(mContext,item.get("image").toString(),(ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_name,item.get("name").toString());
        helper.setText(R.id.tv_message,item.get("message").toString());
        helper.setText(R.id.tv_time,item.get("time").toString());


    }
}
