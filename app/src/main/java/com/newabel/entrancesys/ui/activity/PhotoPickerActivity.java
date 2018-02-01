package com.newabel.entrancesys.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.PhotoDirectory;
import com.newabel.entrancesys.service.entity.PhotoPreInfo;
import com.newabel.entrancesys.service.presenter.PhotoPickerPresenter;
import com.newabel.entrancesys.ui.adapter.PhotoPickerAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.DirectoryDialog;
import com.newabel.entrancesys.ui.iview.PhotoPickerView;
import com.newabel.entrancesys.ui.utils.FileUtils;
import com.newabel.entrancesys.ui.utils.PermissionUtils;
import com.newabel.entrancesys.ui.utils.StatusBarUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoPickerActivity extends BaseActivity<PhotoPickerPresenter> implements PhotoPickerView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, DirectoryDialog.OnClickListener {

    private final int REQUEST_CODE_PERMISSION_1 = 101;
    private final int REQUEST_CODE_PERMISSION_2 = 102;
    private final int REQUEST_CODE_PREVIEW = 103;
    private final int REQUEST_CODE_CAPTURE = 104;

    private ArrayList<String> selectPhotos;
    private boolean isShowCamera;
    private int photoCount;
    private LinearLayout ll_back;
    private Button btn_ok;
    private RecyclerView rv_list;
    private TextView tv_dir_name;
    private boolean isShowGif;
    private PhotoPickerAdapter photoPickerAdapter;
    private List<PhotoDirectory> directories;
    private List<String> photos;
    private DirectoryDialog directoryDialog;
    private int currentIndex = 0;
    private String mPath;
    private TextView tv_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_picker);

//        StatusBarUtils.setStatusBarColor(this, R.color.photo_picker_color_1);

//        receiveData();
//        initView();
//        initData();
//        initListener();
    }

    private void receiveData() {
        Intent intent = this.getIntent();
        selectPhotos = intent.getStringArrayListExtra("PHOTO_LIST");
        if(selectPhotos == null){
            selectPhotos = new ArrayList<>();
        }
        isShowCamera = intent.getBooleanExtra("IS_SHOW_CAMERA",true);
        isShowGif = intent.getBooleanExtra("IS_SHOW_GIF",false);
        photoCount = intent.getIntExtra("PHOTO_COUNT",9);
    }

    @Override
    protected PhotoPickerPresenter createPresenter() {
      return new PhotoPickerPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_photo_picker;
    }

    @Override
    protected void initView() {

        receiveData();

        StatusBarUtils.setStatusBarColor(this, R.color.photo_picker_color_1);

        ll_back = findViewById(R.id.ll_back);
        btn_ok = findViewById(R.id.btn_ok);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new GridLayoutManager(this,3));
        tv_preview = findViewById(R.id.tv_preview);
        if(directories == null){
            directories = new ArrayList<>();
        }

        if(photos == null){
            photos = new ArrayList<>();
            if(isShowCamera){
                photos.add(Constant.FLAG_POSITION_OCCUPY);
            }
        }

        photoPickerAdapter = new PhotoPickerAdapter(R.layout.item_photo_picker,photos,selectPhotos,btn_ok,tv_preview,photoCount);
        rv_list.setAdapter(photoPickerAdapter);
        tv_dir_name = findViewById(R.id.tv_dir_name);
    }
    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        tv_dir_name.setOnClickListener(this);
        photoPickerAdapter.setOnItemClickListener(this);
        btn_ok.setOnClickListener(this);
        tv_preview.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if(PermissionUtils.hasPermission(this,permission)) {
            mPresenter.getPhotoDirs(this, isShowGif);
        }else{
            PermissionUtils.requestPermissions(this,new String[]{permission}, REQUEST_CODE_PERMISSION_1);
        }
    }

    @Override
    public Activity getContext() {
        return this;
    }


    @Override
    public void updateData(List<PhotoDirectory> directories) {
        this.directories.clear();
        this.directories.addAll(directories);
        photos.addAll(directories.get(0).getPhotos());
        photoPickerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions.length == 0 || grantResults.length == 0){
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_1:
                if (PermissionUtils.hasPermissions(this,permissions)) {
                    mPresenter.getPhotoDirs(this, isShowGif);
                } else {
                    UIUtils.showToast(getString(R.string.permission_str));
                }
            break;
            case REQUEST_CODE_PERMISSION_2:
                if (PermissionUtils.hasPermissions(grantResults)) {
                    takePhoto();
                } else {
                    UIUtils.showToast(getString(R.string.permission_str));
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_dir_name:
                if(directoryDialog == null){
                    directoryDialog = new DirectoryDialog(this);
                    directoryDialog.setList(directories);
                    directoryDialog.setOnClickListener(this);
                    directoryDialog.setAdapterIndex(currentIndex);
                    directoryDialog.show(tv_dir_name);
                }else{
                    if(directoryDialog.isShowing()){
                        directoryDialog.dismiss();
                    }else{
                        directoryDialog.setAdapterIndex(currentIndex);
                        directoryDialog.show(tv_dir_name);
                    }
                }
                break;
            case R.id.btn_ok:
                setResult();
                break;
            case R.id.tv_preview:
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                PhotoPreInfo photoPreInfo = new PhotoPreInfo();
                photoPreInfo.setList(selectPhotos);
                photoPreInfo.setEditable(true);
                intent.putExtra(Constant.IMAGE_DATA, photoPreInfo);
                startActivityForResult(intent,REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(Constant.IMAGE_DATA,selectPhotos);
        setResult(RESULT_OK,intent);
        this.finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter,View view, int position) {
        if(isShowCamera && position == 0){
            String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if(PermissionUtils.hasPermissions(this,permissions)) {
                takePhoto();
            }else{
                PermissionUtils.requestPermissions(this,permissions, REQUEST_CODE_PERMISSION_2);
            }
        }else {
            Intent intent = new Intent(this, ImagePreviewActivity.class);
            PhotoPreInfo photoPreInfo = new PhotoPreInfo();
            photoPreInfo.setUrl(photos.get(position));
            photoPreInfo.setEditable(false);
            intent.putExtra(Constant.IMAGE_DATA, photoPreInfo);
            startActivity(intent);
        }
    }

    private void takePhoto() {
        mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/" + System.currentTimeMillis() + ".png";
        File file = new File(mPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri uri = FileUtils.getFileUri(this,file);
        UIUtils.openCamera(this,uri,REQUEST_CODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_CAPTURE:
                    if(mPath != null){
                        File file = new File(mPath);
                        if(file.exists()){
                            UIUtils.scanFile(this,mPath);
                            selectPhotos.add(mPath);
                            setResult();
                            this.finish();
                        }
                    }
                    break;
                case REQUEST_CODE_PREVIEW:
                    PhotoPreInfo photoPreInfo = data.getParcelableExtra(Constant.IMAGE_DATA);
                    if(photoPreInfo != null) {
                        selectPhotos.clear();
                        selectPhotos.addAll(photoPreInfo.getList());
                        photoPickerAdapter.notifyDataSetChanged();
                        photoPickerAdapter.updateUi();
                    }
                    break;
            }
        }
    }




    @Override
    public void onClick(Dialog dialog, int which) {
        photos.clear();
        photos.addAll(directories.get(which).getPhotos());
        if(isShowCamera){
            photos.add(0,Constant.FLAG_POSITION_OCCUPY);
        }
        photoPickerAdapter.notifyDataSetChanged();
        this.currentIndex = which;
    }
}
