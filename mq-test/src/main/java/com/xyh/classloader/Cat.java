package com.xyh.classloader;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/10
 **/
public class Cat {
    public Cat(){
        System.out.println("Cat is loaded by :"+this.getClass().getClassLoader());
        new Dog();
    }
}
