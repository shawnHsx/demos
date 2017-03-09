package com.semion.demo.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/10/24.
 */
public class StackOutOfMemeoryErrorDemo {
    private final static Logger logger = LoggerFactory.getLogger(StackOutOfMemeoryErrorDemo.class);

    /**
     * OutOfMemoryError:因为虚拟机会提供一些参数来保证堆以及方法区的分配,
     * 剩下的内存基本都由栈来占有,又由于每个线程都有独立的栈空间(堆,方法区为线程共有),
     * 所以如果你把虚拟机参数Xss(每个线程的栈空间)调大了,那么可以建立的线程数量必然减少,
     * 下面的程序可以用来验证这点
     * 由于在window平台的虚拟机中,java的线程是隐射到操作系统的内核线程上的,会导致操作系统假死.
     * -XX:MaxPermSize=56m -Xss5m
     */


    public static void main(String[] tag) {
        StackOutOfMemeoryErrorDemo demo = new StackOutOfMemeoryErrorDemo();
        demo.stackLeakByThread();
    }

    private static volatile int threadNumber = 0;

    public void stackLeakByThread() {
        while (true) {
            new Thread() {
                public void run() {
                    threadNumber++;
                    while (true) {
                        System.out.println(Thread.currentThread());
                    }
                }
            }.start();
        }
    }

}
