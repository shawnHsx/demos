package com.semion.demo.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthRespHandler.class);
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whiteList = {"127.0.0.1", "10.13.110.148"};
    private int counter =0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        logger.info("================== the server receive msg :", message.toString());
        // 如果握手信息处理，其他消息透传
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            logger.info("==============remote Address :{}", nodeIndex);// "/127.0.0.1:64197"
            NettyMessage loginResp = null;
            // 重复登陆，拒绝 /127.0.0.1:64197----源地址
            if (nodeCheck.containsKey(nodeIndex)) {
                logger.info("重复登陆，拒绝处理");
                loginResp = bulidResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                logger.info("=================address:{},ip:{}", address, ip);// "/127.0.0.1:64197"  "/127.0.0.1"
                boolean isOk = false;
                // 白名单用户可登陆
                for (String wip : whiteList) {
                    if (wip.equals(ip)) {
                        isOk = true;
                        break;
                    }
                }
                counter++;
                logger.info("客户端["+nodeIndex+"] 发起连接次数：{}",counter);
                /*if(counter==5){
                }else{
                    loginResp = isOk ? bulidResponse((byte) -1) : bulidResponse((byte) -1);
                }*/
                loginResp = isOk ? bulidResponse((byte) 0) : bulidResponse((byte) -1);
                if (isOk) {
                    nodeCheck.put(nodeIndex, true);
                }
                logger.info("================the login response is :" + loginResp);
                ctx.writeAndFlush(loginResp);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage bulidResponse(byte b) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.getValue());
        message.setHeader(header);
        message.setBody(b);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
