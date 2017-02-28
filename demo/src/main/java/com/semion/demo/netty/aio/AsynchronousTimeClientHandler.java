package com.semion.demo.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class AsynchronousTimeClientHandler implements CompletionHandler<Void, AsynchronousTimeClientHandler>, Runnable {

    private String host;
    private int port;
    private CountDownLatch latch;
    private AsynchronousSocketChannel socketChannel;

    public AsynchronousTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        socketChannel.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsynchronousTimeClientHandler client) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writebuff = ByteBuffer.allocate(req.length);
        writebuff.put(req);
        writebuff.flip();
        socketChannel.write(writebuff, writebuff, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer byteBuffer) {
                if (byteBuffer.hasRemaining()) {
                    socketChannel.write(byteBuffer, byteBuffer, this);
                } else {
                    ByteBuffer readBuff = ByteBuffer.allocate(1024);
                    socketChannel.read(readBuff, readBuff, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer buff) {
                            buff.flip();
                            byte[] bytes1 = new byte[buff.remaining()];
                            buff.get(bytes1);
                            try {
                                String body = new String(bytes1, "UTF-8");
                                System.out.println("NOW is :" + body);
                                latch.countDown();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                socketChannel.close();
                                latch.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    socketChannel.close();
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsynchronousTimeClientHandler attachment) {
        try {
            socketChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
