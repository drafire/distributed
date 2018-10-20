package com.drafire.distributed.socketDemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端正在接收消息");
        int a = 1;
        while (a > 0) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                //读取数据
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //PrintWriter bufferedWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                try {
                    System.out.println("服务端接收到：" + bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
