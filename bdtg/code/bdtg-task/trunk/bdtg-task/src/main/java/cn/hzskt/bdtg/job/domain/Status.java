package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_status")
public class Status extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属任务
    @Column(name = "tid")
    private Long tid;

    //成员
    @Column(name = "uid")
    private Long uid;

    //内容
    @Column(name = "content")
    private String content;
    //用户名
    @Column(name="user_name")
    private String userName;
    //时间
    @Column(name = "status_time")
    private java.util.Date statusTime;

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }

    public void setUid(Long uid){
        this.uid = uid;
    }

    public Long getUid(){
        return uid;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
