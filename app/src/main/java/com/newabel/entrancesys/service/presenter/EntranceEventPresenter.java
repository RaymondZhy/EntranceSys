package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.EntranceEventView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public class EntranceEventPresenter extends BasePresenter<EntranceEventView> {

    public EntranceEventPresenter(EntranceEventView view) {
        super(view);
    }

    public List<Map<String,Object>> getData(){

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("description","通讯中断");
        map1.put("time","2017-12-19 08:20:30");
        map1.put("direction","方向：进");
        map1.put("origin","来源：0000D708EE");

        Map<String,Object> map2 = new HashMap<>();
        map2.put("description","合法开门");
        map2.put("time","2017-12-19 08:20:30");
        map2.put("direction","方向：出");
        map2.put("origin","来源：0000D708EE");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("description","合法刷卡");
        map3.put("time","2017-12-19 08:20:30");
        map3.put("direction","方向：进");
        map3.put("origin","来源：0000D708EE");

        Map<String,Object> map4 = new HashMap<>();
        map4.put("description","无权限通行");
        map4.put("time","2017-12-19 08:20:30");
        map4.put("direction","方向：出");
        map4.put("origin","来源：0000D708EE");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        return list;
    }
}
