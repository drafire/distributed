package com.drafire.distributed.webservicedemo;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService   //定义这个后，下面的eat()也会被发布
public interface ISayHello {
    @WebMethod
    String say();

    String eat();
}
