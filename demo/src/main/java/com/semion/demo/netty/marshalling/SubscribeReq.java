package com.semion.demo.netty.marshalling;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heshuanxu on 2017/3/1.
 */
public class SubscribeReq implements Serializable {
    private static final long serialVersionUID = -1145315123108744720L;
    private int subReqId;
    private String userName;
    private String productName;
    private String phoneNumber;
    private List<String> address;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq[" +
                "subReqId=" + subReqId +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ']';
    }
}
