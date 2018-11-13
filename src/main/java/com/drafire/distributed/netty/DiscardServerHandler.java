package com.drafire.distributed.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in=(ByteBuf)msg;
        try {
            while (in.isReadable()){
                System.out.println("服务器收到信息");
                System.out.print((char) in.readByte());
                System.out.flush();
//                ctx.write(msg);
//                ctx.flush();  //如果调用了ctx.flush()，netty会在write()结束后自动释放，不用再 System.out.flush();
                //System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
