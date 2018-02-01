package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.handler.OpenHandler;
import com.newabel.entrancesys.ui.viewholder.HomeModuleSecItemViewHolder;

import java.util.List;

/**
 * Date: 2017/12/6 11:00
 * Description: 首页的功能区块的适配器（轮播图下方的那一块）
 */

public class HomeModuleSecAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutInflater;
    private Context context;

    private List<Content> contents;


    public HomeModuleSecAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new HomeModuleSecItemViewHolder(layoutInflater.inflate(R.layout.home_module_section_item_layout, null));
        holder.itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Content content = contents.get(position);
//        Glide.with(context).load(R.drawable.ic_launcher_background).into(mModuleItemImg);
        HomeModuleSecItemViewHolder moduleSecHolder = (HomeModuleSecItemViewHolder) holder;
        moduleSecHolder.itemView.setTag(content);
        moduleSecHolder.setUp(context, content);
    }

    @Override
    public int getItemCount() {
        if (contents != null) {
            return contents.size();
        }
        return 0;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Content content = (Content) v.getTag();
            OpenHandler.openActivity(v.getContext(), content);
        }
    };

}
