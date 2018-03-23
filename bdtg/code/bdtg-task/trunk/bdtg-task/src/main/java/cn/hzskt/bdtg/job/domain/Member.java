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
@Table(name = "job_member")
public class Member extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户ID
    @Column(name = "user_id")
    private Long userId;
    //用户名
    @Column(name = "user_name")
    private String userName;

    //类型
    @Column(name = "type")
    private Integer type;

    //项目ID
    @Column(name = "tid")
    private Long tid;
    //专业
    @Column(name = "major")
    private Long major;

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

    public Long getMajor() {
        return major;
    }

    public void setMajor(Long major) {
        this.major = major;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
