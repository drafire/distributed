package com.drafire.distributed.socket.multicastDemo;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) throws IOException {
        InetHelper.receive("App客户端");
    }

}
