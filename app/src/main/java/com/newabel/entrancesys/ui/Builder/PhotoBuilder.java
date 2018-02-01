package com.newabel.entrancesys.ui.Builder;

import android.app.Activity;
import android.content.Intent;


import com.newabel.entrancesys.ui.activity.PhotoPickerActivity;

import java.util.ArrayList;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/20 0020.
 */

public class PhotoBuilder {

    public static final int REQUEST_CODE_PHOTO = 666;
    public Activity context;

    private ArrayList<String> selectPhotos;
    private boolean isShowCamera;
    private boolean isShowGif;
    private int photoCount;

    public PhotoBuilder(Activity context){
        this.context = context;
    }

    public ArrayList<String> getSelectPhotos() {
        return selectPhotos;
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    public boolean isShowGif() {
        return isShowGif;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public PhotoBuilder setSelectPhotos(ArrayList<String> selectPhotos) {
        this.selectPhotos = selectPhotos;
        return this;
    }

    public PhotoBuilder setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
        return this;
    }

    public PhotoBuilder setShowGif(boolean showGif) {
        isShowGif = showGif;
        return this;
    }

    public PhotoBuilder setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
        return this;
    }

    public void builder(){
        Intent intent = new Intent(context, PhotoPickerActivity.class);
        intent.putExtra("PHOTO_LIST",getSelectPhotos());
        intent.putExtra("IS_SHOW_CAMERA",isShowCamera());
        intent.putExtra("PHOTO_COUNT",getPhotoCount());
        intent.putExtra("IS_SHOW_GIF",isShowGif());
        context.startActivityForResult(intent,REQUEST_CODE_PHOTO);
    }
}
