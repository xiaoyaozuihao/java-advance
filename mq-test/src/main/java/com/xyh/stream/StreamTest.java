package com.xyh.stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: xuyh
 * @create: 2019/8/28
 **/
public class StreamTest {
    public static void main(String[] args) {
        //参数带类型
        System.out.println(build((String a,String b) -> "hello"));
        //单行语句块
        System.out.println(build((a, b) -> "hello"));
        //多行语句块
        System.out.println(build((a, b) -> {
            System.out.println("ni hao");
            return "hello world";
        }));
        //静态方法引用
        System.out.println(build(StreamTest::build));
        //实例方法引用
        System.out.println(build(new StreamTest()::hello));
        //参数方法引用
        System.out.println(build(String::concat));
        //两个中间操作在一个循环中进行
        new StreamTest().generateStream();

    }

    public void generateStream(){
        Stream<String> stringStream = Stream.of("a", "b", "c");
        stringStream
                .peek(s -> System.out.println(s))
                .peek(s -> System.out.println(s))
                .toArray();

//        Collection.stream(); 例如：new ArrayList<>().stream();
//        Arrays.stream();
    }
    /**
     * 给定一个非负整数数组，求能组成的最大的数
     * @param arr
     * @return
     */
    public static int printMax(int[] arr){
        String[] res=new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i]=String.valueOf(arr[i]);
        }
        Arrays.sort(res,(s2, s1)->(s1+s2).compareTo(s2+s1));
        String temp=Arrays.stream(res).collect(Collectors.joining());
        return Integer.parseInt(temp);
    }

    public String hello(String a,String b){
        return a+b;
    }
    public static String build(String a,String b){
        return a+b;
    }
    public static String build(MyInterface myInterface){
        return myInterface.build("hello"," world!");
    }

    interface MyInterface {
        String build(String a,String b);
    }
}
