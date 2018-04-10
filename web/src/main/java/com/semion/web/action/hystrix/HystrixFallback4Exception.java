package com.semion.web.action.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by heshuanxu on 2018/4/9.
 */
public class HystrixFallback4Exception extends HystrixCommand<String> {

    /*
     * 以下四种情况将触发getFallback调用：
     * 1）run()方法抛出非HystrixBadRequestException异常
     * 2）run()方法调用超时
     * 3）熔断器开启拦截调用
     * 4）线程池/队列/信号量是否跑满
     */


    private static final Logger logger = LoggerFactory.getLogger(HystrixFallback4Exception.class);

    private final String name;

    public HystrixFallback4Exception(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("FallbackGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        // throw new RuntimeException("HystrixBadRequestException is never trigger fallback");
        TimeUnit.SECONDS.sleep(3);
        return name;
    }

    @Override
    protected String getFallback() {
        logger.info("fallback ==================");
        return "fallback: " + name;
    }
}
