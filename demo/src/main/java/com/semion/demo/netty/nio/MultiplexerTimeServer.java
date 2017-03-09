package com.semion.demo.netty.nio;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start in port :" + port);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        // 开始处理
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (null != key.channel()) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // 处理新接入的请求消息
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = ssc.accept();
                socketChannel.configureBlocking(false);
                // 新的连接注册到selector
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                // 读取数据
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuff = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuff);
                if (readBytes > 0) {
                    readBuff.flip();
                    byte[] bytes = new byte[readBuff.remaining()];
                    readBuff.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    System.out.println("currentTime:" + currentTime);
                    // 写数据到客户端
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    // 客户端关闭连接
                    key.cancel();
                    socketChannel.close();
                } else {
                    System.out.println("no data readed ");
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (!StringUtils.isEmpty(response)) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuff = ByteBuffer.allocate(bytes.length);
            writeBuff.put(bytes);
            writeBuff.flip();
            socketChannel.write(writeBuff);
        }
    }
}
