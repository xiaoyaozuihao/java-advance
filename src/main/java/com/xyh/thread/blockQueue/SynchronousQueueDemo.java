package com.xyh.thread.blockQueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class SynchronousQueueDemo {


    public static void main(String[] args) throws InterruptedException {
        testSynchronousQueue();
    }

    public static void testSynchronousQueue() throws InterruptedException {
        SynchronousQueue sq=new SynchronousQueue();
        System.out.println(sq.offer(1));
        System.out.println(sq.offer(2));
        System.out.println(sq.offer(3));
        System.out.println(sq.take());
        System.out.println(sq.size());
        System.out.println(sq.peek());
    }

}
