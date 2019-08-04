package com.xyh.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/4
 **/
public class SockerServer {
    public static void main(String[] args) throws IOException {
//        socketServerBlock();
        socketServerNonBlock();
    }

    public static void socketServerBlock() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 8080));
        byte[] bytes = new byte[2048];
        while (true) {
            System.out.println("wait connect");
            //阻塞
            Socket socket = serverSocket.accept();
            System.out.println("connect success,wait data");
            //阻塞
            int read = socket.getInputStream().read(bytes);
            System.out.println("字节数：" + read);
            System.out.println("收到内容：" + new String(bytes));
        }
    }

    public static void socketServerNonBlock() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        List<SocketChannel> channels=new ArrayList<>();
        while (true) {
            for (SocketChannel channel : channels) {
                int read = channel.read(byteBuffer);
                if(read!=0){
                    System.out.println("字节数:"+read);
                    byteBuffer.flip();
                    System.out.println("收到内容：" + Charset.forName("utf-8").decode(byteBuffer));
                }
            }
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                System.out.println("有客户端连接");
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }
        }
    }
}
