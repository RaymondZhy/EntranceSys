package com.newabel.entrancesys.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.RegisterPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.RegisterView;
import com.newabel.entrancesys.ui.widget.VerifyCodeButton;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_verify_code)
    EditText et_verify_code;

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;

    @BindView(R.id.btn_verify_code)
    VerifyCodeButton btn_verify_code;

    @BindView(R.id.btn_action)
    Button btn_action;

    @BindView(R.id.ll_verify_code)
    LinearLayout ll_verify_code;

    @BindView(R.id.ll_pwd)
    LinearLayout ll_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_register));
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
        switch (v.getId()) {
            case R.id.ll_back:
                if (ll_verify_code.getVisibility() == View.VISIBLE) {
                    finish();
                } else {
                    ll_verify_code.setVisibility(View.VISIBLE);
                    ll_pwd.setVisibility(View.GONE);
                    btn_action.setText(getString(R.string.register_str_12));
                }
                break;
            case R.id.btn_verify_code:
                btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNTING);
                break;
            case R.id.btn_action:
//                if(ll_verify_code.getVisibility() == View.VISIBLE){
//                    String phone = et_phone.getText().toString();
//                    String verifyCode = et_verify_code.getText().toString();
//                    if(mPresenter.checkInput(phone,verifyCode)) {
//                        ll_verify_code.setVisibility(View.GONE);
//                        ll_pwd.setVisibility(View.VISIBLE);
//                        btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNT_STOP);
//                        btn_action.setText(getString(R.string.register_str_13));
//                    }
//                }else{
//                    String username = et_account.getText().toString();
//                    String phone = et_phone.getText().toString();
//                    String certifno = "123";
//                    String pwd = et_pwd.getText().toString();
//                    String confirmpwd = et_confirm_pwd.getText().toString();
//                    if(mPresenter.checkInput(username,phone,certifno,pwd,confirmpwd)) {
//                        mPresenter.register(username, phone, certifno, pwd);
//                    }
//                }

                Intent intent = new Intent(this, RegisterDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (ll_verify_code.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            ll_verify_code.setVisibility(View.VISIBLE);
            ll_pwd.setVisibility(View.GONE);
            btn_action.setText(getString(R.string.register_str_12));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNT_STOP);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
