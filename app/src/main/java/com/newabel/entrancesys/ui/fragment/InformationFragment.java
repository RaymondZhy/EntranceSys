package com.newabel.entrancesys.ui.fragment;

import android.support.v4.view.ViewPager;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.InformationFragAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2017/11/30 15:30
 * Description:
 */

public class InformationFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tab_infor)
    TabLayout mTab;

    @BindView(R.id.viewpager_infor)
    ViewPager mViewpager;

    private String[] tabNames = {"我的消息", "联系人", "动态"};
    private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();

    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_information;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        initFragments();
    }


    @Override
    protected void initListener() {
        InformationFragAdapter adapter = new InformationFragAdapter(getChildFragmentManager(), mFragments, tabNames);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(tabNames.length);
        mTab.setupWithViewPager(mViewpager);
        mTab.setMessageCount(1,1100);
//        mViewpager.setCurrentItem(0);
        mViewpager.addOnPageChangeListener(this);

    }

    private void initFragments() {

        for (int i = 0; i < tabNames.length; i++) {
            MyInformationFragment myInformationFragment = new MyInformationFragment();
            mFragments.add(myInformationFragment);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTab.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
