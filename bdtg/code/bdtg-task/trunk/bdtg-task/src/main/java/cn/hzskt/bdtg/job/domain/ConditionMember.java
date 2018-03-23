package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_condition_member")
public class ConditionMember extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //成员
    @Column(name = "jmb_id")
    private Long jmbId;

    //基础条件
    @Column(name = "condition_id")
    private Long conditionId;

    //所属任务
    @Column(name = "tid")
    private Long tid;

    public void setJmbId(Long jmbId){
        this.jmbId = jmbId;
    }

    public Long getJmbId(){
        return jmbId;
    }

    public void setConditionId(Long conditionId){
        this.conditionId = conditionId;
    }

    public Long getConditionId(){
        return conditionId;
    }

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }
}
