package com.semion.demo.netty.demo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    EventLoopGroup group = new NioEventLoopGroup();
    private int counter = 0;

    public void connect(final String host, final int port) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            logger.info("=================initChannel exec==================");
                            socketChannel.pipeline().addLast("MessageDecoder", MarshallingCodeFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast("MessageEncoder", MarshallingCodeFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler(50));
                            socketChannel.pipeline().addLast("LoginAuthReqHandler", new LoginAuthReqHandler());
                            socketChannel.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port),new InetSocketAddress("127.0.0.1", 1800)).sync();
            future.channel().closeFuture().sync();
        } finally {
            logger.info("finally ====================");
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    counter++;
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        logger.info("client start 重连操作");
                        connect(host, port);// 发起重连
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyClient().connect("127.0.0.1", 8080);
    }

}
