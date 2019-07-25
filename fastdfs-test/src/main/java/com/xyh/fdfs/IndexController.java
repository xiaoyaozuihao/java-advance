package com.xyh.fdfs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author xuyh
 * @date 2019/7/4
 */
@RestController
public class IndexController {

    @GetMapping("hello")
    public String hello(String param) throws UnsupportedEncodingException {
        return "hello "+param;
    }

}
