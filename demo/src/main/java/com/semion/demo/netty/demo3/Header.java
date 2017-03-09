package com.semion.demo.netty.demo3;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by heshuanxu on 2017/2/28.
 */
public final class Header implements Serializable {
    private static final long serialVersionUID = -3507946590064193810L;
    private int crcCode = 0xabef0101;
    private int length; // 消息长度
    private long sessionID;//会话ID
    private byte type;// 消息类型
    private Map<String, Object> attachment = new HashMap<String, Object>();// 附件

    private byte priority;// 消息优先级

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header[" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", attachment=" + attachment +
                ", priority=" + priority +
                ']';
    }
}
