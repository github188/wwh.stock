package com.zjhcsoft.struc.util;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringBeanFactory {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
	        "classpath*:jdbc.xml");

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static void main(String[] args) {
	}
}
