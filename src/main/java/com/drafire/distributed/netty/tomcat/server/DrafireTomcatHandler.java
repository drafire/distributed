package com.drafire.distributed.netty.tomcat.server;

import com.drafire.distributed.netty.tomcat.http.DrafireResponse;
import com.drafire.distributed.netty.tomcat.servlet.DrafireServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class DrafireTomcatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DrafireTomcatHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            //获得参数
            String uri = request.uri();
            String method = request.method().name();

            logger.info("uri是：{}", uri);
            boolean hasPattern = false;

            DrafireResponse response = new DrafireResponse(ctx, request);
            //这里的正在表达式，需要把*转义
            Pattern uriPatter = Pattern.compile("/say/*".replaceAll("\\*", ".*"));
            System.out.println(uriPatter.toString());
            if (uriPatter.matcher(uri).matches()) {
                hasPattern = true;

                DrafireServlet servlet = new DrafireServlet();

                //判断get、post方法
                if ("get".equalsIgnoreCase(method)) {
                    servlet.doGet(new QueryStringDecoder(request.uri()).parameters(), response);
                } else {
                    servlet.doPost(new QueryStringDecoder(request.uri()).parameters(), response);
                }
            }

            if (!hasPattern) {
                String out = String.format("404 Not found URL %s for method %s", uri, method);
                response.write(out, 404);
                return;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
