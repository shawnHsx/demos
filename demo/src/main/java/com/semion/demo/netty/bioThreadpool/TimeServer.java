package com.semion.demo.netty.bioThreadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("the time server is start in port :"+ port);
            Socket socket = null;
            // 任务队列
            ArrayBlockingQueue<Runnable> task = new ArrayBlockingQueue<>(10000);
            // 线程池
            ExecutorService executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),50,120L, TimeUnit.SECONDS,task);
            System.out.println("处理器个数："+Runtime.getRuntime().availableProcessors());
            while (true){
                socket = serverSocket.accept();
                // 处理客户端请求
                executor.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
