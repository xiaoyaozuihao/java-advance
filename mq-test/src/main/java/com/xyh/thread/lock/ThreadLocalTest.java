package com.xyh.thread.lock;

/**
 * 每个线程拥有自己的变量副本，线程间互不影响
 * @author xuyh
 * @date 2019/6/20
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> tl=new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                for(int i=10;i<20;i++){
                    tl.set(i);
                    System.out.println(Thread.currentThread().getName()+"===="+tl.get());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                tl.remove();
            }
        },"thread1").start();

        new Thread(()->{
            try {
                for(int i=0;i<10;i+=2){
                    tl.set(i);
                    System.out.println(Thread.currentThread().getName()+"====="+tl.get());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                tl.remove();
            }
        },"thread2").start();
    }
}
