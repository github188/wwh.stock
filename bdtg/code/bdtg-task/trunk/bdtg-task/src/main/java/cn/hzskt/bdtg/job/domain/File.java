package cn.hzskt.bdtg.job.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "job_file")
public class File extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //文件名
    @Column(name = "name")
    private String name;

    //路径
    @Column(name = "path")
    private String path;
    //上传人姓名
    @Column(name = "upload_name")
    private String uploadName;
    //文件大小
    @Column(name = "fold_size")
    private Long foldSize;
    //所属文件夹
    @Column(name = "folder")
    private Long folder;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void setFolder(Long folder){
        this.folder = folder;
    }

    public Long getFolder(){
        return folder;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public Long getFoldSize() {
        return foldSize;
    }

    public void setFoldSize(Long foldSize) {
        this.foldSize = foldSize;
    }
}
