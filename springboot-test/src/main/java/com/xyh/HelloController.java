package com.xyh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/24
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Greetings from springboot !";
    }
}
