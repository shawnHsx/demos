package com.semion.demo.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.Map;

public class JOLDemo {


    /***
     *
     * @param args
     * @throws InterruptedException
     *
     *  重点：偏向锁已在jvm虚拟机默认开启，并且延时4秒启动。
     *  使用如下命令查看：java -XX:+PrintFlagsFinal -version |grep Bias
     *
     *      intx BiasedLockingDecayTime                    = 25000                               {product}
     *      intx BiasedLockingStartupDelay                 = 4000                                {product}
     *      bool TraceBiasedLocking                        = false                               {product}
     *      bool UseBiasedLocking                          = true                                {product}
     */

    public static void main(String[] args) throws InterruptedException {
        //lock1();
        //lock2();
        lock3();

    }

    // 演示偏向锁未启动时 markword 中锁状态的标识为001---无锁
    public static void lock1() {
        Map map = new HashMap<>();
        String s = ClassLayout.parseInstance(map).toPrintable();
        System.out.println(s);// (object header)  01 00 00 00 (00000001 00000000 00000000 00000000) (1)
    }

    // 延时5秒创建对象，此时偏向锁已启动，markword 中锁状态的标示位101--使用偏向锁
    public static void lock2() throws InterruptedException {
        Thread.sleep(5000);
        Map map = new HashMap<>();
        synchronized (map) {
            String s = ClassLayout.parseInstance(map).toPrintable();
            System.out.println(s);//(object header)  05 00 00 00 (00000101 00000000 00000000 00000000) (5)
        }
        // 如果对象创建后没有竞争（没有使用synchronized） 再该对象使用的为匿名偏向锁。
    }

    // 轻量级锁未启动， markword 中锁状态的标示位 00--使用轻量级锁（自旋锁）
    public static void lock3() throws InterruptedException {
        Map map = new HashMap<>();
        synchronized (map) {
            String s = ClassLayout.parseInstance(map).toPrintable();
            System.out.println(s);//(object header)  98 78 68 0c (10011000 01111000 01101000 00001100) (208173208)
        }
    }


}
