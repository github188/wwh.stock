package com.skoo.stock.common.service;

import com.skoo.common.SystemConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class log4jlistener implements ServletContextListener {

 public static final String log4jdirkey = "log4jdir";
 public void contextDestroyed(ServletContextEvent servletcontextevent) {
  System.getProperties().remove(log4jdirkey);

 }

 public void contextInitialized(ServletContextEvent servletcontextevent) {
  String log4jdir = servletcontextevent.getServletContext().getRealPath("/");
  //System.out.println("log4jdir:"+log4jdir);
  log4jdir = SystemConfig.INSTANCE.getValue("stock");
  System.setProperty(log4jdirkey, log4jdir);
  System.setProperty("stock", log4jdir);
  System.out.println("stock:"+System.getProperty("stock"));

 }

}