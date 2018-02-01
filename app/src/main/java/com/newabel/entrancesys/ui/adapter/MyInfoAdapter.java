package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MyInfoEntity;

import java.util.List;

/**
 * Date: 2017/12/8 14:13
 * Description:
 */

public class MyInfoAdapter extends BaseQuickAdapter<MyInfoEntity, BaseViewHolder> {


    public MyInfoAdapter(@Nullable List<MyInfoEntity> data) {
        super(R.layout.myinfo_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyInfoEntity item) {
        helper.itemView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
