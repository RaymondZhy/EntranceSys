package com.newabel.entrancesys.ui.iview;

import android.content.Context;

import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public interface EntranceDataView {
    Context getContext();

    void showDeepData(Map<String,Object> map);

    void doorModifySuc();

    void doorModifyError(String msg);

    void doorDeleteSuc();

    void doorDeleteError(String msg);
}
