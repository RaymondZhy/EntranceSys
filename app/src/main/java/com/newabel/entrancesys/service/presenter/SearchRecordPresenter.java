package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.newabel.entrancesys.service.Realm.SearchRecord;
import com.newabel.entrancesys.ui.utils.DateUitls;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/13 0013.
 */

public class SearchRecordPresenter extends BasePresenter {

    private final String TAG = "SearchRecordPresenter";
    private Realm mRealm;
    private String mMark = "mark";
    private String mKeyword = "keyword";
    private String mDatetime = "datetime";
    public final int maxCount = 10;

    public SearchRecordPresenter(Object view) {
        super(view);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mRealm != null) {
            mRealm.isClosed();
        }
    }

    public void insert(String mark, String keyword) {
        final SearchRecord searchRecord = new SearchRecord();
        searchRecord.setMark(mark);
        searchRecord.setKeyword(keyword);
        searchRecord.setDatetime(DateUitls.getNowDateTime(DateUitls.PATTERN_1));
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(searchRecord);
            }
        });
    }

    public void deleteRecord(SearchRecord searchRecord) {
        final RealmResults<SearchRecord> results = mRealm.where(SearchRecord.class)
                .equalTo(mMark, searchRecord.getMark())
                .and()
                .equalTo(mKeyword, searchRecord.getKeyword())
                .findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteLastFromRealm();
            }
        });
    }

    public void deleteOldestRecord(String mark) {
        final RealmResults<SearchRecord> results = mRealm.where(SearchRecord.class)
                .equalTo(mMark, mark)
                .findAll().sort(mDatetime, Sort.DESCENDING);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteLastFromRealm();
            }
        });
    }

    public int getRecordCount(String mark) {
        RealmResults<SearchRecord> results = mRealm.where(SearchRecord.class)
                .equalTo(mMark, mark)
                .findAll();
        return results.size();
    }

    public List<SearchRecord> getRecords(String mark, String keyword) {
        String word = TextUtils.isEmpty(keyword) ? "*" : ("*" + keyword + "*");
        RealmResults<SearchRecord> results = mRealm.where(SearchRecord.class)
                .like(mKeyword, word)
                .and()
                .equalTo(mMark, mark)
                .findAll()
                .sort(mDatetime, Sort.DESCENDING);
        return results;
    }

}
