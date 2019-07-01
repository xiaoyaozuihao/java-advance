package com.xyh.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: lock的condition的应用,可以精确唤醒某个线程，貌似用join也可以实现
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class LockConditonDemo {
    public static void main(String[] args) throws InterruptedException {
        Resource resource=new Resource();
        for(int i=0;i<3;i++){
            Thread t1=new Thread(()->{
                resource.print5();
            },"AAA");
            t1.start();
            Thread t2=new Thread(()->{
                    resource.print10();
                },"BBB");
            t2.start();

            Thread t3=new Thread(()->{
                    resource.print15();
                },"CCC");
            t3.start();
//            t1.join();
//            t2.join();
//            t3.join();
        }

    }
}
class Resource{
    private int num=1;
    private Lock lock=new ReentrantLock();
    private Condition c1=lock.newCondition();
    private Condition c2=lock.newCondition();
    private Condition c3=lock.newCondition();

    public void print5(){
        lock.lock();
        try {
          while(num!=1){
              c1.await();
          }
          for(int i=0;i<5;i++){
              System.out.println(Thread.currentThread().getName()+"\t打印"+i);
          }
          num=2;
          c2.signal();
        }catch (Exception e){

        }finally {
          lock.unlock();
        }

    }
    public void print10(){
        lock.lock();
        try {
            while(num!=2){
                c2.await();
            }
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"\t打印"+i);
            }
            num=3;
            c3.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    public void print15(){
        lock.lock();
        try {
            while(num!=3){
                c3.await();
            }
            for(int i=0;i<15;i++){
                System.out.println(Thread.currentThread().getName()+"\t打印"+i);
            }
            num=1;
            c1.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }
}
