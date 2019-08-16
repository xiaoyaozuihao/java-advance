package com.xyh.classloader;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/10
 **/
public class TestClassloader {
    public static void main(String[] args) throws ClassNotFoundException {
        //无法获取根加载器，结果为null
        Class<?> clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());//null
        //加载自己编写的类使用应用类加载器
        Class<?> clazz1 = Class.forName("com.xyh.classloader.C");
        System.out.println(clazz1.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        //使用系统类加载器不会进行类的初始化
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        systemClassLoader.loadClass("com.xyh.classloader.CL");
        System.out.println("----------");
        Class.forName("com.xyh.classloader.CL");
        //获取父类加载器
        System.out.println(systemClassLoader);
        while(systemClassLoader!=null){
            systemClassLoader = systemClassLoader.getParent();
            System.out.println(systemClassLoader);
        }
    }
}
class C {

}
class CL{
    static {
        System.out.println("init class CL");
    }
}