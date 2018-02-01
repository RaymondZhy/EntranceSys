package com.newabel.entrancesys.ui.iview;

import android.content.Context;

/**
 * Date: 2018/1/11 17:15
 * Description:
 */

public interface DoorAddNewView extends MView {

    void doorAddSuc();

    void doorAddError(String msg);

    Context getContext();
}
