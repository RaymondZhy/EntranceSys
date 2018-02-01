package com.newabel.entrancesys.service.entity;

/**
 * Date: 2017/12/7 15:18
 * Description:门禁资料地区级别
 */

public class AreaAccessInfoEntity {

    private String areaimg;
    private String areaName;
    private String areaCode;
    private String areaBelong;
    private int areaType;

    public AreaAccessInfoEntity() {
    }

    public AreaAccessInfoEntity(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaimg() {
        return areaimg;
    }

    public void setAreaimg(String areaimg) {
        this.areaimg = areaimg;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaBelong() {
        return areaBelong;
    }

    public void setAreaBelong(String areaBelong) {
        this.areaBelong = areaBelong;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }
}
