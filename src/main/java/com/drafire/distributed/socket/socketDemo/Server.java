package com.drafire.distributed.socket.socketDemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        System.out.println("服务端正在接收消息");
        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {

                    try {
                        //读取数据
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                        while (true) {
                            String clientMsg = reader.readLine();
                            if (clientMsg == null) {
                                break;
                            }

                            System.out.println("服务端接收到：" + clientMsg);

                            writer.println("java" + LocalDateTime.now());
                            System.out.println("服务器发送：" + LocalDateTime.now());
                            writer.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }

    }
}
