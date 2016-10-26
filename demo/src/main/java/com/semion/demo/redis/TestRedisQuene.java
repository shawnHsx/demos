package com.semion.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/10/26.
 */
public class TestRedisQuene {
    private static final Logger loger = LoggerFactory.getLogger(TestRedisQuene.class);

    public static void main(String[] rags) throws InterruptedException {
        String key = "testRedisQuene";// value = [key_9, key_8, key_7, key_6, key_5, key_4, key_3, key_2, key_1, key_0]

        loger.info( "===== redis quene is :{}", JedisUtil.lrange(key).toString());
        //JedisUtil.del(key);
        String lpop = JedisUtil.lpop(key);
        loger.info(lpop);
        loger.info("===== redis quene is :{}", JedisUtil.lrange(key).toString());
        loger.info(JedisUtil.rpop(key));
        loger.info("===== redis quene is :{}", JedisUtil.lrange(key).toString());

        //JedisUtil.del(key);
        for (int i = 0; i < 10; i++) {
            //Thread.sleep(2000L);
            //JedisUtil.lpush(key,"key_"+i);
           // loger.info(i + "===== redis quene is :{}", JedisUtil.lrange(key).toString());
        }
    }

}
