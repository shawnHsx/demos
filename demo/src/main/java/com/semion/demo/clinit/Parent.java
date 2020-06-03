package com.semion.demo.clinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * Created by heshuanxu on 2016/9/27.
 */
public class Parent {

    private final static Logger logger = LoggerFactory.getLogger(Parent.class);

    static int A = 100;

    static {
        logger.info("Parent init...");
    }
}



// 三个线程顺序打印--1114. 按序打印
class Foo {

    private volatile boolean firstFinshed = false;
    private volatile boolean secondFinshed = false;

    final Object obj = new Object();
    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (obj) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstFinshed = true;
            obj.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (obj){
            while (!firstFinshed){
                obj.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondFinshed = true;
            obj.notifyAll();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (obj){
            while (!secondFinshed){
                obj.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
}


class ZeroEvenOdd {

    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i=0;i<n;i++){
           printNumber.accept(0);
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i=0;i<n;i++){

        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i=0;i<n;i++){

        }
    }
}

class FizzBuzz {

    //CyclicBarrier c = new CyclicBarrier(4);
    Semaphore semaphore = new Semaphore(1);

    private int num=1;

    private int n;

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true){
            semaphore.acquire();
            try {
                if(num>n){
                    return;
                }
                if(num%3 ==0 && num%5 !=0){
                    printFizz.run();
                    num++;
                }
            } finally {
                semaphore.release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true){
            semaphore.acquire();
            try {
                if(num>n){
                    return;
                }
                if(num%3 !=0 && num%5 ==0){
                    printBuzz.run();
                    num++;
                }
            } finally {
                semaphore.release();;
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true){
            semaphore.acquire();
            try {
                if(num>n){
                    return;
                }
                if(num%3 ==0 && num%5 ==0){
                    printFizzBuzz.run();
                    num++;
                }
            } finally {
                semaphore.release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            semaphore.acquire();
            try {
                if (num > n) {
                    return;
                }
                if (num % 3 != 0 && num % 5 != 0) {
                    printNumber.accept(num);
                    num++;
                }
            } finally {
                semaphore.release();
            }
        }
    }
}
