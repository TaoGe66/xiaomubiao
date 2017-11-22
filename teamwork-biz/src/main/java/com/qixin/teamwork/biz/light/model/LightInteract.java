package com.qixin.teamwork.biz.light.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 动态互动
 * @author wuchao
 * @date 2017年9月7日
 * @time 下午2:14:41
 * @version V0.0.3
 */
public class LightInteract extends BaseModel{
	
	 private Long id;// '主键',
	 private Long  leId;//'轻企id',
	 private Long  eId;// '项目id',
	 private Long  eiId;//'行动id',
	 private Long  userId;// '用户id',
	 private Timestamp  createTime;// '创建时间',
	 private Byte  isValid;//'是否有效:0 否 1是',
	 private Timestamp updateTime;//'更新时间',
	 private Long  ledId;//'动态id',
	 private String judge;//内容
	 private Byte type;//'类型:0 评论  、1 点赞',
	 private Long  createBy;// '创建人',
	 
	 private String content;//动态内容
	 private String userName;   //'用户名称'
	 private Byte isRead;
	 private String headUrl;    //'头像'
	public Byte getIsRead() {
		return isRead;
	}
	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
	}
	public Long getEiId() {
		return eiId;
	}
	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Long getLedId() {
		return ledId;
	}
	public void setLedId(Long ledId) {
		this.ledId = ledId;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	 
}
