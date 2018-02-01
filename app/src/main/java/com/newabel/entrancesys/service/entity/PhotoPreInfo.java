package com.newabel.entrancesys.service.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:intent传递的数据用于控制ImagePreview的控制
 * AUTHOR:zhangyue
 * DATE: 2017/11/15 0015.
 */

public class PhotoPreInfo implements Parcelable{
    private List<String> list = new ArrayList<>();
    private boolean isEditable;
    private int currentItem;
    public PhotoPreInfo(){
        super();
    }

    public PhotoPreInfo(Parcel source) {
        list = source.createStringArrayList();
        isEditable = source.readInt() != 0;
        currentItem = source.readInt();
    }

    public List<String> getList() {
        return list;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setList(List<String> list) {
        if(list!=null) {
            this.list.addAll(list);
        }
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public void setUrl(String url) {
        list.add(url);
    }

    public static final Creator<PhotoPreInfo> CREATOR = new Creator<PhotoPreInfo>(){

        @Override
        public PhotoPreInfo createFromParcel(Parcel source) {
            return new PhotoPreInfo(source);
        }

        @Override
        public PhotoPreInfo[] newArray(int size) {
            return new PhotoPreInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(list);
        dest.writeInt(isEditable ? 1 : 0);
        dest.writeInt(currentItem);
    }
}
