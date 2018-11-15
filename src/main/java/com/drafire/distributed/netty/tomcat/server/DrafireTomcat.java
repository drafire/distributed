package com.drafire.distributed.netty.tomcat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class DrafireTomcat {

    private static final int PORT = 8080;

    private void run() throws InterruptedException {
        //接收请求的时间组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理请求的时间组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //addLast()，添加到最后。因此采用倒叙
                            //response需要编码再返回
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            //request需要节码
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new DrafireTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)  //声明通道的属性和数量
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口
            ChannelFuture future = bootstrap.bind(PORT).sync();

            System.out.println("正在监听端口:" + PORT);
            //阻塞，等待接收消息
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new DrafireTomcat().run();
    }
}
