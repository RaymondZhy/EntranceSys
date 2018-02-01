package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.OrganizaManageView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * Date: 2017/12/13 13:48
 * Description:机构管理
 */

public class OrganizaManagePresenter extends BasePresenter<OrganizaManageView> {

    public OrganizaManagePresenter(OrganizaManageView view) {
        super(view);
    }


    public void getOrganizeCodeList() {
        addSubscription(retrofitService.DeptType(), new DisposableObserver<Map<String, Object>>() {

            @Override
            public void onNext(Map<String, Object> map) {
                List<String> list = (List<String>) map.get("RetData");
                LogUtil.e("OrganizaManagePresenter", "list==null..." + (list == null ? "true" : "false"));
                mView.onGetDataSuccess(list);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("OrganizaManagePresenter", "........onError:" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getMenInfo(String id) {
        addSubscription(retrofitService.DeptType(id), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                float retCode = Float.valueOf(map.get("RetCode").toString());
                if (retCode == 0) {
                    mView.setData((Map<String, Object>) map.get("RetData"));
                } else if (retCode == 1) {
                    UIUtils.showToast("获取机构信息失败");
                } else if (retCode == 2) {
                    UIUtils.showToast("机构编号不存在");
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("", "");
            }

            @Override
            public void onComplete() {
                LogUtil.e("", "");
            }
        });
    }


}
