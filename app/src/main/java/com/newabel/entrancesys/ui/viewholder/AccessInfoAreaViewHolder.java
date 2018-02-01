package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.AreaAccessInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/12/7 15:30
 * Description:门禁资料区域级别的ViewHolder
 */

public class AccessInfoAreaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.acccess_info_area_name)
    TextView areaName;


    public AccessInfoAreaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setUp(Context context, AreaAccessInfoEntity areaAccessInfoEntity) {
        areaName.setText(areaAccessInfoEntity.getAreaName());
    }
}
