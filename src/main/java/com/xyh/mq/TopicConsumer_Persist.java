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
    private static final String QUEUE_NAME="queue-test1";

    public static void main(String[] args) throws Exception {
        System.out.println("************消费者aa");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = factory.createConnection();
        connection.setClientID("aa");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic-persist");
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "订阅者名称  aa");
        connection.start();
        Message receive = subscriber.receive();
        while(receive!=null){
            if(receive instanceof TextMessage){
                TextMessage textMessage=(TextMessage)receive;

                System.out.println(textMessage.getText());
            }else if(receive instanceof BytesMessage){
                BytesMessage bytesMessage=(BytesMessage)receive;
                byte[] bytes=new byte[1024];
                bytesMessage.readBytes(bytes);
                System.out.println("收到的持久化消息："+new String(bytes));
                receive=subscriber.receive(5000L);
            }
        }
        subscriber.close();
        session.close();
        connection.close();
    }
}
