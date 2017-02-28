package com.semion.demo.netty.aio;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;

        AsynchronousTimeClientHandler clientHandler = new AsynchronousTimeClientHandler("localhost",port);

        new Thread(clientHandler,"AsynchronousTimeClientHandler").start();
    }
}
