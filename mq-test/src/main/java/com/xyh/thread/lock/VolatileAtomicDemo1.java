package com.xyh.thread.lock;

/**
 * @author xuyh
 * @description: 添加volatile关键字不能保证原子性
 * @date 2019/8/7
 */
public class VolatileAtomicDemo1 {
    static volatile int num=0;

    public static void increase(){
        num++;
    }
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads=new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i]=new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            },String.valueOf(i));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("num:"+num);
    }
}
