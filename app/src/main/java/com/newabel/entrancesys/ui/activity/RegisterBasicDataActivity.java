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
import com.newabel.entrancesys.service.presenter.RegisterBasicDataPresenter;
import com.newabel.entrancesys.ui.Builder.PhotoBuilder;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CalendarView;
import com.newabel.entrancesys.ui.iview.RegisterBasicDataView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterBasicDataActivity extends BaseActivity<RegisterBasicDataPresenter> implements RegisterBasicDataView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_action)
    TextView tv_action;

    @BindView(R.id.tv_example_head)
    TextView tv_example_head;

    @BindView(R.id.tv_example_card)
    TextView tv_example_card;

    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;

    @BindView(R.id.iv_card_front)
    ImageView iv_card_front;

    @BindView(R.id.iv_card_back)
    ImageView iv_card_back;

    @BindView(R.id.rl_sex)
    RelativeLayout rl_sex;

    @BindView(R.id.rl_ethnic)
    RelativeLayout rl_ethnic;

    @BindView(R.id.rl_birthday)
    RelativeLayout rl_birthday;

    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.tv_ethnic)
    TextView tv_ethnic;

    @BindView(R.id.tv_birthday)
    TextView tv_birthday;

    @BindView(R.id.tv_tips)
    TextView tv_tips;

    private final int REQUEST_CODE_PREVIEW = 101;

    private int INDEX_IMAGE_CURRENT = 0;

    private String url_image_head;
    private String url_image_card_front;
    private String url_image_card_back;
    private boolean isEditMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_basic_data);
    }

    @Override
    protected RegisterBasicDataPresenter createPresenter() {
        return new RegisterBasicDataPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_basic_data;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_register_basic_data));
        tv_action.setVisibility(View.VISIBLE);
        tv_action.setText(getString(R.string.next_step));
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
                tv_tips.setText("该申请已被拒绝，拒绝原因:未上传有效身份证");
                tv_action.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        if (isEditMode) {
            rl_sex.setOnClickListener(this);
            rl_ethnic.setOnClickListener(this);
            rl_birthday.setOnClickListener(this);
        }
    }

    @OnClick({R.id.tv_action, R.id.ll_back, R.id.iv_user_head, R.id.iv_card_front, R.id.iv_card_back, R.id.tv_example_head, R.id.tv_example_card})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_action: {
                if(!tv_action.getText().equals("审批")) {
                    Intent intent = new Intent(this, RegisterQualificationActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this, RegisterCommentActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.iv_user_head: {
                actionForImage(0, url_image_head);
                break;
            }
            case R.id.iv_card_front: {
                actionForImage(1, url_image_card_front);
                break;
            }
            case R.id.iv_card_back: {
                actionForImage(2, url_image_card_back);
                break;
            }
            case R.id.tv_example_head: { //头像的示例图
                PhotoPreInfo preInfo = new PhotoPreInfo();
                preInfo.setUrl(String.valueOf(R.mipmap.nature));
                preInfo.setEditable(false);
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                intent.putExtra(Constant.IMAGE_DATA, preInfo);
                startActivity(intent);
                break;
            }
            case R.id.tv_example_card: { //身份证的示例图 2张
                PhotoPreInfo preInfo = new PhotoPreInfo();
                preInfo.setUrl(String.valueOf(R.mipmap.nature));
                preInfo.setUrl(String.valueOf(R.mipmap.nature));
                preInfo.setEditable(false);
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                intent.putExtra(Constant.IMAGE_DATA, preInfo);
                startActivity(intent);
                break;
            }
            case R.id.rl_sex: {
                BottomDialog dialog = new BottomDialog(this, mPresenter.getSexData(tv_sex.getText().toString()));
                dialog.setOnClickListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        switch (which) {
                            case 0:
                                tv_sex.setText(getString(R.string.male));
                                break;
                            case 1:
                                tv_sex.setText(getString(R.string.female));
                                break;
                        }
                    }
                });
                dialog.show();
                break;
            }
            case R.id.rl_ethnic: {
                final List<Map<String, Object>> list = mPresenter.getEthnicData(tv_ethnic.getText().toString());
                BottomDialog dialog = new BottomDialog(this, list);
                dialog.setOnClickListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        tv_ethnic.setText(list.get(which).get("name").toString());
                    }
                });
                dialog.show();
                break;
            }
            case R.id.rl_birthday:
                CalendarView calendarView = new CalendarView(this, tv_birthday);
                calendarView.show();
                break;
        }
    }

    private void actionForImage(int index, String url) {
        INDEX_IMAGE_CURRENT = index;
        if (url == null) {
            PhotoBuilder builder = new PhotoBuilder(this);
            builder.setPhotoCount(1).setShowCamera(true).setShowGif(false).builder();
        } else {
            PhotoPreInfo photoPreInfo = new PhotoPreInfo();
            photoPreInfo.setEditable(true);
            photoPreInfo.setUrl(url);
            Intent intent = new Intent(this, ImagePreviewActivity.class);
            intent.putExtra(Constant.IMAGE_DATA, photoPreInfo);
            startActivityForResult(intent, REQUEST_CODE_PREVIEW);
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
                        switch (INDEX_IMAGE_CURRENT) {
                            case 0:
                                url_image_head = list.get(0);
                                GlideHelper.showImage(this, url_image_head, iv_user_head);
                                break;
                            case 1:
                                url_image_card_front = list.get(0);
                                GlideHelper.showImage(this, url_image_card_front, iv_card_front);
                                break;

                            case 2:
                                url_image_card_back = list.get(0);
                                GlideHelper.showImage(this, url_image_card_back, iv_card_back);
                                break;
                        }
                    }
                    break;
                case REQUEST_CODE_PREVIEW:
                    PhotoPreInfo preInfo = data.getParcelableExtra(Constant.IMAGE_DATA);
                    if (preInfo != null && preInfo.getList().size() == 0) {
                        switch (INDEX_IMAGE_CURRENT) {
                            case 0:
                                url_image_head = null;
                                iv_user_head.setImageDrawable(null);
                                break;
                            case 1:
                                url_image_card_front = null;
                                iv_card_front.setImageDrawable(null);
                                break;

                            case 2:
                                url_image_card_back = null;
                                iv_card_back.setImageDrawable(null);
                                break;
                        }
                    }
                    break;
            }
        }

    }
}
