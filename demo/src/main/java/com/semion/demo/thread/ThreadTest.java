package com.semion.demo.thread;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

/**
 * java自带线程池 ---创建5个线程打印List集合
 * Created by heshuanxu on 2016/6/6.
 */
public class ThreadTest {


    public static void main(String[] args) {
        List<String> strList = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            strList.add("String" + i);
        }
        // 线程个数
        int threadNum = strList.size() < 5 ? strList.size() : 5;


         /*int corePoolSize ：核心线程数 当线程数<corePoolSize ，会创建线程执行runnable
           int maximumPoolSize：最大线程数 最大值（2^29）  当线程数 >= corePoolSize的时候，会把runnable放入workQueue中
           long keepAliveTime ：保持存活时间
           TimeUnit unit ：时间单位
           BlockingQueue<Runnable> workQueue 保存任务的阻塞队列
           ThreadFactory threadFactory ：创建线程的工厂（默认）
           RejectedExecutionHandler handler ：决绝策略（默认）
         */
        /*任务执行过程：
        1、当线程数小于corePoolSize时，创建线程执行任务。

        2、当线程数大于等于corePoolSize并且workQueue没有满时，放入workQueue中

        3、线程数大于等于corePoolSize并且当workQueue满时，新任务新建线程运行，线程总数要小于maximumPoolSize

        4、当线程总数等于maximumPoolSize并且workQueue满了的时候执行handler的rejectedExecution。也就是拒绝策略。
        */

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, threadNum, 300, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(3));


        for (int i = 0; i < threadNum; i++) {
            executor.execute(new PrintStrThread(strList, i, threadNum));
        }
        //shutdown只是给队列的线程发interrupt 不能接受新任务 但等待老任务结束
        executor.shutdown();
        // 立即结束线程
        executor.shutdownNow();

    }


}


class PrintStrThread implements Runnable {

    /**
     * 目标list
     */
    private List<String> strList;

    /**
     * 线程编号
     */
    private int num;
    /**
     * 线程数
     */
    private int threadNum;

    public PrintStrThread(List<String> strList, int num, int threadNum) {
        this.strList = strList;
        this.num = num;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        int len = 0;
        if (!CollectionUtils.isEmpty(this.strList)) {
            for (int i = 0; i < strList.size(); i++) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String s = strList.get(i);
                // 线程满足该规则时打印集合中的数据
                if (len % this.threadNum == this.num) {
                    System.out.println("线程编号：" + this.num + ",字符串：" + s);
                }
                len++;
            }
        }
    }
}