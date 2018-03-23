package com.zjhcsoft.struc.util.mail;

import java.util.List;

public class MailClient {
	
	private final static String ADMIN_EMAIL_ADDRESS = "13336191882@189.cn";
	
	public static void sendHtmlMailToAdmin(String emailContent,String subject) {
		sendHtmlMail(emailContent, ADMIN_EMAIL_ADDRESS, subject);
	}
	
	public static void sendHtmlMailToMailList(String emailContent, List<String> toList, String subject) {
		for (String to : toList) {
			sendHtmlMail(emailContent, to, subject);
		}
	}
	
	public static void sendHtmlMail(String emailContent, String to, String subject) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		
		//hongcheng mail
		mailInfo.setMailServerHost("mail.zjhcsoft.com");
		mailInfo.setUserName("aihuanbao@zjhcsoft.com");
		mailInfo.setPassword("hongcheng");
		
		mailInfo.setFromAddress("aihuanbao@zjhcsoft.com");
		mailInfo.setToAddress(to);
		mailInfo.setSubject(subject);
		mailInfo.setContent(emailContent);
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
	}

	public static void main(String[] args) throws InterruptedException {
		sendHtmlMail("hello<br/>world!", "421659758@qq.com", "test");
	}
}
