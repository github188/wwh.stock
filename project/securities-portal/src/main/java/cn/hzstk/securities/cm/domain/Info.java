package cn.hzstk.securities.cm.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
public class Info extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //分类
    private Long category;

    //外站内容地址
    private String url;

    //
    private String fileName;

    //下载次数
    private Integer uploadSize;

    public void setCategory(Long category){
        this.category = category;
    }

    public Long getCategory(){
        return category;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    public void setUploadSize(Integer uploadSize){
        this.uploadSize = uploadSize;
    }

    public Integer getUploadSize(){
        return uploadSize;
    }
}
