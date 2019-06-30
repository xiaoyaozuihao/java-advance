package com.xyh.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 读写锁示例
 * @author: xuyh
 * @create: 2019-06-29 21:45
 **/
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache=new MyCache();
        for(int i= 0; i < 5; i++) {
            final int index=i;
            new Thread(()->{
                myCache.put(index,index);
            }, String.valueOf(i)).start();
        }
        for(int i= 0; i < 5; i++) {
            final int index=i;
            new Thread(()->{
                myCache.get(index);
            }, String.valueOf(i)).start();
        }
//        myCache.clear();
    }
}
class MyCache{
    private volatile Map<Object,Object> map=new HashMap<>();
    private ReentrantReadWriteLock readWriteLock =new ReentrantReadWriteLock();
    public void get(Object key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t正在读取");
            try{ Thread.sleep(100);}catch(Exception e){}
            System.out.println(Thread.currentThread().getName()+"\t读取完成,值为："+map.get(key));

        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void put(Object key,Object value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t正在写入"+value);
            map.put(key,value);
            try{ Thread.sleep(100);}catch(Exception e){}
            System.out.println(Thread.currentThread().getName()+"\t写入完成");
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }
    public void clear(){
        map.clear();
    }

}