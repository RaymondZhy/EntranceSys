package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.PersonalInfoModifyPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.PersonalInfoModifyView;
import com.newabel.entrancesys.ui.utils.MatchUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.VerifyCodeButton;

import butterknife.BindView;

public class PersonalInfoModifyActivity extends BaseActivity<PersonalInfoModifyPresenter> implements PersonalInfoModifyView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.et_verify_code)
    EditText et_verify_code;

    @BindView(R.id.btn_verify_code)
    VerifyCodeButton btn_verify_code;

    @BindView(R.id.rl_verify_code)
    RelativeLayout rl_verify_code;

    private String hint;
    private String title;
    private String content;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_personal_info_modify);
    }

    @Override
    protected PersonalInfoModifyPresenter createPresenter() {
        return new PersonalInfoModifyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_personal_info_modify;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        title = this.getIntent().getStringExtra("title");
        content = this.getIntent().getStringExtra("content");
        hint = this.getIntent().getStringExtra("hint");
        index = this.getIntent().getIntExtra("index", 0);
        if (title != null) {
            tv_title.setText(title);
        }

        if (content != null) {
            et_content.setText(content);
            et_content.setSelection(content.length());
        }

        if (hint != null) {
            et_content.setHint(hint);
        }

        if (index == 7) {
            rl_verify_code.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkInput() {
        String content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            UIUtils.showToast(hint);
            return false;
        }

        switch (index) {
            case 2://昵称

                break;
            case 3://姓名

                break;
            case 6:
                if (!MatchUtils.isPhoneNumber(content)) {
                    String str = getString(R.string.personal_info_modify_str_4);
                    UIUtils.showToast(str);
                    return false;
                }
                break;
            case 7: //手机
                if (!MatchUtils.isMobieNumbler(content)) {
                    String str = getString(R.string.personal_info_modify_str_5);
                    UIUtils.showToast(str);
                    return false;
                }

                if (TextUtils.isEmpty(et_verify_code.getText().toString())) {
                    String str = getString(R.string.personal_info_modify_str_3);
                    UIUtils.showToast(str);
                    return false;
                }
                break;
            case 8:
                if (!MatchUtils.isEmail(content)) {
                    String str = getString(R.string.personal_info_modify_str_6);
                    UIUtils.showToast(str);
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        btn_verify_code.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_save:
                if(checkInput()){
                    Intent intent = new Intent();
                    intent.putExtra("content",et_content.getText().toString());
                    setResult(RESULT_OK,intent);
                    this.finish();
                }
                break;
            case R.id.btn_verify_code:
                btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNTING);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        btn_verify_code.setButtonStatus(VerifyCodeButton.STATUS_COUNT_STOP);
        super.onDestroy();
    }
}
