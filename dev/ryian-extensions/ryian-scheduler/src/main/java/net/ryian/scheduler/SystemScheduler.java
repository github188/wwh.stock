package net.ryian.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SystemScheduler {

	@Autowired
	private SchedulerFactoryBean sf;

	private static String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP";
	private static String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER";

	/** 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 */
	public void addJob(String jobName, Job job, String cronExpression,Map data)
			throws SchedulerException {
		addJob(jobName, null, jobName, null, job, cronExpression,data);
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param job
	 *            任务
	 * @param cronExpression
	 *            时间设置，参考quartz说明文档
	 */
	public void addJob(String jobName, String jobGroupName, String triggerName,
			String triggerGroupName, Job job, String cronExpression,Map data)
			throws SchedulerException {
		if (StringUtils.isBlank(jobGroupName)) {
			jobGroupName = JOB_GROUP_NAME;
		}
		if (StringUtils.isBlank(triggerGroupName)) {
			triggerGroupName = TRIGGER_GROUP_NAME;
		}
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName,
				jobGroupName).setJobData(new JobDataMap(data)).build();
		CronTrigger trigger = newTrigger()
				.withIdentity(triggerName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown()) {
			sched.start();
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 */
	public void modifyJobTime(String jobName, String cronExpression)
			throws SchedulerException {
		modifyJobTime(jobName, null, cronExpression);
	}

	/**
	 * 修改一个任务的触发时间
	 */
	public void modifyJobTime(String triggerName, String triggerGroupName,
			String cronExpression) throws SchedulerException {
		if (StringUtils.isBlank(triggerGroupName)) {
			triggerGroupName = TRIGGER_GROUP_NAME;
		}
		Scheduler sched = sf.getScheduler();
		CronTrigger trigger = newTrigger()
				.withIdentity(triggerName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		sched.rescheduleJob(triggerKey(triggerName, triggerGroupName), trigger);
	}

	/** 移除一个任务和触发器(使用默认的任务组名，触发器名，触发器组名) */
	public void removeJob(String jobName)
			throws SchedulerException {
		removeJob(jobName, null, jobName, null);
	}

	/** 移除一个任务和触发器 */
	public void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName)
			throws SchedulerException {
		if (StringUtils.isBlank(jobGroupName)) {
			jobGroupName = JOB_GROUP_NAME;
		}
		if (StringUtils.isBlank(triggerGroupName)) {
			triggerGroupName = TRIGGER_GROUP_NAME;
		}
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(triggerKey(triggerName, triggerGroupName));// 停止触发器
		sched.unscheduleJob(triggerKey(triggerName, triggerGroupName));// 移除触发器
		sched.deleteJob(jobKey(jobName, jobGroupName));// 删除任务
	}

}
