package com.semion.demo.rpc.test;

import com.semion.demo.rpc.RPCFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/9/20.
 */
public class Provider {

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
