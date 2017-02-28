package com.semion.demo.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class TimeClient {
    public static void main(String[] args) throws Exception {
        new TimeClient().connect(8080,"127.0.0.1");
    }

    public void connect(int port,String host) throws Exception {
        // 配置客户端线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("========initChannel exec...");
                            socketChannel.pipeline().addLast(new NettyTimeClientHandler());
                        }
                    });

            //发起异步连接操作
            System.out.println("开始连接："+host+"："+port);
            ChannelFuture future = bootstrap.connect(host, port).sync(); // sync方法会阻塞用户线程
            if(future!=null) {
                System.out.println("异步连接结果：" +future.isSuccess());
            }
            // 等待客户端连接关闭程序方可退出
            System.out.println("连接关闭开始");
            future.channel().closeFuture().sync();// sync 方法会一直阻塞到连接关闭
            System.out.println("连接关闭结束");
        } catch (Exception e){
            System.out.println("连接异常"+e.getMessage());
        }
        finally {
            System.out.println("finally 释放线程功组资源");
            group.shutdownGracefully();
        }
    }
}
