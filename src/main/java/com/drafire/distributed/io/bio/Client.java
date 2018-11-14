package com.drafire.distributed.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private static final int PORT = 8088;

    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket("localhost", PORT);
            os = socket.getOutputStream();
            os.write("哈哈哈哈，别人的失败就是我的快乐啦".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
