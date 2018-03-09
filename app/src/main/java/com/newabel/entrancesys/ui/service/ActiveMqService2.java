package com.newabel.entrancesys.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.newabel.entrancesys.service.helper.ActiveMq.ActiveMqHelper;
import com.newabel.entrancesys.service.helper.ActiveMq.MqttHelper;
import com.newabel.entrancesys.ui.utils.LogUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

public class ActiveMqService2 extends Service implements MqttCallback {
    private ActiveMqHelper mActiveMqHelper;

    public static final String ACTION_CONNECT = "ACTION_CONNECT";
    public static final String ACTION_RECONNECT = "ACTION_RECONNECT";
    public static final String ACTION_DISCONNECT = "ACTION_DISCONNECT";
    public static final String ACTION_PUBLISH = "ACTION_PUBLISH";

    private final String username = "admin";
    private final String password = "admin";
    //    private final String brokerUrl = "tcp://192.168.1.4:61614";
    private final String brokerUrl = "tcp://192.168.1.4:1883";
    private final String clientID = UUID.randomUUID().toString().substring(0, 23);
    private MqttHelper mMqttHelper;

    private final String TEMP_TOPIC = "TEMP_TOPIC";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mActiveMqHelper = ActiveMqHelper.getInstance();
//        mActiveMqHelper.setUsername(username);
//        mActiveMqHelper.setPassword(password);
//        mActiveMqHelper.setBrokerUrl(brokerUrl);
//        mActiveMqHelper.setClientID(clientID);

        mMqttHelper = MqttHelper.getInstance();
        mMqttHelper.setmUsername(username);
        mMqttHelper.setmPassword(password);
        mMqttHelper.setBrokerUrl(brokerUrl);
        mMqttHelper.setmClientID(clientID);
        mMqttHelper.setTopics(getTopics());
        mMqttHelper.setmMqttCallBack(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_CONNECT:
//                mActiveMqHelper.connect();
                mMqttHelper.connect();
                break;
            case ACTION_RECONNECT:

                break;
            case ACTION_DISCONNECT:

                break;
            case ACTION_PUBLISH:
                try {
                    String msg = intent.getStringExtra("MESSAGE_MQTT");
                    mMqttHelper.publish(TEMP_TOPIC, msg);
                } catch (Exception e) {
                    LogUtil.e("MqttHelper", e.getMessage());
                }
                break;
        }
        return START_STICKY;
    }

    @Override
    public void connectionLost(Throwable cause) {
        LogUtil.e("MqttHelper", "连接丢失");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = new String(message.getPayload(), "UTF-8");
        LogUtil.e("MqttHelper", "messageArrived: " + msg);
        Intent intent = new Intent();
        intent.setAction("ACTION_MQTT_CHAT");
        intent.putExtra("MESSAGE_MQTT", msg);
        sendBroadcast(intent);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LogUtil.e("MqttHelper", "发送完成");
    }

    public String[] getTopics() {
        return new String[]{TEMP_TOPIC};
    }
}
