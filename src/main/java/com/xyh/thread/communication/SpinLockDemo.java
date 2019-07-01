package com.xyh.thread.communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁手动实现
 * @author xuyh
 * @date 2019/6/29
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference=new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(null,thread)){
            System.out.println(thread.getName()+"轮到我了吗？");
        }
        System.out.println(thread.getName()+"轮到我了，我进来了");
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t come out");
    }


    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo lock=new SpinLockDemo();
        new Thread(()->{
            lock.myLock();
            try{ TimeUnit.MILLISECONDS.sleep(501);}catch(Exception e){}
            lock.myUnlock();
        }).start();
        Thread.sleep(500);
        new Thread(()->{
            lock.myLock();
            lock.myUnlock();
        }).start();
    }
}
