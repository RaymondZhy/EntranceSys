package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.RegisterProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/22 0022.
 */

public class RegisterProgressPresenter extends BasePresenter<RegisterProgressView> {


    public RegisterProgressPresenter(RegisterProgressView view) {
        super(view);
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("content", "完成");
        map1.put("date", "2018-01-23 16:41:06");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("content", "中国银行已审核您提交的申请资料。");
        map2.put("date", "2018-01-23 16:41:06");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("content", "纽贝尔公司已审核了您提交的申请资料。");
        map3.put("date", "2018-01-20 17:41:06");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("content", "您提交了申请资料。");
        map4.put("date", "2018-01-19 10:21:06");
        list.add(map4);

//        Map<String, Object> map5 = new HashMap<>();
//        map5.put("content", "您保存了草稿。");
//        map5.put("date", "2018-01-19 10:21:06");
//        list.add(map5);
//
//        Map<String, Object> map6 = new HashMap<>();
//        map6.put("content", "您保存了草稿。");
//        map6.put("date", "2018-01-19 10:21:06");
//        list.add(map6);
        return list;
    }
}
