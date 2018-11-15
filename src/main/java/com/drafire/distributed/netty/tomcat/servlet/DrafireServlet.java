package com.drafire.distributed.netty.tomcat.servlet;

import com.alibaba.fastjson.JSON;
import com.drafire.distributed.netty.tomcat.http.DrafireResponse;

public class DrafireServlet {

    public void doPost(Object out, DrafireResponse response) {
        String outMsg = JSON.toJSONString(out);
        response.write(outMsg, 200);
    }

    public void doGet(Object out, DrafireResponse response) {
        doPost(out, response);
    }
}
