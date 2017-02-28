package com.semion.demo.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class TimeServer {
    /**
     * BIO 客户端每个请求都新建一个线程来处理 无法满足高性能高并发
     * @param args
     */
    public static void main(String[] args){
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket =null;
            while (true){
                // 此方法阻塞
                socket =serverSocket.accept();
                //每次请求过来开启一个线程来处理
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
