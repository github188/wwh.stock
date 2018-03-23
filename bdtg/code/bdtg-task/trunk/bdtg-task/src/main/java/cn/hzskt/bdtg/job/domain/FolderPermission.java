package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_folder_permission")
public class FolderPermission extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //会员
    @Column(name = "uid")
    private Long uid;

    //文件夹
    @Column(name = "folder")
    private Long folder;

    //权限
    @Column(name = "permission")
    private String permission;
    
    //任务id
    @Column(name = "tid")
    private Long tid;
    
    public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public void setUid(Long uid){
        this.uid = uid;
    }

    public Long getUid(){
        return uid;
    }

    public void setFolder(Long folder){
        this.folder = folder;
    }

    public Long getFolder(){
        return folder;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
