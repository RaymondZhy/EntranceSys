package com.newabel.entrancesys.ui.utils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/22 0022.
 */

public class StatusBarUtils {

    /**
     * 设置状态栏颜色
     * @param context
     * @param colorId
     */
    public static void setStatusBarColor(Activity context,int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            context.getWindow().setStatusBarColor(ContextCompat.getColor(context,colorId));
        }
    }

    /**
     * 设置导航栏的颜色
     * @param context
     * @param colorId
     */
    public static void setNavigationBarColor(Activity context,int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            context.getWindow().setNavigationBarColor(ContextCompat.getColor(context,colorId));
        }
    }

    /**
     * 设置透明状态栏
     * @param context
     */
    public static void setTranslucentStatusBar(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置透明导航栏
     * @param context
     */
    public static void setTranslucentNavigationBar(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
