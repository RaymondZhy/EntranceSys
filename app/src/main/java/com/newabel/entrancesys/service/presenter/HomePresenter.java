package com.newabel.entrancesys.service.presenter;

import com.newabel.entrancesys.ui.iview.HomeView;
import com.newabel.entrancesys.ui.utils.LogUtil;

import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/3/23 0023.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    public final String TAG = this.getClass().getSimpleName();

    public HomePresenter(HomeView view) {
        super(view);
    }

    public void detect(){
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key","KzjjzVbEgUnU6N3dvB16uqDY1DSiJUI2")
                .addFormDataPart("api_secret","ebnAB5akuaL_iK_MVYOOtdxaOlOBpNEq")
                .addFormDataPart("image_url","http://t2.hddhhn.com/uploads/tu/20150507/5103-5KwVRs.jpg")
                .build();
        addSubscription(retrofitService.detect(body), new DisposableObserver<Map<String,Object>>() {

            @Override
            public void onNext(Map<String,Object> map) {
                LogUtil.e(TAG,map.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG,"完成");
            }
        });
    }
}
