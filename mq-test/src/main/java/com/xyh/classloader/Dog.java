package com.xyh.classloader;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/10
 **/
public class Dog {
    public Dog(){
        System.out.println("Dog is loaded by:"+this.getClass().getClassLoader());
    }
}
