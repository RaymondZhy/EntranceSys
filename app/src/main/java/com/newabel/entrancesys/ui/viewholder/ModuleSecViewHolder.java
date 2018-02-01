package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.adapter.HomeModuleSecAdapter;
import com.newabel.entrancesys.ui.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/12/6 10:41
 * Description:首页功能模块区域viewholder
 */

public class ModuleSecViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.module_sec_recyclerview)
    RecyclerView mRecyclerView;

    public ModuleSecViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setUp(Context context, List<Content> contents) {
        LogUtil.e("ModuleSecViewHolder", "------------------contents.size()" + contents.size());
        if (contents != null && contents.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            HomeModuleSecAdapter homeModuleSecAdapter = new HomeModuleSecAdapter(context);
            homeModuleSecAdapter.setContents(contents);
            mRecyclerView.setAdapter(homeModuleSecAdapter);

        } else {
            //没有数据时隐藏布局
            mRecyclerView.setVisibility(View.GONE);
        }
    }
}
