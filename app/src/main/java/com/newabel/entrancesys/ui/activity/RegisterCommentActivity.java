package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.RegisterCommentPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.iview.RegisterCommentView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterCommentActivity extends BaseActivity<RegisterCommentPresenter> implements RegisterCommentView {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.rl_comment)
    RelativeLayout rl_comment;

    @BindView(R.id.tv_comment)
    TextView tv_comment;

    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.tv_action)
    TextView tv_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_comment);
    }

    @Override
    protected RegisterCommentPresenter createPresenter() {
        return new RegisterCommentPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_comment;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_action.setVisibility(View.VISIBLE);
        tv_action.setText(getString(R.string.submit));
        tv_title.setText(getString(R.string.title_activity_register_comment));
    }

    @OnClick({R.id.ll_back, R.id.rl_comment, R.id.tv_action})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_comment:
                final List<Map<String, Object>> list = mPresenter.getData(tv_comment.getText().toString());
                BottomDialog dialog = new BottomDialog(this, mPresenter.getData(tv_comment.getText().toString()));
                dialog.setOnClickListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        tv_comment.setText(list.get(which).get("name").toString());
                    }
                });
                dialog.show();
                break;
            case R.id.tv_action:
                UIUtils.showToast("提交成功");
                this.finish();
                break;
        }
    }
}
