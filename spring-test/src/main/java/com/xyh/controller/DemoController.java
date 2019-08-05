package com.xyh.controller;

import com.xyh.annotation.MyAutowired;
import com.xyh.annotation.MyController;
import com.xyh.annotation.MyRequestMapping;
import com.xyh.annotation.MyRequestParam;
import com.xyh.service.IDemoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/5
 */
@MyController
@MyRequestMapping("/")
public class DemoController {
    @MyAutowired
    private IDemoService demoService;

    @MyRequestMapping("/hello")
    public void hello(@MyRequestParam String name, HttpServletResponse response){
        String result=demoService.hello(name);
        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
