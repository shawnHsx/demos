package com.semion.demo.rpc.test;

import com.semion.demo.rpc.RPCFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/9/20.
 */
public class Provider {

    /**
     * 实际上，RPC的原理其实很简单：
     服务器启动了一个线程监听 Socket 端口,
     有Socket访问了, 反序列化解析出
     调用哪个Service 哪个 方法, 以及传入的 参数,
     再用Socket 写回去.

     客户端 利用 Jdk 的Proxy 生成了一个代理类,
     在创建 Proxy 时建立与服务器的Socket连接.
     调用 Proxy 的方法时, 向服务器发送数据, 等待结果返回.


     */

    private final static Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args){

        HelloService hell = new HelloServiceImpl();

        try {
            RPCFramework.exprot(hell,10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
