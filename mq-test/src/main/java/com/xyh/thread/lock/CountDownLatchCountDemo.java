package com.xyh.thread.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author xuyh
 * @description: countDownLatch实现多线程累加
 * @date 2019/8/12
 */
public class CountDownLatchCountDemo {
    private CountDownLatch countDownLatch;
    private int start=10;
    private int mid=100;
    private int end=200;
    private volatile int res1,res2;

    private int add(int start,int end){
        int sum=0;
        for (int i = start; i <= end; i++) {
            sum+=i;
        }
        return sum;
    }

    private int sum(int a,int b){
        return a+b;
    }

    private int sub(int a,int b){
        return a-b;
    }

    public void calculate(){
        countDownLatch=new CountDownLatch(2);
        Thread t1 = new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"：开始执行");
                res1=add(start,mid);
                System.out.println(Thread.currentThread().getName()+"：执行结果："+res1);
            }finally {
                countDownLatch.countDown();
            }

        },"t1");
        Thread t2 = new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"：开始执行");
                res2=add(mid+1,end);
                System.out.println(Thread.currentThread().getName()+"：执行结果："+res2);
            }finally {
                countDownLatch.countDown();
            }
        },"t2");
        Thread t3 = new Thread(()->{
            try {
                countDownLatch.await();
                int sum= sum(res1,res2);
                System.out.println(Thread.currentThread().getName()+": 计算求和结果："+sum);
            }catch (Exception e){
            }

        },"t3");
        Thread t4 = new Thread(()->{
            try {
                countDownLatch.await();
                int sub= sub(res2,res1);
                System.out.println(Thread.currentThread().getName()+": 计算求差结果："+sub);
            }catch (Exception e){
            }

        },"t4");
        t3.start();
        t4.start();
        t1.start();
        t2.start();
        try{t3.join();t4.join();}catch (Exception e){}
        System.out.println("计算完成");

    }

    public static void main(String[] args) {
        CountDownLatchCountDemo countDownLatchCountDemo = new CountDownLatchCountDemo();
        countDownLatchCountDemo.calculate();
    }
}
