package com.newabel.entrancesys.service.entity;

/**
 * Date: 2017/12/5 16:22
 * Description:详情model
 */

public class Content {
    private String imgurl;//内容图片地址
    private String title;
    private int imgResourceId;//取本地图片
    private String time;
    private int contentType;//这个type目前暂时用于区分功能区块的不同区块


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
