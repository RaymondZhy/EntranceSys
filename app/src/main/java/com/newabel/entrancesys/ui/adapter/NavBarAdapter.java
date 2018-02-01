package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.viewholder.NavViewHolder;
import com.newabel.entrancesys.ui.widget.NavBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/15 0015.
 */

public class NavBarAdapter<T> extends RecyclerView.Adapter<NavViewHolder> {
    private Context context;
    private List<T> mData;
    private OnNavItemWatcher mOnNavItemWatcher;

    public NavBarAdapter(Context context, List<T> mData) {
        this.context = context;
        this.mData = mData == null ? new ArrayList<T>() : mData;
    }


    public NavBarAdapter(Context context, List<T> mData, OnNavItemWatcher mOnNavItemWatcher) {
        this.context = context;
        this.mData = mData == null ? new ArrayList<T>() : mData;
        this.mOnNavItemWatcher = mOnNavItemWatcher;
    }

    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nav_bar, null);
        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavViewHolder holder, int position) {
        holder.nv_view.setTag(position);
        if(position == mData.size()-1){
            holder.nv_view.setSelected(true);
        }else {
            holder.nv_view.setSelected(false);
        }
        if(mOnNavItemWatcher != null) {
            mOnNavItemWatcher.setItemText(holder.nv_view, mData.get(position));
            holder.nv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnNavItemWatcher.onItemClick(NavBarAdapter.this,(NavBarView)v,(int)v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnNavItemWatcher(OnNavItemWatcher mOnNavItemWatcher){
        this.mOnNavItemWatcher = mOnNavItemWatcher;
    }

    public interface OnNavItemWatcher<T> {
        void setItemText(NavBarView view, T item);
        void onItemClick(NavBarAdapter adapter, NavBarView view, int position);
    }
}
