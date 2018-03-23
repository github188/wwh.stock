package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_member")
public class Member extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户ID
    @Column(name = "user_id")
    private Integer userId;

    //用户名
    @Column(name = "user_name")
    private String userName;

    //类型（1:服务商；2:业主；3:制造商；)
    @Column(name = "type")
    private Integer type;

    //项目ID
    @Column(name = "tid")
    private Integer tid;

    //
    @Column(name = "major")
    private String major;

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public void setTid(Integer tid){
        this.tid = tid;
    }

    public Integer getTid(){
        return tid;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getMajor(){
        return major;
    }
}
