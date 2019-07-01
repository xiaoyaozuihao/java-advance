package com.xyh.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁案例
 * @author xuyh
 * @date 2019/6/29
 */
public class ReentrantLockDemo implements Runnable{
    ReentrantLock lock1=new ReentrantLock();
    ReentrantLock lock2=new ReentrantLock();

    @Override
    public void run() {
        get();
    }
    public void get(){
        lock1.lock();
        lock2.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock1.unlock();
            lock2.unlock();
        }
    }

    private void set() {
        lock2.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally {
            lock2.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        for(int i=0;i<2;i++){
            new Thread(reentrantLockDemo).start();
        }
    }
}
