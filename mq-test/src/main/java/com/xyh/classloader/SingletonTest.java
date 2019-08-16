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
        counter1++;
        counter2++;
    }

    public static SingletonTest getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        SingletonTest instance = SingletonTest.getInstance();
        System.out.println(instance.counter1);
        System.out.println(instance.counter2);
    }
}
