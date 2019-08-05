package com.xyh.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/31
 */
@Service
public class UserService implements IUserService {
    @Override
//    @HystrixCommand
    public String sayHello() {
        if(Math.random()>0.5){
            throw new RuntimeException("异常");
        }
        System.out.println();
        return "hello world!";
    }
}
