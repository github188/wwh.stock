package cn.hzskt.sys.domain;

import org.apache.ibatis.type.Alias;

import com.zjhcsoft.smartcity.magic.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Dict")
public class Dict extends BaseEntity {

	private static final long serialVersionUID = 1L;
	

	/** 数据键 **/
	private String keyName;

	/** 数据值 **/
	
	private String value;

	/** 内容 **/
	private String content;

	/** 排序 **/
	private Integer orderBy;

	/** 备注 **/
	private String memo;

	/**
	 * 数据键
	 * 
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * 数据键
	 * 
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * 数据值
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 数据值
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 内容
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 内容
	 * 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 排序
	 * 
	 */
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 排序
	 * 
	 */
	public Integer getOrderBy() {
		return orderBy;
	}

	/**
	 * 备注
	 * 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 备注
	 * 
	 */
	public String getMemo() {
		return memo;
	}

}
