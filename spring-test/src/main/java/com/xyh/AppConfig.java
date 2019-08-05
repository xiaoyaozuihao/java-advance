package com.xyh;

import com.xyh.servlet.MyDispatcherServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/5
 */
public class AppConfig {
    public static void main(String[] args) throws LifecycleException, ServletException {
        Tomcat tomcat=new Tomcat();
        tomcat.setHostname("localhost");
        tomcat.setPort(8080);
        String docBase = new File(".").getAbsolutePath();
        tomcat.addServlet("/*","webmvc",new MyDispatcherServlet());
//        Context webContext = tomcat.addWebapp("/", "/web/app/docroot/");
//        webContext.getServletContext().setAttribute(Globals.ALT_DD_ATTR, "/path/to/custom/web.xml");
        tomcat.start();
        tomcat.getServer().await();
    }
}
