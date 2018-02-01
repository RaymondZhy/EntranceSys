package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.PwdModifyPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.PwdModifyView;

public class PwdModifyActivity extends BaseActivity<PwdModifyPresenter> implements PwdModifyView, View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private RelativeLayout rl_old_pwd;
    private EditText et_old_pwd;
    private LinearLayout ll_new_pwd;
    private EditText et_new_pwd;
    private EditText et_confirm_pwd;
    private Button btn_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pwd_modify);
    }

    @Override
    protected PwdModifyPresenter createPresenter() {
       return new PwdModifyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pwd_modify;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);

        rl_old_pwd = findViewById(R.id.rl_old_pwd);
        et_old_pwd = findViewById(R.id.et_old_pwd);
        ll_new_pwd = findViewById(R.id.ll_new_pwd);
        et_new_pwd = findViewById(R.id.et_new_pwd);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        btn_action = findViewById(R.id.btn_action);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getText(R.string.title_activity_pwd_modify));
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        btn_action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                if(rl_old_pwd.getVisibility() == View.VISIBLE) {
                    this.finish();
                }else{
                    rl_old_pwd.setVisibility(View.VISIBLE);
                    ll_new_pwd.setVisibility(View.GONE);
                    btn_action.setText(getText(R.string.pwd_modify_str_7));
                }
                break;

            case R.id.btn_action:
                if(rl_old_pwd.getVisibility() == View.VISIBLE){
                    rl_old_pwd.setVisibility(View.GONE);
                    ll_new_pwd.setVisibility(View.VISIBLE);
                    btn_action.setText(getText(R.string.pwd_modify_str_8));
                }else{
                    //TODO:执行密码信息提交
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(rl_old_pwd.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }else{
            rl_old_pwd.setVisibility(View.VISIBLE);
            ll_new_pwd.setVisibility(View.GONE);
            btn_action.setText(getText(R.string.pwd_modify_str_7));
        }
    }
}
