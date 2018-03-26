package com.newabel.entrancesys.service.helper.ActiveMq;

import com.newabel.entrancesys.ui.utils.LogUtil;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/3/12 0012.
 */

public class MqMessage extends MqttMessage {
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_FILE = "file";
    private final String TAG = this.getClass().getSimpleName();

    private final String DECOLLATOR = "&";

    private String publisher;
    private String time;
    private String type;
    private String content;

    @Override
    public byte[] getPayload() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("publisher=");
        stringBuilder.append(publisher);
        stringBuilder.append(DECOLLATOR);
        stringBuilder.append("time=");
        stringBuilder.append(time);
        stringBuilder.append(DECOLLATOR);
        stringBuilder.append("type=");
        stringBuilder.append(type);
        stringBuilder.append(DECOLLATOR);
        stringBuilder.append("content=");
        stringBuilder.append(content);
        stringBuilder.append(DECOLLATOR);

        byte[] bytes = stringBuilder.toString().getBytes(Charset.forName("UTF-8"));
        byte[] contents = getContentBytes();
        byte[] buffer = new byte[bytes.length + contents.length];
        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(contents, 0, buffer, bytes.length, contents.length);
        return buffer;
    }

    public MqMessage getMessage(byte[] bytes) {
//        for (int i = 0; i < bytes.length; i++) {
//            String str = new String(bytes);
//            String[] strs = str.split(DECOLLATOR);
//            this.publisher = strs[0].split("=")[1];
//            this.time = strs[1].split("=")[1];
//            this.type = strs[2].split("=")[1];
//            this.content = strs[3].split("=")[1]; //获取文件没写 待写
//        }
//        byte[] b = new byte[16];
//        System.arraycopy(bytes, 11, b, 11, 16);
        return this;
    }

    private byte[] getContentBytes() {

        byte[] bytes = null;
        try {
            switch (type) {
                case TYPE_TEXT:
                    bytes = content.getBytes(Charset.forName("UTF-8"));
                    break;
                case TYPE_FILE:
                    bytes = getContentBytes(content);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return bytes;
    }


    public byte[] getContentBytes(String path) {
        ByteArrayOutputStream bos = null;
        BufferedInputStream bis = null;
        File file = new File(content);
        try {
            if (file.exists()) {
                bis = new BufferedInputStream(new FileInputStream(file));
                bos = new ByteArrayOutputStream((int) file.length());
                byte[] buffer = new byte[4096];
                int len;
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, len);
                }
                return bos.toByteArray();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return new byte[0];
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
