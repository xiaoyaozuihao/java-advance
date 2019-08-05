package com.xyh.service.impl;

import com.xyh.annotation.MyService;
import com.xyh.service.IDemoService;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/5
 */
@MyService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String hello(String name) {
        return "hello "+name;
    }
}
