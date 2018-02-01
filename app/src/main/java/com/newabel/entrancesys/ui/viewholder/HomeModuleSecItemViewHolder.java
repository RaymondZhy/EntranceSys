package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/12/6 11:04
 * Description:
 */

public class HomeModuleSecItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_home_module_sec)
    LinearLayout ll_home_module_sec;
    @BindView(R.id.iv_homemodule_sec)
    ImageView mModuleItemImg;
    @BindView(R.id.tv_homemodule_sec)
    TextView mModuleItemName;

    public HomeModuleSecItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setUp(Context context, Content content) {
        if (content != null) {
            mModuleItemImg.setBackgroundResource(content.getImgResourceId());
            mModuleItemName.setText(content.getTitle());
        }
    }

}
