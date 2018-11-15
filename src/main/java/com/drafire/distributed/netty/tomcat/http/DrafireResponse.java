package com.drafire.distributed.netty.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DrafireResponse {

    private ChannelHandlerContext handlerContext;
    private HttpRequest request;

    private static Map<Integer, HttpResponseStatus> statusMap = new HashMap<>();

    public DrafireResponse(ChannelHandlerContext handlerContext, HttpRequest request) {
        this.handlerContext = handlerContext;
        this.request = request;
    }

    //这种代码很有意思，意思是加载类的时候，就会自动执行static{}内的东西
    static {
        statusMap.put(200, HttpResponseStatus.OK);
        statusMap.put(404, HttpResponseStatus.NOT_FOUND);
    }

    public void write(String out, Integer status) {
        try {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    statusMap.get(status), Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            //报文头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            //报文长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.EXPIRES, 0);

            //如果是长连接，则设置为长连接报文
            if (HttpUtil.isKeepAlive(request)) {
                request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            handlerContext.write(response);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            handlerContext.flush();
        }
    }
}
