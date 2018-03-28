package cn.hzskt.hsa.domain;
import com.zjhcsoft.smartcity.magic.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class PlateStock extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 板块代号**/
	private String conceptId;
			
	/** 股票代码**/
	private String code;
			
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
