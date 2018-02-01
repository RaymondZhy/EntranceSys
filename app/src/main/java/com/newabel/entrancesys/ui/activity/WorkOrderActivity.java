package com.newabel.entrancesys.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.WorkOrderPresenter;
import com.newabel.entrancesys.ui.adapter.RegisterRecordAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.fragment.WorkOrderFragment;
import com.newabel.entrancesys.ui.iview.WorkOrderView;
import com.newabel.entrancesys.ui.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的工单
 */
public class WorkOrderActivity extends BaseActivity<WorkOrderPresenter> implements WorkOrderView, ViewPager.OnPageChangeListener {


    @BindView(R.id.tl_record_workorder)
    TabLayout tlRecord;

    @BindView(R.id.vp_content_workorder)
    ViewPager vpContent;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.ll_back)
    LinearLayout llBack;

    @Override
    protected WorkOrderPresenter createPresenter() {
        return new WorkOrderPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work_place;
    }

    @Override
    protected void initData() {
        super.initData();

        tvTitle.setText(getString(R.string.title_activity_work_wrder));

        String[] mTabNames = new String[]{getString(R.string.work_order_state_start), getString(R.string.work_order_state_unconfirm), getString(R.string.work_order_state_reject),
                getString(R.string.work_order_state_done)};
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 4; i < mTabNames.length + 4; i++) {
            WorkOrderFragment workOrderFragment = new WorkOrderFragment();
            Bundle args = new Bundle();
            args.putInt(workOrderFragment.mTabIndexArg, i);
            workOrderFragment.setArguments(args);
            fragments.add(workOrderFragment);
        }
        RegisterRecordAdapter registerRecordAdapter = new RegisterRecordAdapter(getSupportFragmentManager(), mTabNames, fragments);
        vpContent.setAdapter(registerRecordAdapter);
        tlRecord.setupWithViewPager(vpContent);
        tlRecord.setMessageCount(0, 5);


    }

    @Override
    protected void initListener() {
        super.initListener();
        vpContent.addOnPageChangeListener(this);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tlRecord.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
