package com.xyh.classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/11
 **/
public class HotFix {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\IdeaProjects\\java-advance\\spring-test\\target\\classes\\com\\xyh\\service\\impl\\DemoServiceImpl.class");
        long lastModified = 0L;
        URLClassLoader parentLoader = null;
        URLClassLoader loader = null;
        URL classUrl = new URL("file:D:\\IdeaProjects\\java-advance\\spring-test\\target\\classes\\");
        parentLoader = new URLClassLoader(new URL[]{classUrl}, HotFix.class.getClassLoader());
        while (true) {
            if (file.lastModified() > lastModified) {
                try {
                    System.out.println("class 文件有变化了");
                    lastModified = file.lastModified();
                    URL url = new URL("file:D:\\IdeaProjects\\java-advance\\spring-test\\target\\classes\\");
                    loader = new URLClassLoader(new URL[]{url}, parentLoader);
                    //加载class文件到内存,校验，准备，解析
                    Class<?> clazz = loader.loadClass("com.xyh.service.impl.DemoServiceImpl");
                    //初始化
                    Object object = clazz.newInstance();
                    Method hello = clazz.getMethod("hello", String.class);
                    System.out.println(hello.invoke(object, " world"));
                } catch (Exception e) {
                }
            }
            try{ Thread.sleep(2000);}catch(Exception e){e.printStackTrace();}
        }
    }
}
