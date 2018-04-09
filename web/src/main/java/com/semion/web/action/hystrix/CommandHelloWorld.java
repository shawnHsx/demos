package com.semion.web.action.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshuanxu on 2018/4/9.
 * 命令模式封装依赖逻辑
 */
public class CommandHelloWorld extends HystrixCommand<String> {


    private static final Logger logger = LoggerFactory.getLogger(CommandHelloWorld.class);



/*
    execute()：以同步堵塞方式执行run()。调用execute()后，hystrix先创建一个新线程运行run()，接着调用程序要在execute()调用处一直堵塞着，直到run()运行完成

    queue()：以异步非堵塞方式执行run()。一调用queue()就直接返回一个Future对象，同时hystrix创建一个新线程运行run()，调用程序通过Future.get()拿到run()的返回结果，而Future.get()是堵塞执行的

    observe()：事件注册前执行run()/construct()。第一步是事件注册前，先调用observe()自动触发执行run()/construct()
              （如果继承的是HystrixCommand，hystrix将创建新线程非堵塞执行run()；如果继承的是HystrixObservableCommand，将以调用程序线程堵塞执行construct()）
              第二步是从observe()返回后调用程序调用subscribe()完成事件注册，如果run()/construct()执行成功则触发onNext()和onCompleted()，如果执行异常则触发onError()

    toObservable()：事件注册后执行run()/construct()。第一步是事件注册前，
                    一调用toObservable()就直接返回一个Observable<String>对象，第二步调用subscribe()完成事件注册后自动触发执行run()/construct()
                   （如果继承的是HystrixCommand，hystrix将创建新线程非堵塞执行run()，调用程序不必等待run()；如果继承的是HystrixObservableCommand，
                   将以调用程序线程堵塞执行construct()，调用程序等待construct()执行完才能继续往下走），如果run()/construct()执行成功则触发onNext()和onCompleted()，如果执行异常则触发onError()
*/

    /**
     * HystrixCommand的observe()与toObservable()的区别：
     * 1）observe()会立即执行HelloWorldHystrixCommand.run()；toObservable()要在toBlocking().single()或subscribe()时才执行HelloWorldHystrixCommand.run()
     * 2）observe()中，toBlocking().single()和subscribe()可以共存；在toObservable()中不行，因为两者都会触发执行HelloWorldHystrixCommand.run()，
     *    这违反了同一个HelloWorldHystrixCommand对象只能执行run()一次原则
     */


    private final String name;

    public CommandHelloWorld(String  name) {
        //最少配置:指定命令组名
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name= name;
    }

    //execute()、queue()、observe()、toObservable()这4个方法用来触发执行

    @Override
    protected String run() throws Exception {
        // 依赖逻辑封装在run()方法中
        return "Hello " + name;
    }



    public static void main(String[] args) throws Exception {
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        CommandHelloWorld helloWorldCommand = new CommandHelloWorld("Synchronous-hystrix");
        //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        String result = helloWorldCommand.execute();
        System.out.println("result=" + result);

        helloWorldCommand = new CommandHelloWorld("Asynchronous-hystrix");
        //异步调用,可自由控制获取结果时机,
        Future<String> future = helloWorldCommand.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        System.out.println("result=" + result);
    }
}
