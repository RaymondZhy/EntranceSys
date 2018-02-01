package com.newabel.entrancesys.ui.iview;

import android.app.Activity;

import com.newabel.entrancesys.service.entity.PhotoDirectory;

import java.util.List;


/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/20 0020.
 */

public interface PhotoPickerView extends MView{

    Activity getContext();

    void updateData(List<PhotoDirectory> directories);
}
