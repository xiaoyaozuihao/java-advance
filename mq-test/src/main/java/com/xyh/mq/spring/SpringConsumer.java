package com.xyh.mq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/25
 */
@Service
public class SpringConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringConsumer consumer= (SpringConsumer) context.getBean("spring_consumer");
        String retValue = (String) consumer.jmsTemplate.receiveAndConvert();
        System.out.println("消费者接受到消息："+retValue);

    }
}
