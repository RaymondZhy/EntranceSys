package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.TestView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/11 0011.
 */

public class TestPresenter extends BasePresenter<TestView> {

    public TestPresenter(TestView view) {
        super(view);
    }

    public List<Map<String,Object>> getData(){

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("image","http://www.quanjing.com/image/2017index/lx1.png");
        map1.put("name","会飞的鱼儿");
        map1.put("message","有一个会飞的梦想...");
        map1.put("time","上午 08:40");

        Map<String,Object> map2 = new HashMap<>();
        map2.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map2.put("name","谦虚低调");
        map2.put("message","傍晚的火烧云...");
        map2.put("time","下午 15:30");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map3.put("name","谦虚低调");
        map3.put("message","傍晚的火烧云...");
        map3.put("time","下午 15:30");

        Map<String,Object> map4 = new HashMap<>();
        map4.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map4.put("name","谦虚低调");
        map4.put("message","傍晚的火烧云...");
        map4.put("time","下午 15:30");

        Map<String,Object> map5 = new HashMap<>();
        map5.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map5.put("name","谦虚低调");
        map5.put("message","傍晚的火烧云...");
        map5.put("time","下午 15:30");

        Map<String,Object> map6 = new HashMap<>();
        map6.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map6.put("name","谦虚低调");
        map6.put("message","傍晚的火烧云...");
        map6.put("time","下午 15:30");

        Map<String,Object> map7 = new HashMap<>();
        map7.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map7.put("name","谦虚低调");
        map7.put("message","傍晚的火烧云...");
        map7.put("time","下午 15:30");

        Map<String,Object> map8 = new HashMap<>();
        map8.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map8.put("name","谦虚低调");
        map8.put("message","傍晚的火烧云...");
        map8.put("time","下午 15:30");

        Map<String,Object> map9 = new HashMap<>();
        map9.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map9.put("name","谦虚低调");
        map9.put("message","傍晚的火烧云...");
        map9.put("time","下午 15:30");

        Map<String,Object> map10 = new HashMap<>();
        map10.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map10.put("name","谦虚低调");
        map10.put("message","傍晚的火烧云...");
        map10.put("time","下午 15:30");

        Map<String,Object> map11 = new HashMap<>();
        map11.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map11.put("name","谦虚低调");
        map11.put("message","傍晚的火烧云...");
        map11.put("time","下午 15:30");

        Map<String,Object> map12 = new HashMap<>();
        map12.put("image","http://mpic.tiankong.com/382/f74/382f74267c5c6a27dd38fb3407bc7df3/640.jpg");
        map12.put("name","谦虚低调");
        map12.put("message","傍晚的火烧云...");
        map12.put("time","下午 15:30");
        
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        list.add(map8);
        list.add(map9);
        list.add(map10);
        list.add(map11);
        list.add(map12);
        return list;
    }
}
