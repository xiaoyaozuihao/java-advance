package com.xyh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/24
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class,args);
    }

//    @Bean
//    public ApplicationRunner applicationRunner() throws InterruptedException {
//        System.out.println("------------模拟启动延迟----------");
//        Thread.sleep(3000L);
//        return args -> System.out.println("-------------模拟延迟启动--------");
//    }
}
