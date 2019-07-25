package com.xyh.thread.communication;

import java.util.concurrent.Semaphore;

/**
 * @description:信号量演示
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(3);
        for(int i= 0; i < 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    try{ Thread.sleep(2000);}catch(Exception e){}
                    System.out.println(Thread.currentThread().getName()+"\t停车两秒，离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
