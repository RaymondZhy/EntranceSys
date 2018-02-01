package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.viewholder.ProgressViewHolder;
import com.newabel.entrancesys.ui.widget.StepView;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/23 0023.
 */

public class ProgressAdapter<T> extends RecyclerView.Adapter<ProgressViewHolder> {

    private Context mContext;
    private List<T> mData;
    private OnItemWatcher<T> mOnItemWatcher;

    public ProgressAdapter(Context context, List<T> mData) {
        this.mContext = context;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_progress, null);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgressViewHolder holder, int position) {
        if (position == 0) {
            holder.sv_step.setSelected(true);
            holder.sv_step.setRectTopVisible(false);
            holder.sv_step.setRectBottomVisible(true);
            holder.sv_step.setDividerVisible(true);
        } else if (position == getItemCount() - 1) {
            holder.sv_step.setSelected(false);
            holder.sv_step.setRectTopVisible(true);
            holder.sv_step.setRectBottomVisible(false);
            holder.sv_step.setDividerVisible(false);
        } else {
            holder.sv_step.setSelected(false);
            holder.sv_step.setRectTopVisible(true);
            holder.sv_step.setRectBottomVisible(true);
            holder.sv_step.setDividerVisible(true);
        }
        if (mOnItemWatcher != null) {
            mOnItemWatcher.setItemText(holder.sv_step, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemWatcher(OnItemWatcher<T> mOnItemWatcher) {
        this.mOnItemWatcher = mOnItemWatcher;
    }

    public interface OnItemWatcher<T> {
        void setItemText(StepView view, T item);
    }
}
