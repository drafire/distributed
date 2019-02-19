package com.drafire.distributed.socket.multicastDemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        //设定地址段:224.0.0.0-239.255.255.255 --这个是规定的
        InetAddress group = InetAddress.getByName(InetHelper.getADDRESS());

        MulticastSocket socket = new MulticastSocket();

        for (int i = 0; i < 10; i++) {
            String msg = "hello drafire";
            byte[] bytes = msg.getBytes();
            System.out.println("服务器发送：" + msg);
            socket.send(new DatagramPacket(bytes, bytes.length, group, InetHelper.getPORT()));  //使用的DatagramPacket ---UDP
            TimeUnit.SECONDS.sleep(2);   //沉睡2秒，这个方法很有意思
        }
    }
}
