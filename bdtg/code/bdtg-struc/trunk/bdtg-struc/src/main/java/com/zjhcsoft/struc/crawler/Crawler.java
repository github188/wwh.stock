package com.zjhcsoft.struc.crawler;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.bean.ResultStatus;
import com.zjhcsoft.struc.conf.Configuration;
import com.zjhcsoft.struc.fetch.Fetcher;
import com.zjhcsoft.struc.parse.Parser;
import com.zjhcsoft.struc.save.Saver;
import com.zjhcsoft.struc.update.Updater;
import com.zjhcsoft.struc.util.StrucContext;
import com.zjhcsoft.struc.util.mail.MailClient;

public class Crawler implements Callable<Integer> {

	protected static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

	// 缓存线程池
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	// ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10,
	// 7,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new
	// ThreadPoolExecutor.DiscardOldestPolicy());

	private String mark; // Class标识
	private Updater updater; // 更新类
	protected static Queue<String> queue; // 爬取队列
	protected static Queue<String> retryQueue; // 重试队列
	private int retries; // 已重试的次数
	private CountDownLatch countDownLatch = null; // 线程计数器

	Crawler(String mark) {
		this.mark = mark.substring(0, 1).toUpperCase() + mark.substring(1);// 首字母大写
		try {
			Class<?> updaterClass = Class.forName("com.zjhcsoft.struc.update." + mark + "Updater");
			try {
				updater = (Updater) updaterClass.newInstance();
			} catch (InstantiationException e) {
				LOG.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (ClassNotFoundException e) {
			LOG.info("忽略UPDATE");
		}
		this.queue = new ConcurrentLinkedQueue<String>();
		this.retryQueue = new ConcurrentLinkedQueue<String>();
		this.retries = 0;
	}

	@Override
	public Integer call() {
		LOG.info("URL数===" + String.valueOf(queue.size()));

		crawl();
		
		LOG.info("------------正在对失败的url进行重试--------------");
		// 对于采集失败的URL进行重试
		while (retries < Configuration.retries && !retryQueue.isEmpty()) {
			queue.addAll(retryQueue);
			retryQueue.clear();
			retries++;
			LOG.info("-----第" + retries + "次重试:" + queue.size() + "-----");
			crawl();
		}

		if (updater != null) {
			LOG.info("更新开始");
			updater.update(retryQueue);
		}

		// 关闭线程池
		cachedThreadPool.shutdown();
		LOG.info("---工作已完成---------");
		return 0;
	}

	private void crawl() {
		// 计数器初始化
		countDownLatch = new CountDownLatch(Configuration.THREADS);

		for (int i = 0; i < Configuration.THREADS; i++) {
			CrawlTask task = new CrawlTask();
			// cachedThreadPool.submit(thread);
			Thread thread = new Thread(task);
			LOG.info("线程数++++++");
			// 线程托管
			thread.start();
		}

		try {
			// 当前线程会一直等待，直到计数值到达0
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 爬虫抓取流程,核心调度代码类
	 */
	private class CrawlTask implements Runnable {

		@Override
		public void run() {
			try {
				Fetcher fetcher = null;
				Parser parser = null;
				Saver saver = null;
				try {
					Class<?> parserClass = Class.forName("com.zjhcsoft.struc.parse." + mark + "Parser");
					Class<?> saverClass = Class.forName("com.zjhcsoft.struc.save." + mark + "Saver");
					try {
						parser = (Parser) parserClass.newInstance();
						saver = (Saver) saverClass.newInstance();
					} catch (InstantiationException e) {
						LOG.error(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						LOG.error(e.getMessage(), e);
					}
				} catch (ClassNotFoundException e) {
					LOG.error(e.getMessage(), e);
				}
				LOG.info("抓取数量为： " + queue.size());
				while (!queue.isEmpty()) {
					String url = queue.poll();
					LOG.info("剩余url数量：" + queue.size());
					if (url == null) {
						break;
					}
					// 核心工作环节 fetch-parse-save
					String status = null;
					LOG.info("抓取开始 - " + url);
					// Object fetchObject = fetcher.fetch(url);
					try {
						fetcher = (Fetcher) Class.forName("com.zjhcsoft.struc.fetch." + mark + "Fetcher").newInstance();
						fetcher.setUrl(url);
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					Object fetchObject = null;
					Future<Object> fetch = cachedThreadPool.submit(fetcher);
					try {
						fetchObject = fetch.get(Configuration.fetchTimeout, TimeUnit.SECONDS);
					} catch (Exception e) {
						LOG.error("Error fetching: " + url + "---" + e.getMessage(), e);
					} finally {
						fetcher = null;
						fetch.cancel(true);
					}

					if (fetchObject != null) {
						LOG.info("解析开始 - " + url);
						Map<Object, String> parseMap = parser.parse(fetchObject, url);
						if (parseMap != null) {
							boolean isSuccessed = true;
							LOG.info("保存开始:" + parseMap.size() + " - " + url);
							for (Entry<Object, String> entry : parseMap.entrySet()) {
								if (saver.save(entry.getKey(), entry.getValue()) == false) {
									isSuccessed = false;
								}
							}
							if (!isSuccessed) {
								status = ResultStatus.SAVE_FAILURE.getStatusDescription();
							}
						} else {
							status = ResultStatus.PARSE_FAILURE.getStatusDescription();
						}
					} else {
						status = ResultStatus.FETCH_FAILURE.getStatusDescription();
					}

					if (status != null) {
						retryQueue.offer(url);
						LOG.info(status + ",放入重试队列===" + url);
					}
				}
			} finally {
				// 业务运行结束，计数器减值
				countDownLatch.countDown();
			}
		}
	}
}
