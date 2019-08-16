package com.xyh.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁案例
 *
 * @author xuyh
 * @date 2019/6/29
 */
public class ReentrantLockDemo1 {
    private Lock lock = new ReentrantLock();

    private void workOn() {
        System.out.println(Thread.currentThread().getName() + " 上班");
    }

    private void workOff() {
        System.out.println(Thread.currentThread().getName() + " 下班");
    }

    public void work() {
        try {
            lock.lock();
            workOn();
            System.out.println(Thread.currentThread().getName() + " 工作中");
            Thread.sleep(100);
            workOff();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo1 demo=new ReentrantLockDemo1();
        List<Thread> list=new ArrayList<>(10);
        int i=0;
        do{
            Thread aa = new Thread(()->{
                demo.work();
            },"aa"+i);
            Thread bb = new Thread(()->{
                demo.work();
            },"bb"+i);
            list.add(aa);
            list.add(bb);
        }while(i++<5);
        list.parallelStream().forEachOrdered(t->{
            try {
                t.start();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("work is over ");

    }

}
