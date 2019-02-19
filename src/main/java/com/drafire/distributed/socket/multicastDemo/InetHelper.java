package com.drafire.distributed.socket.multicastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class InetHelper {
    private static final String ADDRESS = "225.4.5.6";

    private static final int PORT = 8888;

    public static void receive(String clientName) throws IOException {
        InetAddress group = InetAddress.getByName(ADDRESS);

        MulticastSocket socket = new MulticastSocket(PORT);

        socket.joinGroup(group);   //加入到指定的组

        byte[] buf = new byte[256];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String msg = new String(packet.getData());
            System.out.println(clientName + "接收到：" + msg);
        }
    }

    public static String getADDRESS() {
        return ADDRESS;
    }

    public static int getPORT() {
        return PORT;
    }
}
