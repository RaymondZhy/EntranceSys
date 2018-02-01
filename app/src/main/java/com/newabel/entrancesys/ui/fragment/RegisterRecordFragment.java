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
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.presenter.RegisterRecordFPresenter;
import com.newabel.entrancesys.ui.activity.RegisterDataActivity;
import com.newabel.entrancesys.ui.activity.RegisterProgressActivity;
import com.newabel.entrancesys.ui.adapter.RegisterRecordFAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.dialog.ListDialog;
import com.newabel.entrancesys.ui.iview.RegisterRecordFView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import butterknife.BindView;

/**
 *
 */
public class RegisterRecordFragment extends BaseFragment<RegisterRecordFPresenter> implements RegisterRecordFView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener, ListDialog.OnClickListener {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    public final String mTabIndexArg = "mTabIndexArg";
    private int mTabIndex;
    private RegisterRecordFAdapter recordFAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabIndex = getArguments().getInt(mTabIndexArg);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_register_record;
    }

    @Override
    protected RegisterRecordFPresenter createPresenter() {
        return new RegisterRecordFPresenter(this);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        srl_refresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        recordFAdapter = new RegisterRecordFAdapter(R.layout.item_register_record_f, mPresenter.getData(mTabIndex));
        rv_list.setAdapter(recordFAdapter);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initListener() {
        super.initListener();
        srl_refresh.setOnRefreshListener(this);
        recordFAdapter.setOnItemClickListener(this);
        recordFAdapter.setOnItemLongClickListener(this);
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
        switch (mTabIndex) {
            case 0:
                Constant.REGISTER_DATA_STATE = Constant.REGISTER_DATA_FILLING;
                break;
            case 1:
                Constant.REGISTER_DATA_STATE = Constant.REGISTER_DATA_REVIEWING;
                break;
            case 2:
                Constant.REGISTER_DATA_STATE = position == 0 ? Constant.REGISTER_DATA_PASS : Constant.REGISTER_DATA_REFUSED;
                break;
            case 3:
                Constant.REGISTER_DATA_STATE = Constant.REGISTER_DATA_REVIEWING;
                break;
            case 4:
                Constant.REGISTER_DATA_STATE =  Constant.REGISTER_DATA_PASS;
                break;
            case 5:
                Constant.REGISTER_DATA_STATE = Constant.REGISTER_DATA_REFUSED;
                break;
        }
        Intent intent = new Intent(getContext(), RegisterDataActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ListDialog listDialog = new ListDialog(getContext());
        String[] items = new String[]{getString(R.string.register_record_str_4), getString(R.string.register_record_str_5)};
        listDialog.setItem(items);
        listDialog.setOnClickListener(this);
        listDialog.show();
        return false;
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which) {
            case 0: {
                Intent intent = new Intent(getContext(), RegisterDataActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                Intent intent = new Intent(getContext(), RegisterProgressActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
