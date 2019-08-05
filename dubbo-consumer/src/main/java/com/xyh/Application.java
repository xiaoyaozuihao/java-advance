package com.xyh;

import com.xyh.service.IOrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/30
 */
public class Application {
    public static void main(String[] args) throws IOException {
//        ReferenceConfig<IUserService> referenceConfig = new ReferenceConfig<IUserService>();
//        referenceConfig.setApplication(new ApplicationConfig("dubbo-consumer"));
//        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
//        referenceConfig.setInterface(IUserService.class);
//        IUserService userService = referenceConfig.get();
//        System.out.println(userService.listUserAddress(1));
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("consumer.xml");
        IOrderService orderService = context.getBean(IOrderService.class);
        orderService.initOrder(1);
        System.in.read();
    }
}
