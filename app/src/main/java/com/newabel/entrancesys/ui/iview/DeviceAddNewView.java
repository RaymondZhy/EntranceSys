package com.newabel.entrancesys.ui.iview;

import android.content.Context;

/**
 * Date: 2018/1/10 16:15
 * Description:新增设备
 */

public interface DeviceAddNewView extends MView {

    void deviceAddSuc();

    void deviceAddFail(String msg);

    Context getContext();
}
