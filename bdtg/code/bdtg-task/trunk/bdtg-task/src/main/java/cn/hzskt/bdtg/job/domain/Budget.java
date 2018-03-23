package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_budget")
public class Budget extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属节点
    @Column(name = "process_id")
    private Long processId;

    //成员
    @Column(name = "uid")
    private Long uid;

    //预算费用
    @Column(name = "budget_fare")
    private Integer budgetFare;

    //实付费用
    @Column(name = "real_fare")
    private Integer realFare;

    public void setProcessId(Long processId){
        this.processId = processId;
    }

    public Long getProcessId(){
        return processId;
    }

    public void setUid(Long uid){
        this.uid = uid;
    }

    public Long getUid(){
        return uid;
    }

    public void setBudgetFare(Integer budgetFare){
        this.budgetFare = budgetFare;
    }

    public Integer getBudgetFare(){
        return budgetFare;
    }

    public void setRealFare(Integer realFare){
        this.realFare = realFare;
    }

    public Integer getRealFare(){
        return realFare;
    }
}
