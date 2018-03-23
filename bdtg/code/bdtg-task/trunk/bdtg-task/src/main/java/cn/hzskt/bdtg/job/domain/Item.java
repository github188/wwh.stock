package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import cn.hzskt.bdtg.sys.utils.json.Dict;
import org.springframework.core.annotation.Order;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_item")
public class Item extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属任务
    @Column(name = "task")
    private Long task;

    //名称
    @Column(name = "name")
    private String name;

    //专业(多个专业用逗号分隔)
    @Column(name = "major")
    private String major;

    public void setTask(Long task){
        this.task = task;
    }

    public Long getTask(){
        return task;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getMajor(){
        return major;
    }
}
