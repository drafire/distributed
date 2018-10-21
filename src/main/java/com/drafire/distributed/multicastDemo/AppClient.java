package com.drafire.distributed.multicastDemo;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) throws IOException {
        InetHelper.receive("App客户端");
    }

}
