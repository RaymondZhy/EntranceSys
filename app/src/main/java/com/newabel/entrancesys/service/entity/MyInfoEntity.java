package com.newabel.entrancesys.service.entity;

/**
 * Date: 2017/12/8 14:31
 * Description:我的消息
 */

public class MyInfoEntity {

    private String myinfoTitle;

    private String myinfoTime;

    private String myinfoDesc;

    public MyInfoEntity(String myinfoTitle) {
        this.myinfoTitle = myinfoTitle;
    }

    public String getMyinfoTitle() {
        return myinfoTitle;
    }

    public void setMyinfoTitle(String myinfoTitle) {
        this.myinfoTitle = myinfoTitle;
    }

    public String getMyinfoTime() {
        return myinfoTime;
    }

    public void setMyinfoTime(String myinfoTime) {
        this.myinfoTime = myinfoTime;
    }

    public String getMyinfoDesc() {
        return myinfoDesc;
    }

    public void setMyinfoDesc(String myinfoDesc) {
        this.myinfoDesc = myinfoDesc;
    }
}
