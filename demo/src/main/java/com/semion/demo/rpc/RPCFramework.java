package com.semion.demo.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by heshuanxu on 2016/9/20.
 * 模拟远程调用框架
 */
public class RPCFramework {
    /**
     * java 中还有一种比较特殊的调用就是多态，也就是一个接口可能有多个实现，
     * 那么远程调用时到底调用哪个？ 这个本地调用的语义是通过 jvm 提供的引用多态性隐式实现的，
     * 那么对于 RPC 来说跨进程的调用就没法隐式实现了。 如果前面 DemoService 接口有 2 个实现，
     * 那么在导出接口时就需要特殊标记不同的实现.
     * DemoService demo   = new DemoServiceImpl();
     * DemoService demo2  = new DemoServiceImpl2();
     * server.export(DemoService.class, demo, options);
     * server.export("demo2", DemoService.class, demo2, options);
     * 此处多穿一个标记 "demo2"
     */


    /**
     * 导出远程接口 接受客户端socket连接 并解析参数
     *
     * @param serviceName
     * @param port
     */
    public static void exprot(final Object serviceName, final int port) throws Exception {

        ServerSocket server = new ServerSocket(port);

        while (true) {
            final Socket socket = server.accept();
            // 创建一个线程并启动
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                        try {
                            String methodName = input.readUTF();

                            Class<?>[] paramTypes = (Class<?>[]) input.readObject();

                            Object[] params = (Object[]) input.readObject();

                            Object result = null;

                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                            try {
                                Method method = serviceName.getClass().getMethod(methodName, paramTypes);

                                result = method.invoke(serviceName, params);// 执行serviceName的对象的该方法
                                // 执行结果返回给客户端
                                output.writeObject(result);

                            } catch (Throwable t) {
                                output.writeObject(t);
                            } finally {
                                output.close();
                            }
                        } finally {
                            input.close();
                        }
                    } catch (Exception e) {

                    }
                }
            }).start();
        }


    }

    /**
     * 远程接口代理实现
     *
     * @param interfaceClass 被代理接口
     * @param host           服务器host
     * @param port           socket连接端口
     * @param <T>            泛型接口
     * @return 代理对象
     */
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) {
        // 代理类的具体实现与服务端socket连接
        InvocationHandler handler = new ConnectorInvocationHadle(host, port);
        // 被代理的接口
        Class<?>[] interfaces = new Class[]{interfaceClass};
        // sun.misc.ProxyGenerator.generateProxyClass() 生成代理对象类的字节码文件
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaces, handler);

    }

}
