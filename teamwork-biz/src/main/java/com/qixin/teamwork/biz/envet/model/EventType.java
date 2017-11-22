package com.qixin.teamwork.biz.envet.model;

import org.framework.utils.BaseModel;

/**
 * 事件类型实体
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午11:42:48
 * @version V0.0.1
 */
public class EventType extends BaseModel{

	private Long id;
	private String name;//名称
	private String logo;
	private Byte isVaild;//是否有效:0 否 1 是
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Byte getIsVaild() {
		return isVaild;
	}
	public void setIsVaild(Byte isVaild) {
		this.isVaild = isVaild;
	}
	
	
}
