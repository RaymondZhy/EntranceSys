package com.newabel.entrancesys.ui.iview;

import android.content.Context;

/**
 * Date: 2018/1/11 10:38
 * Description:
 */

public interface DeviceDetailView extends MView {

    void deviceModifySuc();

    void deviceModifyError(String msg);

    void deviceDeleteSuc();

    void deviceDeleteError(String msg);

    Context getContext();
}
