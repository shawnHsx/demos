package com.semion.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        //test0();
        //test1()
        // test2();
        test3();


    }

    private static void test0() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("CompletableFuture");
    }


    private static void test1() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello");
            return "hello future";
        });
        try {
            String s = future.get();
            System.out.println("get futrue value:"+s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture");
    }
    private static void test2() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> getStr("java book"));
        try {
            String s = future.get();
            System.out.println("get futrue value:"+s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("CompletableFuture");
    }
    private static void test3() {
        List<String>  lists = new ArrayList<String>();
        lists.add("java");
        lists.add("python");
        lists.add("c++");
        lists.add("angular");
        lists.add("redis");

        lists.stream().map(item -> CompletableFuture.runAsync(() -> getName(item))).collect(Collectors.toList());
        //System.out.println("list size:"+collect.size());
        /*collect.stream().forEach(item->{
            try {

                String s = item.get();
                //System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });*/

        System.out.println("CompletableFuture");
    }

    public static String getStr(String str){
        System.out.println("tast method done ....");
        return "this is getStr method "+str;
    }
    public static void getName(String name){
        System.out.println(Thread.currentThread().getName()+  ":get  task name ====+++++++++>>>>>:"+name);
        //return "[Stream]"+name;
    }


}
