package com.newabel.entrancesys.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.WorkOrderProcessPresenter;
import com.newabel.entrancesys.ui.adapter.ProgressAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.WorkOrderProcessView;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.StepView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 单据流程
 */
public class WorkOrderProcessActivity extends BaseActivity<WorkOrderProcessPresenter> implements WorkOrderProcessView, ProgressAdapter.OnItemWatcher<Map<String, Object>> {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_workorder_state)
    TextView tvOrderState;

    @BindView(R.id.tv_workorder_name)
    TextView tvOrderName;

    @BindView(R.id.tv_workorder_code)
    TextView tvOrderCode;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.sv_content)
    ScrollView sv_content;

    private ProgressAdapter mProgressAdapter;

    @Override
    protected WorkOrderProcessPresenter createPresenter() {
        return new WorkOrderProcessPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work_order_process;
    }


    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(UIUtils.getString(R.string.work_order_process));
        tvOrderState.setText("待确认");
        tvOrderName.setText("门禁维修");
        tvOrderCode.setText("12345678");

        rvList.setLayoutManager(new LinearLayoutManager(this));
        mProgressAdapter = new ProgressAdapter(this, mPresenter.getData());
        mProgressAdapter.setOnItemWatcher(this);
        rvList.setAdapter(mProgressAdapter);


    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.ll_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void setItemText(StepView view, Map<String, Object> item) {
        view.setText(item.get("content").toString());
        view.setDate(item.get("date").toString());
    }
}
