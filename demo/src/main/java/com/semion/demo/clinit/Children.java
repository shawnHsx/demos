package com.semion.demo.clinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heshuanxu on 2016/9/27.
 */
public class Children extends Parent {
    private final static Logger logger = LoggerFactory.getLogger(Children.class);

    static {
        logger.info("Children init...");
    }
}
