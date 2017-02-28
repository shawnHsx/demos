package com.semion.demo.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class AsynTimeServerHandler implements Runnable {


    private int port;

    CountDownLatch latch;

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsynTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("the time server[asyn] start ino port :"+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();// 人为让程序阻塞，防止服务端执行完成退出，实际项目中不需要。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        asynchronousServerSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AsynTimeServerHandler>() {
            @Override
            public void completed(AsynchronousSocketChannel result, AsynTimeServerHandler attachment) {
                attachment.asynchronousServerSocketChannel.accept(attachment,this);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                result.read(byteBuffer, byteBuffer,new ReadCompletionHandler(result));
            }

            @Override
            public void failed(Throwable exc, AsynTimeServerHandler attachment) {
                exc.printStackTrace();
                attachment.latch.countDown();
            }
        });
    }
}
