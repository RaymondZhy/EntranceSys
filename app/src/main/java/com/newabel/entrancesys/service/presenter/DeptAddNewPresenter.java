package com.newabel.entrancesys.service.presenter;

import com.google.gson.Gson;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.DeptAddNewView;
import com.newabel.entrancesys.ui.utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date: 2018/1/9 09:43
 * Description:
 */

public class DeptAddNewPresenter extends BasePresenter<DeptAddNewView> {
    public DeptAddNewPresenter(DeptAddNewView view) {
        super(view);
    }

    public void deptAdd(int DeptID, String DeptNo, int MasterID, String DeptName, int DeptType) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("DeptID", DeptID);
        map.put("DeptNo", DeptNo);
        map.put("MasterID", MasterID);
        map.put("DeptName", DeptName);
        map.put("DeptType", DeptType);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.DeptAdd(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //新增机构成功
                        mView.deptAddSuc();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.deptAddError(msg);
                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }

    public void getDeptTypeData() {
        addSubscription(retrofitService.DeptType(), new DisposableObserver<Map<String, Object>>() {

            @Override
            public void onNext(Map<String, Object> map) {
//                List<String> list = (List<String>) map.get("RetData");
//                LogUtil.e("OrganizaManagePresenter", "list==null..." + (list == null ? "true" : "false"));
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("retData");
                mView.getDeptTypeDataSuc(list);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("OrganizaManagePresenter", "........onError:" + e.getLocalizedMessage());
                mView.getDeptTypeDataError(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public List<Map<String, Object>> getDeptTypeData(int mType, List<Map<String, Object>> mapList) {


//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "身份证");
//        map1.put("isSelect", mType == 1);
//        list.add(map1);
//
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "军官证");
//        map2.put("isSelect", mType == 2);
//        list.add(map2);
//
//        Map<String, Object> map3 = new HashMap<>();
//        map3.put("name", "士兵证");
//        map3.put("isSelect", mType == 3);
//        list.add(map3);
        for (Map<String, Object> mapListItem : mapList) {
            int typeId = Math.round(Float.valueOf(mapListItem.get("TypeID").toString()));
            mapListItem.put("isSelect", mType == typeId);
            mapListItem.put("name", mapListItem.get("TypeName").toString());
        }
        return mapList;
    }

}
