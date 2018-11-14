package com.drafire.distributed.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listener() {
        System.out.println("服务端正在监听...");
        int a = 1;
        while (a > 0) {
            try {
                Socket socket = server.accept();
                InputStream is = socket.getInputStream();
                byte[] buff = new byte[1024];
                int length = is.read(buff);

                if (length > 0) {
                    String msg = new String(buff);
                    System.out.println("服务端收到：" + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server(8088).listener();
    }
}
