package com.semion.demo.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区模拟OOM溢出
 * Created by heshuanxu on 2016/10/24.
 */
public class MethodAreaOutOfMemeoryErrorDemo {

    private final static Logger logger = LoggerFactory.getLogger(MethodAreaOutOfMemeoryErrorDemo.class);

    /**
     * 由于这个区域内存回收的主要目标是运行时常量池以及对类型的卸载,
     * 一般来说这个区的回收效率不是很高,为了演示这个区的异常,
     * 可以通过不断的产生类信息来以及调整-XX:PermSize=10m -XX:MaxPermSize=10m
     * 两个参数(设置永久带 ---方法区的大小)来达到这个目的。
     * 利用CGLib技术不断的生成动态Class,这些Class的信息会被存放在方法区中,
     * 如果方法区不是很大会造成方法去的OOM
     * <p>
     * -XX:PermSize=10m -XX:MaxPermSize=10m
     * 异常信息：
     * Caused by: java.lang.OutOfMemoryError: PermGen space
     */

    public static void main(String[] tag) {
        try {
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(Object.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(obj, params);
                    }
                });
                // 返回代理对象
                Object proxyObj = enhancer.create();
                logger.info(proxyObj.toString());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
