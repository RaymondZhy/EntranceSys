package com.newabel.entrancesys.ui.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/17 0017.
 */

public class LocaleUtils {


    public static void saveConfiguration(int index){
        PreUtils.putInt(PreUtils.LOCALE_SETTING,index);
    }

    public static Locale getConfiguration(){
        int language = PreUtils.getInt(PreUtils.LOCALE_SETTING,-1);
        Locale locale;
        switch (language){
            case 0:
                locale = Locale.getDefault();
                break;
            case 1:
                locale = Locale.CHINA;
                break;
            case 2:
                locale = Locale.TAIWAN;
                break;
            case 3:
                locale = Locale.US;
                break;
            default:
                locale = Locale.getDefault();
                break;
        }
        return locale;
    }

    public static void switchLanguage(Context context, Locale locale){
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }else{
            configuration.locale = locale;
        }
        context.getResources().updateConfiguration(configuration,context.getResources().getDisplayMetrics());
    }

    public static boolean equal(Locale srcLocale,Locale desLocale){
        return srcLocale.getCountry().equals(desLocale.getCountry()) && srcLocale.getLanguage().equals(desLocale.getLanguage());
    }
}
