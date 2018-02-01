package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;
import java.util.Map;

/**
 * Date: 2018/1/30 11:05
 * Description:我的工单的Fragment的列表数据适配器
 */

public class WorkOrderFAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    public WorkOrderFAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
//        GlideHelper.showImage(mContext, R.mipmap.nature, (ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_work_order_title, item.get("title").toString());
        helper.setText(R.id.tv_work_order_site, "地点: " + item.get("site").toString());
        helper.setText(R.id.tv_work_order_desc, "描述: " + item.get("desc").toString());
        helper.setText(R.id.tv_work_order_time, "时间: " + item.get("date").toString());
        helper.setText(R.id.tv_state, item.get("state").toString());

    }
}
