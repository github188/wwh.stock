package com.zjhcsoft.struc.crawler;

import java.text.ParseException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dom4j.DocumentException;

import com.zjhcsoft.struc.bean.TimeDegree;
import com.zjhcsoft.struc.conf.Configuration;
import com.zjhcsoft.struc.util.TimingUtil;
import com.zjhcsoft.struc.util.datasource.DataSourceHandler;
import com.zjhcsoft.struc.util.mail.MailClient;

public class CommonCrawler extends Crawler {

	CommonCrawler() {
		super("Common");
		DataSourceHandler.loadConfig(Configuration.dataSourceName);
		queue.offer(DataSourceHandler.urlPattern.getUrl());
		
	}

	public static void main(String[] args) throws DocumentException, ParseException {
		long start = System.currentTimeMillis();

		LOG.info("Usage: (-dsn <dataSourceName>) [-threads n] [-retry n] [-timeout n]");

		for (int i = 0; i < args.length; i++) {
			if ("-dsn".equals(args[i])) {
				Configuration.dataSourceName = args[i + 1];
				i++;
			} else if ("-threads".equals(args[i])) {
				Configuration.THREADS = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("-retry".equals(args[i])) {
				Configuration.retries = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("-timeout".equals(args[i])) {
				Configuration.timeout = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("-starttime".equals(args[i])) {
                Configuration.degree = TimeDegree.PERIOD;
                Configuration.starttime = TimingUtil.sdf10.parse(args[i + 1]);
                i++;
            } else if ("-endtime".equals(args[i])) {
                Configuration.degree = TimeDegree.PERIOD;
                Configuration.endtime = TimingUtil.sdf10.parse(args[i + 1]);
                i++;
            } 
		}

		if (Configuration.dataSourceName == null) {
			LOG.error("未指定dataSourceName");
			System.exit(-1);
		}

		CommonCrawler crawler = new CommonCrawler();

		// 缓存线程池
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		Future<Integer> task = cachedThreadPool.submit(crawler);
		cachedThreadPool.shutdown();
		try {
			task.get(Configuration.timeout, TimeUnit.SECONDS);
		} catch (ExecutionException | TimeoutException | InterruptedException e) {
			MailClient.sendHtmlMailToAdmin(ExceptionUtils.getStackTrace(e).replaceAll("\n", "\n</br>"), crawler
			        .getClass().getSimpleName() + Arrays.toString(args) + " === 任务超时(" + Configuration.timeout + "s)");
			LOG.error(e.getMessage(), e);
			boolean cancelStatus = task.cancel(true);
			LOG.info("代码超时，强制结束:" + cancelStatus);
			System.exit(0);
		}

		LOG.info(TimingUtil.elapsedTime(start, System.currentTimeMillis()));
	}

}
