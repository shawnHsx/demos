package com.semion.demo.rpc.test;

/**
 * Created by heshuanxu on 2016/10/12.
 */
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String sayHi(String words) {

        return "hello2 Impl "+ words;
    }
}
