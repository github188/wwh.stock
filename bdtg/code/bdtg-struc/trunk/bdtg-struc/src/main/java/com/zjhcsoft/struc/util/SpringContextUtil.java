package com.zjhcsoft.struc.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
	        "classpath:/spring/application-orm.xml");

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
}
