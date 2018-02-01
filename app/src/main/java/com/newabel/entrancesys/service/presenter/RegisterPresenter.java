package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.RegisterView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.MD5Utils;
import com.newabel.entrancesys.ui.utils.MatchUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/8 0008.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private final String TAG = this.getClass().getSimpleName();

    public RegisterPresenter(RegisterView view) {
        super(view);
    }

    public void register(String UserName, String MobilePhone, String CertifNo, String UserPwd) {

        Map<String, Object> map = new HashMap<>();
        map.put("UserName", UserName);
        map.put("MobilePhone", MobilePhone);
        map.put("CertifNo", CertifNo);
        map.put("UserPwd", MD5Utils.toHexString(UserPwd));
        LoadingDialog.show(mView.getContext());
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.User(body), new DisposableObserver<Map<String, Object>>() {

            @Override
            public void onNext(Map<String, Object> map) {
                LogUtil.e(TAG, "");
                int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        UIUtils.showToast(UIUtils.getString(R.string.register_str_14));
                        mView.startActivity();
                        break;
                    case Constant.RESULT_CODE_USEREXIST:
                        UIUtils.showToast(UIUtils.getString(R.string.register_str_18));
                        break;
                    case Constant.RESULT_CODE_ERROR:
                        UIUtils.showToast(UIUtils.getString(R.string.register_str_15));
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG,e.getMessage());
                String msg = parserThrowable(e);
                if(msg == null) {
                    UIUtils.showToast(UIUtils.getString(R.string.register_str_15));
                }else {
                    UIUtils.showToast(msg);
                }
                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }

    public boolean checkInput(String userName, String mobilePhone, String certifNo, String userPwd, String confirPwd) {
        if (TextUtils.isEmpty(mobilePhone)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_2));
            return false;
        }
        if (!MatchUtils.isMobieNumbler(mobilePhone)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_16));
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_7));
            return false;
        }

        if (TextUtils.isEmpty(userPwd) || userPwd.trim().length() < 6) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_9));
            return false;
        }

        if (TextUtils.isEmpty(confirPwd)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_11));
            return false;
        }

        if (!TextUtils.equals(userPwd, confirPwd)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_17));
            return false;
        }
        return true;
    }

    public boolean checkInput(String phone,String verifyCode){
        if (TextUtils.isEmpty(phone)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_2));
            return false;
        }
        if (!MatchUtils.isMobieNumbler(phone)) {
            UIUtils.showToast(UIUtils.getString(R.string.register_str_16));
            return false;
        }

        if(TextUtils.isEmpty(verifyCode)){
            UIUtils.showToast(UIUtils.getString(R.string.register_str_4));
            return false;
        }
        return true;
    }
}
