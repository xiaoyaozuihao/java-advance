package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class TopicProvider_Persist {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
    private static final String QUEUE_NAME="queue-test1";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic-persist");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        BytesMessage bytesMessage = session.createBytesMessage();
        String hello="hello world!";
        bytesMessage.writeBytes(hello.getBytes());
        producer.send(bytesMessage);
        session.commit();
        producer.close();
        session.close();
        connection.close();
        System.out.println("持久化topic消息发送成功");
    }

}
