package com.xyh.concurrency;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/3
 **/
public class JavaLayoutTest {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());
        countHash(o);
//        String s = ClassLayout.parseInstance(o).toPrintable();
//        System.out.println(s);
        //hashcode值：1159190947
        //对象头header中的前56个字节表示hashcode，注意是小端存储，低字节存在内存的低地址中。
        //java.lang.Object object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           01 a3 d9 17 (00000001 10100011 11011001 00010111) (400139009)
        //      4     4        (object header)                           45 00 00 00 (01000101 00000000 00000000 00000000) (69)
        //      8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        //     12     4        (loss due to the next object alignment)
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
    }

    private static void countHash(Object object) {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            long hashCode=0;
            for(long i=7;i>0;i--){
                hashCode |= (unsafe.getByte(object, i) & 0xFF) << ((i - 1) * 8);
            }
            String code= Long.toHexString(hashCode);
            System.out.println("hashcode: "+hashCode);
            System.out.println("hashcode: 0x"+code);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
