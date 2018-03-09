package com.newabel.entrancesys.ui.fragment;


import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.Column;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.activity.MqttChatActivity;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2017/11/30 15:29
 * Description:
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onFragmentVisibleChange(boolean isFragmentVisible) {

    }

    @OnClick({R.id.btn_Mqtt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Mqtt:
                Intent intent = new Intent(getContext(), MqttChatActivity.class);
                startActivity(intent);
                break;
        }
    }

}
