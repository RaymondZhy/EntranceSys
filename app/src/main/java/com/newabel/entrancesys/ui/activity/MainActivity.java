package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
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
import com.newabel.entrancesys.ui.fragment.MeFragment;
import com.newabel.entrancesys.ui.fragment.InformationFragment;
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

    private String TAG = "MainActivity";

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
        mFragments = new ArrayList<BaseFragment>(3);
        mFragments.add(new HomeFragment());
        mFragments.add(new InformationFragment());
        mFragments.add(new MeFragment());
    }

    @Override
    protected void initListener() {
        mTabAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
        mVpContent.setAdapter(mTabAdapter);
        //设置预加载页面为4，即打开应用，就直接全部加载。
        mVpContent.setOffscreenPageLimit(mFragments.size());
        mBottomBarLayout.setViewPager(mVpContent);

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        try {
            if (event.getAction() == MessageEvent.ACTION_SWITCH_LANGUAGE) {
                recreate();
            } else if (event.getAction() == MessageEvent.ACTION_SET_MAIN_CURRENT_PAGE) {
                if (event.getMessage() != null) {
                    Class c = Class.forName((String) event.getMessage());
                    for(int i = 0;i<mFragments.size();i++){
                        if(mFragments.get(i).getClass() == c ){
                            mVpContent.setCurrentItem(i);
                        }
                    }
                }
            }
        }catch (Exception e){
            LogUtil.e("TAG",e.getMessage());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregistEventBus(this);
    }
}
