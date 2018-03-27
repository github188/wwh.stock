package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("PlateStock")
public class PlateStock extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 板块代号**/
	private String conceptId;
			
	/** 股票代码**/
	private String code;
			
	/** 网站标志**/
	private String netFlag;
			
	/** 板块类别**/
	private String industryType;
			
	/** 备注**/
	private String memo;
			
	/** 顺序**/
	private Integer orderBy;

	public void setConceptId(String conceptId){
		this.conceptId = conceptId;
	} 
	
	public String getConceptId(){
		return conceptId;
	} 
			
	public void setCode(String code){
		this.code = code;
	} 
	
	public String getCode(){
		return code;
	} 
			
	public void setNetFlag(String netFlag){
		this.netFlag = netFlag;
	} 
	
	public String getNetFlag(){
		return netFlag;
	} 
			
	public void setIndustryType(String industryType){
		this.industryType = industryType;
	} 
	
	public String getIndustryType(){
		return industryType;
	} 
			
	public void setMemo(String memo){
		this.memo = memo;
	} 
	
	public String getMemo(){
		return memo;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
