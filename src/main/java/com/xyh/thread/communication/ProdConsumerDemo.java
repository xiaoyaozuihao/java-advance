package com.xyh.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class ProdConsumerDemo {
    public static void main(String[] args) {
        ShareData shareData=new ShareData();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            },"aaa").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"bbb").start();
    }
}
class ShareData{
    private int num;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void increment()throws Exception{
        lock.lock();
        try {
            while(num!=0){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"\t增加元素"+num);
            condition.signalAll();
        } finally {
          lock.unlock();
        }
    }
    public void decrement()throws Exception{
        lock.lock();
        try {
            while (num==0){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"\t减少元素"+num);
            condition.signalAll();
        } finally {
          lock.unlock();
        }
    }
}
