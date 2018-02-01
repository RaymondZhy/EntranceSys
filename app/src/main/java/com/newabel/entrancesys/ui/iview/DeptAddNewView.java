package com.newabel.entrancesys.ui.iview;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Date: 2018/1/9 09:44
 * Description:
 */

public interface DeptAddNewView extends MView {

    void getDeptTypeDataSuc(List<Map<String, Object>> deptTypeData);

    void getDeptTypeDataError(String msg);

    void deptAddSuc();

    void deptAddError(String msg);

    Context getContext();
}
