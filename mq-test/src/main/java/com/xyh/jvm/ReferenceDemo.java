package com.xyh.jvm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author xuyh
 * @date 2019/7/1
 */
public class ReferenceDemo {
    public static void main(String[] args) {
//        referencetest();
//        softreferenceenough();
//        softreferencenotenough();
        weakReferenceTest();
    }

    //强引用
    public static void referenceTest(){
        Object obj=new Object();
        obj=null;
        System.gc();
        System.out.println(obj);

    }

    public static void softReferenceEnough(){
        Object obj=new Object();
        SoftReference<Object> softReference=new SoftReference<>(obj);
        System.out.println(obj);
        System.out.println(softReference.get());

        obj=null;
        System.gc();

        System.out.println(obj);
        System.out.println(softReference.get());


    }
    public static void softReferenceNotEnough(){
        Object obj=new Object();
        SoftReference<Object> softReference=new SoftReference<>(obj);
        System.out.println(obj);
        System.out.println(softReference.get());

        obj=null;
        try {
            byte[] bytes=new byte[50*1024*1024];
        }catch(Exception e){

        }finally {
            System.out.println(obj);
            System.out.println(softReference.get());
        }
    }

    public static void weakReferenceTest(){
        Object obj=new Object();
        WeakReference<Object> weakReference=new WeakReference<>(obj);
        System.out.println(obj);
        System.out.println(weakReference.get());
        obj=null;
        System.gc();
        System.out.println(obj);
        System.out.println(weakReference.get());
    }
}
