package com.xyh.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/26
 */
@Configuration
public class MqConfig {
    @Value("${myqueue}")
    public String queueName;
    @Value("${mytopic}")
    public String topicName;
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }
}
