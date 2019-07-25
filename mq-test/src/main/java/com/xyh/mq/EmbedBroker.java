package com.xyh.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

/**
 * @author xuyh
 * @description: 嵌入式mq,应急使用
 * @date 2019/7/25
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService=new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
        brokerService.start();
    }
}
