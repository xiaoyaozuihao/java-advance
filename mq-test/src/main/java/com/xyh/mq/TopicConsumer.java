package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * 多消费者同时消费，一定要先启动消费者，否则消息就是废消息
 * @author xuyh
 * @date 2019/7/16
 */
public class TopicConsumer {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
    private static final String TOPIC_NAME="topic-test1";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer=session.createConsumer(topic);
        consumer.setMessageListener((message)->{
            TextMessage textMessage=(TextMessage)message;
            try {
                System.out.println("消费者1收到信息："+textMessage.getText());
            } catch (JMSException e) {
            }
        });
        MessageConsumer consumer1=session.createConsumer(topic);
        consumer1.setMessageListener((message)->{
            TextMessage textMessage=(TextMessage)message;
            try {
                System.out.println("消费者2收到信息："+textMessage.getText());
            } catch (JMSException e) {
            }
        });
        MessageConsumer consumer2=session.createConsumer(topic);
        consumer2.setMessageListener((message)->{
            TextMessage textMessage=(TextMessage)message;
            try {
                System.out.println("消费者3收到信息："+textMessage.getText());
            } catch (JMSException e) {
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("*****消费者消费消息完成");
    }
}
