package com.semion.demo.nio.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by heshuanxu on 2017/2/3.
 */
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private Selector selector;

    public static void main(String[] arsg) throws IOException {
        Client client = new Client();
        client.initClient("localhost", 8080);
        client.listen();
    }

    public void initClient(String ip, int port) throws IOException {
        //获得一个Socket通道
        SocketChannel channel = SocketChannel.open();
        //设置通道为非阻塞
        channel.configureBlocking(false);
        //获得一个通道管理器
        this.selector = Selector.open();
        /*客户端连接服务器，其实方法执行并没有实现连接，需要在listen()方法中
        *调用channel.finishConnect()才能完成连接
        */
        channel.connect(new InetSocketAddress(ip, port));
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(this.selector, SelectionKey.OP_CONNECT);
    }

    public void listen() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            while (true) {
                int select = selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();// 移除key 防止重复处理
                    if (key.isConnectable()) {
                        logger.info("isConnectable is true");
                        SocketChannel sc = (SocketChannel) key.channel();
                        //如果正在连接，则等待完成连接
                        if (sc.isConnectionPending()) {
                            sc.finishConnect();
                        }
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        // 发送消息
                        String info = "I'm information from client";
                        buffer.clear();
                        buffer.put(info.getBytes());
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            sc.write(buffer);
                        }
                    }
                    if (key.isReadable()) {
                        logger.info("read  is true");
                        handleReadable(key);
                    }
                    if (key.isWritable()) {
                        logger.info("write  is true");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("finally is runing .....");
        }
    }

    /**
     * 读取消息并返回消息到client
     *
     * @param key
     * @throws IOException
     */
    private static void handleReadable(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel sc = (SocketChannel) key.channel();
        sc.read(buffer);
        byte[] array = buffer.array();
        String message = new String(array).trim();
        logger.info("client received msg ：{}", message);
        //sc.write(ByteBuffer.wrap(message.getBytes()));// 消息返回给server
    }

}
