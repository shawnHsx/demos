package com.semion.demo.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class HeartBeatTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatTask.class);

    private final ChannelHandlerContext ctx;

    public HeartBeatTask(final ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        NettyMessage heartBeat = buildHeartBeat();
        logger.info("Client send heart beat message to server :----->" + heartBeat);
        ctx.writeAndFlush(heartBeat);
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_REQ.getValue());
        message.setHeader(header);
        return message;
    }
}
