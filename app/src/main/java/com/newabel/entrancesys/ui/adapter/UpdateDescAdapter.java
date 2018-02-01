package com.newabel.entrancesys.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/7 0007.
 */

public class UpdateDescAdapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder> {

    public UpdateDescAdapter(int layoutResId, List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.tv_title,item.get("title").toString());
        helper.setText(R.id.tv_date,item.get("date").toString());
    }
}
