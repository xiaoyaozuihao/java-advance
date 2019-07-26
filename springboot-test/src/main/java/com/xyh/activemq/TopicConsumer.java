package com.xyh.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/26
 */
@Component
public class TopicConsumer {

    @JmsListener(destination = "${mytopic}")
    public void receive1(TextMessage textMessage)throws JMSException{
        System.out.println("订阅者1收到消息："+textMessage.getText());
    }
    @JmsListener(destination = "${mytopic}")
    public void receive2(TextMessage textMessage)throws JMSException{
        System.out.println("订阅者2收到消息："+textMessage.getText());
    }
}
