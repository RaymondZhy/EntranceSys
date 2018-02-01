package com.newabel.entrancesys.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.TestPresenter;
import com.newabel.entrancesys.ui.adapter.TestAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.TestView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.MapMark;
import com.newabel.entrancesys.ui.widget.MapView;
import com.newabel.entrancesys.ui.widget.RefreshLoadMoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements SwipeRefreshLayout.OnRefreshListener, TestView, View.OnClickListener, MapView.OnMarkWatcher {

    private SwipeRefreshLayout srl_refresh;
    private RecyclerView rv_list;
    private TestAdapter adapter;
    private int page = 1;
    private int pageNow = 5;
    private List<Map<String, Object>> mData;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.iv_animation)
    ImageView iv_animation;

    @BindView(R.id.rv_step)
    RecyclerView rv_step;

    private LinearLayoutManager mLayout;
    private ArrayList<String> sList;

    @BindView(R.id.mark)
    MapMark mark;

    @BindView(R.id.mv_map)
    MapView mv_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        initViews();
//        initListeners();
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mData = mPresenter.getData();
        adapter = new TestAdapter(R.layout.item_test, mData);
        rv_list.setAdapter(adapter);

        addMark(500, 500);
    }

    @Override
    protected void initListener() {
        btn_search.setOnClickListener(this);
        srl_refresh.setOnRefreshListener(this);
//        adapter.setNewData(null);
        adapter.setLoadMoreView(new RefreshLoadMoreView());
        adapter.setEnableLoadMore(true);
        iv_animation.setOnClickListener(this);
//        srl_refresh.setRefreshing(true);
        mark.setOnClickListener(this);
        mv_map.setOnAddMarkListener(this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtil.e("AAAA", "--------------" + Thread.currentThread());

                UIUtils.postTaskDelay(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("BBBB", "--------------" + Thread.currentThread());
                        mData.addAll(mPresenter.getData());
                        adapter.setNewData(mData);
                    }
                }, 3000);

            }
        }, rv_list);
    }

    @Override
    public void onRefresh() {
//        adapter.setEnableLoadMore(false);
        adapter.setNewData(mPresenter.getData());
        srl_refresh.setRefreshing(false);
//        adapter.setEnableLoadMore(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
//                startActivity(new Intent(this,SearchRecordActivity.class));
                Intent intent = new Intent(this, OrderRepairActivity.class);
                startActivity(intent);

                break;
            case R.id.iv_animation:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_animation, "scaleX", 1.0f, 0.9f);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                break;

        }
    }


    private void addMark(float x, float y) {
        MapMark markView = new MapMark(this);
        markView.setMapX(x);
        markView.setMapY(y);
        markView.setBitmap(R.mipmap.ic_map_mark);
        markView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("您好啊");
            }
        });
        mv_map.addView(markView);
    }

    @Override
    public void onAddMark(View v, float x, float y) {
        addMark(x, y);
    }
}
