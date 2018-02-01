package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.ModuleView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.NetUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/18 0018.
 */

public class ModulePresenter extends BasePresenter<ModuleView> {

    private final String TAG = this.getClass().getSimpleName();
    private Gson gson = new Gson();

    public ModulePresenter(ModuleView view) {
        super(view);
    }


    public void getZoneData(final String type, final String value, final int code) {
        //查询所有数据
        try {
            if (NetUtils.isConnected(UIUtils.getContext())) {
//                LoadingDialog.show(mView.getContext());
                mView.beginRefresh(true);
                addSubscription(retrofitService.Place(type, value), new DisposableObserver<Map<String, Object>>() {

                    @Override
                    public void onNext(Map<String, Object> map) {
                        int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                        switch (retCode) {
                            case Constant.RESULT_CODE_OK:
                                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                                mView.showData(list);
                                switch (code) {
                                    case Constant.MODULE_ENTRANCE_DATA:
                                        if (!TextUtils.equals("0", type)) {
                                            getDoorData(type, value);
                                        }
                                        break;
                                    case Constant.MODULE_ENTRANCE_EVENT:
                                        if (!TextUtils.equals("0", type)) {
                                            getDoorData(type, value);
                                        }
                                        break;
                                    case Constant.MODULE_ENTRANCE_CONTROL:
                                        if (!TextUtils.equals("0", type)) {
                                            getDoorData(type, value);
                                        }
                                        break;
                                    case Constant.MODULE_ZONE_MANAGE:

                                        break;
                                    case Constant.MODULE_DEVICE_DATA:
                                        //设备资料
                                        if (!TextUtils.equals("0", type)) {
                                            getDeviceData(type, value);
                                        }
                                        break;
                                    case Constant.MODULE_DEPARTMENT_MANAGE:
                                        //部门资料
                                        if (!TextUtils.equals("0", type)) {
                                            getDeptData(type, value);
                                        }
                                        break;
                                }
                                break;
                            case Constant.RESULT_CODE_ERROR:
                                mView.setErrorView();
                                break;
                            case Constant.RESULT_CODE_USERUNLOGIN:
                                mView.setErrorView();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, e.getMessage());
                        String msg = parserThrowable(e);
                        if (msg == null) {
                            mView.setErrorView();
                        } else {
                            mView.setNoNetWorkView();
                        }
//                        LoadingDialog.dismiss();
                        mView.beginRefresh(false);
                    }

                    @Override
                    public void onComplete() {
//                        LoadingDialog.dismiss();
//                        mView.beginRefresh(false);
                    }
                });
            } else {
                mView.setNoNetWorkView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 机构管理,人员管理
     **/
    public void getDeptData(final String type, final String value, final int code) {
        //查询所有数据
        try {
            if (NetUtils.isConnected(UIUtils.getContext())) {
//                LoadingDialog.show(mView.getContext());
                mView.beginRefresh(true);
                addSubscription(retrofitService.Dept(type, value), new DisposableObserver<Map<String, Object>>() {

                    @Override
                    public void onNext(Map<String, Object> map) {
                        int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                        switch (retCode) {
                            case Constant.RESULT_CODE_OK:
                                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                                mView.showData(list);
                                switch (code) {
                                    case Constant.MODULE_EMPLOYEE_MANAGE:
                                        //人员资料
                                        if (!TextUtils.equals("0", type)) {
                                            getEmployeeData(type, value);
                                        }
                                        break;
                                }
                                break;
                            case Constant.RESULT_CODE_ERROR:
                                mView.setErrorView();
                                break;
                            case Constant.RESULT_CODE_USERUNLOGIN:
                                mView.setErrorView();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, e.getMessage());
                        String msg = parserThrowable(e);
                        if (msg == null) {
                            mView.setErrorView();
                        } else {
                            mView.setNoNetWorkView();
                        }
//                        LoadingDialog.dismiss();
                        mView.beginRefresh(false);
                    }

                    @Override
                    public void onComplete() {
//                        LoadingDialog.dismiss();
                        mView.beginRefresh(false);
                    }
                });
            } else {
                mView.setNoNetWorkView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void getEmployeeData(String type, String value) {
        if (NetUtils.isConnected(mView.getContext())) {
//            LoadingDialog.show(mView.getContext());
            mView.beginRefresh(true);
            addSubscription(retrofitService.Employee(type, value), new DisposableObserver<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                    switch (retCode) {
                        case Constant.RESULT_CODE_OK:
                            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                            mView.showDeepData(list);
                            break;
                        case Constant.RESULT_CODE_ERROR:
                            mView.setErrorView();
                            break;

                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(TAG, e.getMessage());
                    String msg = parserThrowable(e);
                    if (msg == null) {
                        mView.setErrorView();
                    } else {
                        mView.setNoNetWorkView();
                    }
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }

                @Override
                public void onComplete() {
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }
            });
        } else {
            mView.setNoNetWorkView();
        }
    }

    public void getDeptData(String type, String value) {
        if (NetUtils.isConnected(mView.getContext())) {
            addSubscription(retrofitService.Dept(type, value), new DisposableObserver<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                    switch (retCode) {
                        case Constant.RESULT_CODE_OK:
                            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                            mView.showDeepData(list);
                            break;
                        case Constant.RESULT_CODE_ERROR:
                            mView.setErrorView();
                            break;

                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(TAG, e.getMessage());
                    String msg = parserThrowable(e);
                    if (msg == null) {
                        mView.setErrorView();
                    } else {
                        mView.setNoNetWorkView();
                    }
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }

                @Override
                public void onComplete() {
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }
            });
        } else {
            mView.setNoNetWorkView();
        }
    }

    public void getDoorData(String type, String value) {
        if (NetUtils.isConnected(mView.getContext())) {
            addSubscription(retrofitService.Door(type, value), new DisposableObserver<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                    switch (retCode) {
                        case Constant.RESULT_CODE_OK:
                            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                            mView.showDeepData(list);
                            break;
                        case Constant.RESULT_CODE_ERROR:
                            mView.setErrorView();
                            break;

                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(TAG, e.getMessage());
                    String msg = parserThrowable(e);
                    if (msg == null) {
                        mView.setErrorView();
                    } else {
                        mView.setNoNetWorkView();
                    }
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }

                @Override
                public void onComplete() {
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }
            });
        } else {
            mView.setNoNetWorkView();
        }
    }

    public void getDeviceData(String type, String value) {
        if (NetUtils.isConnected(mView.getContext())) {
            addSubscription(retrofitService.Device(type, value), new DisposableObserver<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                    switch (retCode) {
                        case Constant.RESULT_CODE_OK:
                            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                            mView.showDeepData(list);
                            break;
                        case Constant.RESULT_CODE_ERROR:
                            mView.setErrorView();
                            break;

                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(TAG, e.getMessage());
                    String msg = parserThrowable(e);
                    if (msg == null) {
                        mView.setErrorView();
                    } else {
                        mView.setNoNetWorkView();
                    }
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }

                @Override
                public void onComplete() {
//                    LoadingDialog.dismiss();
                    mView.beginRefresh(false);
                }
            });
        } else {
            mView.setNoNetWorkView();
        }
    }

    public void getData(String type, String value, int code) {
        List<Map<String, Object>> list = new ArrayList<>();
        switch (code) {
            case Constant.MODULE_ENTRANCE_DATA: {
                //门禁资料
                getZoneData(type, value, code);
                break;
            }
            case Constant.MODULE_ENTRANCE_EVENT: {
                //门禁事件
                getZoneData(type, value, code);
                break;
            }

            case Constant.MODULE_ENTRANCE_CONTROL: {
                //门禁控制
                getZoneData(type, value, code);
                break;
            }
            case Constant.MODULE_ZONE_MANAGE: {
                //区域管理
                getZoneData(type, value, code);
                break;
            }
            case Constant.MODULE_DEVICE_DATA: {
                //设备资料
                getZoneData(type, value, code);
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE: {
                //机构管理
                Log.e(TAG, "-------------type:" + type + "-----value:" + value + "--------code:" + code);
                getDeptData(type, value, code);
                break;
            }
            case Constant.MODULE_EMPLOYEE_MANAGE: {
                //人员管理
                getDeptData(type, value, code);
                break;
            }
        }
    }

    public void delete(int PlaceID){
        LoadingDialog.show(mView.getContext());
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("PlaceID",PlaceID);
//        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), new Gson().toJson(map));
        addSubscription(retrofitService.placeDelete(PlaceID), new DisposableObserver<Map<String,Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_19));
                        mView.refresh();
                        break;
                    case Constant.RESULT_CODE_ERROR:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_20));
                        break;
                    case Constant.RESULT_CODE_PLACENOTEXIST:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_15));
                        break;
                    case Constant.RESULT_CODE_PLACEEXIST:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_16));
                        break;
                    case Constant.RESULT_CODE_USERUNLOGIN:
                        UIUtils.showToast(UIUtils.getString(R.string.login_expired_tip));
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                if (msg == null) {
                    UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_20));
                } else {
                    UIUtils.showToast(UIUtils.getString(R.string.network_all_closed));
                }

                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }



}
