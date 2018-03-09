package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.helper.Retrofit.RetrofitHelper;
import com.newabel.entrancesys.service.helper.Retrofit.RetrofitService;
import com.newabel.entrancesys.ui.utils.UIUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Created by huan on 2017/11/14 10:38.
 */

public abstract class BasePresenter<V> {
    //CompositeDisposable是用来存放RxJava中的订阅关系,
    //请求完数据要及时清掉这个订阅关系，不然会发生内存泄漏
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected RetrofitService retrofitService = RetrofitHelper.getInstance().create(RetrofitService.class);

    protected V mView;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void addSubscription(Observable<? extends Object> observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mCompositeDisposable.add(observer);
    }

    public  String parserThrowable(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code == 504) {
                return UIUtils.getContext().getString(R.string.network_timeout);
            } else if (code == 502 || code == 404) {
                return UIUtils.getContext().getString(R.string.network_timeout);
            }
        }
        return null;
    }

    /**
     * 用于绑定我们自己的View
     *
     * @param view
     */
    public void attachView(V view) {
        mView = view;
    }

    ;

    public void detachView() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

}
