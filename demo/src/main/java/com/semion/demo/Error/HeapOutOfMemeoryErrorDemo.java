package com.semion.demo.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出(OOM)
 * Created by heshuanxu on 2016/10/24.
 */
public class HeapOutOfMemeoryErrorDemo {

    private final static Logger logger = LoggerFactory.getLogger(HeapOutOfMemeoryErrorDemo.class);

    public static void main(String[] tag) {
        headOutMemeory();
    }

    /**
     * 修改head 内存大小
     * -Xms5m -Xms5m
     */
    static void headOutMemeory() {

        long count = 0L;
        try {
            List<Object> list = new ArrayList<Object>();
            while (true) {
                list.add(new Object());
                count++;
            }
        } catch (Throwable e) {
            logger.error("当前创建对象个数：{}时，发生错误", count);
            e.printStackTrace();
        }
    }
}