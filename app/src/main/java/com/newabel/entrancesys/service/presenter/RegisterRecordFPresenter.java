package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.RegisterRecordFView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/19 0019.
 */

public class RegisterRecordFPresenter extends BasePresenter<RegisterRecordFView> {

    public RegisterRecordFPresenter(RegisterRecordFView view) {
        super(view);
    }

    public List<Map<String,Object>> getData(int index){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        list.add(map);

        map.put("name","张三");
        map.put("profession","维修工人");
        map.put("date","2018-01-19");

        switch (index){
            case 0:
                map.put("state","待处理");
                break;
            case 1:
                map.put("state","审核中");
                break;
            case 2:
                map.put("state","审核通过");

                Map<String,Object> map1 = new HashMap<>();
                map1.put("name","张三");
                map1.put("profession","维修工人");
                map1.put("date","2018-01-19");
                map1.put("state","暂不合作");
                list.add(map1);
                break;
            case 3:
                map.put("state","审核中");
                break;
            case 4:
                map.put("state","审核通过");
                break;
            case 5:
                map.put("state","暂不合作");
                break;

        }
        return list;
    }
}
