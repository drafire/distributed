package com.drafire.distributed.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private int port = 8088;
    //监听的ip地址
    private InetSocketAddress address = null;

    private Selector selector = null;

    /**
     * Bytebuffer的长度
     */
    private static final int LENGTH = 1024;

    public NIOServer(int port) {
        this.port = port;
        this.address = new InetSocketAddress(this.port);

        ServerSocketChannel channel = null;
        try {
            //打开channel
            channel = ServerSocketChannel.open();
            channel.bind(address);
            //设置channel为非阻塞
            channel.configureBlocking(false);
            //打开Selector
            selector = Selector.open();
            //这里可以只注册关心的事件，不一定是OP_ACCEPT，也不一定需要从OP_ACCEPT开始
            channel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器正在监听：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void listen() {
        int a = 1;
        while (a > 0) {
            try {
                int wait = selector.select();
                if (wait == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    process(key);
                    //每次处理完，都remove掉
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        //ByteBuffer，并分配空间
        ByteBuffer buffer = ByteBuffer.allocate(LENGTH);

        //做状态切换
        if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            //这里会阻塞
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            //切换为只读状态
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            int length = client.read(buffer);
            if (length > 0) {
                //把Bytebuffer从写模式变成读模式
                buffer.flip();
                String msg = new String(buffer.array(), 0, length);
                System.out.println("NIO服务端收到：" + msg);
                //切换到写模式
                client.register(selector, SelectionKey.OP_WRITE);
            }

        } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(buffer.wrap("NIO版黑白郎君".getBytes()));
            client.close();
        }
    }

    public static void main(String[] args) {
        new NIOServer(8088).listen();
    }
}
