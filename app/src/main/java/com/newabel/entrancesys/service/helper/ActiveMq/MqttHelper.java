package com.newabel.entrancesys.service.helper.ActiveMq;

import android.util.Log;

import com.newabel.entrancesys.ui.utils.LogUtil;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/3/9 0009.
 */

public class MqttHelper {
    private final String TAG = this.getClass().getSimpleName();
    private final int mQos = 2; //0 最多一次；1 最少一次；2：只有一次
    private String mUsername;
    private String mPassword;
    private String brokerUrl;
    private String mClientID;
    private MqttCallback mMqttCallBack;
    private String[] topics;

    private static MqttConnectOptions mConnectOptions;
    private static MqttClient mMqttClient;
    private static MqttHelper instance;

    private MqttHelper() {

    }

    public static MqttHelper getInstance() {
        if (instance == null) {
            instance = new MqttHelper();
        }
        return instance;
    }

    public MqttClient createMqttClient() {
        MqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(brokerUrl, mClientID, new MemoryPersistence());
            mqttClient.setCallback(mMqttCallBack);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            try {
                if (mqttClient != null) {
                    mqttClient.close();
                }
            } catch (Exception e1) {
                LogUtil.e(TAG, e1.getMessage());
            }
        }
        return mqttClient;
    }

    public MqttConnectOptions getConnectOptions() {
        MqttConnectOptions mConnectOptions = new MqttConnectOptions();
        mConnectOptions.setUserName(mUsername);
        mConnectOptions.setPassword(mPassword.toCharArray());
        mConnectOptions.setAutomaticReconnect(true);
        mConnectOptions.setConnectionTimeout(3);
        mConnectOptions.setKeepAliveInterval(60);
        mConnectOptions.setCleanSession(true);
        return mConnectOptions;
    }

    public void connect() {
        if (mConnectOptions == null) {
            mConnectOptions = getConnectOptions();
        }
        new Thread() {
            @Override
            public void run() {
                while (!isConnect()) {
                    try {
                        if (mMqttClient != null) {
                            mMqttClient.close();
                            mMqttClient = null;
                        }
                        mMqttClient = createMqttClient();
                        if(mMqttClient!=null) {
                            mMqttClient.connect(mConnectOptions);
                            Log.e(TAG, "连接成功！");
                        }
                        subscribe(topics);
                        LogUtil.e(TAG, "c");
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        try {
                            sleep(3000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    public void subscribe(String topic) throws MqttException {
        if (isConnect()) {
            mMqttClient.subscribe(topic);
        }
    }

    public void subscribe(String[] topics) throws MqttException {
        if (isConnect()) {
            mMqttClient.subscribe(topics);
        }
    }

    public void unsubscribe(String topic) throws MqttException {
        if (isConnect()) {
            mMqttClient.unsubscribe(topic);
        }
    }

    public void unsubscribe(String[] topics) throws MqttException {
        if (isConnect()) {
            mMqttClient.unsubscribe(topics);
        }
    }

    public void publish(String topic, String msg) throws MqttException {
        if (isConnect()) {
            mMqttClient.publish(topic, msg.getBytes(), mQos, false);
            LogUtil.e(TAG, "发送消息：" + msg);
        }
    }

    public boolean isConnect() {
        return mMqttClient != null && mMqttClient.isConnected();
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getmClientID() {
        return mClientID;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public void setmClientID(String mClientID) {
        this.mClientID = mClientID;
    }

    public void setmMqttCallBack(MqttCallback mMqttCallBack) {
        this.mMqttCallBack = mMqttCallBack;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }
}
