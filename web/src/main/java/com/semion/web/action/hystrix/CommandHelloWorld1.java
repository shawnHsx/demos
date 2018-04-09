package com.semion.web.action.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;


/**
 * Created by heshuanxu on 2018/4/9.
 */
public class CommandHelloWorld1 extends HystrixCommand<String> {



    private final String name;

    protected CommandHelloWorld1(String  name) {
        //最少配置:指定命令组名(CommandGroup)
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name= name;
    }

    //execute()、queue()、observe()、toObservable()这4个方法用来触发执行

    @Override
    protected String run() throws Exception {
        // 依赖逻辑封装在run()方法中
        return "Hello " + name + "! thread :"+Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        //注册观察者事件拦截
        Observable<String> fs = new CommandHelloWorld1("World").observe();
        //注册结果回调事件
        fs.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                //执行结果处理,result 为HelloWorldCommand返回的结果
                //用户对结果做二次处理.
                System.out.println("call:"+s);
            }
        });

        //注册完整执行生命周期事件
        fs.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                System.out.println("execute onCompleted");
            }
            @Override
            public void onError(Throwable throwable) {
                // 当产生异常时回调
                System.out.println("onError " + throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                // 获取结果后回调
                System.out.println("onNext: " + s);
            }
        });
    }

}
