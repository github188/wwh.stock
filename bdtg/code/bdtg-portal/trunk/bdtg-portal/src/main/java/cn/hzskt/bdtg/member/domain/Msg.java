package cn.hzskt.bdtg.member.domain;

import cn.hzskt.bdtg.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "member_msg")
public class Msg extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //短信父编号
    @Column(name = "pid")
    private Integer pid;

    //用户编号
    @Column(name = "uid")
    private Long uid;
    //用户编号
    @Column(name = "tid")
    private Long tid;
    //用户名
    @Column(name = "username")
    private String username;

    //收件人编号
    @Column(name = "to_uid")
    private Long toUid;

    //收件人名
    @Column(name = "to_username")
    private String toUsername;

    //删除状态(1=>发送方删，2=>接收方删，3=>在状态为2时发放删除)
    @Column(name = "msg_status")
    private Integer msgStatus;

    //查看状态
    @Column(name = "view_status")
    private Integer viewStatus;

    //短信标题
    @Column(name = "title")
    private String title;

    //短信内容
    @Column(name = "content")
    private String content;

    //发布时间
    @Column(name = "on_time")
    private Integer onTime;

    //(0:默认值；1:业主消息；2:团队消息；3:财务消息)
    @Column(name = "job_type")
    private Integer jobType;

    //消息类型（1通知2动态 3在线作业）
    @Column(name = "type")
    private Integer type;

    public void setPid(Integer pid){
        this.pid = pid;
    }

    public Integer getPid(){
        return pid;
    }

    public void setUid(Long uid){
        this.uid = uid;
    }

    public Long getUid(){
        return uid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setToUid(Long toUid){
        this.toUid = toUid;
    }

    public Long getToUid(){
        return toUid;
    }

    public void setToUsername(String toUsername){
        this.toUsername = toUsername;
    }

    public String getToUsername(){
        return toUsername;
    }

    public void setMsgStatus(Integer msgStatus){
        this.msgStatus = msgStatus;
    }

    public Integer getMsgStatus(){
        return msgStatus;
    }

    public void setViewStatus(Integer viewStatus){
        this.viewStatus = viewStatus;
    }

    public Integer getViewStatus(){
        return viewStatus;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setOnTime(Integer onTime){
        this.onTime = onTime;
    }

    public Integer getOnTime(){
        return onTime;
    }

    public void setJobType(Integer jobType){
        this.jobType = jobType;
    }

    public Integer getJobType(){
        return jobType;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }
}
