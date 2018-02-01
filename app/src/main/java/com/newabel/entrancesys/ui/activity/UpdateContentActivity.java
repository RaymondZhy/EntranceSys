package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.UpdateContentPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.UpdateContentView;

import butterknife.BindView;

public class UpdateContentActivity extends BaseActivity<UpdateContentPresenter> implements UpdateContentView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_update_content)
    EditText et_update_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_content);
    }

    @Override
    protected UpdateContentPresenter createPresenter() {
        return new UpdateContentPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_update_content;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        String title = this.getIntent().getStringExtra("title");
        String content = this.getIntent().getStringExtra("content");

        tv_title.setText(title == null ? getText(R.string.title_activity_update_content) : title);
        et_update_content.setText(content == null ? "" : content);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
        }
    }
}
