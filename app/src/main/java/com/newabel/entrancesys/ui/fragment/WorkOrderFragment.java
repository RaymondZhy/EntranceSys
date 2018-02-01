package com.newabel.entrancesys.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.WorkOrderFragmentPresenter;
import com.newabel.entrancesys.ui.activity.OrderRepairActivity;
import com.newabel.entrancesys.ui.activity.WorkOrderProcessActivity;
import com.newabel.entrancesys.ui.adapter.WorkOrderFAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.dialog.ListDialog;
import com.newabel.entrancesys.ui.iview.WorkOrderFragmentView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import butterknife.BindView;

/**
 * Date: 2018/1/30 10:36
 * Description:
 */

public class WorkOrderFragment extends BaseFragment<WorkOrderFragmentPresenter> implements WorkOrderFragmentView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener, ListDialog.OnClickListener {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    public final String mTabIndexArg = "mTabIndexArg";
    private int mTabIndex;

    private WorkOrderFAdapter workOrderFAdapter;


    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_work_order;
    }

    @Override
    protected WorkOrderFragmentPresenter createPresenter() {
        return new WorkOrderFragmentPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabIndex = getArguments().getInt(mTabIndexArg);
        }
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        srl_refresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        workOrderFAdapter = new WorkOrderFAdapter(R.layout.item_work_order_f, mPresenter.getData(mTabIndex));
        rv_list.setAdapter(workOrderFAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();

        srl_refresh.setOnRefreshListener(this);
        workOrderFAdapter.setOnItemClickListener(this);
        workOrderFAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onRefresh() {
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                srl_refresh.setRefreshing(false);
                UIUtils.showToast("列表已刷新！");
            }
        }, 2000);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //进入工单详情
        Intent intent = new Intent(getContext(), OrderRepairActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ListDialog listDialog = new ListDialog(getContext());
        String[] items = new String[]{getString(R.string.work_order_detail), getString(R.string.work_order_process)};
        listDialog.setItem(items);
        listDialog.setOnClickListener(this);
        listDialog.show();
        return false;
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which) {
            case 0: {
                Intent intent = new Intent(getContext(), OrderRepairActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                Intent intent = new Intent(getContext(), WorkOrderProcessActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
