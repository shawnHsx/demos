package com.semion.demo.netty.nio;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class TimeClient {
    public static void main(String[] args) {
        TimeClientHandle clientHandle = new TimeClientHandle("127.0.0.1", 8080);
        new Thread(clientHandle, "NIO-TimeClient-001").start();
    }
}
