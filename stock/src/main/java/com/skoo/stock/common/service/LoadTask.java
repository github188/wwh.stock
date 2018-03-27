package com.skoo.stock.common.service;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.sys.domain.ScheduleJob;
import com.skoo.stock.sys.service.ScheduleJobService;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadTask {
    ScheduleJobService jService = SpringContextUtil.getBean(ScheduleJobService.class);
    //schedulerFactoryBean 由spring创建注入
    Scheduler scheduler = SpringContextUtil.getBean("springJobSchedulerFactoryBean");

 public void initTask() throws Exception {
     //这里获取任务信息数据
     List<ScheduleJob> jobList = jService.getAll();
/*
     for(int i=0;i<5;i++)
     {
         ScheduleJob schedulejob=new ScheduleJob();
         schedulejob.setJobId("10001" + i);
         schedulejob.setJobName("任务-" + schedulejob.getJobId());
         schedulejob.setJobGroup("groupsimpletrigger");
         schedulejob.setJobStatus("1");
         schedulejob.setBeanClass("com.skoo.stock.common.service.StockIndexQtz");
         schedulejob.setMethodName("execute");
         if (i == 0) {
             //schedulejob.setCronExpression("0 20/5 9 ? * 2-6");
             schedulejob.setCronExpression("0 1 15 ? * 2-6");
         } else if (i == 1) {
             schedulejob.setCronExpression("0 0/5 10,13-14 ? * 2-6");
         } else if (i == 2) {
             schedulejob.setCronExpression("0 0-30/5 11 ? * 2-6");
         } else if (i == 3) {
             schedulejob.setCronExpression("0 15 15 ? * 1-7");
             schedulejob.setBeanClass("com.skoo.stock.common.service.StockInfoQtz");
         } else if (i == 4) {
             schedulejob.setCronExpression("0 0 16 ? * 1-7");
             schedulejob.setBeanClass("com.skoo.stock.common.service.SpringQtz");
         }
         jobList.add(schedulejob);
     }

     //这里获取任务信息数据
     List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
     List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
     for (JobExecutionContext executingJob : executingJobs) {
         ScheduleJob job = new ScheduleJob();
         JobDetail jobDetail = executingJob.getJobDetail();
         JobKey jobKey = jobDetail.getKey();
         Trigger trigger = executingJob.getTrigger();
         job.setJobName(jobKey.getName());
         job.setJobGroup(jobKey.getGroup());
         job.setDesc("触发器:" + trigger.getKey());
         Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
         job.setJobStatus(triggerState.name());
         if (trigger instanceof CronTrigger) {
             CronTrigger cronTrigger = (CronTrigger) trigger;
             String cronExpression = cronTrigger.getCronExpression();
             job.setCronExpression(cronExpression);
         }
         jobList.add(job);
     }
*/

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