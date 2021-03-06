package com.xyh.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xuyh
 * @date 2019/6/29
 */
public class VolatileAtomicDemo {
    volatile int num;
    public void addNum(){
        num=60;
    }
    public void addPlusPlus(){
        num++;
    }

    AtomicInteger atomicInteger=new AtomicInteger();

   static AtomicReference<Integer> atomicReference=new AtomicReference<>(100);
   static AtomicStampedReference<Integer> atomicStampedReference=new AtomicStampedReference<>(100,0);
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }


    public static void main(String[] args) {
//        seeOKByVolatile();
//        notGuaranteeAtomic();
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
            atomicStampedReference.compareAndSet(100,101,0,1);
            atomicStampedReference.compareAndSet(101,100,1,2);
        },"t1").start();
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,2019)+"\tvalue:"+atomicReference.get());
            System.out.println(atomicStampedReference.compareAndSet(100, 2000,2, 3)+"\tvalue:"+atomicStampedReference.getReference()+"\tstamp:"+atomicStampedReference.getStamp());
        },"t1").start();
    }

    /**
     * 不保证原子性
     */
    public static void notGuaranteeAtomic(){
        VolatileAtomicDemo volatileAtomicDemo =new VolatileAtomicDemo();
        for(int i=0;i<20;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    volatileAtomicDemo.addPlusPlus();
                    volatileAtomicDemo.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+"\tint type: final num value:"+ volatileAtomicDemo.num);
        System.out.println(Thread.currentThread().getName()+"\tatomicInteger type:final num value:"+ volatileAtomicDemo.atomicInteger);
    }

    /**
     * 通过volatile关键字保证可见性
     */
    private static void seeOKByVolatile() {
        VolatileAtomicDemo volatileAtomicDemo =new VolatileAtomicDemo();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            volatileAtomicDemo.addNum();
            System.out.println(Thread.currentThread().getName()+"\t update num value "+ volatileAtomicDemo.num);
        },"aaa").start();
        while(volatileAtomicDemo.num==0){

        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over");
    }
}
