package com.xyh.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuyh
 * @description: lock,condition实现多线程累加
 * @date 2019/8/12
 */
public class LockConditionCountDemo {
    private int start=10;
    private int mid=90;
    private int end=200;
    
    private volatile int res1=0;
    private volatile int res2=0;
    
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    private AtomicInteger count=new AtomicInteger(0);

    public int add(int start,int end){
        try {
            lock.lock();
            int sum=0;
            for (int i = start; i <= end; i++) {
                sum+=i;
            }
            return sum;
        } finally {
            atomic();
            lock.unlock();
        }
    }

    public int sum() {
        try {
            lock.lock();
            condition.await();
            return res1+res2;
        } catch (Exception e){

        }finally {
            lock.unlock();
        }
        return 0;
    }

    private void atomic() {
        if(2==count.addAndGet(1)){
            condition.signal();
        }
    }

    public static void main(String[] args) throws Exception {
        LockConditionCountDemo demo=new LockConditionCountDemo();
        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：开始执行");
            demo.res1=demo.add(demo.start,demo.mid);
            System.out.println(Thread.currentThread().getName()+"：执行结果："+demo.res1);
        },"t1");
        Thread t2 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：开始执行");
            demo.res2=demo.add(demo.mid+1,demo.end);
            System.out.println(Thread.currentThread().getName()+"：执行结果："+demo.res2);
        },"t2");
        Thread t3 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+": 计算总结果");
            int sum= demo.sum();
            System.out.println(Thread.currentThread().getName()+": 总结果："+sum);
        },"t3");
        t3.start();
        t2.start();
        t1.start();
        Thread.sleep(1000);
        System.out.println("计算完成");

    }
}
