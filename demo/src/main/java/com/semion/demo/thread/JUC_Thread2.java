package com.semion.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JUC_Thread2 {

    public static void main(String[] args) {
        JUC_Thread2 juc_thread = new JUC_Thread2();
        juc_thread.m2();
        juc_thread.m1();
    }

    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 300, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(3));

    final Object lock = new Object();

    private static char[] nums = "123456789".toCharArray();
    private static char[] chars = "ABCDEFGHI".toCharArray();

    CountDownLatch latch = new CountDownLatch(1);

    public void m1(){
        new Thread(()->{
            synchronized (lock) {
                for (int i = 0; i < nums.length; i++) {
                    System.out.println(nums[i]);
                    latch.countDown();
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        },"nums-Thread").start();

    }

    public void m2(){
        new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                for (int i = 0; i < chars.length; i++) {
                    System.out.println(chars[i]);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        },"chars-Thread").start();
    }

}
