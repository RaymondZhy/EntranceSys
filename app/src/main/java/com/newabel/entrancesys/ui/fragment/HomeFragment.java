package com.newabel.entrancesys.ui.fragment;


import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.Column;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2017/11/30 15:29
 * Description:
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srf)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onRefresh() {
        UIUtils.showToast("刷新首页数据...");
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isFragmentVisible) {
        if (isFragmentVisible) {
            //不可见-->可见,发一个消息,轮播图开始轮播
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_START_BANNER_TURN));
        } else {
            //可见-->不可见,发一个消息,轮播图停止轮播
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_STOP_BANNER_TURN));
        }
    }
}
