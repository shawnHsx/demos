package com.semion.demo.netty.aio;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        AsynTimeServerHandler handler = new AsynTimeServerHandler(port);
        new Thread(handler,"AsynTimeServerHandler").start();
    }
}
