package com.skoo.stock.common.service;

import com.skoo.commons.StringUtils;
import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.sys.domain.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
    private static final Logger log = LoggerFactory.getLogger("mytest");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        invokMethod(scheduleJob);
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
/*
        if ("100010".equals(scheduleJob.getJobId())) {
            indexQtz.execute();
        } else if ("100011".equals(scheduleJob.getJobId())) {
            indexQtz.execute();
        } else if ("100012".equals(scheduleJob.getJobId())) {
            indexQtz.execute();
        } else if ("100013".equals(scheduleJob.getJobId())) {
            infoQtz.execute();
        } else if ("100014".equals(scheduleJob.getJobId())) {
            springQtz.execute();
        }
*/
    }

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
        //springId不为空先按springId查找bean
        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            object = SpringContextUtil.getBean(scheduleJob.getSpringId());
        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if (object == null) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}