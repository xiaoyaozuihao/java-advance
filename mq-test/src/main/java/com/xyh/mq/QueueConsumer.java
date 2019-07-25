package com.xyh.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class QueueConsumer {
        public static final String MQ_URL="tcp://192.168.40.210:61616";
//    private static final String MQ_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
    public static final String QUEUE_NAME = "queue-test1";
    public static final String USERANME = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(USERANME, PASSWORD, MQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        //手动签收，需要消息ack，
        // 如果开启了事务，没有commit，ack没用。
        // 有commit，即使手动签收，也不需要ack
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        //使用循环
        while (true) {
            Message message = consumer.receive(1000L);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                if (textMessage != null) {
                    System.out.println(textMessage.getText());
                    textMessage.acknowledge();
                }
            } else if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                if (mapMessage != null) {
                    System.out.println(mapMessage.getString("k1"));
                    System.out.println(mapMessage.getStringProperty("p1"));
                    mapMessage.acknowledge();
                }
            } else {
                break;
            }
        }
//        session.commit();
        //使用监听器
//        consumer.setMessageListener((message)->{
//            if(message instanceof TextMessage){
//                TextMessage textMessage=(TextMessage)message;
//                try {
//                    System.out.println(textMessage.getText());
//                } catch (JMSException e) {
//                }
//            }else if(message instanceof MapMessage){
//                MapMessage mapMessage=(MapMessage)message;
//                try {
//                    System.out.println(mapMessage.getString("k1"));
//                } catch (JMSException e) {
//                }
//            }
//        });
//        //起到阻塞的方式
//        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("*********消费者消费消息成功");
    }
}
