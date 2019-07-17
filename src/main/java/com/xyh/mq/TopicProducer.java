package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class TopicProducer {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
    private static final String TOPIC_NAME="topic-test1";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("hello,topic");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
        System.out.println("*****生产消息完成");
    }
}
