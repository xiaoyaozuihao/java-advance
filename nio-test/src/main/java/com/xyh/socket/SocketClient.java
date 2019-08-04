package com.xyh.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @description:
 * @author: xuyh
 * @create: 2019/8/4
 **/
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8080));
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            System.out.println("输入内容：" + next);
            socket.getOutputStream().write(next.getBytes());
        }
    }
}
