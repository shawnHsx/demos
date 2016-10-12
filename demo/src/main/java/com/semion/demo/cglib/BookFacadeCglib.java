package com.semion.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * Created by heshuanxu on 2016/6/7.
 *
 * 使用cglib动态代理
 * 代理类：BookFacadeImpl
 */
public class BookFacadeCglib implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(BookFacadeCglib.class);

    private Object target;// 被代理对象

    /**
     * 创建代理对象 -- 实现被代理对象的子类
     *
     *         原理就是用Enhancer生成一个原有类的子类，并且设置好callback ，
     *         则原有类的每个方法调用都会转成调用实现了MethodInterceptor接口的proxy的intercept()；
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法 织入逻辑（原有方法的增强）
        // this:代表BookFacadeCglib，BookFacadeCglib implements  MethodInterceptor extends Callback
        enhancer.setCallback(this);// 需要一个Callback 对象
        // 创建代理对象
        return enhancer.create();
    }

    /**
     * 返回实现回调的代理对象
     * @param target
     * @return
     */
    public Object getInstance2(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());

        //内部类实现回调拦截
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if("addBook".equals(method.getName())){
                    return 0;//Callback callbacks[0]
                }else if("editBook".equals(method.getName())){
                    return 1;//Callback callbacks[1]
                }else if("delBook".equals(method.getName())){
                    return 2;//Callback callbacks[2]
                }
                return 1;
            }
        });

        Callback interceptor= new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                logger.info("before ....");
                Object o1 = methodProxy.invokeSuper(o, args);
                logger.info("after .....");
                return o1;
            }
        };
        //不进行拦截
        Callback noOp= NoOp.INSTANCE;
        // 锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值
        Callback fixedValue= new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                Object obj = 123;
                return obj;
            }
        };
        // 回调方法的数组拦截索引
        Callback[] callbacks=new Callback[]{interceptor,noOp,fixedValue};
        enhancer.setCallbacks(callbacks);
        // 创建代理对象
        return enhancer.create();
    }


    /**
     * 回调方法 拦击所有被调用父类的方法
     * @param o
     * @param method
     * @param args  // 方法参数
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        logger.info("开启事务");
        Object o1 = methodProxy.invokeSuper(o, args);
        logger.info("提交事务");
        return o1;
    }
}
