package com.semion.web.action.hystrix;

/**
 * Created by heshuanxu on 2018/4/10.
 */
public class Node {
    // 分流渠道
    public String channel;
    // 初始分配权重
    public int weight;
    //服务端权重
    public int effectiveWeight;
    //当前权重
    public int currentWeight;
    //是否可用
    public boolean down = false;

    public Node(String channel, int weight) {
        this.channel = channel;
        this.weight = weight;
        this.effectiveWeight = this.weight;
        if (this.weight < 0) {
            this.down = true;
        } else {
            this.down = false;
        }
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public int getEffectiveWeight() {
        return effectiveWeight;
    }

    public void setEffectiveWeight(int effectiveWeight) {
        this.effectiveWeight = effectiveWeight;
    }
}
