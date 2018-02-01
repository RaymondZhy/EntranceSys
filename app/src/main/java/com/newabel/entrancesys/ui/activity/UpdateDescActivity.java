package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.UpdateDescPresenter;
import com.newabel.entrancesys.ui.adapter.UpdateDescAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.UpdateDescView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class UpdateDescActivity extends BaseActivity<UpdateDescPresenter> implements UpdateDescView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private UpdateDescAdapter adapter;
    private List<Map<String, Object>> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_desc);
    }

    @Override
    protected UpdateDescPresenter createPresenter() {
        return new UpdateDescPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_update_desc;
    }

    @Override
    protected void initView() {
        super.initView();

        mList = mPresenter.getData();
        adapter = new UpdateDescAdapter(R.layout.item_update_desc,mList);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getText(R.string.title_activity_update_desc));
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter,View view, int position) {
        Intent intent = new Intent(this, UpdateContentActivity.class);
        intent.putExtra("title",mList.get(position).get("title").toString());
        intent.putExtra("content",mList.get(position).get("content").toString());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
        }
    }
}
