package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.newabel.entrancesys.ui.iview.RegisterCommentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/26 0026.
 */

public class RegisterCommentPresenter extends BasePresenter<RegisterCommentView> {

    public RegisterCommentPresenter(RegisterCommentView view) {
        super(view);
    }

    public List<Map<String, Object>> getData(String initValue) {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(getMap("审核通过", initValue));
        list.add(getMap("暂不合作", initValue));
        return list;
    }

    private Map<String, Object> getMap(String value, String initValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", value);
        map.put("isSelect", TextUtils.equals(value, initValue));
        return map;
    }
}
