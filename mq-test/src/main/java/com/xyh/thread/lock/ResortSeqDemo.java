package com.xyh.thread.lock;

/**
 * @description:指令重排序案例
 * @author: xuyh
 * @create: 2019/6/30
 **/
public class ResortSeqDemo {
    static int x,y,a,b;

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            ResortSeqDemo resortSeqDemo = new ResortSeqDemo();
            Thread thread1 = new Thread(() -> {
                a = 1;
                x= b;
            }, String.valueOf(i));
            Thread thread2 = new Thread(() -> {
                b=2;
                y=a;
            }, String.valueOf(i));
            x=0;y=0;a=0;b=0;
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(x==0&&y==0){
                System.out.println("指令重排");
            }
        }
    }
}
