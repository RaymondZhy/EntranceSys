package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;

import com.idlestar.ratingstar.RatingStarView;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.EmployeeInfoPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.EmployeeInfoView;

import butterknife.BindView;

public class EmployeeInfoActivity extends BaseActivity<EmployeeInfoPresenter> implements EmployeeInfoView {

    @BindView(R.id.rsv_rating)
    RatingStarView rsv_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_employee_info);
    }

    @Override
    protected EmployeeInfoPresenter createPresenter() {
        return new EmployeeInfoPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_employee_info;
    }

    @Override
    protected void initView() {
        super.initView();
        rsv_rating.setEnabled(false);
    }
}
