package com.xyh.jvm;

/**
 * Hello world!
 *
 */
public class JvmMemory
{
    public static void main( String[] args )
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().freeMemory());
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("totalMemory(-xms):"+totalMemory/(double)1024/1024+"M");
        System.out.println("maxMemory(-xmx):"+ maxMemory/(double)1024/1024+"M");
//        byte[] bytes=new byte[11*1024*1024];
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
