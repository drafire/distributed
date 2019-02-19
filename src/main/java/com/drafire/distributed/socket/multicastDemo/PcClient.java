package com.drafire.distributed.socket.multicastDemo;

import java.io.IOException;

public class PcClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetHelper.receive("PC客户端");
    }
}
