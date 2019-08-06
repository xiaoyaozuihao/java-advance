package com.xyh;

import com.xyh.servlet.MyDispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/5
 */
public class AppConfig {
    public static final String CONTEXT_PATH="/";
    public static final String HOST_NAME="localhost";
    public static final int PORT=8080;

    public static void main(String[] args) throws LifecycleException, ServletException {
        Tomcat tomcat=new Tomcat();
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        tomcat.setHostname(HOST_NAME);
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(PORT);
        tomcat.getHost().setAutoDeploy(false);

        Context context = tomcat.addWebapp(CONTEXT_PATH, baseDir);
        tomcat.enableNaming();
        context.setReloadable(false);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        Wrapper wrapper = tomcat.addServlet("", "dispatcherServlet", new MyDispatcherServlet());
        wrapper.addInitParameter("contextConfigLocation","application.properties");
        wrapper.addMapping("/*");
//        Wrapper wrapper = context.createWrapper();
//        wrapper.setName("dispatcherServlet");
//        wrapper.setServletClasmappeds(MyDispatcherServlet.class.getName());
//        wrapper.addInitParameter("contextConfigLocation","application.properties");
//        context.addChild(wrapper);
//        context.addServletMappingDecoded("/*", "dispatcherServlet");
        tomcat.start();
        tomcat.getServer().await();


    }
}
