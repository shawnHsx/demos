package com.semion.demo.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.serial.SerialMarshallerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    private static final Logger logger = LoggerFactory.getLogger(NettyMessageEncoder.class);

    NettyMarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(factory, configuration);
        marshallingEncoder = new NettyMarshallingEncoder(provider);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage msg, List<Object> list) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("the encode message is null");
        }
        ByteBuf sendBuff = Unpooled.buffer();
        sendBuff.writeInt((msg.getHeader().getCrcCode()));
        sendBuff.writeInt((msg.getHeader().getLength()));
        sendBuff.writeLong((msg.getHeader().getSessionID()));
        sendBuff.writeByte((msg.getHeader().getType()));
        sendBuff.writeByte((msg.getHeader().getPriority()));
        sendBuff.writeInt((msg.getHeader().getAttachment().size()));

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            sendBuff.writeInt(keyArray.length);
            sendBuff.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(channelHandlerContext, value, sendBuff);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            marshallingEncoder.encode(channelHandlerContext, msg.getBody(), sendBuff);
        } else {
            sendBuff.writeInt(0);
            sendBuff.setInt(4, sendBuff.readableBytes());
        }
    }
}
