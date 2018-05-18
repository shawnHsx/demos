package com.semion.web.action.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


/**
 * Created by heshuanxu on 2018/5/18.
 */
public class HelloTask implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HelloTask execute ==============");
    }
}
