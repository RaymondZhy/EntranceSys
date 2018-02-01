package com.newabel.entrancesys.ui.fragment;


import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.base.BaseFragment;

/**
 * Date: 2017/12/1 13:54
 * Description:
 */

public class NewsListFragment extends BaseFragment {

    public NewsListFragment() {
    }



    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_newslist;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
