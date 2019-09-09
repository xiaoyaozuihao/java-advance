package com.xyh.jvm;

/**
 * Hello world!
 */
public class JvmMemory {
    public static void main(String[] args) {
        System.out.println("最大内存："+Runtime.getRuntime().maxMemory()/1024/1024+"M");
        System.out.println("可用内存："+Runtime.getRuntime().freeMemory()/1024/1024+"M");
        System.out.println("已使用内存："+Runtime.getRuntime().totalMemory()/1024/1024+"M");
        System.out.println("cpu核心数：" + Runtime.getRuntime().availableProcessors());
        byte[] bytes = new byte[9 * 1024 * 1024];
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("totalMemory(-xms初始堆内存):" + totalMemory / (double) 1024 / 1024 + "M");
        System.out.println("freeMemory(jvm当前可用堆内存)：" + freeMemory / (double) 1024 / 1024 + "M");
        System.out.println("maxMemory(-xmx最大堆内存):" + maxMemory / (double) 1024 / 1024 + "M");
//        byte[] bytes=new byte[11*1024*1024];
//        try {
//            Thread.sleep(Integer.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
