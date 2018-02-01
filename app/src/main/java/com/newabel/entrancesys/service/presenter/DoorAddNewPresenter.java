package com.newabel.entrancesys.service.presenter;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.DoorAddNewView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date: 2018/1/11 17:14
 * Description:
 */

public class DoorAddNewPresenter extends BasePresenter<DoorAddNewView> {

    public DoorAddNewPresenter(DoorAddNewView view) {
        super(view);
    }


    public void doorAdd(int DoorID, String DoorNo, String DoorName, int DoorType, int PlaceID, int DeviceID,
                        int ChildID, boolean StopState, String Remark) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("DoorID", DoorID);
        map.put("DoorNo", DoorNo);
        map.put("DoorName", DoorName);
        map.put("DoorType", DoorType);
        map.put("PlaceID", PlaceID);
        map.put("PlaceID", PlaceID);
        map.put("DeviceID", DeviceID);
        map.put("ChildID", ChildID);
        map.put("StopState", StopState);
        map.put("Remark", Remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.DoorAdd(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //新增机构成功
                        mView.doorAddSuc();
                        break;
                    default:
                        String msg = data.get("retMsg").toString();
                        mView.doorAddError(msg);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.doorAddError(msg);
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }


    //获取门禁类型数据,这个实际上应该从网络接口获取数据
    public List<Map<String, Object>> getDoorData(int doorType) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", getDoorType(1));
        map1.put("isSelect", doorType == 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", getDoorType(2));
        map2.put("isSelect", doorType == 2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", getDoorType(3));
        map3.put("isSelect", doorType == 3);

        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
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
