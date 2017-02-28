package com.semion.demo.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2017/2/24.
 */
public class MyTimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MyTimeClientHandler.class);

    private byte[] req;

    private int counter;

    public MyTimeClientHandler() {
        logger.info("MyTimeClientHandler 构造函数执行");
        this.req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive 方法被调用");
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer(this.req.length);
            buffer.writeBytes(this.req);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead 方法被调用");
        String body = (String) msg;
        System.out.println("Now is :" + body + ", counter is " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
