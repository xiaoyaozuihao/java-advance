package com.xyh.mq;

import com.xyh.activemq.QueueProduce;
import com.xyh.activemq.TopicProduce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActivemq {
    @Autowired
    private QueueProduce queueProduce;
    @Autowired
    private TopicProduce topicProduce;

    @Test
    public void testProduce(){
        queueProduce.produceMsg();
    }
    @Test
    public void testScheduleMsg(){
        queueProduce.produceScheduleMsg();
    }
    @Test
    public void testProduceTopic(){
        topicProduce.produceTopicMsg();
    }
}
