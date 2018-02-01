package com.newabel.entrancesys.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.OrganizaManagePresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.OrganizaManageView;
import com.newabel.entrancesys.ui.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 机构管理
 */
public class OrganizaManageActivity extends BaseActivity<OrganizaManagePresenter> implements OrganizaManageView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.recyclerview_organiza)
    RecyclerView mRecyclerview;

    @BindView(R.id.ll_organiza_info)
    LinearLayout llOrganizaInfo;

    @BindView(R.id.tv_organiza_name)
    TextView organizaName;

    @BindView(R.id.tv_organiza_code)
    TextView organizaCode;

    @BindView(R.id.tv__higher_level_organiza_code)
    TextView organizaHigherCode;

    @BindView(R.id.tv_organiza_type)
    TextView organizaType;

    private MyAdapter myAdapter;

    private List<String> mData = new ArrayList<String>();

    @Override
    protected OrganizaManagePresenter createPresenter() {
        return new OrganizaManagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_organiza_manage;
    }

    @Override
    protected void initView() {
        tvTitle.setText("机构管理");

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.item_organiza_manage, mData);
        mRecyclerview.setAdapter(myAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.getOrganizeCodeList();
    }

    @Override
    protected void initListener() {
        myAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.ll_back})
    void onIconClicked() {
        if (mRecyclerview.getVisibility() == View.VISIBLE) {
            this.finish();
        } else {
            mRecyclerview.setVisibility(View.VISIBLE);
            llOrganizaInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mPresenter.getMenInfo(mData.get(position));
    }

    @Override
    public void onGetDataSuccess(List<String> data) {
        LogUtil.e("OrganizaManageActivity", "onGetDataSuccess...data:" + (data == null ? "null" : "not null"));
        if (data != null) {
            mData.addAll(data);
            myAdapter.setNewData(mData);
        }
    }

    @Override
    public void onGetDataError() {

    }

    @Override
    public void setData(Map<String, Object> retData) {
        mRecyclerview.setVisibility(View.GONE);
        llOrganizaInfo.setVisibility(View.VISIBLE);
        organizaName.setText(retData.get("DeptName").toString());
        organizaCode.setText(retData.get("DeptNo").toString());
        organizaHigherCode.setText(retData.get("ParentNo").toString());
        organizaType.setText(retData.get("DeptType").toString());

    }

    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_code, item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mRecyclerview.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            mRecyclerview.setVisibility(View.VISIBLE);
            llOrganizaInfo.setVisibility(View.GONE);
        }
    }
}
