package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.helper.GlideHelper;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/19 0019.
 */

public class RegisterRecordFAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    public RegisterRecordFAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        GlideHelper.showImage(mContext, R.mipmap.nature, (ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_name, item.get("name").toString());
        helper.setText(R.id.tv_profession, item.get("profession").toString());
        helper.setText(R.id.tv_date, item.get("date").toString());
        helper.setText(R.id.tv_state, item.get("state").toString());
    }

}
