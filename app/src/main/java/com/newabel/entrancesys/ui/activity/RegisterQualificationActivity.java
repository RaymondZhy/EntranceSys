package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.PhotoPreInfo;
import com.newabel.entrancesys.service.helper.GlideHelper;
import com.newabel.entrancesys.service.presenter.RegisterQualificationPresenter;
import com.newabel.entrancesys.ui.Builder.PhotoBuilder;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CalendarView;
import com.newabel.entrancesys.ui.iview.RegisterQualificationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterQualificationActivity extends BaseActivity<RegisterQualificationPresenter> implements RegisterQualificationView, View.OnClickListener {

    private final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_action)
    TextView tv_action;

    @BindView(R.id.rl_company)
    RelativeLayout rl_company;

    @BindView(R.id.tv_company)
    TextView tv_company;

    @BindView(R.id.rl_job)
    RelativeLayout rl_job;

    @BindView(R.id.tv_job)
    TextView tv_job;

    @BindView(R.id.rl_date)
    RelativeLayout rl_date;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.rl_degree)
    RelativeLayout rl_degree;

    @BindView(R.id.tv_degree)
    TextView tv_degree;

    @BindView(R.id.iv_certificate)
    ImageView iv_certificate;

    @BindView(R.id.tv_tips)
    TextView tv_tips;

    private boolean isEditMode = true;
    private String url_image_certificate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_qualification);
    }

    @Override
    protected RegisterQualificationPresenter createPresenter() {
        return new RegisterQualificationPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_qualification;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getText(R.string.title_activity_register_qualification));
        tv_action.setVisibility(View.VISIBLE);
        tv_action.setText(getString(R.string.submit));

        switch (Constant.REGISTER_DATA_STATE) {
            case Constant.REGISTER_DATA_FILLING:
                tv_tips.setVisibility(View.GONE);
                break;
            case Constant.REGISTER_DATA_REVIEWING:
                tv_tips.setVisibility(View.GONE);
                tv_action.setText("审批");
                break;
            case Constant.REGISTER_DATA_PASS:
                tv_tips.setVisibility(View.VISIBLE);
                tv_tips.setText("该申请已被通过");
                tv_action.setVisibility(View.VISIBLE);
                tv_action.setText("编辑");
                break;
            case Constant.REGISTER_DATA_REFUSED:
                tv_tips.setVisibility(View.VISIBLE);
                tv_tips.setText("该申请已被拒绝，拒绝原因:未输入有效证件号码");
                tv_action.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        if (isEditMode) {
            rl_company.setOnClickListener(this);
            rl_job.setOnClickListener(this);
            rl_date.setOnClickListener(this);
            rl_degree.setOnClickListener(this);
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_action, R.id.iv_certificate,R.id.tv_example})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_action:
                if(!tv_action.getText().equals("审批")) {
                    this.finish();
                }else{
                    Intent intent = new Intent(this, RegisterCommentActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_company:

                break;
            case R.id.tv_example: {
                PhotoPreInfo preInfo = new PhotoPreInfo();
                preInfo.setUrl(String.valueOf(R.mipmap.nature));
                preInfo.setEditable(false);
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                intent.putExtra(Constant.IMAGE_DATA, preInfo);
                startActivity(intent);
                break;
            }
            case R.id.iv_certificate:
                if (url_image_certificate == null) {
                    PhotoBuilder builder = new PhotoBuilder(this);
                    builder.setPhotoCount(1).setShowCamera(true).setShowGif(false).builder();
                } else {
                    PhotoPreInfo photoPreInfo = new PhotoPreInfo();
                    photoPreInfo.setEditable(true);
                    photoPreInfo.setUrl(url_image_certificate);
                    Intent intent = new Intent(this, ImagePreviewActivity.class);
                    intent.putExtra(Constant.IMAGE_DATA, photoPreInfo);
                    startActivityForResult(intent, REQUEST_CODE_PREVIEW);
                }
                break;
            case R.id.rl_job: {
                final List<Map<String, Object>> jobs = mPresenter.getJobData(tv_job.getText().toString());
                BottomDialog dialog = new BottomDialog(this, jobs);
                dialog.setOnClickListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        tv_job.setText(jobs.get(which).get("name").toString());
                    }
                });
                dialog.show();
                break;
            }
            case R.id.rl_date:
                CalendarView calendarView = new CalendarView(this, tv_date);
                calendarView.show();
                break;
            case R.id.rl_degree: {
                final List<Map<String, Object>> degrees = mPresenter.getDegreeData(tv_degree.getText().toString());
                BottomDialog dialog = new BottomDialog(this, degrees);
                dialog.setOnClickListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        tv_degree.setText(degrees.get(which).get("name").toString());
                    }
                });
                dialog.show();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case PhotoBuilder.REQUEST_CODE_PHOTO:
                    ArrayList<String> list = data.getStringArrayListExtra(Constant.IMAGE_DATA);
                    if (list != null && list.size() > 0) {
                        url_image_certificate = list.get(0);
                        GlideHelper.showImage(this, url_image_certificate, iv_certificate);
                    }
                    break;
                case REQUEST_CODE_PREVIEW:
                    PhotoPreInfo preInfo = data.getParcelableExtra(Constant.IMAGE_DATA);
                    if (preInfo != null && preInfo.getList().size() == 0) {
                        url_image_certificate = null;
                        iv_certificate.setImageDrawable(null);
                    }
                    break;
            }
        }

    }

}
