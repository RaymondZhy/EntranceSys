package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.RegisterReviewPresenter;
import com.newabel.entrancesys.ui.adapter.RegisterRecordAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.fragment.RegisterRecordFragment;
import com.newabel.entrancesys.ui.iview.RegisterReviewView;
import com.newabel.entrancesys.ui.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterReviewActivity extends BaseActivity<RegisterReviewPresenter> implements RegisterReviewView, ViewPager.OnPageChangeListener, android.view.View.OnClickListener {

    @BindView(R.id.tl_record)
    TabLayout tl_record;

    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_review);
    }

    @Override
    protected RegisterReviewPresenter createPresenter() {
        return new RegisterReviewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_review;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_register_view));

        String[] mTabNames = new String[]{getString(R.string.register_review_str_1), getString(R.string.register_review_str_2), getString(R.string.register_review_str_3)};
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 3; i < mTabNames.length + 3; i++) {
            RegisterRecordFragment recordFragment = new RegisterRecordFragment();
            Bundle args = new Bundle();
            args.putInt(recordFragment.mTabIndexArg, i);
            recordFragment.setArguments(args);
            fragments.add(recordFragment);
        }
        RegisterRecordAdapter registerRecordAdapter = new RegisterRecordAdapter(getSupportFragmentManager(), mTabNames, fragments);
        vp_content.setAdapter(registerRecordAdapter);
        tl_record.setupWithViewPager(vp_content);
        tl_record.setMessageCount(0, 100);
    }

    @Override
    protected void initListener() {
        super.initListener();
        vp_content.addOnPageChangeListener(this);
        ll_back.setOnClickListener(this);
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
        tl_record.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
