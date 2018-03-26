package com.newabel.entrancesys.service.helper.Retrofit;

import com.google.gson.GsonBuilder;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.User;
import com.newabel.entrancesys.ui.utils.LogUtil;

import java.io.IOException;
import java.util.Collections;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huan on 2017/11/14 10:02.
 */

public class RetrofitHelper {
    private static Retrofit mRetrofit = null;

    private RetrofitHelper() {

    }


    public static void reSetRetrofit() {
        mRetrofit = null;
        //登录成功后再初始化一次用户信息
        User.getInstance().init();
        getInstance();
    }

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            OkHttpClient.Builder mBuild = new OkHttpClient.Builder();
//            if (User.getInstance().getRetCode() == 0) {
                //用户未登录时和用户登录成功时,为每一次接口调用都添加请求头信息
                //同时拦截并打印请求request和响应Response数据
                mBuild.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
//                                .addHeader("UserID", User.getInstance().getUserID())
//                                .addHeader("UserKey", User.getInstance().getRetData())
                                .addHeader("Content-Type","application/x-www-form-urlencoded")
                                .build();
                        long startTime = System.currentTimeMillis();
                        Response response = chain.proceed(request);
                        long endTime = System.currentTimeMillis();
                        long duration = endTime - startTime;
                        MediaType mediaType = response.body().contentType();
                        String content = response.body().string();
                        LogUtil.e("ApiRetrofit", "---------------Request Start----------------");
                        LogUtil.e("ApiRetrofit", "|...Request:" + request.toString());
                        LogUtil.e("ApiRetrofit", "|...Response:" + content);
                        LogUtil.e("ApiRetrofit", "---------------Request End----------------" + duration + "毫秒-------");

                        return response.newBuilder()
                                .body(ResponseBody.create(mediaType, content))
                                .build();
                    }
                });
//            }
            OkHttpClient mOkHttpClient = mBuild.build();
            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(String.format("http://%s:%s/", Constant.APP_SERVER_URL, Constant.APP_SERVER_PORT))
                    .baseUrl(Constant.FACE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Rxjava
                    .build();
        }
        return mRetrofit;
    }
}
