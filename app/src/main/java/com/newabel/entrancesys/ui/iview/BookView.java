package com.newabel.entrancesys.ui.iview;


import com.newabel.entrancesys.service.entity.Book;

/**
 * Created by huan on 2017/11/14 10:36.
 */

public interface BookView extends MView {
    void onSuccess(Book book);

    void onError(String result);

}
