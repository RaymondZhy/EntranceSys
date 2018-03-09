package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.MainTabAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.fragment.HomeFragment;
import com.newabel.entrancesys.ui.fragment.ManageFragment;
import com.newabel.entrancesys.ui.fragment.MeFragment;
import com.newabel.entrancesys.ui.fragment.InformationFragment;
import com.newabel.entrancesys.ui.service.ActiveMqService;
import com.newabel.entrancesys.ui.service.ActiveMqService2;
import com.newabel.entrancesys.ui.service.MyService;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.widget.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragments;

    private MainTabAdapter mTabAdapter;

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent service1 = new Intent(this, MyService.class);
//        startService(service1);

        Intent service2 = new Intent(ActiveMqService2.ACTION_CONNECT);
        service2.setClass(this,ActiveMqService2.class);
        startService(service2);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new InformationFragment());
        mFragments.add(new ManageFragment());
        mFragments.add(new MeFragment());
    }

    @Override
    protected void initListener() {
        mTabAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
        mBottomBarLayout.setViewPager(mVpContent);

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {

            }
        });
    }
}
