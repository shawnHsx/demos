package com.semion.web.action.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by heshuanxu on 2018/4/9.
 */
public class CommandHelloWorld_1 extends  HystrixObservableCommand<String>{

    private static final Logger logger = LoggerFactory.getLogger(CommandHelloWorld_1.class);
    private final String name;

    public CommandHelloWorld_1(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorld_1"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        logger.info(Thread.currentThread().getName()+" is running......");
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                logger.info("construct---call ===============");
                if (!observer.isUnsubscribed()) {
                    logger.info("==================sleep start=========");
                    //TimeUnit.MILLISECONDS.sleep(3000);
                    //TimeUnit.SECONDS.sleep(3);
                    logger.info("==================sleep end=========");
                    observer.onNext(name);
                    observer.onCompleted();
                }
                throw new HystrixBadRequestException("HystrixBadRequestException is never trigger fallback");
            }
        }).subscribeOn(Schedulers.io());
    }


    @Override
    protected Observable<String> resumeWithFallback() {
        logger.info("resumeWithFallback ===============");
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    logger.info("resumeWithFallback---call ===============");
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(name);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
