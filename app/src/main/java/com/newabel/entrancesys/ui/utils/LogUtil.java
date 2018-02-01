package com.newabel.entrancesys.ui.utils;

import android.util.Log;

import com.newabel.entrancesys.BuildConfig;


/**
 * Author:liuh
 * Date: 2017/11/30 10:26
 * Description:
 */

public class LogUtil {

    public static void e(String tag,String msg){
        if(BuildConfig.DEBUG){
            Log.e(tag,msg);
        }
    }

}
