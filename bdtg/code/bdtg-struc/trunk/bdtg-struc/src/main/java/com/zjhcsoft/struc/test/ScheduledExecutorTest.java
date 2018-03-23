package com.zjhcsoft.struc.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest implements Runnable {

	@SuppressWarnings("static-access")
	@Override
	public void run() {
	}

	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

		long initialDelay1 = 0;
		long period1 = 60;
		// 从现在开始0秒钟之后，每隔60分钟执行一次job1
		service.scheduleAtFixedRate(new ScheduledExecutorTest(), initialDelay1,
				period1, TimeUnit.MINUTES);
	}
}