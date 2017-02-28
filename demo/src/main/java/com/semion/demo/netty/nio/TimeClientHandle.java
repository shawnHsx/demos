package com.semion.demo.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class TimeClientHandle implements Runnable {

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override

    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            System.exit(1);
        }
        while (!stop) {
            try {
                selector.select(1000);// 每隔1秒轮询一次
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                }else {
                    // 连接失败 进程结束
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer readBuff = ByteBuffer.allocate(1024);
                int read = socketChannel.read(readBuff);
                if (read > 0) {
                    readBuff.flip();
                    byte[] bytes = new byte[readBuff.remaining()];
                    readBuff.get(bytes);// 将缓冲区的可读字节数组复制到新建的bytes数组中
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is :" + body);
                    this.stop = true;
                } else if (read < 0) {
                    // 服务端断开连接
                    key.cancel();
                    socketChannel.close();
                } else {
                    ;
                }
            }
        }
    }

    private void doConnect() throws IOException {
        boolean connected = socketChannel.connect(new InetSocketAddress(this.host, this.port));
        if (connected) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuff = ByteBuffer.allocate(req.length);
        writeBuff.put(req);
        writeBuff.flip();
        socketChannel.write(writeBuff);
        if (!writeBuff.hasRemaining()) {// 判断消息是否发送完成 "写半包" 问题待后续
            System.out.println("send order 2 server succeed.");
        }
    }
}
