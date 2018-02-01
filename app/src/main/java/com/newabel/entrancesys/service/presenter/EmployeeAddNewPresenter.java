package com.newabel.entrancesys.service.presenter;

import com.google.gson.Gson;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.EmployeeAddNewView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date: 2018/1/8 11:01
 * Description:
 */

public class EmployeeAddNewPresenter extends BasePresenter<EmployeeAddNewView> {

    public EmployeeAddNewPresenter(EmployeeAddNewView view) {
        super(view);
    }

    public void empAdd(int EmpID, String EmpNo, String EmpName, int DeptID, int sex,
                       int EmpType, String CertifNo, String CertifType, String MobilePhone) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("EmpID", EmpID);
        map.put("EmpNo", EmpNo);
        map.put("EmpName", EmpName);
        map.put("DeptID", DeptID);
        map.put("Sex", sex);

        map.put("EmpType", EmpType);
        map.put("CertifNo", CertifNo);
        map.put("CertifType", CertifType);
        map.put("MobilePhone", MobilePhone);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.EmployeeAdd(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //新增人员信息成功,返回至列表界面
                        mView.empAddSuc();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.empAddError(msg);
                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }

    /***********下面这些获取类型是在本地造的数据,是最开始时做的,如果要从网络获取数据,代码编写可参考机构管理里面的机构类型获取****************/

    /**
     * 获取性别类型
     *
     * @param mType
     * @return
     */
    public List<Map<String, Object>> getSexTypeData(int mType) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "男");
        map1.put("isSelect", mType == 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "女");
        map2.put("isSelect", mType == 2);

        list.add(map1);
        list.add(map2);
        return list;
    }

    /**
     * 获取员工类型(民警or职工)
     *
     * @param mType
     * @return
     */
    public List<Map<String, Object>> getEmpTypeData(int mType) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "民警");
        map1.put("isSelect", mType == 1);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "职工");
        map2.put("isSelect", mType == 2);
        list.add(map2);
        return list;
    }

    /**
     * 证件类型
     *
     * @param mType
     * @return
     */
    public List<Map<String, Object>> getCertifTypeData(int mType) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "身份证");
        map1.put("isSelect", mType == 1);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "军官证");
        map2.put("isSelect", mType == 2);
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "士兵证");
        map3.put("isSelect", mType == 3);
        list.add(map3);

        return list;
    }
}
