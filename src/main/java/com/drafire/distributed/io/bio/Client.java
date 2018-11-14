package com.drafire.distributed.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final int PORT = 8088;

    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket("localhost", PORT);
            os = socket.getOutputStream();
            os.write("哈哈哈哈，别人的失败就是我的快乐啦".getBytes());
            int a = 1;
            while (a > 0) {
                TimeUnit.SECONDS.sleep(3);
                InputStream inputStream = socket.getInputStream();
                byte[] data = new byte[1024];
                int index = inputStream.read(data);
                if (index > 0) {
                    System.out.println("客户端收到：" + new String(data));
                }

            }
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
