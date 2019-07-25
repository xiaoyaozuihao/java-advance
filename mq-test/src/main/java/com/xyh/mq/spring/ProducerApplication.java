package com.xyh.mq.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/25
 */
public class ProducerApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringProducer producer = (SpringProducer) context.getBean("springProducer");
        producer.sendQueueMessage("product queue message");
        producer.sendTopicMessage("product topic message1");
        producer.sendTopicMessage("product topic message2");
    }
}
