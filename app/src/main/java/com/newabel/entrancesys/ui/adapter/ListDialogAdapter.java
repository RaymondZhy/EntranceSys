package com.newabel.entrancesys.ui.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/17 0017.
 */

public class ListDialogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int gravity;
    private int enablePosition = -1;

    public ListDialogAdapter(int layoutResId, List<String> data, int gravity) {
        super(layoutResId, data);
        this.gravity = gravity;
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int cPosition = helper.getAdapterPosition();
        helper.setText(R.id.tv_content, item);
        LinearLayout view = helper.getView(R.id.ll_content);
        view.setGravity(gravity);
        view.setEnabled(cPosition != enablePosition);
        helper.getView(R.id.tv_content).setEnabled(cPosition != enablePosition);
        if (cPosition == 0) {
            view.setBackgroundResource(R.drawable.selector_dialog_sex_male);
        } else if (cPosition == mData.size() - 1) {
            view.setBackgroundResource(R.drawable.selector_dialog_sex_female);
        } else {
            view.setBackgroundResource(R.drawable.selector_click_style);
        }

    }

    public void notifyDataSetChanged(int gravity) {
        this.gravity = gravity;
        notifyDataSetChanged();
    }

    public void setItemEnable(int position) {
        this.enablePosition = position;
        notifyDataSetChanged();
    }

}

