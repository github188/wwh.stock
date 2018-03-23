package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import cn.hzskt.bdtg.sys.utils.json.Dict;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_condition")
public class Condition extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属任务
    @Column(name = "tid")
    private Long tid;

    //专业
    @Column(name = "major")
    @Dict(key="major_type")
    private String major;

    //名称
    @Column(name = "name")
    private String name;

    //附件名称
    @Column(name = "file_name")
    private String fileName;

    //附件路径
    @Column(name = "file_path")
    private String filePath;

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getMajor(){
        return major;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return filePath;
    }
}
