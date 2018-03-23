package cn.hzskt.bdtg.task.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "task_contract")
public class Contract extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //任务记录主键
    @Column(name = "tk_id")
    private Long tkId;

    //支付金额
    @Column(name = "py_cash")
    private Double pyCash;

    //预计支付时间
    @Column(name = "py_time")
    private java.util.Date pyTime;
    
    //支付时间
    @Column(name = "cash_time")
    private java.util.Date cashTime;
    
    //确认支付时间
    @Column(name = "sub_time")
    private java.util.Date subTime;

    //业主分期描述
    @Column(name = "py_content")
    private String pyContent;

    //记录状态
    @Column(name = "py_status")
    private String pyStatus;
    
    //任务标题
    @Column(name = "task_title")
    private String taskTitle;

    public void setTkId(Long tkId){
        this.tkId = tkId;
    }

    public Long getTkId(){
        return tkId;
    }

    public void setPyCash(Double pyCash){
        this.pyCash = pyCash;
    }

    public Double getPyCash(){
        return pyCash;
    }

    public void setPyTime(java.util.Date pyTime){
        this.pyTime = pyTime;
    }

    public java.util.Date getPyTime(){
        return pyTime;
    }

    public void setPyContent(String pyContent){
        this.pyContent = pyContent;
    }

    public String getPyContent(){
        return pyContent;
    }

    public void setPyStatus(String pyStatus){
        this.pyStatus = pyStatus;
    }

    public String getPyStatus(){
        return pyStatus;
    }

	public java.util.Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(java.util.Date cashTime) {
		this.cashTime = cashTime;
	}

	public java.util.Date getSubTime() {
		return subTime;
	}

	public void setSubTime(java.util.Date subTime) {
		this.subTime = subTime;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

}
