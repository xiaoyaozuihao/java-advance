package com.xyh.mq.spring.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/25
 */
@Component
public class QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage= (TextMessage) message;
            try {
                System.out.println("队列监听器收到信息："+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("暂时只支持TextMessage类型消息");
        }
    }
}
