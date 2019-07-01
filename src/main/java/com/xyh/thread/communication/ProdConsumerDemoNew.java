package com.xyh.thread.communication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:生产者消费者使用阻塞队列
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class ProdConsumerDemoNew {
    public static void main(String[] args) {
        MyResource myResource=new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产者开始生产");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t消费者开始消费");
            System.out.println();
            System.out.println();
            try {
                myResource.myCons();
//                System.out.println();
//                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"cons").start();
        try{ Thread.sleep(5000);}catch(Exception e){}
        System.out.println("5秒时间到，大老板叫停，生产活动停止");
        myResource.close();
    }
}
class MyResource{
    private volatile boolean flag=true;//生产活动是否进行中
    private BlockingQueue<String> blockingQueue=null;
    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue=blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    private AtomicInteger atomicInteger=new AtomicInteger();
    public void myProd() throws InterruptedException {
        boolean result;
        String value;
        while(flag){
            value=atomicInteger.incrementAndGet()+"";
            result = blockingQueue.offer(value, 2, TimeUnit.SECONDS);
            if(result){
                System.out.println(Thread.currentThread().getName()+"\t生产成功"+value);
            }else{
                System.out.println(Thread.currentThread().getName()+"\t生产失败"+value);
            }
            try{ Thread.sleep(1000);}catch(Exception e){}
        }
        System.out.println("生产部门接到通知，生产活动停止");
    }

    public void myCons() throws InterruptedException {
        String res;
        while(flag){
            res = blockingQueue.poll(2, TimeUnit.SECONDS);
            if(res==null){
                System.out.println("消费端超过2秒钟没有获取，消费退出");
                flag=false;
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费者成功消费"+res);
        }
    }

    public void close(){
        flag=false;
    }
}