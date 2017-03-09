package com.semion.demo.netty.demo3;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public enum MessageType {
    /**
     * 握手返回类型
     */
    LOGIN_RESP((byte) 4),
    /**
     * 握手请求类型
     */
    LOGIN_REQ((byte) 3),
    /**
     * 心跳返回类型
     */
    HEARTBEAT_RESP((byte) 6),
    /**
     * 心跳请求类型
     */
    HEARTBEAT_REQ((byte) 5);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
