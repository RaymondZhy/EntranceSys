package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.WorkOrderFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2018/1/30 10:38
 * Description:
 */

public class WorkOrderFragmentPresenter extends BasePresenter<WorkOrderFragmentView> {

    public WorkOrderFragmentPresenter(WorkOrderFragmentView view) {
        super(view);
    }

    public List<Map<String, Object>> getData(int mTabIndex) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        list.add(map);

        map.put("title", "门禁维修");
        map.put("site", "深圳市宝安区新湖路");
        map.put("desc", "门禁开门失灵,影响加钞,请尽快处理");
        map.put("date", "2018-01-19");

        switch (mTabIndex) {
            case 4:
                map.put("state", "已同意");
                break;
            case 5:
                map.put("state", "待确认");
                break;
            case 6:
                map.put("state", "已拒绝");

                Map<String, Object> map1 = new HashMap<>();
                map1.put("title", "设备安装");
                map1.put("site", "深圳市南山区科技园稻香路");
                map1.put("date", "2018-01-19");
                map1.put("desc", "新到设备,请尽快安装安装安装安装安装安装安装装装装装装装装装~");
                map1.put("state", "已拒绝");
                list.add(map1);
                break;
            case 7:
                map.put("state", "已完成");
                break;

        }
        return list;
    }
}
