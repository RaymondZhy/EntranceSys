package com.newabel.entrancesys.ui.iview;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Date: 2018/1/9 14:08
 * Description:
 */

public interface DeptModifyView extends MView {

    void getDeptTypeDataSuc(List<Map<String, Object>> deptTypeData);

    void getDeptTypeDataError(String msg);

    void modifyDeptSuc();

    void modifyDeptError(String msg);

    void deleteDeptSuc();

    void deleteDeptError(String msg);

    Context getContext();
}
