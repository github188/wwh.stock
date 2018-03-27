package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.sys.domain.ScheduleJob;
import cn.hzstk.securities.sys.service.HolidayService;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.sys.service.ScheduleJobService;
import cn.hzstk.securities.util.DateUtil;
import net.ryian.commons.DateUtils;
import net.ryian.core.utils.SpringContextUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@SuppressWarnings("JavaDoc")
@Component
public class LoadTask {
    private static final Logger logger = LoggerFactory.getLogger(Constant.LOG_ERROR);
    ScheduleJobService jService = SpringContextUtil.getBean(ScheduleJobService.class);
    HolidayService hService = SpringContextUtil.getBean(HolidayService.class);
    ParamService paramService = SpringContextUtil.getBean(ParamService.class);
    //schedulerFactoryBean 由spring创建注入
    Scheduler scheduler = SpringContextUtil.getBean("springJobSchedulerFactoryBean");

    public void execTask() {
        try {
            String tDate = DateUtils.format(new Date());
            String dt = tDate;
            while (hService.chkHoliday(dt)) {
                dt = DateUtil.getLastDay(dt);
            }
            String initDate = paramService.getbyName(ParamKeys.DATA_INIT_DATE);
            if (!initDate.equals(tDate)) {
                if (dt.equals(tDate)) {
                    StkInitTask.initData(dt);
                } else {
                    StkInitTask.initHoliData(dt);
                }
                paramService.updatebyName(ParamKeys.DATA_INIT_DATE, dt);
            }
        } catch (Exception ex) {
            logger.error("[" + this.getClass() + "]： " + ex.toString());
        }
    }

     public void initTask() throws Exception {
         //这里获取任务信息数据
         List<ScheduleJob> jobList = jService.getAll();

         for (ScheduleJob job : jobList) {
             TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

             //获取trigger
             CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

             //不存在，创建一个
             if (null == trigger) {
                 if (ScheduleJob.STATUS_DELETE.equals(job.getJobStatus())) {
                     continue;
                 }

                 JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                         .withIdentity(job.getJobName(), job.getJobGroup()).build();
                 jobDetail.getJobDataMap().put("scheduleJob", job);

                 //表达式调度构建器
                 CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                         .getCronExpression());

                 //按新的cronExpression表达式构建一个新的trigger
                 trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                 scheduler.scheduleJob(jobDetail, trigger);
             } else {
                 if (ScheduleJob.STATUS_DELETE.equals(job.getJobStatus())) {
                     deleteJob(job);
                     continue;
                 } else if (ScheduleJob.STATUS_RESUME.equals(job.getJobStatus())) {
                     resumeJob(job);
                     continue;
                 }

                 // Trigger已存在，那么更新相应的定时设置
                 //表达式调度构建器
                 CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                         .getCronExpression());

                 //按新的cronExpression表达式重新构建trigger
                 trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                         .withSchedule(scheduleBuilder).build();

                 //按新的trigger重新设置job执行
                 scheduler.rescheduleJob(triggerKey, trigger);
             }

             if (ScheduleJob.STATUS_PAUSE.equals(job.getJobStatus())) {
                 pauseJob(job);
             }
         }
     }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

}