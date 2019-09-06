package com.xyh.classloader;
/**
 *
 * @author: xuyh
 * @create: 2019/8/10
 **/

public class SingletonTest {
    private static final SingletonTest singleton =new SingletonTest();
    public static int counter1;
    public static int counter2=0;
    private SingletonTest(){
        System.out.println("构造函数执行");
        counter1++;
        counter2++;
    }

    public static SingletonTest getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println(SingletonTest.counter1);
        System.out.println(SingletonTest.counter2);
    }
}
