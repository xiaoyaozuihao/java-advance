package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * @author xuyh
 * @date 2019/7/16
 */
public class QueueProducer_Schedule {
    private static final String MQ_URL="tcp://192.168.40.210:61616";
    private static final String QUEUE_NAME="queue-schedule";
//    public static final String MQ_URL="failover:(tcp://192.168.40.210:61616,tcp://192.168.40.211:61616,tcp://192.168.40.212:61616)?randomize=false";
//    private static final String QUEUE_NAME="queue-cluster";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        factory.setUseAsyncSend(true);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);
        for(int i=0;i<3;i++) {
            TextMessage textMessage = session.createTextMessage("activemq_schedule_message"+i );
            //持久模式
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            String messageId = UUID.randomUUID().toString().substring(0, 6);
            textMessage.setJMSMessageID(messageId);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,3000);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,4000);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,2);
            producer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(messageId+" send ok");
                }
                @Override
                public void onException(JMSException e) {
                    System.out.println(messageId+" send fail");
                }
            });
        }
        session.commit();
        producer.close();
        session.close();
        connection.close();
        System.out.println("*********生产者发送消息成功");
    }
}
