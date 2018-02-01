package com.newabel.entrancesys.ui.iview;

import android.content.Context;

/**
 * Date: 2017/12/20 09:46
 * Description:
 */

public interface EmployeeInfoListView extends MView {


    void empDeleteSuc();

    void empDeleteError(String msg);

    void empModifySuc();

    void empModifyError(String msg);

    Context getContext();
}
