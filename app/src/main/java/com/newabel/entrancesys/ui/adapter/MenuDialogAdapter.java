package com.newabel.entrancesys.ui.adapter;

import android.widget.LinearLayout;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/13 0013.
 */

public class MenuDialogAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public MenuDialogAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_content,item);

        LinearLayout view = helper.getView(R.id.ll_content);
        if(helper.getAdapterPosition() == 0){
            view.setBackgroundResource(R.drawable.selector_dialog_sex_male);
        }else if(helper.getAdapterPosition() == mData.size()-1){
            view.setBackgroundResource(R.drawable.selector_dialog_sex_female);
        }else{
            view.setBackgroundResource(R.drawable.selector_click_style);
        }

    }
}
