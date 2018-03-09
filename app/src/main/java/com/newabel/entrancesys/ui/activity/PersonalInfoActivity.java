package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.helper.Glide.GlideHelper;
import com.newabel.entrancesys.service.presenter.PersonalInfoPresenter;
import com.newabel.entrancesys.ui.Builder.PhotoBuilder;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.SexDialog;

import java.util.ArrayList;

public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements View.OnClickListener {

    private final int REQUEST_CODE_DATA = 101;
    private RelativeLayout rl_user_head;
    private ImageView iv_user_head;
    private RelativeLayout rl_account;
    private TextView tv_account;
    private RelativeLayout rl_nickname;
    private TextView tv_nickname;
    private RelativeLayout rl_name;
    private TextView tv_name;
    private RelativeLayout rl_qrcode;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_tel;
    private TextView tv_tel;
    private RelativeLayout rl_phone;
    private TextView tv_phone;
    private RelativeLayout rl_email;
    private TextView tv_email;
    private RelativeLayout rl_personal_number;
    private TextView tv_personal_number;
    private RelativeLayout rl_personal_structure;
    private TextView tv_personal_structure;
    private RelativeLayout rl_personal_type;
    private TextView tv_personal_type;
    private LinearLayout ll_back;
    private TextView tv_title;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_personal_info);
    }

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        rl_user_head = findViewById(R.id.rl_user_head);
        iv_user_head = findViewById(R.id.iv_user_head);
        rl_account = findViewById(R.id.rl_account);
        tv_account = findViewById(R.id.tv_account);
        rl_nickname = findViewById(R.id.rl_nickname);
        tv_nickname = findViewById(R.id.tv_nickname);
        rl_name = findViewById(R.id.rl_name);
        tv_name = findViewById(R.id.tv_name);
        rl_qrcode = findViewById(R.id.rl_qrcode);
        rl_sex = findViewById(R.id.rl_sex);
        tv_sex = findViewById(R.id.tv_sex);
        rl_tel = findViewById(R.id.rl_tel);
        tv_tel = findViewById(R.id.tv_tel);
        rl_phone = findViewById(R.id.rl_phone);
        tv_phone = findViewById(R.id.tv_phone);
        rl_email = findViewById(R.id.rl_email);
        tv_email = findViewById(R.id.tv_email);
        rl_personal_number = findViewById(R.id.rl_personal_number);
        tv_personal_number = findViewById(R.id.tv_personal_number);
        rl_personal_structure = findViewById(R.id.rl_personal_structure);
        tv_personal_structure = findViewById(R.id.tv_personal_structure);
        rl_personal_type = findViewById(R.id.rl_personal_type);
        tv_personal_type = findViewById(R.id.tv_personal_type);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getText(R.string.title_activity_personal_info));

    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        rl_user_head.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_qrcode.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_tel.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_email.setOnClickListener(this);
        rl_personal_number.setOnClickListener(this);
        rl_personal_structure.setOnClickListener(this);
        rl_personal_type.setOnClickListener(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode && data != null){
            switch (requestCode){
                case PhotoBuilder.REQUEST_CODE_PHOTO:
                    ArrayList<String> list = data.getStringArrayListExtra(Constant.IMAGE_DATA);
                    if(list!=null && list.size()>0) {
                        GlideHelper.showAvatar(this, list.get(0), iv_user_head);
                    }
                    break;
                case REQUEST_CODE_DATA:
                    String content = data.getStringExtra("content");
                    if(content != null) {
                        setText(currentIndex, content);
                    }
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_user_head:
                PhotoBuilder builder = new PhotoBuilder(this);
                builder.setShowCamera(true)
                        .setPhotoCount(1)
                        .builder();
                break;
            case R.id.rl_account:

                break;
            case R.id.rl_nickname: {
                currentIndex = 2;
                String title = getString(R.string.personal_info_str_13);
                String hint = getString(R.string.personal_info_str_14);
                String content = tv_nickname.getText().toString();
                startActivity(title, hint, content,currentIndex);
                break;
            }
            case R.id.rl_name: {
                currentIndex = 3;
                String title = getString(R.string.personal_info_str_15);
                String hint = getString(R.string.personal_info_str_16);
                String content = tv_name.getText().toString();
                startActivity(title, hint, content,currentIndex);
                break;
            }
            case R.id.rl_qrcode:
                Intent intent = new Intent(this,QrCardActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_sex:
                SexDialog sexDialog = new SexDialog(this,tv_sex);
                sexDialog.show();
                break;
            case R.id.rl_tel: {
                currentIndex = 6;
                String title = getString(R.string.personal_info_str_17);
                String hint = getString(R.string.personal_info_str_18);
                String content = tv_tel.getText().toString();
                startActivity(title, hint, content,currentIndex);
                break;
            }
            case R.id.rl_phone: {
                currentIndex = 7;
                String title = getString(R.string.personal_info_str_19);
                String hint = getString(R.string.personal_info_str_20);
                String content = tv_phone.getText().toString();
                startActivity(title, hint, content,currentIndex);
                break;
            }
            case R.id.rl_email: {
                currentIndex = 8;
                String title = getString(R.string.personal_info_str_21);
                String hint = getString(R.string.personal_info_str_22);
                String content = tv_email.getText().toString();
                startActivity(title, hint, content,currentIndex);
                break;
            }
            case R.id.rl_personal_number:

                break;
            case R.id.rl_personal_structure:

                break;
            case R.id.rl_personal_type:

                break;

        }
    }

    private void startActivity(String title,String hint,String content,int currentIndex) {
        Intent intent = new Intent(this,PersonalInfoModifyActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("hint",hint);
        intent.putExtra("content",content);
        intent.putExtra("index",currentIndex);
        startActivityForResult(intent,REQUEST_CODE_DATA);
    }

    private void setText(int index,String content){
        switch (index){
            case 2:
                tv_nickname.setText(content);
                break;
            case 3:
                tv_name.setText(content);
                break;
            case 6:
                tv_tel.setText(content);
                break;
            case 7:
                tv_phone.setText(content);
                break;
            case 8:
                tv_email.setText(content);
                break;
        }
    }
}
