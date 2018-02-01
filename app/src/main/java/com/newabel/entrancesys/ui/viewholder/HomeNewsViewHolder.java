package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.adapter.HomeNewsSecAdapter;
import com.newabel.entrancesys.ui.widget.ItemDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Date: 2017/12/6 14:13
 * Description:
 */

public class HomeNewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rl_news_section)
    RelativeLayout rlNewsTitleSection;

    @BindView(R.id.home_news_sec_title)
    TextView newsSecTitle;

    @BindView(R.id.news_recyclerview)
    RecyclerView mRecyclerView;

    public HomeNewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void setUp(Context context, List<Content> contents) {

        if (contents != null || contents.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new ItemDivider(context, R.drawable.item_divider));
            HomeNewsSecAdapter homeNewsSecAdapter = new HomeNewsSecAdapter(context);
            homeNewsSecAdapter.setContents(contents);
            mRecyclerView.setAdapter(homeNewsSecAdapter);
        } else {
            rlNewsTitleSection.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }


    }
}
