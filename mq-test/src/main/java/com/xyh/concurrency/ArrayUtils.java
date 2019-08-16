package com.xyh.concurrency;

import java.util.Random;

/**
 * @author xuyh
 * @description:
 * @date 2019/8/15
 */
public class ArrayUtils {
    /**
     * 大小为10000000的数组，数组元素最大为1000000
     * @return
     */
    public static int[] getArrays(){
        return getArrays(10000000,100000);
    }

    public static int[] getArrays(int maxSize,int maxValue) {
        int[] arrays = new int[maxSize];
        Random random = new Random();
        for (int i = 0; i < maxSize; i++) {
            arrays[i] = random.nextInt(maxValue);
        }
        return arrays;
    }
}
