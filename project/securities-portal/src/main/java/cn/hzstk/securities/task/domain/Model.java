package cn.hzstk.securities.task.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "tb_model")
public class Model extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //模型编号
    @Column(name = "model_id")
    private Integer modelId;

    //模型代码
    @Column(name = "model_code")
    private String modelCode;

    //模型中文名
    @Column(name = "model_name")
    private String modelName;

    //模型文件夹
    @Column(name = "model_dir")
    private String modelDir;

    //模型类型(task,shop)
    @Column(name = "model_type")
    private String modelType;

    //开发者
    @Column(name = "model_dev")
    private String modelDev;

    //模型开启状态
    @Column(name = "model_status")
    private Integer modelStatus;

    //模型描述
    @Column(name = "model_desc")
    private String modelDesc;

    //安装时间
    @Column(name = "on_time")
    private Integer onTime;

    //是否私有模式（0=>非私有，1=>私有）
    @Column(name = "hide_mode")
    private Integer hideMode;

    //模型排序
    @Column(name = "listorder")
    private Integer listorder;

    //模型介绍
    @Column(name = "model_intro")
    private String modelIntro;

    //绑定行业
    @Column(name = "indus_bid")
    private String indusBid;

    //模型扩展配置
    @Column(name = "config")
    private String config;

    //是否开启自定义字段
    @Column(name = "open_custom")
    private Integer openCustom;

    public void setModelId(Integer modelId){
        this.modelId = modelId;
    }

    public Integer getModelId(){
        return modelId;
    }

    public void setModelCode(String modelCode){
        this.modelCode = modelCode;
    }

    public String getModelCode(){
        return modelCode;
    }

    public void setModelName(String modelName){
        this.modelName = modelName;
    }

    public String getModelName(){
        return modelName;
    }

    public void setModelDir(String modelDir){
        this.modelDir = modelDir;
    }

    public String getModelDir(){
        return modelDir;
    }

    public void setModelType(String modelType){
        this.modelType = modelType;
    }

    public String getModelType(){
        return modelType;
    }

    public void setModelDev(String modelDev){
        this.modelDev = modelDev;
    }

    public String getModelDev(){
        return modelDev;
    }

    public void setModelStatus(Integer modelStatus){
        this.modelStatus = modelStatus;
    }

    public Integer getModelStatus(){
        return modelStatus;
    }

    public void setModelDesc(String modelDesc){
        this.modelDesc = modelDesc;
    }

    public String getModelDesc(){
        return modelDesc;
    }

    public void setOnTime(Integer onTime){
        this.onTime = onTime;
    }

    public Integer getOnTime(){
        return onTime;
    }

    public void setHideMode(Integer hideMode){
        this.hideMode = hideMode;
    }

    public Integer getHideMode(){
        return hideMode;
    }

    public void setListorder(Integer listorder){
        this.listorder = listorder;
    }

    public Integer getListorder(){
        return listorder;
    }

    public void setModelIntro(String modelIntro){
        this.modelIntro = modelIntro;
    }

    public String getModelIntro(){
        return modelIntro;
    }

    public void setIndusBid(String indusBid){
        this.indusBid = indusBid;
    }

    public String getIndusBid(){
        return indusBid;
    }

    public void setConfig(String config){
        this.config = config;
    }

    public String getConfig(){
        return config;
    }

    public void setOpenCustom(Integer openCustom){
        this.openCustom = openCustom;
    }

    public Integer getOpenCustom(){
        return openCustom;
    }
}
