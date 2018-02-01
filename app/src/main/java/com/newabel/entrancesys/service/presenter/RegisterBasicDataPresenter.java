package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.iview.RegisterBasicDataView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/24 0024.
 */

public class RegisterBasicDataPresenter extends BasePresenter<RegisterBasicDataView> {

    public RegisterBasicDataPresenter(RegisterBasicDataView view) {
        super(view);
    }

    public List<Map<String, Object>> getSexData(String sex) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        String str = UIUtils.getString(R.string.male);
        map1.put("name", str);
        map1.put("isSelect", TextUtils.equals(sex, str));
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        str = UIUtils.getString(R.string.female);
        map2.put("name", str);
        map2.put("isSelect", TextUtils.equals(sex, str));
        list.add(map2);

        return list;
    }

    public List<Map<String,Object>> getEthnicData(String ethnic){
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(getMap("汉族",ethnic));
        list.add(getMap("壮族",ethnic));
        list.add(getMap("回族",ethnic));
        list.add(getMap("满族",ethnic));
        list.add(getMap("维吾尔族",ethnic));
        list.add(getMap("苗族",ethnic));
        list.add(getMap("彝族",ethnic));
        list.add(getMap("土家族",ethnic));
        list.add(getMap("藏族",ethnic));
        list.add(getMap("蒙古族",ethnic));
        list.add(getMap("侗族",ethnic));
        list.add(getMap("布依族",ethnic));
        list.add(getMap("瑶族",ethnic));
        list.add(getMap("白族",ethnic));
        list.add(getMap("朝鲜族",ethnic));
        list.add(getMap("哈尼族",ethnic));
        list.add(getMap("黎族",ethnic));
        list.add(getMap("哈萨克族",ethnic));
        list.add(getMap("傣族",ethnic));
        list.add(getMap("畲族",ethnic));
        list.add(getMap("傈僳族",ethnic));
        list.add(getMap("东乡族",ethnic));
        list.add(getMap("仡佬族",ethnic));
        list.add(getMap("拉祜族",ethnic));
        list.add(getMap("佤族",ethnic));
        list.add(getMap("水族",ethnic));
        list.add(getMap("纳西族",ethnic));
        list.add(getMap("羌族",ethnic));
        list.add(getMap("土族",ethnic));
        list.add(getMap("仫佬族",ethnic));
        list.add(getMap("锡伯族",ethnic));
        list.add(getMap("柯尔克孜族",ethnic));
        list.add(getMap("景颇族",ethnic));
        list.add(getMap("达斡尔族",ethnic));
        list.add(getMap("撒拉族",ethnic));
        list.add(getMap("布朗族",ethnic));
        list.add(getMap("毛南族",ethnic));
        list.add(getMap("塔吉克族",ethnic));
        list.add(getMap("普米族",ethnic));
        list.add(getMap("阿昌族",ethnic));
        list.add(getMap("怒族",ethnic));
        list.add(getMap("鄂温克族",ethnic));
        list.add(getMap("京族",ethnic));
        list.add(getMap("基诺族",ethnic));
        list.add(getMap("德昂族",ethnic));
        list.add(getMap("保安族",ethnic));
        list.add(getMap("俄罗斯族",ethnic));
        list.add(getMap("裕固族",ethnic));
        list.add(getMap("乌孜别克族",ethnic));
        list.add(getMap("门巴族",ethnic));
        list.add(getMap("鄂伦春族",ethnic));
        list.add(getMap("独龙族",ethnic));
        list.add(getMap("赫哲族",ethnic));
        list.add(getMap("高山族",ethnic));
        list.add(getMap("珞巴族",ethnic));
        list.add(getMap("塔塔尔族",ethnic));

        return list;
    }

    private Map<String, Object> getMap(String ethnic, String initValue){
        Map<String, Object> map = new HashMap<>();
        map.put("name", ethnic);
        map.put("isSelect", TextUtils.equals(ethnic, initValue));
        return map;
    }
}
