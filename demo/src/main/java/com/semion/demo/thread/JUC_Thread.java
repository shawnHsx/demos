package com.semion.demo.thread;

import java.util.concurrent.*;

public class JUC_Thread {

    private static char[] nums = "123456789".toCharArray();
    private static char[] chars = "ABCDEFGHI".toCharArray();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 300, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(2));


    // 锁对象
    final Object lock = new Object();
    // 控制线程最先开始的顺序先开始打印nums线程
    CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //JUC_Thread juc_thread = new JUC_Thread();

        int i1 = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < 5000; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "i");
                try {
                    Thread.sleep(3000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
           /*new Thread(new Runnable() {
               @Override
               public void run() {
                   System.out.println("start run "+Thread.currentThread().getName());
                   try {
                       Thread.sleep(500000l);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           },"Thread-"+i).start();*/

        }


        //juc_thread.m2();
        //juc_thread.m1();
    }


    public void m1() {
        executor.execute(() -> {
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
                executor.shutdown();
            }
        });
    }

    public void m2() {
        executor.execute(() -> {
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
                lock.notify();// 最后唤醒另一个线程让其结束
                executor.shutdown();// 此处使用如果不关闭，则当前用户线程不能结束
            }
        });
    }

}
