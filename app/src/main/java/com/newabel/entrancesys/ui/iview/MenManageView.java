package com.newabel.entrancesys.ui.iview;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/12 0012.
 */

public interface MenManageView extends MView {

    void notifyDataSetChanged(List<String> data);
    void setData(Map<String,Object> data);

    void complete();
}
