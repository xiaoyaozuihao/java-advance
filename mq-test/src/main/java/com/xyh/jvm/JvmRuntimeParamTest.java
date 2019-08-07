package com.xyh.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/7
 */
public class JvmRuntimeParamTest {
    //-XX:+UseConcMarkSweepGC  -Xms10m -Xmx10m -XX:+PrintGCDetails
    byte[] bytes=new byte[1024];
    public static void main(String[] args) throws InterruptedException {
        List<JvmRuntimeParamTest> list=new ArrayList<>();
        while(true){
            list.add(new JvmRuntimeParamTest());
            Thread.sleep(10);
        }
    }
}
