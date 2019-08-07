package com.xyh.thread.lock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/7
 */
public class VolatileSerialTest {
    static volatile int x=0,y=0;

    public static void main(String[] args) throws InterruptedException {
        Set<String> sets=new HashSet<>();
        Map<String,Object> maps=new HashMap<>();
        for (int i=0;i<200000;i++){
            x=0;y=0;
            maps.clear();
            Thread one=new Thread(()->{
                int a=y;
                x=1;
                maps.put("a",a);
            },"aaa");
            Thread two=new Thread(()->{
                int b=x;
                y=1;
                maps.put("b",b);
            },"bbb");
            one.start();
            two.start();
            one.join();
            two.join();

            sets.add("a="+maps.get("a")+",b="+maps.get("b"));
            System.out.println("第"+i+"次:"+sets);
        }
    }
}
