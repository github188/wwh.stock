package com.bm.wanma.entity;


import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
/*消息中系统公告表 */

@Table(name = "tb_system_notice")
public class SystemNoticeBean implements Serializable{
	@Id(column="id")
	private int id;
    private String title;//名称
    private String content;//内容
    private long time;//时间
    
	//必须包含这个默认的构造方法，否则在进行数据查找时，会报错
	public SystemNoticeBean() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	

  
}
