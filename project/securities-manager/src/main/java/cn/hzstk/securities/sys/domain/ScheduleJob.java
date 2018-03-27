package cn.hzstk.securities.sys.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import cn.hzstk.securities.sys.utils.json.Dict;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Table(name = "sys_schedule_job")
public class ScheduleJob extends BaseDomain {
    public static final String STATUS_PAUSE = "1";
    public static final String STATUS_DELETE = "0";
    public static final String STATUS_RESUME = "2";

    private static final long serialVersionUID = 1L;

    //任务id
    @Column(name = "job_id")
    private String jobId;
    //任务名称
    @Column(name = "job_name")
    private String jobName;
    //任务分组
    @Column(name = "job_group")
    private String jobGroup;
    //任务状态
    @Column(name = "job_status")
    @Dict(key = "job_sts")
    private String jobStatus;
    //cron表达式
    @Column(name = "cron_expression")
    private String cronExpression;
    //描述
    @Column(name = "description")
    private String description;
    //包名+类名
    @Column(name = "bean_class")
    private String beanClass;
    //任务是否有状态
    @Column(name = "is_concurrent")
    private String isConcurrent;
    //spring bean
    @Column(name = "spring_id")
    private String springId;
    //任务调用的方法名
    @Column(name = "method_name")
    private String methodName;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setJobId(String jobId){
        this.jobId = jobId;
    }

    public String getJobId(){
        return jobId;
    }

    public void setJobName(String jobName){
        this.jobName = jobName;
    }

    public String getJobName(){
        return jobName;
    }

    public void setJobGroup(String jobGroup){
        this.jobGroup = jobGroup;
    }

    public String getJobGroup(){
        return jobGroup;
    }

    public void setJobStatus(String jobStatus){
        this.jobStatus = jobStatus;
    }

    public String getJobStatus(){
        return jobStatus;
    }

    public void setCronExpression(String cronExpression){
        this.cronExpression = cronExpression;
    }

    public String getCronExpression(){
        return cronExpression;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setBeanClass(String beanClass){
        this.beanClass = beanClass;
    }

    public String getBeanClass(){
        return beanClass;
    }

    public void setIsConcurrent(String isConcurrent){
        this.isConcurrent = isConcurrent;
    }

    public String getIsConcurrent(){
        return isConcurrent;
    }

    public void setSpringId(String springId){
        this.springId = springId;
    }

    public String getSpringId(){
        return springId;
    }

    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    public String getMethodName(){
        return methodName;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
