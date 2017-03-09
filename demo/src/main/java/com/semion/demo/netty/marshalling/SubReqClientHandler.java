package com.semion.demo.netty.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshuanxu on 2017/3/1.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SubReqClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TCP 连接 success");
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();

    }

    private SubscribeReq subReq(int i) {
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSubReqId(i);
        subscribeReq.setUserName("heshuanxu" + i);
        subscribeReq.setPhoneNumber("13146385064");
        subscribeReq.setProductName("测试Marshalling");
        List<String> address = new ArrayList<String>();
        address.add("BeiJing.ChaoYang");
        address.add("BeiJing.DaXing");
        address.add("BeiJing.XiCheng");
        subscribeReq.setAddress(address);
        logger.info("发送对象数据：{}", subscribeReq.toString());
        return subscribeReq;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("receive server msg response:{}" + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
