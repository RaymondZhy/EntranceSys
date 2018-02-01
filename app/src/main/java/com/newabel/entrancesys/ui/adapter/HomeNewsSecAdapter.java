package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.viewholder.HomeNewsItemViewHolder;

import java.util.List;

/**
 * Date: 2017/12/6 14:11
 * Description:首页的新闻的适配器
 */

public class HomeNewsSecAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<Content> contents;

    LayoutInflater layoutInflater;

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public HomeNewsSecAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new HomeNewsItemViewHolder(layoutInflater.inflate(R.layout.news_item_layout, null));
        holder.itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeNewsItemViewHolder homeNewsItemViewHolder = (HomeNewsItemViewHolder) holder;
        homeNewsItemViewHolder.setUp(context, contents.get(position));
    }

    @Override
    public int getItemCount() {
        if (contents != null) {
            return contents.size();
        }
        return 0;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
