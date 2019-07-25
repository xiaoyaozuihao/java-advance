package com.xyh.thread.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class BlockQueueDemo {
    public static void main(String[] args) {
        BlockingQueue blockingQueue=new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.add(2));
        System.out.println(blockingQueue.add(3));
//        System.out.println(blockingQueue.add(3));超出队列长度报异常
//        System.out.println(blockingQueue.element());//返回队列的头元素，队列为空报异常
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());//移除队列头元素，为空异常
//        System.out.println(blockingQueue.offer(1));//添加成功，返回true，否则false
//        System.out.println(blockingQueue.offer(2));
//        System.out.println(blockingQueue.offer(3));
//        System.out.println(blockingQueue.peek());//返回队列头元素，不出队列，没有返回null
        System.out.println(blockingQueue.poll());//返回队列头元素，出队列，没有返回null
//        try {
//            blockingQueue.put(1);
//            System.out.println(blockingQueue.take());
//            blockingQueue.offer(4,2, TimeUnit.SECONDS);//添加元素，队列满了阻塞，有超时设置，超时后返回false
//            blockingQueue.offer(4,2,TimeUnit.SECONDS);
//            System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));//返回队列头元素，队列空了阻塞，超时时间后返回null
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void testBlockingQueueThread(){
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
