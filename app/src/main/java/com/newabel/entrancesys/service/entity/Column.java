package com.newabel.entrancesys.service.entity;

import java.util.List;

/**
 * Date: 2017/12/5 16:14
 * Description:首页数据分类
 */

public class Column {

    public static final int column_style_1 = 0;//轮播图
    public static final int column_style_2 = 1;//gridview样式
    public static final int column_style_3 = 2;//新闻灰色导航栏
    public static final int column_style_4 = 3;//新闻列表样式


    private int style;
    private List<Content> contents;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
