package com.xyh;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/7/14
 **/
public class NIOTest {

    @Test
    public void testBasicOpt(){
        //创建缓冲区
        IntBuffer buffer=IntBuffer.allocate(10);
        System.out.println(buffer.capacity());
        //放入数据
        buffer.put(1234);
        System.out.println("---------put----------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        //切换读取模式
        buffer.flip();
        System.out.println("---------flip----------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        buffer.get();
        System.out.println("---------get----------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        buffer.rewind();//可重复读
        System.out.println("---------rewind----------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        buffer.clear();//清空缓冲区，但数据依然存在，处于被遗忘状态
        System.out.println("---------clear----------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
    }

    @Test
    public void testMark(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        String str="abcde";
        buffer.put(str.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        buffer.flip();
        byte[] bytes=new byte[buffer.limit()];
        buffer.get(bytes,0,2);
        buffer.mark();
        System.out.println(buffer.position());
        System.out.println(buffer.get(bytes, 2, 2));
        System.out.println(buffer.position());
        buffer.reset();
        System.out.println(buffer.position());
        if(buffer.hasRemaining()){
            System.out.println(buffer.remaining());
        }
    }

    @Test
    public void testDirect(){
        ByteBuffer buffer=ByteBuffer.allocateDirect(100);
        System.out.println(buffer.isDirect());
    }
    @Test
    public void testChannel(){
        FileInputStream in= null;
        FileOutputStream out = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            in = new FileInputStream("aaaa.txt");
            out = new FileOutputStream("bbbb.txt");
            inChannel = in.getChannel();
            outChannel = out.getChannel();
            ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
            while (inChannel.read(byteBuffer)!=-1){
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void testFileChannel() throws Exception{
        FileChannel inchannel = FileChannel.open(Paths.get("G:/aaaa.txt"), StandardOpenOption.READ);
        FileChannel outchannel = FileChannel.open(Paths.get("G:/bbbb.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        MappedByteBuffer mapin = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, inchannel.size());
        MappedByteBuffer mapout = outchannel.map(FileChannel.MapMode.READ_WRITE, 0, inchannel.size());
        byte[] bytes=new byte[mapin.limit()];
        mapin.get(bytes);
        mapout.put(bytes);
        inchannel.close();
        outchannel.close();
    }

    @Test
    public void testChannel1() throws Exception {
        FileChannel inchannel = FileChannel.open(Paths.get("G:\\007电影\\2012(720P)cd1.mkv"), StandardOpenOption.READ);
        FileChannel outchannel = FileChannel.open(Paths.get("G:/aaa.mkv"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
//        inchannel.transferTo(0,inchannel.size(),outchannel);
        outchannel.transferFrom(inchannel,0,inchannel.size());
        inchannel.close();
        outchannel.close();
//        Files.newByteChannel(Paths.get("G:/aaa.txt"),StandardOpenOption.READ);
    }
}
