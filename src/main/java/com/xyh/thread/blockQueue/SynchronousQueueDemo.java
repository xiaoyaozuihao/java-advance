package com.xyh.thread.blockQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue blockingQueue=new SynchronousQueue();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t添加元素");
                blockingQueue.put(1);
                System.out.println(Thread.currentThread().getName()+"\t添加元素");
                blockingQueue.put(2);
                System.out.println(Thread.currentThread().getName()+"\t添加元素");
                blockingQueue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        new Thread(()->{
            try {
                Thread.sleep(1000);
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t取出元素");
                Thread.sleep(1000);
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t取出元素");
                Thread.sleep(1000);
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t取出元素");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
