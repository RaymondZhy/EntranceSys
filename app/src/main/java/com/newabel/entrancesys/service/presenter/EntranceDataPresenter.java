package com.newabel.entrancesys.service.presenter;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.EntranceDataView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.NetUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public class EntranceDataPresenter extends BasePresenter<EntranceDataView> {

    private final String TAG = this.getClass().getSimpleName();

    public EntranceDataPresenter(EntranceDataView view) {
        super(view);
    }

    public void DoorModify(int DoorID, String DoorNo, String DoorName, int DoorType, int PlaceID,
                           int DeviceID, int ChildID, boolean StopState, String Remark) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("DoorID", DoorID);
        map.put("DoorNo", DoorNo);
        map.put("DoorName", DoorName);
        map.put("DoorType", DoorType);
        map.put("PlaceID", PlaceID);

        map.put("DeviceID", DeviceID);
        map.put("ChildID", ChildID);
        map.put("StopState", StopState);
        map.put("Remark", Remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.doorModify(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //修改机构资料成功
                        mView.doorModifySuc();
                        break;
                    default:
                        String msg = data.get("retMsg").toString();
                        mView.doorModifyError(msg);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.doorModifyError(msg);
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }


    public void deleteDoor(int doorId) {
        //把员工id转换下,因为得到的id是带小数点的
        LoadingDialog.show(mView.getContext());

        addSubscription(retrofitService.doorDelete(doorId), new DisposableObserver<Map<String, Object>>() {

            @Override
            public void onNext(Map<String, Object> map) {
                float retCode = Float.valueOf(map.get("retCode").toString());
                if (retCode == 0) {
                    //删除成功
                    mView.doorDeleteSuc();
                } else {
                    String msg = map.get("retMsg").toString();
                    mView.doorDeleteError(msg);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.doorDeleteError(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }


    public List<Map<String, Object>> getData(int type) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", getDoorType(1));
        map1.put("isSelect", type == 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", getDoorType(2));
        map2.put("isSelect", type == 2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", getDoorType(3));
        map3.put("isSelect", type == 3);

        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }

    public void getDeviceData(int deviceID) {
        if (NetUtils.isConnected(mView.getContext())) {
            addSubscription(retrofitService.Device(deviceID), new DisposableObserver<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    int retCode = Math.round(Float.valueOf(map.get("retCode").toString()));
                    switch (retCode) {
                        case Constant.RESULT_CODE_OK:
                            Map<String, Object> m = (Map<String, Object>) map.get("retData");
                            mView.showDeepData(m);
                            break;
                        case Constant.RESULT_CODE_DEVICENOTEXIST:
                            UIUtils.showToast(UIUtils.getString(R.string.entrance_data_str_19));
                            break;
                        case Constant.RESULT_CODE_ERROR:
                            UIUtils.showToast(UIUtils.getString(R.string.entrance_data_str_17));
                            break;
                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(TAG, e.getMessage());
                    String msg = parserThrowable(e);
                    if (msg == null) {
                        UIUtils.showToast(UIUtils.getString(R.string.entrance_data_str_17));
                    } else {
                        UIUtils.showToast(msg);
                    }
                    LoadingDialog.dismiss();
                }

                @Override
                public void onComplete() {
                    LoadingDialog.dismiss();
                }
            });
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.network_all_closed));
        }
    }


    public String getDoorType(int type) {
        String str = "";
        switch (type) {
            case 1:
                str = UIUtils.getString(R.string.entrance_data_str_14);
                break;
            case 2:
                str = UIUtils.getString(R.string.entrance_data_str_15);
                break;
            case 3:
                str = UIUtils.getString(R.string.entrance_data_str_16);
                break;
        }
        return str;
    }

}
