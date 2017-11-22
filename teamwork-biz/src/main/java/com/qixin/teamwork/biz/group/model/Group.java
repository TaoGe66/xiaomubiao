package com.qixin.teamwork.biz.group.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 好友组 实体
 * @author wuchao
 * @date 2017年6月14日
 * @time 上午9:33:51
 * @version V0.0.1
 */
public class Group extends BaseModel {
	
	private Long id;
	private String name;//名称
	private Long createBy;//创建人
	private Timestamp createTime;//创建时间
	private Byte isValid;//是否有效:0 否 1 是
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
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	
	
}
