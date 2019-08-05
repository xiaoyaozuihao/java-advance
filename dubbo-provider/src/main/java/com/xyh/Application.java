package com.xyh;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/30
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ioc=new ClassPathXmlApplicationContext("provider.xml");
        ioc.start();
        System.in.read();
    }
}
