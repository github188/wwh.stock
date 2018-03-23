package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_duty")
public class Duty extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //
    @Column(name = "member_id")
    private Integer memberId;

    //
    @Column(name = "tid")
    private Long tid;

    //
    @Column(name = "item")
    private Long item;

    //
    @Column(name = "major")
    private String major;

    //
    @Column(name = "duty")
    private Integer duty;

    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }

    public Integer getMemberId(){
        return memberId;
    }

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }

    public void setItem(Long item){
        this.item = item;
    }

    public Long getItem(){
        return item;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getMajor(){
        return major;
    }

    public void setDuty(Integer duty){
        this.duty = duty;
    }

    public Integer getDuty(){
        return duty;
    }
}
