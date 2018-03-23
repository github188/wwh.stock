package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_folder")
public class Folder extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //所属任务
    @Column(name = "tid")
    private Long tid;

    //编码(uuid)
    @Column(name = "code")
    private String code;

    //名称
    @Column(name = "name")
    private String name;

    //父目录
    @Column(name = "pcode")
    private String pcode;

    //类型
    @Column(name = "type")
    private Long type;

    public void setTid(Long tid){
        this.tid = tid;
    }

    public Long getTid(){
        return tid;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPcode(String pcode){
        this.pcode = pcode;
    }

    public String getPcode(){
        return pcode;
    }

    public void setType(Long type){
        this.type = type;
    }

    public Long getType(){
        return type;
    }
}
