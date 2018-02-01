package com.newabel.entrancesys.service.entity;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/20 0020.
 */

public class MessageEvent {
    /**
     * 切换语言
     */
    public static final int ACTION_SWITCH_LANGUAGE = 0;
    /**
     * 设置Main当前界面
     */
    public static final int ACTION_SET_MAIN_CURRENT_PAGE = 1;
    /**
     * 开始轮播图轮播
     */
    public static final int ACTION_START_BANNER_TURN = 3;
    /**
     * 停止轮播图轮播
     */
    public static final int ACTION_STOP_BANNER_TURN = 4;


    /**
     * 用于activity刷新界面数据
     * */
    public static final int ACTION_ACTIVITY_UPDATE = 5;

    private int action;
    private Object message;

    public MessageEvent(int action) {
        this.action = action;
    }

    public MessageEvent(Object message) {
        this.message = message;
    }

    public MessageEvent(int action, Object message) {
        this.action = action;
        this.message = message;
    }

    public int getAction() {
        return action;
    }

    public Object getMessage() {
        return message;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
