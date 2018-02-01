package com.newabel.entrancesys.ui.iview;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/18 0018.
 */

public interface DeviceDialogView {

    Context getContext();
    void showData(List<Map<String, Object>> list);
    void setEmptyView();
    void setErrorView();
    void setNoNetWorkView();
    void showDeepData(List<Map<String, Object>> list);
    void beginRefresh(boolean bl);
    void refresh();
}
