package com.skoo.stock.sys.domain;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Sysdept")
public class Sysdept extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 父部门编号**/
	private Long upId;

	/**
	 * 上级菜单 *
	 */
	private Sysdept papa;
			
	/** 部门类型**/
	@DictAnnotation(key="dept_type")
	private String deptType;
			
	/** 部门编码**/
	private String deptCode;
			
	/** 部门名称**/
	private String deptName;
			
	/** 部门职能**/
	private String deptFunction;
			
	/** 部门描述**/
	private String deptDesc;
			
	/** 负责人**/
	private String manager;
			
	/** 联系电话**/
	private String phone;
			
	/** 备注**/
	private String memo;
			
	/** 顺序**/
	private Integer orderBy;
					
	public void setUpId(Long upId){
		this.upId = upId;
	} 
	
	public Long getUpId(){
		return upId;
	}

	public Sysdept getPapa() {
		return papa;
	}

	public void setPapa(Sysdept papa) {
		this.papa = papa;
	}

	public void setDeptType(String deptType){
		this.deptType = deptType;
	} 
	
	public String getDeptType(){
		return deptType;
	} 
			
	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	} 
	
	public String getDeptCode(){
		return deptCode;
	} 
			
	public void setDeptName(String deptName){
		this.deptName = deptName;
	} 
	
	public String getDeptName(){
		return deptName;
	} 
			
	public void setDeptFunction(String deptFunction){
		this.deptFunction = deptFunction;
	} 
	
	public String getDeptFunction(){
		return deptFunction;
	} 
			
	public void setDeptDesc(String deptDesc){
		this.deptDesc = deptDesc;
	} 
	
	public String getDeptDesc(){
		return deptDesc;
	} 
			
	public void setManager(String manager){
		this.manager = manager;
	} 
	
	public String getManager(){
		return manager;
	} 
			
	public void setPhone(String phone){
		this.phone = phone;
	} 
	
	public String getPhone(){
		return phone;
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
