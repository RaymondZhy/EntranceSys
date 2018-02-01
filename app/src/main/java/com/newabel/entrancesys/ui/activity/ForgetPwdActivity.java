package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.ForgetPwdPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.ForgetPwdView;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.VerifyCodeButton;

import butterknife.BindView;

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_verify_code)
    EditText et_verify_code;

    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;

    @BindView(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;

    @BindView(R.id.btn_verify_code)
    VerifyCodeButton btn_verify_code;

    @BindView(R.id.btn_action)
    Button btn_action;

    @BindView(R.id.ll_verify_code)
    LinearLayout ll_verify_code;

    @BindView(R.id.ll_new_pwd)
    LinearLayout ll_new_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forget_pwd);
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
       return new ForgetPwdPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(R.string.title_activity_forget_pwd);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        btn_action.setOnClickListener(this);
        btn_verify_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                if(ll_verify_code.getVisibility() == View.VISIBLE){
                    finish();
                }else{
                    ll_verify_code.setVisibility(View.VISIBLE);
                    ll_new_pwd.setVisibility(View.GONE);
                    btn_action.setText(getString(R.string.forget_pwd_str_10));
                }
                break;
            case R.id.btn_verify_code:
                btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNTING);
                break;
            case R.id.btn_action:
                if(ll_verify_code.getVisibility() == View.VISIBLE){
                    ll_verify_code.setVisibility(View.GONE);
                    ll_new_pwd.setVisibility(View.VISIBLE);
                    btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNT_STOP);
                    btn_action.setText(getString(R.string.forget_pwd_str_11));
//                    btn_verify_code.setText(getString(R.string.forget_pwd_str_5));
                }else{
                    this.finish();
                    UIUtils.showToast(getString(R.string.forget_pwd_str_12));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(ll_verify_code.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else{
            ll_verify_code.setVisibility(View.VISIBLE);
            ll_new_pwd.setVisibility(View.GONE);
            btn_action.setText(getString(R.string.forget_pwd_str_10));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNT_STOP);
    }
}
