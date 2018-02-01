package com.newabel.entrancesys.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.QrScanPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.QrScanView;
import com.newabel.entrancesys.ui.utils.FileUtils;
import com.newabel.entrancesys.ui.utils.PermissionUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;

public class QrScanActivity extends BaseActivity<QrScanPresenter> implements QrScanView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_image)
    TextView tv_image;

    @BindView(R.id.iv_led)
    ImageView iv_led;

    private final int REQUEST_CODE_IMAGE = 101;
    private final int REQUEST_CODE_PERMISSION_ALL = 102;
    private final int REQUEST_CODE_PERMISSION_READ_FILE = 103;
    private boolean isLighten = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qr_scan);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏
        requestPermission();
    }

    @Override
    protected QrScanPresenter createPresenter() {
       return new QrScanPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qr_scan;
    }


    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.READ_EXTERNAL_STORAGE};
        PermissionUtils.requestPermissions(this,permissions,REQUEST_CODE_PERMISSION_ALL);
    }

    @Override
    protected void initView() {
        super.initView();
        initZxing();
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_image.setOnClickListener(this);
        iv_led.setOnClickListener(this);
    }

    private void initZxing() {
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            QrScanActivity.this.setResult(RESULT_OK, resultIntent);
            QrScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            QrScanActivity.this.setResult(RESULT_OK, resultIntent);
            QrScanActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_image:
                String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                if(PermissionUtils.hasPermission(this,permission)){
                    UIUtils.openImageStore(this,REQUEST_CODE_IMAGE);
                }else{
                    PermissionUtils.requestPermissions(this,new String[]{permission},REQUEST_CODE_PERMISSION_READ_FILE);
                }
                break;
            case R.id.iv_led:
                isLighten = !isLighten;
                CodeUtils.isLightEnable(isLighten);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_ALL:
                if(!PermissionUtils.hasPermissions(grantResults)){
                    UIUtils.showToast(getString(R.string.permission_str));
                }
                break;
            case REQUEST_CODE_PERMISSION_READ_FILE:

                if(!PermissionUtils.hasPermissions(grantResults)){
                    UIUtils.showToast(getString(R.string.permission_str));
                }else {
                    UIUtils.openImageStore(this,REQUEST_CODE_IMAGE);
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case REQUEST_CODE_IMAGE:
                    Uri uri = data.getData();
                    CodeUtils.analyzeBitmap(FileUtils.getRealPathFromUri(this,uri),analyzeCallback);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
