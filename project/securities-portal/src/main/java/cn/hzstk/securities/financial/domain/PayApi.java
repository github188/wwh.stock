package cn.hzstk.securities.financial.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "financial_pay_api")
public class PayApi extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //支付方式
    @Column(name = "payment")
    private String payment;

    //支付类型
    @Column(name = "type")
    private String type;

    //支付配置
    @Column(name = "config")
    private String config;

    //是否开启
    @Column(name = "isopen")
    private String isopen;

    public void setPayment(String payment){
        this.payment = payment;
    }

    public String getPayment(){
        return payment;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setConfig(String config){
        this.config = config;
    }

    public String getConfig(){
        return config;
    }

    public void setIsopen(String isopen){
        this.isopen = isopen;
    }

    public String getIsopen(){
        return isopen;
    }
}
