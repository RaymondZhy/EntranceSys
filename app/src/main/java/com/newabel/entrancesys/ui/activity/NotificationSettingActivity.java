package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.NotificationSettingPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.NotificationSetingView;

public class NotificationSettingActivity extends BaseActivity<NotificationSettingPresenter> implements NotificationSetingView, View.OnClickListener {

    private ImageView iv_sound;
    private ImageView iv_vibrate;
    private LinearLayout ll_back;
    private TextView tv_title;
    private RelativeLayout rl_sound;
    private RelativeLayout rl_vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notificaiton_setting);
    }

    @Override
    protected NotificationSettingPresenter createPresenter() {
      return new NotificationSettingPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_notificaiton_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        rl_sound = findViewById(R.id.rl_sound);
        rl_vibrate = findViewById(R.id.rl_vibrate);
        iv_sound = findViewById(R.id.iv_sound);
        iv_vibrate = findViewById(R.id.iv_vibrate);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getText(R.string.title_activity_notification_setting));
    }

    @Override
    protected void initListener() {
        super.initListener();
        rl_sound.setOnClickListener(this);
        rl_vibrate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_sound:
                iv_sound.setSelected(!iv_sound.isSelected());
                break;
            case R.id.rl_vibrate:
                iv_vibrate.setSelected(!iv_vibrate.isSelected());
                break;
        }
    }
}
