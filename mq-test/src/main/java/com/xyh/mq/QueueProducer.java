package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class QueueProducer {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
//    private static final String MQ_URL=ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
    private static final String QUEUE_NAME="queue-test1";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        //带事务提交
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        for(int i=0;i<3;i++){
            TextMessage textMessage = session.createTextMessage("hello-activemq" + i);
            //持久模式
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(textMessage);
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","v1");
            mapMessage.setStringProperty("p1","vip");
            producer.send(mapMessage);
        }
        session.commit();
        producer.close();
        session.close();
        connection.close();
        System.out.println("*********生产者发送消息成功");
    }
}
