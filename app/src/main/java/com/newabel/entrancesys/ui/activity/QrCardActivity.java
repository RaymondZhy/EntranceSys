package com.newabel.entrancesys.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.QrCardPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.QrCardView;
import com.newabel.entrancesys.ui.utils.PermissionUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;

public class QrCardActivity extends BaseActivity<QrCardPresenter> implements QrCardView, View.OnClickListener, MenuDialog.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    @BindView(R.id.iv_sex)
    ImageView iv_sex;
    @BindView(R.id.tv_position)
    TextView tv_position;

    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;

    @BindView(R.id.rl_content)
    RelativeLayout rl_content;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    private final int REQUEST_CODE_QRCODE = 101;
    private final int REQUEST_CODE_SAVE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qr_card);
    }

    @Override
    protected QrCardPresenter createPresenter() {
      return new QrCardPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qr_card;
    }

    @Override
    protected void initData() {
        super.initData();
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.mipmap.ic_more);

        tv_title.setText(getString(R.string.title_activity_qr_card));

        String textContent = "118695325648798654546565";
        Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_me_user_head));
        iv_qrcode.setImageBitmap(mBitmap);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_add:
                MenuDialog dialog = new MenuDialog(this);
                String[] items = {getString(R.string.qr_card_str_2),getString(R.string.qr_card_str_3),getString(R.string.qr_card_str_4)};
                dialog.setItem(items);
                dialog.setWidth(120);
                dialog.setOnClickListener(this);
                dialog.show(iv_add);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_QRCODE:
                    //处理扫描结果（在界面上显示）
                    if (null != data) {
                        Bundle bundle = data.getExtras();
                        if (bundle == null) {
                            return;
                        }
                        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                            String result = bundle.getString(CodeUtils.RESULT_STRING);
                            UIUtils.showToast("解析结果:" + result);
                        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                            UIUtils.showToast("解析二维码失败");
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which){
            case 0:

                break;
            case 1:
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if(PermissionUtils.hasPermissions(this,permissions)) {
                    rl_content.setDrawingCacheEnabled(true);
                    mPresenter.saveBitmap(this,rl_content.getDrawingCache());
                    UIUtils.showToast(getString(R.string.qr_card_str_5));
                    rl_content.setDrawingCacheEnabled(false);
                }else{
                    PermissionUtils.requestPermissions(this,permissions, REQUEST_CODE_SAVE);
                }
                break;
            case 2:
                Intent intent = new Intent(this, QrScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QRCODE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_SAVE:
                if(PermissionUtils.hasPermissions(this,permissions)){
//                    mPresenter.saveBitmap(this,mBitmap);
//                    UIUtils.showToast(getString(R.string.qr_card_str_5));
                }else{
                    UIUtils.showToast(getString(R.string.permission_str));
                }
                break;
        }
    }
}
