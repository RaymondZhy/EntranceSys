package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.EntranceEventPresenter;
import com.newabel.entrancesys.ui.adapter.EntranceEventAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.EntranceEventView;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.RefreshLoadMoreView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceEventActivity extends BaseActivity<EntranceEventPresenter> implements EntranceEventView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srf_layout)
    SwipeRefreshLayout srf_layout;

    @BindView(R.id.rv_event)
    RecyclerView rv_event;

    @BindView(R.id.tv_title)
    TextView tv_title;
    private List<Map<String, Object>> eList;
    private EntranceEventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_entrance_event);
    }

    @Override
    protected EntranceEventPresenter createPresenter() {
        return new EntranceEventPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_entrance_event;
    }

    @Override
    protected void initView() {
        super.initView();
        srf_layout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));

        rv_event.setLayoutManager(new LinearLayoutManager(this));
        eList = mPresenter.getData();
        eventAdapter = new EntranceEventAdapter(R.layout.item_entrance_event, eList);
        eventAdapter.setEnableLoadMore(true);
        eventAdapter.setLoadMoreView(new RefreshLoadMoreView());
        rv_event.setAdapter(eventAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        String title = this.getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        srf_layout.setOnRefreshListener(this);
        eventAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                UIUtils.postTaskDelay(new Runnable() {
                    @Override
                    public void run() {
                        eList.addAll(mPresenter.getData());
                        eventAdapter.notifyDataSetChanged();
                        if(mPresenter.getData().size()<20) {
                            eventAdapter.loadMoreEnd();
                        }
                    }
                },3000);
            }
        }, rv_event);
    }

    @OnClick(R.id.ll_back)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        eventAdapter.setEnableLoadMore(false);
        eList.clear();
        eList.addAll(mPresenter.getData());
        eventAdapter.notifyDataSetChanged();
        srf_layout.setRefreshing(false);
        eventAdapter.setEnableLoadMore(true);

        if(mPresenter.getData().size()<20) {
            eventAdapter.loadMoreEnd();
        }
    }
}
