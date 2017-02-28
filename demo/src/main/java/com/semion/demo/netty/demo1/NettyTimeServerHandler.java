package com.semion.demo.netty.demo1;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class NettyTimeServerHandler extends ChannelInboundHandlerAdapter {

    int counter ;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("========channelRead 方法被调用");
        ByteBuf buffer = (ByteBuf)msg;
        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);
        String body = new String(req,"UTF-8").substring(0,req.length-System.getProperty("line.separator").length());
        System.out.println("the time server[netty] receive order:"+body+", counter is "+(++counter));

        String cuttentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        cuttentTime = cuttentTime+System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(cuttentTime.getBytes());
        ctx.write(resp);// 不直接写入到SocketChannel中，而是写入到缓冲数组中，而是通过flush方法将缓冲区的数据发送到SocketChannel
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();// 将消息队列中的消息写入到SocketChannel中发送给对方
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
