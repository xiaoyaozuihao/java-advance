package com.xyh.mq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/25
 */
@Service
public class SpringProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destinationQueue;
    @Autowired
    private Destination destinationTopic;

    public void sendQueueMessage(String messageContent){
        System.out.println("生产者发送队列消息成功");
        jmsTemplate.send(destinationQueue,session -> {
            TextMessage textMessage = session.createTextMessage(messageContent);
            return textMessage;
        });
    }

    public void sendTopicMessage(String messageContent){
        System.out.println("生产者发送主题消息成功");
        jmsTemplate.send(destinationTopic,session -> {
            TextMessage textMessage = session.createTextMessage(messageContent);
            return textMessage;
        });
    }


}
