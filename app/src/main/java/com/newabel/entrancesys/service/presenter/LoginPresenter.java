package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.helper.Retrofit.RetrofitHelper;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.LoginView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.MD5Utils;
import com.newabel.entrancesys.ui.utils.NetUtils;
import com.newabel.entrancesys.ui.utils.PreUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/8 0008.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void login(final String UserID, final String UserPwd) {
        if (NetUtils.isConnected(UIUtils.getContext())) {
            if (checkInput(UserID, UserPwd)) {
                LoadingDialog.show(mView.getContext());
                addSubscription(retrofitService.login(UserID, MD5Utils.toHexString(UserPwd).toUpperCase()), new DisposableObserver<Map<String, Object>>() {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                        switch (retCode) {
                            case Constant.RESULT_CODE_OK: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_10));
                                mView.startActivity();

                                map.put("UserID", UserID);
                                map.put("UserPwd", UserPwd);
                                LogUtil.e("LoginPresenter", "UserId:" + UserID + ".....UserPwd" + UserPwd);
                                PreUtils.putString(PreUtils.USER_INFO, new Gson().toJson(map));

                                RetrofitHelper.reSetRetrofit();
                                break;
                            }
                            case Constant.RESULT_CODE_USERNOTEXIST: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_11));
                                break;
                            }
                            case Constant.RESULT_CODE_USERPWDERROR: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_8));
                                break;
                            }
                            case Constant.RESULT_CODE_USERSTOPPED: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_12));
                            }
                            case Constant.RESULT_CODE_USEREXPIRE: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_13));
                            }
                            case Constant.RESULT_CODE_USERLOCKED: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_14));
                            }
                            case Constant.RESULT_CODE_USERKEYERROR: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_14));
                            }
                            case Constant.RESULT_CODE_USERNOTRIGHT: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_15));
                            }
                            case Constant.RESULT_CODE_ERROR: {
                                UIUtils.showToast(UIUtils.getString(R.string.login_str_9));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = parserThrowable(e);
                        if (msg != null) {
                            UIUtils.showToast(msg);
                        } else {
                            UIUtils.showToast(UIUtils.getContext().getString(R.string.login_str_9));
                        }
                        LoadingDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        LoadingDialog.dismiss();
                    }
                });
            }
        } else {
            UIUtils.showToast(UIUtils.getContext().getString(R.string.network_all_closed));
        }
    }

    private boolean checkInput(String userID, String userPwd) {
        if (TextUtils.isEmpty(userID)) {
            UIUtils.showToast(UIUtils.getContext().getString(R.string.login_str_2));
            return false;
        }
        if (TextUtils.isEmpty(userPwd)) {
            UIUtils.showToast(UIUtils.getContext().getString(R.string.login_str_4));
            return false;
        }
        return true;
    }
}
