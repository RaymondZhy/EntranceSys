package com.newabel.entrancesys.ui.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MyInfoEntity;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的消息
 */
public class MyInformationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srf_myinfo)
    SwipeRefreshLayout srfMyinfo;

    @BindView(R.id.recyclerview_myinfo)
    RecyclerView recyclerView;

    @Override
    protected void loadData() {


    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_my_information;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onRefresh() {
        UIUtils.showToast("刷新数据");
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                srfMyinfo.setRefreshing(false);
            }
        }, 2000);
    }
}
