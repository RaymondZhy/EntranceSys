package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.RegisterRecordPresenter;
import com.newabel.entrancesys.ui.adapter.RegisterRecordAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.fragment.RegisterRecordFragment;
import com.newabel.entrancesys.ui.iview.RegisterRecordView;
import com.newabel.entrancesys.ui.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterRecordActivity extends BaseActivity<RegisterRecordPresenter> implements RegisterRecordView, ViewPager.OnPageChangeListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tl_record)
    TabLayout tl_record;

    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    private String[] mTabName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_record);
    }

    @Override
    protected RegisterRecordPresenter createPresenter() {
        return new RegisterRecordPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_record;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_back.setVisibility(View.INVISIBLE);
        tv_title.setText(getString(R.string.title_activity_register_record));
        iv_add.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mTabName = new String[]{getString(R.string.register_record_str_1), getString(R.string.register_record_str_2), getString(R.string.register_record_str_3)};
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTabName.length; i++) {
            RegisterRecordFragment fragment = new RegisterRecordFragment();
            Bundle args = new Bundle();
            args.putInt(fragment.mTabIndexArg, i);
            fragment.setArguments(args);
            fragments.add(fragment);
        }

        RegisterRecordAdapter registerRecordAdapter = new RegisterRecordAdapter(getSupportFragmentManager(), mTabName, fragments);
        vp_content.setAdapter(registerRecordAdapter);
        tl_record.setupWithViewPager(vp_content);
        tl_record.setMessageCount(1, 10);
    }

    @Override
    protected void initListener() {
        super.initListener();
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tl_record.setSelected(position);
        iv_add.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
    }


    @OnClick({R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                Intent intent = new Intent(this,RegisterDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
