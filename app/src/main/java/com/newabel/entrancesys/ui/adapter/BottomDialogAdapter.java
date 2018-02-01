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
 * DATE: 2017/12/20 0020.
 */

public class BottomDialogAdapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder> {

    public BottomDialogAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.tv_name,item.get("name").toString());
        helper.setVisible(R.id.iv_select,Boolean.valueOf(item.get("isSelect").toString()));
    }
}
