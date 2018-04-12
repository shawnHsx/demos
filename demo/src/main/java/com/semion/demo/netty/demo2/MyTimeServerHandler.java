package com.semion.demo.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshuanxu on 2017/2/24.
 */
public class MyTimeServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);
        String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
        //String body =(String)msg;
        System.out.println("the time server receive order :" + body + ", counter is :" + (++counter));
        String currentTime = "QUERY TIMER ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime += System.getProperty("line.separator");
        ByteBuf buf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
