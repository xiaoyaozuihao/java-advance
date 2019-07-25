package com.xyh.thread.communication;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch=new CountDownLatch(5);
        for(int i= 0; i < 5; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("班长关门，离开教室");
    }
}
