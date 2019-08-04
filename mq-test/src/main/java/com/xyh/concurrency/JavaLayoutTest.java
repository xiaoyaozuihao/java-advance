package com.xyh.concurrency;

import org.openjdk.jol.info.ClassLayout;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/3
 **/
public class JavaLayoutTest {
    public static void main(String[] args) {
        String s = ClassLayout.parseInstance(JavaLayoutTest.class).toPrintable();
        System.out.println(s);
    }
}
