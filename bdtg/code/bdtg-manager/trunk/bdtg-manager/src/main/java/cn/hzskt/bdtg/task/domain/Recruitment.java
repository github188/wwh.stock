package cn.hzskt.bdtg.task.domain;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzskt.bdtg.common.domain.BaseDomain;
import cn.hzskt.bdtg.sys.utils.json.Dict;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "task_recruitment")
public class Recruitment extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //职位名称
    @Column(name = "zwmc")
    private String zwmc;

    //应届生限制
    @Column(name = "yjsxz")
    private String yjsxz;

    //性别
    @Column(name = "sex")
    private String sex;

    //招聘人数
    @Column(name = "zprs")
    private Integer zprs;

    //职位标签
    @Column(name = "zwbq")
    private String zwbq;

    //学历要求
    @Column(name = "xlyq")
    private String xlyq;

    //工资范围
    @Column(name = "gzfw")
    private String gzfw;

    //工作经验
    @Column(name = "gzjy")
    private String gzjy;

    //职位类别
    @Column(name = "zwlb")
    private String zwlb;

    //省份
    @Column(name = "province")
    private String province;

    //城市
    @Column(name = "city")
    private String city;

    //专业要求
    @Column(name = "zyyq")
    private String zyyq;

    //岗位描述
    @Column(name = "gwms")
    private String gwms;

    //项目概述
    @Column(name = "xmgs")
    private String xmgs;

    //联系人
    @Column(name = "lxr")
    private String lxr;

    //联系电话
    @Column(name = "lxdh")
    private String lxdh;

    //邮箱
    @Column(name = "email")
    private String email;

    //
    @Column(name = "qq")
    private String qq;

    //联系地址
    @Column(name = "address")
    private String address;

    //截止日期
    @Column(name = "subtime")
    private java.util.Date subtime;

    //项目周期
    @Column(name = "endtime")
    private java.util.Date endtime;

    //发布费用选择类型 0、不支付 1、支付
    @Column(name = "paystatus")
    private String paystatus;

    //支付类型
    @Column(name = "paytype")
    private String paytype;

    //支付发布费用金额
    @Column(name = "paycash")
    private Integer paycash;

    //用户类型 1、业主 2、设计院
    @Column(name = "usrType")
    private String usrtype;

    //状态
    @Dict(key = "zmrc_status")
    @Column(name = "status")
    private String status;

    public void setZwmc(String zwmc){
        this.zwmc = zwmc;
    }

    public String getZwmc(){
        return zwmc;
    }

    public void setYjsxz(String yjsxz){
        this.yjsxz = yjsxz;
    }

    public String getYjsxz(){
        return yjsxz;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getSex(){
        return sex;
    }

    public void setZprs(Integer zprs){
        this.zprs = zprs;
    }

    public Integer getZprs(){
        return zprs;
    }

    public void setZwbq(String zwbq){
        this.zwbq = zwbq;
    }

    public String getZwbq(){
        return zwbq;
    }

    public void setXlyq(String xlyq){
        this.xlyq = xlyq;
    }

    public String getXlyq(){
        return xlyq;
    }

    public void setGzfw(String gzfw){
        this.gzfw = gzfw;
    }

    public String getGzfw(){
        return gzfw;
    }

    public void setGzjy(String gzjy){
        this.gzjy = gzjy;
    }

    public String getGzjy(){
        return gzjy;
    }

    public void setZwlb(String zwlb){
        this.zwlb = zwlb;
    }

    public String getZwlb(){
        return zwlb;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return province;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setZyyq(String zyyq){
        this.zyyq = zyyq;
    }

    public String getZyyq(){
        return zyyq;
    }

    public void setGwms(String gwms){
        this.gwms = gwms;
    }

    public String getGwms(){
        return gwms;
    }

    public void setXmgs(String xmgs){
        this.xmgs = xmgs;
    }

    public String getXmgs(){
        return xmgs;
    }

    public void setLxr(String lxr){
        this.lxr = lxr;
    }

    public String getLxr(){
        return lxr;
    }

    public void setLxdh(String lxdh){
        this.lxdh = lxdh;
    }

    public String getLxdh(){
        return lxdh;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setQq(String qq){
        this.qq = qq;
    }

    public String getQq(){
        return qq;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setSubtime(java.util.Date subtime){
        this.subtime = subtime;
    }

    public java.util.Date getSubtime(){
        return subtime;
    }

    public void setEndtime(java.util.Date endtime){
        this.endtime = endtime;
    }

    public java.util.Date getEndtime(){
        return endtime;
    }

    public void setPaystatus(String paystatus){
        this.paystatus = paystatus;
    }

    public String getPaystatus(){
        return paystatus;
    }

    public void setPaytype(String paytype){
        this.paytype = paytype;
    }

    public String getPaytype(){
        return paytype;
    }

    public void setPaycash(Integer paycash){
        this.paycash = paycash;
    }

    public Integer getPaycash(){
        return paycash;
    }

    public void setUsrtype(String usrtype){
        this.usrtype = usrtype;
    }

    public String getUsrtype(){
        return usrtype;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
    
    public String getSubtimeStr(){
    	if(this.subtime!=null){
    		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(subtime);
    	}
    	return "";
    }
}
