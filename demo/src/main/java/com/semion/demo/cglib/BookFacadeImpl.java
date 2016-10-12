package com.semion.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/6/7.
 */
public class BookFacadeImpl {

    private final static Logger logger = LoggerFactory.getLogger(BookFacadeImpl.class);


    void addBook(String words){
        int i = words.hashCode();
        logger.info("开始添加图书的普通方法。。。。。。。。。。。");
    }

    void editBook(String words){
        int i = words.hashCode();
        logger.info("edit 图书的普通方法。。。。。。。。。。。");
    }

    void delBook(String words){
        int i = words.hashCode();
        logger.info("del 图书的普通方法。。。。。。。。。。。");
    }

}
