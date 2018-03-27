package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.sys.domain.ScheduleJob;
import net.ryian.commons.StringUtils;
import net.ryian.core.utils.SpringContextUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("JavaDoc")
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
    private static final Logger logger = LoggerFactory.getLogger(Constant.LOG_ERROR);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        invokMethod(scheduleJob);
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
    }

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz;
        //springId不为空先按springId查找bean
        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
            object = SpringContextUtil.getBean(scheduleJob.getSpringId());
        } else {
            if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
                try {
                    clazz = Class.forName(scheduleJob.getBeanClass());
                    object = clazz.newInstance();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        if (object == null) {
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            if (StringUtils.isEmpty(scheduleJob.getDescription())) {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            } else {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName(), new Class[]{Class.forName(scheduleJob.getDescription())});
            }
        } catch (NoSuchMethodException e) {
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                if (StringUtils.isEmpty(scheduleJob.getDescription())) {
                    method.invoke(object);
                } else {
                    method.invoke(object,"");
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}