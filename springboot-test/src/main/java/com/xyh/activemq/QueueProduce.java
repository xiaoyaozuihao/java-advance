package com.xyh.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/26
 */
@Component
public class QueueProduce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"*******:"+ UUID.randomUUID().toString().substring(0,6));
    }

//    @Scheduled(fixedDelay = 3000)
    public void produceScheduleMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"***scheduleMsg:"+UUID.randomUUID().toString().substring(0,6));
        System.out.println("scheduleMsg send ok");
    }
}
