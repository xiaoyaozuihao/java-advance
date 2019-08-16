package com.xyh.servlet;

import com.xyh.service.IDemoService;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/9
 */
@HandlesTypes({IDemoService.class})
public class HelloServletInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        System.out.println("param class\t" + set);
        if (set != null) {
            for (Class<?> clazz : set ){
                System.out.println(clazz.getCanonicalName());
            }
        }
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("init", HelloServlet.class);
        dynamic.addMapping("/init");

    }
}
