package com.qixin.teamwork.biz.memo.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 备忘录实体类
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
public class Memo extends BaseModel{

	private Long   id ;
	private String   mName;// '名称',
	private Long   userId;// '用户id',
	private Long     createBy ;//
	private Timestamp   createTime;
	private Timestamp    updateTime;
	private Byte   isValid ;//'是否有效:0 否 1 是',
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	
	
}
