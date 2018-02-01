package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.LanguagePresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.LanguageView;
import com.newabel.entrancesys.ui.utils.LocaleUtils;
import com.newabel.entrancesys.ui.utils.PreUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

public class LanguageActivity extends BaseActivity<LanguagePresenter> implements LanguageView, View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private int currentIndex = 0;
    private TextView[] tvArr;
    private boolean isSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_language);

//        initData();
//        initViews();
//        initListeners();
    }


    @Override
    protected void initView() {
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.title_activity_language));
        tvArr = new TextView[4];
        tvArr[0] = findViewById(R.id.tv_default);
        tvArr[1] = findViewById(R.id.tv_simplified);
        tvArr[2] = findViewById(R.id.tv_traditional);
        tvArr[3] = findViewById(R.id.tv_english);


    }

    @Override
    protected void initData() {
        currentIndex = PreUtils.getInt(PreUtils.LOCALE_SETTING,0);
        tvArr[currentIndex].setSelected(true);
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        for(int i = 0; i<tvArr.length;i++){
            tvArr[i].setOnClickListener(this);
        }
    }

    @Override
    protected LanguagePresenter createPresenter() {
        return new LanguagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_language;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if(event.getAction() == MessageEvent.ACTION_SWITCH_LANGUAGE){
            tv_title.setText(getString(R.string.title_activity_language));
            tvArr[0].setText(R.string.language_str_1);
            tvArr[1].setText(R.string.language_str_2);
            tvArr[2].setText(R.string.language_str_3);
            tvArr[3].setText(R.string.language_str_4);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        registEventBus(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregistEventBus(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                switchMainActivityPage();
                this.finish();
                break;
            case R.id.tv_default:
                //LocaleUtils.equal(LocaleUtils.getConfiguration(),Locale.getDefault()
                if(currentIndex != 0){
                    LocaleUtils.switchLanguage(this,Locale.getDefault());
                    LocaleUtils.saveConfiguration(0);
                    tvArr[currentIndex].setSelected(false);
                    currentIndex = 0;
                    tvArr[currentIndex].setSelected(true);
                    isSwitch = true;
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_SWITCH_LANGUAGE));
                }
                break;
            case R.id.tv_simplified:
                if(currentIndex != 1){
                    LocaleUtils.switchLanguage(this,Locale.CHINA);
                    LocaleUtils.saveConfiguration(1);
                    tvArr[currentIndex].setSelected(false);
                    currentIndex = 1;
                    tvArr[currentIndex].setSelected(true);
                    isSwitch = true;
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_SWITCH_LANGUAGE));
                }
                break;
            case R.id.tv_traditional:
                if(currentIndex != 2){
                    LocaleUtils.switchLanguage(this,Locale.TAIWAN);
                    LocaleUtils.saveConfiguration(2);
                    tvArr[currentIndex].setSelected(false);
                    currentIndex = 2;
                    tvArr[currentIndex].setSelected(true);
                    isSwitch = true;
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_SWITCH_LANGUAGE));
                }
                break;
            case R.id.tv_english:
                if(currentIndex != 3){
                    LocaleUtils.switchLanguage(this,Locale.US);
                    LocaleUtils.saveConfiguration(3);
                    tvArr[currentIndex].setSelected(false);
                    currentIndex = 3;
                    tvArr[currentIndex].setSelected(true);
                    isSwitch = true;
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_SWITCH_LANGUAGE));
                }
                break;
        }
    }

    private void switchMainActivityPage() {
        if(isSwitch){
            MessageEvent event = new MessageEvent(MessageEvent.ACTION_SET_MAIN_CURRENT_PAGE);
            event.setMessage("com.newabel.entranceguardsys.ui.fragment.MeFragment");
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void onBackPressed() {
        switchMainActivityPage();
        super.onBackPressed();
    }
}
