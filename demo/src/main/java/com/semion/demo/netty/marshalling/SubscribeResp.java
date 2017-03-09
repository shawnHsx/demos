package com.semion.demo.netty.marshalling;

import java.io.Serializable;

/**
 * Created by heshuanxu on 2017/3/1.
 */
public class SubscribeResp implements Serializable {
    private static final long serialVersionUID = 1789077917439650091L;
    private int subReqId;
    private byte respCode;
    private String desc;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public byte getRespCode() {
        return respCode;
    }

    public void setRespCode(byte respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp[" +
                "subReqId=" + subReqId +
                ", respCode=" + respCode +
                ", desc='" + desc + '\'' +
                ']';
    }
}
