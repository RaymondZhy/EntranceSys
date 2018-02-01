package com.newabel.entrancesys.service.presenter;

import android.content.Context;

import com.newabel.entrancesys.service.entity.Book;
import com.newabel.entrancesys.ui.iview.BookView;
/**
 * Created by huan on 2017/11/14 10:42.
 * 发起网络请求示例代码
 */

public class BookPresenter extends BasePresenter<BookView> {
    private Context mContext;
    private Book mBook;

    public BookPresenter(BookView view) {
        super(view);
    }

    //这块的代码没有抽取到父类，感觉写在这里逻辑会清楚些，如果有好的抽取也是可以的
//    public void getSearchBooks(String name, String tag, int start, int count) {

//        DisposableObserver<Book> disposableObserver = new DisposableObserver<Book>() {
//            @Override
//            public void onNext(Book book) {
//                mBook = book;
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mBookView.onError("请求失败 " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                if (mBook != null) {
//                    mBookView.onSuccess(mBook);
//                }
//            }
//        };
//
//        mDataManager.getSearchBooks(name, tag, start, count)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(disposableObserver);
//
//        mCompositeDisposable.add(disposableObserver);
//    }

}
