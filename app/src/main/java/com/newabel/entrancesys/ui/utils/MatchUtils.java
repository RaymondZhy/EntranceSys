package com.newabel.entrancesys.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/7 0007.
 */

public class MatchUtils {

    /**
     * 是否是Email
     * @param input
     * @return
     */
    public static boolean isEmail(String input){
        String regex = "\\S{3,}@\\S+\\.\\S+";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches();
    }

    /**
     * 是否是电话号码
     * @param input
     * @return
     */
    public static boolean isPhoneNumber(String input){
        String regex = "(\\d{3,4}-|\\(\\d{3,4}\\))?\\d{7,8}";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches();
    }

    /**
     * 是否是手机号码
     * @param input
     * @return
     */
    public static boolean isMobieNumbler(String input){
        String regex = "1\\d{10}";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches();
    }

    /**
     * 时否是IP地址
     * @param input
     * @return
     */
    public static boolean isIp(String input){
        String regex = "(?:(?:25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.matches();
    }

}
