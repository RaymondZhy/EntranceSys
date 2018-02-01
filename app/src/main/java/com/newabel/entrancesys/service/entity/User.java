package com.newabel.entrancesys.service.entity;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/12 0012.
 */

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.PreUtils;

/**
 * {
 * "RetCode": 0,
 * "RetMsg": "成功",
 * "RetData": "956203193848AF8A6387C2C460DDB676A649CF971312F5"
 * }
 */
public class User {
    private String UserID;
    private String UserPwd;
    private float retCode;
    private String retMsg;
    private String retData;

    private static User instance;

    private User() {
        retCode = -1;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public float getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public String getRetData() {
        return retData;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public void setUserPwd(String userPwd) {
        this.UserPwd = userPwd;
    }

    public void setRetCode(float retCode) {
        this.retCode = retCode;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void setRetData(String retData) {
        this.retData = retData;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void init() {
        String userInfo = PreUtils.getString(PreUtils.USER_INFO, "");
        if (userInfo != null && !TextUtils.isEmpty(userInfo)) {
            try {
                instance = new Gson().fromJson(userInfo, User.class);
                LogUtil.e("User", "------retData:" + instance.getRetData());
            } catch (Exception e) {
                e.printStackTrace();
                PreUtils.putString(PreUtils.USER_INFO, "");
            }
        }
    }

}
