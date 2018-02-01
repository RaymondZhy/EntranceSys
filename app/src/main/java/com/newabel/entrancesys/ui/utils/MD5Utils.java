package com.newabel.entrancesys.ui.utils;

import java.security.MessageDigest;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/12 0012.
 */

public class MD5Utils {
    public static String toHexString(String string) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                int i = (b & 0xFF);
                if (i < 0x10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
