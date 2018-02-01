package com.newabel.entrancesys.ui.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:动态申请权限
 * AUTHOR:zhangyue
 * DATE: 2017/11/14 0014.
 */

public class PermissionUtils {

    /**
     * 动态申请权限
     * @param context Activity
     * @param permissions 需要申请的权限
     * @param requestCode 申请权限的请求码
     */
    public static void requestPermissions(Activity context, String[] permissions,int requestCode){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            List<String> list = new ArrayList<>();
            for(int i = 0;i<permissions.length;i++){
                if(context.checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_DENIED){
                    list.add(permissions[i]);
                }
            }
            if(list.size() > 0){
                String[] deniedArray = new String[list.size()];
                list.toArray(deniedArray);
                context.requestPermissions(deniedArray,requestCode);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(permissions,requestCode);
        }
    }

    public static boolean hasPermission(Activity context, String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }else{
            return true;
        }
    }

    public static boolean hasPermissions(Activity context, String[] permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(int i = 0;i<permissions.length;i++){
                if(context.checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_DENIED){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasPermissions(int[] grantResults){
        for(int i = 0;i< grantResults.length;i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

}
