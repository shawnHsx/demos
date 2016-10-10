package com.semion.demo.clinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/9/27.
 */
public class Parent {

    private final static Logger logger = LoggerFactory.getLogger(Parent.class);

    static int A = 100;
    static {
        logger.info("Parent init...");
    }
}
