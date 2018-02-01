package com.newabel.entrancesys.ui.iview;

import android.content.Context;

/**
 * Date: 2018/1/8 11:02
 * Description:新增员工
 */

public interface EmployeeAddNewView extends MView {

    void empAddSuc();

    void empAddError(String msg);

    Context getContext();

}
