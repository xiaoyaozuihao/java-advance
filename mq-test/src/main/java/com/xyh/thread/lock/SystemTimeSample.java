package com.xyh.thread.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuyh
 * @description: system提供的获取当前时间存在性能问题
 *
 * 1.调用gettimeofday()需要从用户态切换到内核态；
 *
 * 2.gettimeofday()的表现受Linux系统的计时器（时钟源）影响，在HPET计时器下性能尤其差；
 *
 * 3.系统只有一个全局时钟源，高并发或频繁访问会造成严重的争用。
 * @date 2019/9/6
 */
public class SystemTimeSample {
    public static final int COUNT=100;

    public static void main(String[] args) throws InterruptedException {
        long start=System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            System.currentTimeMillis();
        }
        long elapsedTime=System.nanoTime()-start;
        System.out.println("100 System.currentTimeMillis() serial calls "+elapsedTime+" ns");
        CountDownLatch startLatch=new CountDownLatch(1);
        CountDownLatch endLatch=new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; i++) {
            new Thread(()->{
                try {
                    startLatch.await();
                    System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    endLatch.countDown();
                }
            }).start();
        }
        start=System.nanoTime();
        startLatch.countDown();
        endLatch.await();
        elapsedTime=System.nanoTime()-start;
        System.out.println("100 System.currentTimeMillis() parallel calls "+elapsedTime+" ns");
    }
}

/**
 * 解决方案
 */
class CurrentTimeMillisClock {
    private volatile long now;

    private CurrentTimeMillisClock() {
        this.now = System.currentTimeMillis();
        scheduleTick();
    }

    private void scheduleTick() {
        new ScheduledThreadPoolExecutor(1, runnable -> {
            Thread thread = new Thread(runnable, "current-time-millis");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(() -> {
            now = System.currentTimeMillis();
        }, 1, 1, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now;
    }

    public static CurrentTimeMillisClock getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CurrentTimeMillisClock INSTANCE = new CurrentTimeMillisClock();
    }
}