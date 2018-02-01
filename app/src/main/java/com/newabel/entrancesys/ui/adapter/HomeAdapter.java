package com.newabel.entrancesys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Column;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.viewholder.HomeNewsViewHolder;
import com.newabel.entrancesys.ui.viewholder.ModuleSecViewHolder;
import com.newabel.entrancesys.ui.viewholder.TopViewHolder;

import java.util.List;

/**
 * Date: 2017/12/5 16:04
 * Description:首页的数据适配器
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Column> columns;

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public HomeAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {

            case Column.column_style_1:
                holder = new TopViewHolder(layoutInflater.inflate(R.layout.base_top_recommend_layout, null));

                break;

            case Column.column_style_2:
                holder = new ModuleSecViewHolder(layoutInflater.inflate(R.layout.home_module_section_layout, null));

                break;

            case Column.column_style_3:
                holder = new HomeNewsViewHolder(layoutInflater.inflate(R.layout.home_news_section_layout, null));

                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Column column = columns.get(position);
        List<Content> contents = column.getContents();
        switch (viewType) {
            case Column.column_style_1:
                TopViewHolder topViewHolder = (TopViewHolder) holder;
                topViewHolder.setUp(contents);
                break;

            case Column.column_style_2:
                ModuleSecViewHolder moduleSecViewHolder = (ModuleSecViewHolder) holder;
                moduleSecViewHolder.setUp(context, contents);
                break;

            case Column.column_style_3:
                HomeNewsViewHolder homeNewsViewHolder= (HomeNewsViewHolder) holder;
                homeNewsViewHolder.setUp(context,contents);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (columns != null) {
            return columns.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Column column = columns.get(position);
        return column.getStyle();
    }
}
