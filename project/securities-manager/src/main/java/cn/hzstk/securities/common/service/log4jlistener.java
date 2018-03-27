package cn.hzstk.securities.common.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.sys.service.HolidayService;
import net.ryian.commons.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;

@Component
public class log4jlistener implements ServletContextListener {

    public static final String LOG4J_HOME = "log_path";
    public static final String ENV_HOME = "LOG4J_HOME";

    public void contextDestroyed(ServletContextEvent servletcontextevent) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) servletcontextevent
                .getServletContext()
                .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Scheduler scheduler = (Scheduler) webApplicationContext
                .getBean("springJobSchedulerFactoryBean");
        try {
            if(scheduler != null) scheduler.shutdown();
            Thread.sleep(1000);
        } catch (InterruptedException | SchedulerException e) {
            e.printStackTrace();
        }

        System.getProperties().remove(LOG4J_HOME);
    }

    public void contextInitialized(ServletContextEvent servletcontextevent) {
        String logsHome = System.getenv(ENV_HOME);
        if (StringUtils.isEmpty(logsHome)) {
            logsHome = Constant.LOG_PATH;//SystemConfig.INSTANCE.getValue(LOG4J_HOME);
        }
        System.setProperty(LOG4J_HOME, logsHome);
        execTask(servletcontextevent);
        /*log4jdir = servletcontextevent.getServletContext().getRealPath("/");*/
    }

    private void execTask(ServletContextEvent servletcontextevent) {
        try {
            ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(
                    servletcontextevent.getServletContext());
            HolidayService hService = ac.getBean(HolidayService.class);
            LoadTask loadJob = ac.getBean(LoadTask.class);

            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            boolean bFlag = hService.chkHoliday(DateUtils.format(new Date()));
            if (!bFlag && hour < 16) {
                loadJob.initTask();
            } else {
                loadJob.execTask();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}