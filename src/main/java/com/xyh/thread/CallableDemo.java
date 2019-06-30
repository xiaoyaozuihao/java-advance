package com.xyh.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(()->{
            System.out.println("callable come in");
            try{ Thread.sleep(1000);}catch(Exception e){}
            return 1024;
        });
        new Thread(futureTask,"AA").start();
//        new Thread(futureTask,"BB").start();
        int result1=100;
        while(!futureTask.isDone()){
            try{ Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
        }
        Integer result2 = futureTask.get();
        System.out.println(result1+result2);

    }
}
