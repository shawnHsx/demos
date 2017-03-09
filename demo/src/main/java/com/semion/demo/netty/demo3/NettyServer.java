package com.semion.demo.netty.demo3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }

    public void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup worerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, worerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        logger.info("==================initChannel exec====================");
                        socketChannel.pipeline().addLast("MessageDecoder", MarshallingCodeFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast("MessageEncoder", MarshallingCodeFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                        socketChannel.pipeline().addLast("LoginAuthRespHandler", new LoginAuthRespHandler());
                        socketChannel.pipeline().addLast("heartBeatHandler", new HeartBeatRespHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind("127.0.0.1", 8080).sync();
        future.channel().closeFuture().sync();

    }

}
