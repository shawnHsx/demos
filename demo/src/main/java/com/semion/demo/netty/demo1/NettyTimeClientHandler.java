package com.semion.demo.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2017/2/23.
 */
public class NettyTimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NettyTimeClientHandler.class);

    //private final ByteBuf firstMsg;

    private byte[] req;

    public NettyTimeClientHandler() {
        logger.info("NettyTimeClientHandler 构造函数执行");
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        //firstMsg = Unpooled.buffer(req.length);// 拷贝到缓冲区
        //firstMsg.writeBytes(req);
    }

    /**
     * 客户端连接服务端TCP 成功之后 netty NIO 线程调用该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("tcp 连接success  channelActive 被调用 ");
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer(req.length);
            buffer.writeBytes(req);
            ctx.writeAndFlush(buffer);
        }
    }

    /**
     * 当服务端返回数据时 该方法被调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("server 端返回消息 channelRead 被调用");
        int counter = 0;// 计数
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);// 拷贝到bytes
        String body = new String(bytes, "UTF-8");
        System.out.println("Now is :" + body + ",counter is " + (++counter));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("异常：{}", cause.getMessage());
        ctx.close();
    }
}
