package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.UpdateDescView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/7 0007.
 */

public class UpdateDescPresenter extends BasePresenter<UpdateDescView> {

    public UpdateDescPresenter(UpdateDescView view) {
        super(view);
    }


    public List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("title", "手机门禁1.9主要更新");
        map1.put("date", "12月07日");
        map1.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("title", "手机门禁1.8主要更新");
        map2.put("date", "08月07日");
        map2.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("title", "手机门禁1.7主要更新");
        map3.put("date", "07月07日");
        map3.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("title", "手机门禁1.6主要更新");
        map4.put("date", "06月07日");
        map4.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("title", "手机门禁1.5主要更新");
        map5.put("date", "05月07日");
        map5.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map5);

        Map<String, Object> map6 = new HashMap<>();
        map6.put("title", "手机门禁1.4主要更新");
        map6.put("date", "04月07日");
        map6.put("content", "1 解决了已知Bug。\n2 增加了聊天功能。\n3 增加了门禁报警信息推送");
        list.add(map6);

        return list;
    }
}
