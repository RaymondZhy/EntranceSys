package com.newabel.entrancesys.service.presenter;

import com.google.gson.Gson;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.EmployeeInfoListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date: 2017/12/20 09:45
 * Description:
 */

public class EmployeeInfoListPresenter extends BasePresenter<EmployeeInfoListView> {
    public EmployeeInfoListPresenter(EmployeeInfoListView view) {
        super(view);
    }

    //删除人员信息

    public void deleteEmp(int empId) {
        //把员工id转换下,因为得到的id是带小数点的
        LoadingDialog.show(mView.getContext());

        addSubscription(retrofitService.EmployeeDelete(empId), new DisposableObserver<Map<String, Object>>() {

            @Override
            public void onNext(Map<String, Object> map) {
                float retCode = Float.valueOf(map.get("retCode").toString());
                if (retCode == 0) {
                    //删除成功
                    mView.empDeleteSuc();
                } else {
                    String msg=map.get("retMsg").toString();
                    mView.empDeleteError(msg);
                }
            }

            @Override
            public void onError(Throwable e) {
            mView.empDeleteError(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }

    /**
     * 修改人员资料
     *
     * @param EmpID       人员编号
     * @param EmpNo       人员编号
     * @param EmpName     人员姓名
     * @param DeptID      部门id
     * @param sex         性别(1男2女)
     * @param EmpType     人员类型(1民警2职工)
     * @param CertifNo    证件号码
     * @param CertifType  证件类型(不明)
     * @param MobilePhone 手机号码
     */
    public void modify(int EmpID, String EmpNo, String EmpName, int DeptID, int sex,
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
        addSubscription(retrofitService.empModify(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //修改信息成功,返回至列表界面
                        mView.empModifySuc();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.empModifyError(msg);
                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }

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
