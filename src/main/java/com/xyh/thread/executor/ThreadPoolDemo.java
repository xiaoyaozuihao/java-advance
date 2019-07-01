package com.xyh.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class ThreadPoolDemo {
    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try{
            for(int i=0;i<10;i++){
                executor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            executor.shutdown();
        }
    }
}
