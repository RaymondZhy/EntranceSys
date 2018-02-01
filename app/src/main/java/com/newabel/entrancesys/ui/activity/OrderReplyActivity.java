package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.OrderReplyPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.ListDialog;
import com.newabel.entrancesys.ui.iview.OrderReplyView;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderReplyActivity extends BaseActivity<OrderReplyPresenter> implements OrderReplyView, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_action)
    TextView tv_action;

    @BindView(R.id.rl_state)
    RelativeLayout rl_state;

    @BindView(R.id.iv_state)
    ImageView iv_state;

    @BindView(R.id.rl_accept)
    RelativeLayout rl_accept;

    @BindView(R.id.tv_accept)
    TextView tv_accept;

    @BindView(R.id.et_remark)
    TextView et_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_reply);
    }

    @Override
    protected OrderReplyPresenter createPresenter() {
        return new OrderReplyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_reply;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_order_reply));
        tv_action.setVisibility(View.VISIBLE);
        tv_action.setText(getString(R.string.submit));
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_remark.addTextChangedListener(this);
    }


    @OnClick({R.id.ll_back, R.id.rl_state, R.id.rl_accept, R.id.tv_action})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_accept:
                ListDialog dialog = new ListDialog(this);
                final String[] items = new String[]{getString(R.string.order_reply_str_6), getString(R.string.order_reply_str_7)};
                dialog.setItem(items);
                dialog.setOnClickListener(new ListDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        tv_accept.setText(items[which]);
                    }
                });
                dialog.show();
                break;
            case R.id.rl_state:
                iv_state.setSelected(!iv_state.isSelected());
                break;
            case R.id.tv_action:
                this.finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.equals(et_remark.getText().toString().trim(),"")){
            et_remark.setGravity(Gravity.RIGHT);
        }else {
            et_remark.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
