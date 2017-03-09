package com.semion.demo.nio.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by heshuanxu on 2017/2/3.
 */
public class Server {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private Selector selector;

    public static void main(String[] arsg) throws IOException {
        Server server = new Server();
        server.initServer(8080);
        server.listen();
    }

    public void initServer(int port) throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询方式监听selector上已就绪事件并处理
     */
    public void listen() {
        try {
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        logger.info("accept  is true");
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        logger.info("read  is true");
                        handleReadable(key);
                    }
                    if (key.isConnectable()) {
                        logger.info("isConnectable is true");
                    }
                    if (key.isWritable()) {
                        logger.info("write  is true");
                        handleWritable(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleWritable(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        buf.compact();
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
        logger.info("server received msg ：{}", message);
        sc.write(ByteBuffer.wrap(message.getBytes()));// 消息返回给client
    }

    /**
     * 注册读就绪事件到selector，并发送消息到client
     *
     * @param key
     * @throws IOException
     */
    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel sschannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = sschannel.accept();
        if (sc != null) {
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
            // 此处也可进行发送消息
            //sc.write(ByteBuffer.wrap(new String("connect server is success").getBytes()));
        }
    }


}
