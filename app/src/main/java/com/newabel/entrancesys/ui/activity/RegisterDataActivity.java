package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.presenter.RegisterDataPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.RegisterDataView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterDataActivity extends BaseActivity<RegisterDataPresenter> implements RegisterDataView {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_basic_state)
    ImageView iv_basic_state;

    @BindView(R.id.iv_qualification_state)
    ImageView iv_qualification_state;

    @BindView(R.id.tv__basic_state)
    TextView tv__basic_state;

    @BindView(R.id.tv__qualification_state)
    TextView tv__qualification_state;

    @BindView(R.id.rl_basic)
    RelativeLayout rl_basic;

    @BindView(R.id.rl_qualification)
    RelativeLayout rl_qualification;

    @BindView(R.id.tv_tips)
    TextView tv_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_data);
    }

    @Override
    protected RegisterDataPresenter createPresenter() {
        return new RegisterDataPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_data;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(R.string.title_activity_register_data);
        iv_qualification_state.setVisibility(View.INVISIBLE);
        switch (Constant.REGISTER_DATA_STATE) {
            case Constant.REGISTER_DATA_FILLING:
                iv_basic_state.setVisibility(View.INVISIBLE);
                tv__basic_state.setText(getString(R.string.register_data_str_5));
                iv_qualification_state.setVisibility(View.INVISIBLE);
                tv__qualification_state.setText(getString(R.string.register_data_str_5));
                tv_tips.setVisibility(View.VISIBLE);
                break;
            case Constant.REGISTER_DATA_REVIEWING:
                iv_basic_state.setVisibility(View.INVISIBLE);
                tv__basic_state.setText(getString(R.string.register_data_str_6));
                iv_qualification_state.setVisibility(View.INVISIBLE);
                tv__qualification_state.setText(getString(R.string.register_data_str_6));
                tv_tips.setVisibility(View.VISIBLE);
                tv_tips.setText("资料正在审核，请耐心等待");
                break;
            case Constant.REGISTER_DATA_PASS:
                iv_basic_state.setVisibility(View.VISIBLE);
                tv__basic_state.setText(getString(R.string.register_data_str_7));
                iv_qualification_state.setVisibility(View.VISIBLE);
                tv__qualification_state.setText(getString(R.string.register_data_str_7));
                tv_tips.setVisibility(View.VISIBLE);
                tv_tips.setText("审核已通过");
                break;
            case Constant.REGISTER_DATA_REFUSED:
                iv_basic_state.setVisibility(View.VISIBLE);
                tv__basic_state.setText(getString(R.string.register_data_str_8));
                iv_qualification_state.setVisibility(View.VISIBLE);
                tv__qualification_state.setText(getString(R.string.register_data_str_8));
                tv_tips.setVisibility(View.VISIBLE);
                tv_tips.setText("审核未通过");
        }
    }


    @OnClick({R.id.ll_back, R.id.rl_basic, R.id.rl_qualification})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_basic: {
                Intent intent = new Intent(this, RegisterBasicDataActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_qualification: {
                Intent intent = new Intent(this, RegisterQualificationActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
