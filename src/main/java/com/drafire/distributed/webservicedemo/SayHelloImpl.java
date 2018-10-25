package com.drafire.distributed.webservicedemo;

import javax.jws.WebService;

@WebService
public class SayHelloImpl implements ISayHello {

    //这里就不需要WebMethod的注解了
    @Override
    public String say() {
        return "hello world ,saying";
    }

    @Override
    public String eat() {
        return  "hello world ,eating";
    }
}
