package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.OrderRepairPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.OrderRepairView;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderRepairActivity extends BaseActivity<OrderRepairPresenter> implements OrderRepairView {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_action)
    TextView tv_action;

    @BindView(R.id.tv_tips)
    TextView tv_tips;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_cause)
    TextView tv_cause;

    @BindView(R.id.tv_device)
    TextView tv_device;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_client)
    TextView tv_client;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_complete_date)
    TextView tv_complete_date;

    @BindView(R.id.rl_employee)
    RelativeLayout rl_employee;

    @BindView(R.id.rl_address)
    RelativeLayout rl_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_repair);
    }

    @Override
    protected OrderRepairPresenter createPresenter() {
        return new OrderRepairPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_repair;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(R.string.title_activity_order_repair);
        tv_action.setVisibility(View.VISIBLE);
        tv_action.setText(R.string.order_repair_str_9);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick({R.id.rl_employee, R.id.rl_address, R.id.tv_action})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_employee: {
                Intent intent = new Intent(this, EmployeeInfoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_address:

                break;
            case R.id.tv_action: {
                Intent intent = new Intent(this, OrderReplyActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
