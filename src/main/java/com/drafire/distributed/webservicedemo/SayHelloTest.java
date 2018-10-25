package com.drafire.distributed.webservicedemo;

import javax.xml.ws.Endpoint;

public class SayHelloTest {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/sayhello",new SayHelloImpl());
        System.out.println("publish success");

        //生成客户端
        //d:\java\jdk1.8.0_112\bin>wsimport.exe -s d:\java\my_workspace -p com.drafire.testall.webservice -keep http://localhost:8888/sayhello?wsdl
        // -s 后面的是生成的路径 ，-p 后面的是生成的包名，-keep后面的是webservice的wsdl
        //需要注意的是，后面的包名，一定需要跟生成的文件的包名是一样的。而这个例子中，生成的文件则必须要放在com.drafire.testall.webservice下面
        //否则，会报：请使用 @XmlType.name 和 @XmlType.namespace 为类...的异常
        //调用方法如下：
        //SayHelloImpl hello = new SayHelloImplService().getSayHelloImplPort();
        //String str = hello.say();
        //System.out.println(str);
    }
}
