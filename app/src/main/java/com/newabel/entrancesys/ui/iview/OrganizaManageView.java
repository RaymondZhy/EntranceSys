package com.newabel.entrancesys.ui.iview;

import java.util.List;
import java.util.Map;

/**
 * Date: 2017/12/13 14:19
 * Description:机构管理
 */

public interface OrganizaManageView extends MView {

    void onGetDataSuccess(List<String> data);

    void onGetDataError();


    void setData(Map<String, Object> retData);
}
