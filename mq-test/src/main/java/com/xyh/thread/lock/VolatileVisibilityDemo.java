package com.xyh.thread.lock;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/7
 */
public class VolatileVisibilityDemo {
    private static volatile boolean initFlag=false;

    public static void waitData(){
        System.out.println("waiting data........");
        while(!initFlag){
        }
        System.out.println("success............");
    }

    public static void prepareData(){
        System.out.println("prepareData........");
        initFlag=true;
        System.out.println("finish.........");
    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            waitData();
        },"aaa").start();
        Thread.sleep(1000);
        new Thread(()->{
            prepareData();
        },"bbb").start();
    }
}
