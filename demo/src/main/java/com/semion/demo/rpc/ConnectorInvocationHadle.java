package com.semion.demo.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by heshuanxu on 2016/9/20.
 * 通过socket 连接服务端并发送参数 返回代理对象
 */
public class ConnectorInvocationHadle implements InvocationHandler {

    private String host;
    private int port;

    public ConnectorInvocationHadle(String host,int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //建立socket 连接
        SocketChannel socketChannel = SocketChannel.open();
        try {
            SocketAddress socketAddress = new InetSocketAddress(this.host, this.port);
            socketChannel.connect(socketAddress);
            //获取输出流
            ObjectOutputStream output = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            try {
                // 写入参数
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);

                //获取输入流
                ObjectInputStream input = new ObjectInputStream(socketChannel.socket().getInputStream());
                // 返回结果
                try {
                    Object object = input.readObject();
                    if (object instanceof Throwable) {
                        throw (Throwable) object;
                    }
                    return  object;
                } finally {
                    input.close();
                }
            } finally {
                output.close();
            }
        } finally {
            socketChannel.close();
        }
    }
}
