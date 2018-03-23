package cn.hzskt.bdtg.job.domain;
import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzskt.bdtg.common.domain.BaseDomain;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_process")
public class Process extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属任务
    @Column(name = "tid")
    private Long tid;

    //类型(数据字典process_type:1,作业节点，2：业主付款节点，3：PM付款节点)
    @Column(name = "type")
    private String type;

    //内容
    @Column(name = "content")
    private String content;

    //时间
    @Column(name = "status_time")
    private java.util.Date statusTime;

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setStatusTime(java.util.Date statusTime){
        this.statusTime = statusTime;
    }

    public java.util.Date getStatusTime(){
        return statusTime;
    }
}
