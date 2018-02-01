package com.newabel.entrancesys.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/14 0014.
 */

public class DateUitls {

    public final static String PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static String getNowDateTime(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

}
