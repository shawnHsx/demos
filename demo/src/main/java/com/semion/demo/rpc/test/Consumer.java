package com.semion.demo.rpc.test;

import com.semion.demo.rpc.RPCFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by heshuanxu on 2016/9/20.
 */
public class Consumer {

    private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] rag) throws Exception {

        //生成$Proxy0的class文件 反编译可查看  ----尚未获取该代理类待验证
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles",true);

      /*  Properties properties = System.getProperties();
        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<Object, Object> entry = iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            logger.info(key.toString() + " =========> " + value.toString());
        }*/
        // 获取远程代理对象接口
        HelloService service = RPCFramework.refer(HelloService.class,"localhost",10000);

        logger.info(service.getClass().getName());//com.sun.proxy.$Proxy0

        // 调用该类的sayHi方法时 调用了代理类的sayHi方法，在代理类的sayHi方法中调用了 ConnectorInvocationHadle 的invoke方法(建立socket连接发送调用信息等)
        String consumer = service.sayHi("是我consumer");
        logger.info(consumer);
    }

}
