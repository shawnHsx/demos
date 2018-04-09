package com.semion.demo.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2017/2/23.
 * 展示服务端与客户端TCP 粘包问题----解决方案见demo2
 */
public class TimeServer {

    private static final Logger logger = LoggerFactory.getLogger(TimeServer.class);


    public static void main(String[] args) {
        int port = 8080;

        try {
            new TimeServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bind(int port) throws Exception {
        // 配置服务单nio线程组----Reactor 多线程模型
        EventLoopGroup bossGroup = new NioEventLoopGroup();//线程组---用于服务端接受客户端连接
        EventLoopGroup workGroup = new NioEventLoopGroup();//线程组---用于SocketChannel的读写

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();// 服务端的辅助启动类
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)// NioServerSocketChannel等同于 NIO 中的ServerSocketChannel 启动时会执行
                    .option(ChannelOption.SO_BACKLOG, 1024)// 设置TCP参数 接受连接的缓冲池大小
                    .childHandler(new ChannelInitializer<SocketChannel>() {// 客户端connect之后执行 处理网络IO时间，编解码等。
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            logger.info("==================server initChannel exce");
                            // 绑定处理类
                            socketChannel.pipeline().addLast(new NettyTimeServerHandler());
                        }
                    });
            // 异步通知回调
            logger.info("bind 端口方法开始执行");
            ChannelFuture future = bootstrap.bind(port).sync();// 绑定端口，调用sync方法阻塞当前线程 等待绑定完成结果
            logger.info("bind 端口方法执行完成");

            ChannelFuture sync = future.channel().closeFuture().sync();// 等待服务端链路关闭之后main函数才退出
            logger.info("sync==========");
        } finally {
            // 优雅退出
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
