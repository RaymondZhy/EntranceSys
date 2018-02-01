package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.newabel.entrancesys.ui.iview.RegisterQualificationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/24 0024.
 */

public class RegisterQualificationPresenter extends BasePresenter<RegisterQualificationView> {

    public RegisterQualificationPresenter(RegisterQualificationView view) {
        super(view);
    }

    public List<Map<String, Object>> getJobData(String job) {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(getMap("加钞员", job));
        list.add(getMap("维修工", job));
        list.add(getMap("安装工", job));
        list.add(getMap("其他", job));
        return list;
    }

    public List<Map<String, Object>> getDegreeData(String degree) {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(getMap("博士", degree));
        list.add(getMap("研究生", degree));
        list.add(getMap("本科", degree));
        list.add(getMap("专科", degree));
        list.add(getMap("其他", degree));
        return list;
    }

    private Map<String, Object> getMap(String value, String initValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", value);
        map.put("isSelect", TextUtils.equals(value, initValue));
        return map;
    }
}
