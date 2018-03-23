package com.zjhcsoft.struc.monitor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.util.mail.MailClient;

public abstract class Monitoring {

	protected static Logger LOG = LoggerFactory.getLogger(Monitoring.class);

	public void process() {
		MonitorMessage message = judge();
		if (message != null && message.isError()) {
			List<String> emailList = new ArrayList<>();
			emailList.add("13388612378@189.cn");// 李纺
			emailList.add("13082852502@wo.cn");// 王诚
			emailList.add("13588385247@139.com");// 陶肖寅
			emailList.add("wujc@zjhcsoft.com");// 吴骏超

			MailClient.sendHtmlMailToMailList(message.getMessage(), emailList,
			        this.getClass().getSimpleName().replace("Monitoring", "") + "数据监控异常");
		}
	}

	public abstract MonitorMessage judge();
}
