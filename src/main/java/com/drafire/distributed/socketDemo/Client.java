package com.drafire.distributed.socketDemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        String message = "hello world,drafire";
        printWriter.write(message);
        System.out.println("客服端发送：" + message);
        printWriter.close();
        socket.close();
    }
}
