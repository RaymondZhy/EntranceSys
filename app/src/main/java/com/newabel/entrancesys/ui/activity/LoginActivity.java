package com.newabel.entrancesys.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.entity.User;
import com.newabel.entrancesys.service.presenter.LoginPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.LoginView;
import com.newabel.entrancesys.ui.utils.StatusBarUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;

    @BindView(R.id.tv_register)
    TextView tv_register;

    @BindView(R.id.iv_setting)
    ImageView iv_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        StatusBarUtils.setTranslucentStatusBar(this);
        registEventBus(this);
    }

    @Override
    protected void onDestroy() {
        unregistEventBus(this);
        super.onDestroy();
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();
        if(isLogin){
            et_account.setText(User.getInstance().getUserID());
            et_pwd.setText(User.getInstance().getUserPwd());
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_login.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_login:
                mPresenter.login(et_account.getText().toString(),et_pwd.getText().toString());
                break;
            case R.id.tv_forget_pwd:
                intent.setClass(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_setting:
                intent.setClass(this, IpSettingActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void startActivity() {
//        Intent intent = new Intent(UIUtils.getContext(), MainActivity.class);
        Intent intent = new Intent(UIUtils.getContext(), RegisterRecordActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event != null){
            if(TextUtils.equals("UPDATE_URL", (String)event.getMessage())){//IP设置
                recreate();
            }
        }
    }
}
