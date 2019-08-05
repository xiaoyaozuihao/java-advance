package com.xyh;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/31
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.xyh.service")
//@EnableHystrix
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
