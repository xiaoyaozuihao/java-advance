package com.xyh.jvm;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/7/1
 **/
public class JvmErrorDemo {
    public static void main(String[] args) {
//        stackoverflowTest();
//        outOfMemoryTest();
        gcOverHeadTest();
//        directMemoryTest();
    }

    private static void directMemoryTest() {
        System.out.println("配置的直接内存大小："+sun.misc.VM.maxDirectMemory()/1024/1024+"M");
        try{ Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
        ByteBuffer buffer=ByteBuffer.allocateDirect(6*1024*1024);
    }

    private static void gcOverHeadTest() {
        //便于演示，需要添加vm参数 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
//        List<String> list=new ArrayList<>();
//        int i=0;
//        try {
//            while(true){
//                list.add(String.valueOf(++i).intern());
//            }
//        } catch (Throwable e) {
//            System.out.println("#################i"+i);
//            throw e;
//        }
        //第二种示例
        Map<Integer,String> map=new HashMap<>();
        Random r=new Random();
        while(true){
            map.put(r.nextInt(),"value");
        }
    }

    private static void outOfMemoryTest() {
        byte[] bytes=new byte[10*1024*1024];//方便演示，设置vm启动参数-Xms5m,-Xmx5m
    }

    private static void stackoverflowTest() {
        stackoverflowTest();
    }
}
