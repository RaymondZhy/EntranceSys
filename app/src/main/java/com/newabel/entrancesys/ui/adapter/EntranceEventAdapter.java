package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public class EntranceEventAdapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder> {

    public EntranceEventAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
            helper.setText(R.id.tv_description,item.get("description").toString());
            helper.setText(R.id.tv_time,item.get("time").toString());
            helper.setText(R.id.tv_direction,item.get("direction").toString());
            helper.setText(R.id.tv_origin,item.get("origin").toString());
    }
}
