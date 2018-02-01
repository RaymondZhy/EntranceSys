package com.newabel.entrancesys.ui.handler;

import com.newabel.entrancesys.ui.utils.LogUtil;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/18 0018.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogUtil.e(TAG,e.getMessage());
    }
}
