package com.xyh.thread.lock;

/**
 * @description:双重检测锁的线程安全问题
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class SingletonDemo {
    private volatile static SingletonDemo instance=null;
    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t构造函数执行");
    }

    public static SingletonDemo getInstance() {
        if(instance==null){
            synchronized (SingletonDemo.class){
                if(instance==null){
                    instance=new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for(int i= 0; i < 1000; i++) {
            new Thread(()->{
                SingletonDemo instance = SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
