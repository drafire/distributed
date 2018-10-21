package com.drafire.distributed.socketDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        String message = "hello world,drafire";
        writer.println(message);      //注意，这里使用的println，而不是write。println()的作用是，打印一个字符串，然后终止行。
        System.out.println("客服端发送：" + message);

        while (true) {
            String serverData = reader.readLine();
            if (serverData == null) {
                break;
            }
            System.out.println("客户端收到：" + serverData);
        }
        writer.close();
        socket.close();
    }
}
