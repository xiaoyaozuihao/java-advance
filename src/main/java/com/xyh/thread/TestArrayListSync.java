package com.xyh.thread;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试arrayList同步问题
 * @author xuyh
 * @date 2019/6/29
 */
public class TestArrayListSync {
    public static void main(String[] args) {
        List<String> list=new CopyOnWriteArrayList<>();
        for(int i=0;i<10;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(Thread.currentThread().getName()+"\t listValue:"+list);
            }).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println();
        System.out.println(Thread.currentThread().getName()+"\t listValue:"+list);
    }
}
