package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.AboutUsPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.iview.AboutUsView;
import com.newabel.entrancesys.ui.utils.UIUtils;

public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsView, View.OnClickListener {

    private TextView tv_version;
    private ImageView iv_icon;
    private RelativeLayout rl_update_content;
    private RelativeLayout rl_check_version;
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView tv_new_version;
    private String version = "1.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about_us;
    }


    @Override
    protected void initView() {
        super.initView();
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        tv_version = findViewById(R.id.tv_version);
        iv_icon = findViewById(R.id.iv_icon);
        rl_update_content = findViewById(R.id.rl_update_content);
        rl_check_version = findViewById(R.id.rl_check_version);
        tv_new_version = findViewById(R.id.tv_new_version);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getText(R.string.title_activity_about_us));
        tv_version.setText(UIUtils.getAppName(this) + " " + UIUtils.getVersionName(this));
        iv_icon.setImageDrawable(UIUtils.getAppIcon(this));
        tv_new_version.setText(getString(R.string.about_us_str_6, version));
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        rl_update_content.setOnClickListener(this);
        rl_check_version.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_update_content: {
                Intent intent = new Intent();
                intent.setClass(this, UpdateDescActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_check_version:
                CommonDialog dialog = new CommonDialog(this);

                dialog.setTitle(getString(R.string.about_us_str_7, version));
                dialog.setMessage("1 增加视频聊天功能。\n2 增加聊天记录删除功能。\n3 修复已知bug。");
                dialog.setNegativeButton(getText(R.string.dialog_negative).toString());
                dialog.setPositiveButton(getText(R.string.about_us_str_8).toString());
                dialog.show();
                break;
            case R.id.iv_icon:
                Intent intent = new Intent();
                intent.setClass(this, TestActivity.class);
                startActivity(intent);
                break;
        }
    }
}
