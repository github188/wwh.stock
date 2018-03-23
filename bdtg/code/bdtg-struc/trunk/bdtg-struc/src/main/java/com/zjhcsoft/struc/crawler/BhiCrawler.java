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

public class BhiCrawler extends Crawler{

	BhiCrawler() {
		super("Bhi");
		String url = "http://projectinfo.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=200&solr_rsort=0&solr_keywords=%25u8BF7%25u8F93%25u5165%25u5173%25u952E%25u8BCD&solr_area=&solr_industry=%25u77F3%25u6CB9%25u5316%25u5DE5%252C%25u7164%25u5316%25u5DE5%252C%25u7CBE%25u7EC6%25u5316%25u5DE5%252C%25u65E0%25u673A%25u5316%25u5DE5&solr_date=-366&solr_cbcolumns=0,1&solr_currentPage=1&solr_fund=&solr_jinzhan=&solr_xmxingzhi=&solr_qyxingzhi=&solr_leibie=&solr_fenlei=";
		queue.offer(url);
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

		BhiCrawler crawler = new BhiCrawler();
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
