package com.newabel.entrancesys.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.widget.StepView;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/23 0023.
 */

public class ProgressViewHolder extends RecyclerView.ViewHolder {
    public StepView sv_step;

    public ProgressViewHolder(View itemView) {
        super(itemView);
        sv_step = itemView.findViewById(R.id.sv_step);
    }
}
