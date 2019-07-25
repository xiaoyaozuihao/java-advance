package com.xyh.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
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
    public static final String QUEUE_NAME="queue-test1";
    public static final String USERANME= ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;


    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(USERANME,PASSWORD,MQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        //使用循环
//        while(true){
//            TextMessage message = (TextMessage) consumer.receive(1000L);
//            if(message!=null){
//                System.out.println(message.getText());
//            }else{
//                break;
//            }
//        }
        //使用监听器
        consumer.setMessageListener((message)->{
            if(message instanceof TextMessage){
                TextMessage textMessage=(TextMessage)message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                }
            }else if(message instanceof MapMessage){
                MapMessage mapMessage=(MapMessage)message;
                try {
                    System.out.println(mapMessage.getString("k1"));
                } catch (JMSException e) {
                }
            }
        });
        //起到阻塞的方式
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("*********消费者消费消息成功");
    }
}
