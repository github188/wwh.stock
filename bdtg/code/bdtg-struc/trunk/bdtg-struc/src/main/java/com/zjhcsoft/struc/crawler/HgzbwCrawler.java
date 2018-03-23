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

import com.zjhcsoft.struc.conf.Configuration;
import com.zjhcsoft.struc.util.TimingUtil;
import com.zjhcsoft.struc.util.mail.MailClient;

public class HgzbwCrawler extends Crawler{

	HgzbwCrawler() {
		super("Hgzbw");
		for(int i=1;i<=Configuration.page;i++){
		String url = "http://www.hgzbw.com/l_huagong-xiangmu_"+i+".html";
		queue.offer(url);
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ParseException {
		long start = System.currentTimeMillis();

		LOG.info("Usage: [-threads n] [-retry n] [-timeout n]");

		for (int i = 0; i < args.length; i++) {
			if ("-threads".equals(args[i])) {
				Configuration.THREADS = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("-retry".equals(args[i])) {
				Configuration.retries = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("-timeout".equals(args[i])) {
				Configuration.timeout = Integer.parseInt(args[i + 1]);
				i++;
			}else if ("-page".equals(args[i])) {
				Configuration.page = Integer.parseInt(args[i + 1]);
				i++;
			}
		}

		HgzbwCrawler crawler = new HgzbwCrawler();
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
			task.cancel(true);
			System.exit(0);
		}

		LOG.info(TimingUtil.elapsedTime(start, System.currentTimeMillis()));
	}

}
