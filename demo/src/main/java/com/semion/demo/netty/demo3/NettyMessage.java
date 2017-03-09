package com.semion.demo.netty.demo3;

import java.io.Serializable;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public class NettyMessage implements Serializable {

    private static final long serialVersionUID = 8326339108403338113L;
    private Header header;// 消息头
    private Object body;// 消息体


    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
