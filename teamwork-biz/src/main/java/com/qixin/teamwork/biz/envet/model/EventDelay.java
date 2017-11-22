package com.qixin.teamwork.biz.envet.model;

import java.sql.Timestamp;

/**
 * 延期记录表
 *@author wuchao
 *@data 2017年10月27日
 *@version V0.0.5
 */
public class EventDelay {

	  private Long id;// '主键',
	  private Long eId ;//'事件id',
	  private Long  userId;//'用户id',
	  private Long  createBy;// '创建人',
	  private Timestamp createTime; // '创建时间',
	  private Byte isValid; //'是否有效:0 否 1 是',
	  private String delayReason;// '延期原因',
	  private Long delayTime;//'延期时长',
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
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
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}
	public Long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	  
}
