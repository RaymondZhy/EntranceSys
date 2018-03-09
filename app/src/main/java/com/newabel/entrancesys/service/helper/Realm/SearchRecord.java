package com.newabel.entrancesys.service.helper.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/13 0013.
 */

public class SearchRecord extends RealmObject {

    private String mark;

    @PrimaryKey
    @Required
    private String keyword;
    private String datetime;

    public String getMark() {
        return mark;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
