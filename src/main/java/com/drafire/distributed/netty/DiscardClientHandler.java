package com.drafire.distributed.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    //channel 在使用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       this.ctx=ctx;
       //初始化buffer的长度，并写入null值
       content=ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);
    }

    //channel 不在使用
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
