package com.xyh.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/7/1
 **/
public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object obj=new Object();
        ReferenceQueue referenceQueue=new ReferenceQueue();
//        WeakReference<Object> weakReference=new WeakReference<>(obj,referenceQueue);
        PhantomReference<Object> phantomReference=new PhantomReference<>(obj,referenceQueue);
//         System.out.println(weakReference.get());
         System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        obj=null;
        System.gc();
        try{ Thread.sleep(100);}catch(Exception e){e.printStackTrace();}
//        System.out.println(weakReference.get());
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

    }
}
