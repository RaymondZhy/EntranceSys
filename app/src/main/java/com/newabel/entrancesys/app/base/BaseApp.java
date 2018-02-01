package com.newabel.entrancesys.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.Realm.BaseRealmHelper;
import com.newabel.entrancesys.service.entity.User;
import com.newabel.entrancesys.ui.handler.CrashHandler;
import com.newabel.entrancesys.ui.utils.LocaleUtils;
import com.newabel.entrancesys.ui.utils.PreUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePal;

/**
 * Author:liuh
 * Date: 2017/11/29 17:54
 * Description:
 */

public class BaseApp extends MultiDexApplication {

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//主线程的消息循环队列
    private static Handler mHandler;//主线程的Handler

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());

        //初始化用户登录信息
        User.getInstance().init();

        //初始化IP设置
        initIpSetting();

        //******************************相关第三方SDK的初始化等操作******************************

        LitePal.initialize(this);//初始化LitePal

        LocaleUtils.switchLanguage(this, LocaleUtils.getConfiguration()); //国际化切换

        ZXingLibrary.initDisplayOpinion(this); //二维码扫描

        BaseRealmHelper.init(this); //数据库
    }

    /**
     * 初始化IP设置
     */
    private void initIpSetting() {
        String url = PreUtils.getString(PreUtils.IP_SETTING, "");
        if (!TextUtils.isEmpty(url)) {
            String[] arr = url.split(":");
            if (arr.length >= 2) {
                Constant.APP_SERVER_URL = arr[0];
                Constant.APP_SERVER_PORT = arr[1];
            }
        }
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        BaseApp.mContext = mContext;
    }

    public static Thread getmMainThread() {
        return mMainThread;
    }

    public static void setmMainThread(Thread mMainThread) {
        BaseApp.mMainThread = mMainThread;
    }

    public static long getmMainThreadId() {
        return mMainThreadId;
    }

    public static void setmMainThreadId(long mMainThreadId) {
        BaseApp.mMainThreadId = mMainThreadId;
    }

    public static Looper getmMainLooper() {
        return mMainLooper;
    }

    public static void setmMainLooper(Looper mMainLooper) {
        BaseApp.mMainLooper = mMainLooper;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static void setmHandler(Handler mHandler) {
        BaseApp.mHandler = mHandler;
    }
}
