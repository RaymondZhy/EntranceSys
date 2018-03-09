package com.newabel.entrancesys.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.utils.LogUtil;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.UUID;

public class ActiveMqService extends Service implements Listener {

    private final String TAG = this.getClass().getSimpleName();
    private MQTT mMqtt;
    private CallbackConnection mCallbackConnection;

    public static final String ACTION_CONNECT = "ACTION_CONNECT";
    public static final String ACTION_RECONNECT = "ACTION_RECONNECT";
    public static final String ACTION_DISCONNECT = "ACTION_DISCONNECT";
    public static final String ACTION_PUBLISH = "ACTION_PUBLISH";
    private final long RECONNECTION_ATTEMPT_MAX = 6;
    private final long RECONNECTION_DELAY = 3000; //毫秒
    private final short KEEP_ALIVE = 30; //30秒
    private static final int SEND_BUFFER_SIZE = 2 * 1024 * 1024; //2M

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMQTT();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "进入onStartCommand");
        switch (intent.getAction()) {
            case ACTION_CONNECT:
                connect();
                break;
            case ACTION_RECONNECT:

                break;
            case ACTION_DISCONNECT:
                disConnect();
                break;
            case ACTION_PUBLISH:

                break;
        }
        return START_STICKY;
    }

    private void initMQTT() {
        try {
            mMqtt = new MQTT();
            mMqtt.setHost(Constant.ACTIVE_MQ_URL, Constant.ACTIVE_MQ_PORT);
            mMqtt.setCleanSession(true);
            //设置重新连接的次数
            mMqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            //设置重连的间隔时间
            mMqtt.setReconnectDelay(RECONNECTION_DELAY);
            //设置心跳时间
            mMqtt.setKeepAlive(KEEP_ALIVE);
            //设置缓冲的大小
            mMqtt.setSendBufferSize(SEND_BUFFER_SIZE);

            mMqtt.setClientId(UUID.randomUUID().toString().substring(0, 23));
            mMqtt.setUserName("system");
            mMqtt.setPassword("manager");
            mCallbackConnection = mMqtt.callbackConnection();
            mCallbackConnection.listener(new Listener() {
                @Override
                public void onConnected() {
                    LogUtil.e(TAG, "Listener-->onSuccess");
                }

                @Override
                public void onDisconnected() {
                    LogUtil.e(TAG, "Listener-->onSuccess");
                }

                @Override
                public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
                    LogUtil.e(TAG, "Listener-->onSuccess");
                }

                @Override
                public void onFailure(Throwable value) {
                    LogUtil.e(TAG, "Listener-->onSuccess");
                }
            });
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    /**
     * 建立链接
     */
    public void connect() {
        mCallbackConnection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                LogUtil.e(TAG, "connect-->onSuccess");
            }

            @Override
            public void onFailure(Throwable value) {
                LogUtil.e(TAG, "connect-->onFailure");
            }
        });
    }

    /**
     * 断开链接
     */
    public void disConnect() {
        mCallbackConnection.disconnect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }

    /**
     * 订阅主题
     *
     * @param topics
     */
    public void subscribe(Topic[] topics) {
        mCallbackConnection.subscribe(topics, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }

    /**
     * 取消订阅
     *
     * @param topics
     */
    public void unSubscribe(UTF8Buffer[] topics) {
        mCallbackConnection.unsubscribe(topics, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }

    /**
     * 发布消息
     *
     * @param topic
     * @param message
     * @param qoS
     * @param retain
     */
    public void publish(String topic, String message, QoS qoS, boolean retain) {
        mCallbackConnection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, true, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }

    @Override
    public void onConnected() {
        LogUtil.e(TAG, "listener-->onConnected");
    }

    @Override
    public void onDisconnected() {
        LogUtil.e(TAG, "listener-->onDisconnected");
    }

    @Override
    public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
        LogUtil.e(TAG, "listener-->onPublish");
    }

    @Override
    public void onFailure(Throwable value) {
        LogUtil.e(TAG, "listener-->onFailure");
    }
}
