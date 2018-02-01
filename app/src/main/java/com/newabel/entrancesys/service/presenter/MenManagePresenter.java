package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.MenManageView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/12 0012.
 */

public class MenManagePresenter extends BasePresenter<MenManageView> {

    public MenManagePresenter(MenManageView view) {
        super(view);
    }

    public ArrayList<String> getData(){
        ArrayList<String> list = new ArrayList<>();
        list.add("宇宙");
        list.add("太阳");
        list.add("地球");
        list.add("地球");
        list.add("中国");
        list.add("广东");
        list.add("深圳");
        list.add("福田区");
        return list;
    }

    public void getMenCodeList(){
        addSubscription(retrofitService.Employee(), new DisposableObserver<Map<String,Object>>() {
            @Override
            public void onNext(Map<String,Object> map) {
                int retCode = Math.round(Float.valueOf(map.get("RetCode").toString()));
                switch (retCode){
                    case 0:
                        List<String> list = (List<String>) map.get("RetData");
                        mView.notifyDataSetChanged(list);
                        break;
                    case 1:
                        UIUtils.showToast("获取人员编号失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("","");
            }

            @Override
            public void onComplete() {
                mView.complete();
            }
        });
    }

    public void getMenInfo(String id){
        addSubscription(retrofitService.Employee(id), new DisposableObserver<Map<String,Object>>() {
            @Override
            public void onNext(Map<String,Object> map) {
                float retCode = Float.valueOf(map.get("RetCode").toString());
                if(retCode == 0) {
                    mView.setData((Map<String, Object>) map.get("RetData"));
                }else if(retCode == 1){
                    UIUtils.showToast("获取人员信息失败");
                }else if(retCode == 4){
                    UIUtils.showToast("人员编号不存在");
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("","");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
