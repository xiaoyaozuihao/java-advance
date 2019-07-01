package com.xyh.thread.communication;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5,()-> System.out.println("******召唤神龙"));
        for(int i= 0; i < 5; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t收集龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
