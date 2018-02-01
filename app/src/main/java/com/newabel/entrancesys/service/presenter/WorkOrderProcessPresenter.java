package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.WorkOrderProcessView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2018/1/31 09:24
 * Description:
 */

public class WorkOrderProcessPresenter extends BasePresenter<WorkOrderProcessView> {

    public WorkOrderProcessPresenter(WorkOrderProcessView view) {
        super(view);
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("content", "完成");
        map1.put("date", "2018-01-23 16:41:06");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("content", "中国银行已对维修结果进行确认。");
        map2.put("date", "2018-01-23 16:41:06");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("content", "纽贝尔公司已对维修结果进行了确认。");
        map3.put("date", "2018-01-20 17:41:06");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("content", "维修人员已完成维修任务。");
        map4.put("date", "2018-01-19 10:21:06");
        list.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("content", "纽贝尔公司已指派维修人员前去维修。");
        map5.put("date", "2018-01-18 17:21:06");
        list.add(map5);

        Map<String, Object> map6 = new HashMap<>();
        map6.put("content", "中国银行委托提交了维修工单并指派纽贝尔公司完成维修工作。");
        map6.put("date", "2018-01-18 14:21:06");
        list.add(map6);

        return list;
    }
}
