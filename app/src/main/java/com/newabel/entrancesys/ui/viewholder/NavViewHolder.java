package com.newabel.entrancesys.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.widget.NavBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/15 0015.
 */

public class NavViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.nv_view)
    public NavBarView nv_view;

    public NavViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
