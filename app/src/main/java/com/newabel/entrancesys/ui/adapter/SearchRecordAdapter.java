package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.Realm.SearchRecord;
import com.newabel.entrancesys.service.presenter.SearchRecordPresenter;

import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/14 0014.
 */

public class SearchRecordAdapter extends BaseQuickAdapter<SearchRecord, BaseViewHolder> {

    private SearchRecordPresenter mPresenter;

    public SearchRecordAdapter(int layoutResId, @Nullable List<SearchRecord> data, SearchRecordPresenter mPresenter) {
        super(layoutResId, data);
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SearchRecord item) {
        helper.setText(R.id.tv_keyword, item.getKeyword());
        helper.getView(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteRecord(item);
                mData.remove(item);
                notifyDataSetChanged();

            }
        });
    }
}
