package com.xyh.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/26
 */
@Component
public class TopicProduce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    public void produceTopicMsg(){
        jmsMessagingTemplate.convertAndSend(topic,"topicMsg:"+ UUID.randomUUID().toString().substring(0,4));
    }
}
