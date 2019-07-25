package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class TopicConsumer_Persist {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
    private static final String TOPIC_NAME="topic-persist";
    private static final String CLIENT_ID="消费者3";
    private static final String CLIENT_NAME="订阅者3";


    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        connection.setClientID(CLIENT_ID);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, CLIENT_NAME);
        connection.start();
        Message receive = subscriber.receive();
        while(receive!=null){
            if(receive instanceof TextMessage){
                TextMessage textMessage=(TextMessage)receive;
                System.out.println(textMessage.getText());
                receive = subscriber.receive(2000L);
            }else if(receive instanceof BytesMessage){
                BytesMessage bytesMessage=(BytesMessage)receive;
                byte[] bytes=new byte[1024];
                bytesMessage.readBytes(bytes);
                System.out.println("收到的持久化消息："+new String(bytes));
                receive=subscriber.receive(2000L);
            }
        }
        subscriber.close();
        session.close();
        connection.close();
    }
}
