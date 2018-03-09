package com.newabel.entrancesys.service.helper.ActiveMq;

//import com.newabel.entrancesys.ui.utils.LogUtil;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import java.util.Map;
//import java.util.UUID;
//
//import javax.jms.Connection;
//import javax.jms.DeliveryMode;
//import javax.jms.JMSException;
//import javax.jms.MessageConsumer;
//import javax.jms.MessageListener;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import javax.jms.Topic;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/3/8 0008.
 */

public class ActiveMqHelper {


//    private final String TAG = this.getClass().getSimpleName();
//    private String username = "system";
//    private String password = "manager";
//    private String brokerUrl = "mqtt://tcp://192.168.1.4:1883";
//    private String clientID = UUID.randomUUID().toString().substring(0, 23);
//    private MessageListener mActiveMqCallBack;
//
//    private static ActiveMQConnectionFactory mConnectionFactory;
//    private static Connection mConnection;
//    private static Session mSession;
//    private static Map<String, MessageConsumer> consumers;
//    private static ActiveMqHelper instance;

    private ActiveMqHelper() {

    }

//    public static ActiveMqHelper getInstance() {
//        if (instance == null) {
//            instance = new ActiveMqHelper();
//        }
//        return instance;
//    }
//
//    public boolean connect() {
//        try {
//            closeConnection();
//            mConnection = createConnection();
//
//            if (mConnection != null) {
//                closeSession();
//                mSession = createSession();
//            }
//            return mConnection != null && mSession != null;
//        } catch (Exception e) {
//            LogUtil.e(TAG, e.getMessage());
//        }
//        return false;
//    }
//
//
//    public boolean isConnect() {
//        return mConnection != null && mSession != null;
//    }
//
//
//    public Connection createConnection() {
//        Connection mConnection = null;
//        try {
//            if (mConnectionFactory == null) {
//                mConnectionFactory = new ActiveMQConnectionFactory(username,password,brokerUrl);
//            }
////            mConnectionFactory.setUserName(username);
////            mConnectionFactory.setPassword(password);
////            mConnectionFactory.setBrokerURL(brokerUrl);
////            mConnectionFactory.setClientID(clientID);
//            mConnection = mConnectionFactory.createConnection();
//            mConnection.start();
//        } catch (Exception e) {
//            try {
//                if (mConnection != null) {
//                    mConnection.close();
//                    mConnection = null;
//                }
//            } catch (Exception e1) {
//                LogUtil.e(TAG, e1.getMessage());
//            }
//            LogUtil.e(TAG, e.getMessage());
//        }
//        return mConnection;
//    }
//
//    private void closeConnection() throws JMSException {
//        if (mConnection != null) {
//            mConnection.close();
//            mConnection = null;
//        }
//    }
//
//    public Session createSession() {
//        Session mSession = null;
//        try {
//            mSession = mConnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//        } catch (Exception e) {
//            LogUtil.e(TAG, e.getMessage());
//            try {
//                if (mSession != null) {
//                    mSession.close();
//                    mSession = null;
//                }
//            } catch (Exception e1) {
//                LogUtil.e(TAG, e1.getMessage());
//            }
//        }
//        return mSession;
//    }
//
//    private void closeSession() throws JMSException {
//        if (mSession != null) {
//            mSession.close();
//            mSession = null;
//        }
//    }
//
//    public boolean sendMessage(String topic, String message) {
//        MessageProducer producer = null;
//        try {
//            Topic tp = mSession.createTopic(topic);
//            producer = mSession.createProducer(tp);
//            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//            TextMessage tMessage = mSession.createTextMessage(message);
//            producer.send(tMessage);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (producer != null) {
//                    producer.close();
//                    producer = null;
//                }
//            } catch (JMSException e) {
//                LogUtil.e(TAG, e.getMessage());
//
//            }
//        }
//        return false;
//    }
//
//    public void subscribe(String topic) {
//        MessageConsumer consumer = null;
//        try {
//            if (consumers.get(topic) == null) {
//                Topic tp = mSession.createTopic(topic);
//                consumer = mSession.createConsumer(tp);
//                consumer.setMessageListener(mActiveMqCallBack);
//                consumers.put(topic, consumer);
//            }
//        } catch (Exception e) {
//            try {
//                LogUtil.e(TAG, e.getMessage());
//                if (consumer != null) {
//                    consumer.close();
//                    consumer = null;
//                }
//            } catch (Exception e1) {
//                LogUtil.e(TAG, e1.getMessage());
//            }
//        }
//    }
//
//    public void unsubscribe(String topic) {
//        try {
//            if (consumers.get(topic) == null) {
//                mSession.unsubscribe(topic);
//                consumers.get(topic).close();
//                consumers.remove(topic);
//            }
//        } catch (Exception e) {
//            LogUtil.e(TAG, e.getMessage());
//        }
//    }
//
//    public void subscribe(String[] topics) {
//        for (String topic : topics) {
//            subscribe(topic);
//        }
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getBrokerUrl() {
//        return brokerUrl;
//    }
//
//    public String getClientID() {
//        return clientID;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setBrokerUrl(String brokerUrl) {
//        this.brokerUrl = brokerUrl;
//    }
//
//    public void setClientID(String clientID) {
//        this.clientID = clientID;
//    }
//
//    public void setmActiveMqCallBack(MessageListener mActiveMqCallBack) {
//        this.mActiveMqCallBack = mActiveMqCallBack;
//    }
}
