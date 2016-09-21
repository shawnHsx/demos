package com.semion.demo.rpc.test;

/**
 * Created by heshuanxu on 2016/9/20.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String words) {
        return "server:"+words;
    }
}
