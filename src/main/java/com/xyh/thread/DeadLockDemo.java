package com.xyh.thread;

/**
 * @description:死锁案例，排除死锁，“jps -l”  查询java程序进程号，“jstack 进程号” 定位错误信息
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class DeadLockDemo implements Runnable{
    private String lockA;
    private String lockB;

    public DeadLockDemo(String lockA,String lockB){
        this.lockA=lockA;
        this.lockB=lockB;
    }
    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t持有"+lockA+",尝试获得"+lockB);
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t持有"+lockB+",尝试获得"+lockA);
            }
        }
    }

    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";
        new Thread(new DeadLockDemo(lockA,lockB),"aaa").start();
        new Thread(new DeadLockDemo(lockB,lockA),"bbb").start();
    }
}
