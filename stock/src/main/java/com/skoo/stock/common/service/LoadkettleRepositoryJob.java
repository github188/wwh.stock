package com.skoo.stock.common.service;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.sys.domain.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Calendar;

public class LoadkettleRepositoryJob {

	private SchedulerFactoryBean schedulerFactoryBean;
//	private Scheduler schedulerFactoryBean;

	public void execute() {
		try
		{
			//schedulerFactoryBean 由spring创建注入
			List<ScheduleJob> jobList = new ArrayList();
			Scheduler scheduler = SpringContextUtil.getBean("springJobSchedulerFactoryBean");//getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains("");
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					ScheduleJob job = new ScheduleJob();
					job.setJobName(jobKey.getName());
					job.setJobGroup(jobKey.getGroup());
					job.setDescription("触发器:" + trigger.getKey());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					job.setJobStatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						if ("stockIndexQtzJobMethod".equals(jobKey.getName())) {
							int hour = Calendar.getInstance().get(Calendar.HOUR);
							int minute = Calendar.getInstance().get(Calendar.MINUTE);
							hour = hour * 100 + minute;
							if (hour <= 1130) {
								cronExpression = "0 0/1 * ? * 2-6";
							} else if (hour <= 1501) {
								cronExpression = "0 0/1 13 ? * 2-6";
							} else {
								cronExpression = "0 15 9 ? * 2-6";
							}
						}
						job.setCronExpression(cronExpression);
					}
					jobList.add(job);
				}
			}

			for (ScheduleJob job : jobList) {
				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

				//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

				//不存在，创建一个
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
						.withIdentity(job.getJobName(), job.getJobGroup()).build();
					jobDetail.getJobDataMap().put("scheduleJob", job);

					//表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
						.getCronExpression());

					//按新的cronExpression表达式构建一个新的trigger
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

					scheduler.scheduleJob(jobDetail, trigger);
				} else {
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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
}