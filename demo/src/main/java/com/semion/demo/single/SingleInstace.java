package com.semion.demo.single;

public class SingleInstace {

    //volatile 禁止在构造实力过程中指令冲排序
    private static volatile SingleInstace singleInstace = null;

    private SingleInstace(){}

    // 双重检查
    public static SingleInstace getInstance(){
        if(singleInstace ==null){
            synchronized (SingleInstace.class){
                if(singleInstace==null){
                    // new 的过程发生 指令重排序
                    /**
                     * memory = allocate();　　// 1：分配对象的内存空间
                     * ctorInstance(memory);　// 2：初始化对象
                     * instance = memory;　　// 3：设置instance指向刚分配的内存地址
                     * 以上三步 可能发生指令重排序 1-->3--->2  导致其他线程获取一个未实力化的对象。
                     */

                    return  new SingleInstace();
                }
            }
        }
        return singleInstace;
    }
}
