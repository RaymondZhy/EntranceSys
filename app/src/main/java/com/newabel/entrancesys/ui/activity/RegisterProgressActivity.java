package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.RegisterProgressPresenter;
import com.newabel.entrancesys.ui.adapter.ProgressAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.RegisterProgressView;
import com.newabel.entrancesys.ui.widget.StepView;

import java.util.Map;

import butterknife.BindView;

public class RegisterProgressActivity extends BaseActivity<RegisterProgressPresenter> implements RegisterProgressView, ProgressAdapter.OnItemWatcher<Map<String, Object>> {


    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.tv_apply)
    TextView tv_apply;

    @BindView(R.id.tv_code)
    TextView tv_code;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.sv_content)
    ScrollView sv_content;

    private ProgressAdapter mProgressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_progress);
    }

    @Override
    protected RegisterProgressPresenter createPresenter() {
        return new RegisterProgressPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_progress;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mProgressAdapter = new ProgressAdapter(this, mPresenter.getData());
        mProgressAdapter.setOnItemWatcher(this);
        rv_list.setAdapter(mProgressAdapter);
        sv_content.scrollTo(0,0);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_register_progress));
        tv_state.setText("完成");
        tv_apply.setText("张三");
        tv_code.setText("336645963");
    }

    @Override
    public void setItemText(StepView view, Map<String, Object> item) {
        view.setText(item.get("content").toString());
        view.setDate(item.get("date").toString());
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
        }
    }
}
