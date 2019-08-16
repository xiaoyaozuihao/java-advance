package com.xyh.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/10
 **/
public class MyClassloader extends ClassLoader {
    private String name;
    private String path = "G:/";
    private String fileType = ".class";

    public MyClassloader(String name) {
        super();
        this.name = name;
    }

    public MyClassloader(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "com.xyh.classloader.MyClassloader{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = loadClassData(name);
        if (bytes != null) {
            return defineClass(name, bytes, 0, bytes.length);
        }
        return null;
    }

    private byte[] loadClassData(String name) {
        InputStream ins = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            name = name.replace(".", "/");
            baos=new ByteArrayOutputStream();
            ins = new FileInputStream(path + name + fileType);
            int len = 0;
            while ((len = ins.read()) != -1) {
                baos.write(len);
            }
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
            }
        }
        return bytes;
    }

    public static void main(String[] args) {
        MyClassloader loader1 = new MyClassloader("loader1");
        loader1.setPath("G:/myapp/serverlib/");
        MyClassloader loader2 = new MyClassloader(loader1, "loader2");
        loader2.setPath("G:/myapp/clientlib/");
        MyClassloader loader3 = new MyClassloader(null, "loader3");
        loader3.setPath("G:/myapp/otherlib/");
        test(loader1);
//        test(loader2);
    }

    public static void test(ClassLoader loader) {
        try {
            Class<?> clazz = loader.loadClass("com.xyh.classloader.Cat");
            clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
