package com.semion.demo.clinit;

/**
 * Created by heshuanxu on 2016/9/27.
 */
public class Init {
    static int a = 1;
    static {
        System.out.println(a);
    }
    public static void main(String[] args){
        System.out.println(Children.A);// 子类引用父类静态属性 不会初始化子类
        Parent[] arr = new Parent[10];// 定义数组不会初始化Parent类
    }
}
