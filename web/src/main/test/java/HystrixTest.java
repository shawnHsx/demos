import com.semion.web.action.hystrix.CommandHelloWorld;
import com.semion.web.action.hystrix.CommandHelloWorld_1;
import com.semion.web.action.hystrix.HystrixFallback4Exception;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by heshuanxu on 2018/4/9.
 */
public class HystrixTest {

    private static final Logger logger = LoggerFactory.getLogger(HystrixTest.class);

    // 同步阻塞
    @Test
    public void testSynchronous() {
        assertEquals("Hello World", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob", new CommandHelloWorld("Bob").execute());
    }

    //异步阻塞
    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello World", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob", new CommandHelloWorld("Bob").queue().get());
    }

    // 异步
    @Test
    public void testAsynchronous2() throws Exception {
        logger.info("==========start========");
        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();
        logger.info("==========exec========");
        assertEquals("Hello World", fWorld.get());// get方法阻塞
        assertEquals("Hello Bob", fBob.get());
        logger.info("==============end==============");
    }


    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        assertEquals("Hello World", fWorld.toBlocking().single());
        assertEquals("Hello Bob", fBob.toBlocking().single());

        fWorld.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
                logger.info("onCompleted============");
            }

            @Override
            public void onError(Throwable e) {
                logger.info("{}",e);
            }

            @Override
            public void onNext(String v) {
                logger.info("onNext==========="+v);
            }

        });

        fBob.subscribe(new Action1<String>() {

            @Override
            public void call(String v) {
                logger.info("onNext: " + v);
            }
        });
    }


    //异步
    @Test
    public void testHystrixObservable() {

        CommandHelloWorld_1 world = new CommandHelloWorld_1("World");

        Observable<String> observe = world.observe();

        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                logger.info("===========onCompleted=============");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                logger.info("===============:"+s);
            }
        });

    }
    @Test
    public void testHystrixFallback4Exception(){
        try {
            assertEquals("fallback: Hlx", new HystrixFallback4Exception("Hlx").execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
