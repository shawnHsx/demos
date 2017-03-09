package com.semion.demo.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/10/24.
 */
public class StackOverflowErrorDemo {

    private final static Logger logger = LoggerFactory.getLogger(StackOverflowErrorDemo.class);

    public static void main(String[] tag) {
        StackOverflowErrorDemo demo = new StackOverflowErrorDemo();
        demo.stackOverflow();
    }

    private int stackNumber1 = 1;

    /**
     * StackOverflowError:通过递归调用方法,不停的产生栈帧,
     * 一直把栈空间堆满,直到抛出异常
     * 在hotspot虚拟机中不区分虚拟机栈(-Xss)和本地方法栈(-Xoss)，
     * 且只有对Xss参数的设置,才对栈的分配有影响
     * -Xss512k
     */
    public void stackOverflow() {
        try {
            stackLeck1();
        } catch (Throwable e) {
            logger.error("递归调用深度：{}", stackNumber1);
            e.printStackTrace();
        }
    }

    /**
     * 递归调用
     */
    public void stackLeck1() {
        stackNumber1++;
        stackLeck1();
    }
}
