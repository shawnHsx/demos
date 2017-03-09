package com.semion.demo.dynamicProxy.jdkProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by heshuanxu on 2016/6/6.
 * 通过jdk自带的InvocationHadle 实现AOP代理切面织入
 * 属于非侵入式编程本身就是侵入式
 * <p>
 * 缺点：性能不高，只能代理实现了接口的类  推荐使用 cglib
 */
public class InvocationHadleTest {

    private final static Logger logger = LoggerFactory.getLogger(InvocationHadleTest.class);

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, FileNotFoundException {
        //生成proxy 的代理类com.sun.proxy.$Proxy0的class文件 可进行反编译

        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles",true);

        // 通过反射获取目标对象实例
        Hello hello = (Hello) Class.forName("HelloImpl").newInstance();

        // 获取aop切面类（在目标方法前后需要做的动作）
        InvocationHandler handler = new AopFactory(hello);// 目标对象通过构造方法传入代理类

        // 动态生成的代理类
        writeProxyClassToHardDisk("E:\\$Proxy0.class");

        // 生成代理对象
        /**
         * ClassLoader loader, 类加载器 主要有三种，具体见类加载器内容
         * Class<?>[] interfaces, 所有接口
         * InvocationHandler h 实现InvocationHandler的子类
         */
        Hello proxy = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);

        // 通过代理对象调用方法
        proxy.setInfo("hello", "world");
    }

    /**
     * 保存代理类到本地
     *
     * @param path
     */
    public static void writeProxyClassToHardDisk(String path) {

        // 获取代理类的字节码
        /**
         * $Proxy0 为代理类的类名
         */
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", HelloImpl.class.getInterfaces());

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
