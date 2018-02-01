package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.MenManagePresenter;
import com.newabel.entrancesys.ui.adapter.NavBarAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.MenManageView;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.NavBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MenManageActivity extends BaseActivity<MenManagePresenter> implements MenManageView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener, NavBarAdapter.OnNavItemWatcher<String> {

    private final int REQUEST_CODE_SEARCH = 101;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private ArrayList<String> mData;
    private MyAdapter adapter;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_men_code)
    TextView tv_men_code;

    @BindView(R.id.tv_structure_code)
    TextView tv_structure_code;

    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.tv_man_type)
    TextView tv_man_type;

    @BindView(R.id.tv_certification_code)
    TextView tv_certification_code;

    @BindView(R.id.ll_man_info)
    LinearLayout ll_man_info;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    @BindView(R.id.tv_keyword)
    TextView tv_keyword;

    @BindView(R.id.rv_nav)
    RecyclerView rv_nav;


    private String mark = "MenManageActivity";
    private List<String> mList;
    private NavBarAdapter<String> navBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_men_manage);
    }

    @Override
    protected MenManagePresenter createPresenter() {
        return new MenManagePresenter(this);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_men_manage;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        adapter = new MyAdapter(R.layout.item_men_manage, mData);
        rv_list.setAdapter(adapter);

        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        mLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_nav.setLayoutManager(mLayout);
        mList = mPresenter.getData();
        navBarAdapter = new NavBarAdapter<>(this, mList);
        rv_nav.setAdapter(navBarAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_men_manage));
        mPresenter.getMenCodeList();
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(this);
        ll_back.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        navBarAdapter.setOnNavItemWatcher(this);
    }

    @Override
    public void notifyDataSetChanged(List<String> data) {
        mData.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setData(Map<String, Object> data) {
        rv_list.setVisibility(View.GONE);
        ll_search.setVisibility(View.GONE);
        ll_man_info.setVisibility(View.VISIBLE);
        rv_nav.setVisibility(View.GONE);
        tv_name.setText(data.get("EmpName").toString());
        tv_men_code.setText(data.get("EmpNo").toString());
        tv_structure_code.setText(data.get("DeptNo").toString());
        tv_sex.setText(data.get("EmpSex").toString());
        tv_certification_code.setText(data.get("CertifNo").toString());
    }

    @Override
    public void complete() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mPresenter.getMenInfo(mData.get(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                if (rv_list.getVisibility() == View.VISIBLE) {
                    finish();
                } else {
                    rv_list.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.VISIBLE);
                    ll_man_info.setVisibility(View.GONE);
                    rv_nav.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this, SearchRecordActivity.class);
                intent.putExtra("mark", mark);
                intent.putExtra("hint", "输入机构或人员名称");
                startActivityForResult(intent, REQUEST_CODE_SEARCH);
                break;
            case R.id.tv_title:
                if (mList.size() < mPresenter.getData().size()) {
                    mList.add(mPresenter.getData().get(mList.size()));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mList.size() - 1);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SEARCH:
                    if (data != null) {
                        String keyword = data.getStringExtra("keyword");
                        if (keyword != null) {
                            ll_search.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            tv_keyword.setText(keyword);
                            UIUtils.showToast("正在搜索" + keyword);
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void setItemText(NavBarView view, String item) {
        view.setText(item);
    }

    @Override
    public void onItemClick(NavBarAdapter adapter, NavBarView view, int position) {
        for (int i = position + 1; i < mList.size(); i++) {
            mList.remove(i);
            i--;
        }
        navBarAdapter.notifyDataSetChanged();
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
        if (rv_list.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            rv_list.setVisibility(View.VISIBLE);
            ll_man_info.setVisibility(View.GONE);
            ll_search.setVisibility(View.VISIBLE);
            rv_nav.setVisibility(View.VISIBLE);
        }
    }
}
