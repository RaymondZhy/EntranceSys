package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.ZoneDetailView;
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
 * DATE: 2017/12/20 0020.
 */

public class ZoneDetailPresenter extends BasePresenter<ZoneDetailView> {

    private final String TAG = this.getClass().getSimpleName();

    public ZoneDetailPresenter(ZoneDetailView view) {
        super(view);
    }

    public List<Map<String, Object>> getData(int mType) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", UIUtils.getString(R.string.zone_detail_str_10));
        map1.put("isSelect", mType == 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", UIUtils.getString(R.string.zone_detail_str_11));
        map2.put("isSelect", mType == 2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", UIUtils.getString(R.string.zone_detail_str_12));
        map3.put("isSelect", mType == 3);

        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }

    public String getZoneTypeStr(int type) {
        String str = "";
        switch (type) {
            case 1:
                str = UIUtils.getString(R.string.zone_detail_str_10);
                break;
            case 2:
                str = UIUtils.getString(R.string.zone_detail_str_11);
                break;
            case 3:
                str = UIUtils.getString(R.string.zone_detail_str_12);
                break;
        }
        return str;
    }


    public void add(int PlaceID, String PlaceNo, int MasterID, String PlaceName, int PlaceType) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("PlaceID", PlaceID);
        map.put("PlaceNo", PlaceNo);
        map.put("MasterID", MasterID);
        map.put("PlaceName", PlaceName);
        map.put("PlaceType", PlaceType);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.placeAdd(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_17));
                        mView.startActivity();
                        break;
                    case Constant.RESULT_CODE_ERROR:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_18));
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
                    UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_18));
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


    public void modify(int PlaceID, String PlaceNo, int MasterID, String PlaceName, int PlaceType) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("PlaceID", PlaceID);
        map.put("PlaceNo", PlaceNo);
        map.put("MasterID", MasterID);
        map.put("PlaceName", PlaceName);
        map.put("PlaceType", PlaceType);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.placeModify(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_13));
                        mView.startActivity();
                        break;
                    case Constant.RESULT_CODE_ERROR:
                        UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_14));
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
                    UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_14));
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
                        mView.startActivity();
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


    public boolean checkInput(String PlaceNo, String PlaceName, String PlaceType) {
        if (TextUtils.isEmpty(PlaceNo)) {
            UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_2));
            return false;
        }
        if (TextUtils.isEmpty(PlaceName)) {
            UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_4));
            return false;
        }
        if (TextUtils.isEmpty(PlaceType)) {
            UIUtils.showToast(UIUtils.getString(R.string.zone_detail_str_7));
            return false;
        }
        return true;
    }
}
