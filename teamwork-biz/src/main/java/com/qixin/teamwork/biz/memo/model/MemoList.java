package com.qixin.teamwork.biz.memo.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 备忘录列表
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
public class MemoList extends BaseModel{

	  private Long id;
	  private String  mName;//'名称',
	  private Long  userId;//'用户id',
	  private Long createBy ;//           bigint comment '创建人',
	  private Timestamp  createTime ;  // '创建时间',
	  private Timestamp  updateTime; //更新时间',
	  private Byte  isValid;//'是否有效：0 否 1 是',
	  private Long  mId ;// '备忘id',
	  private String  content;// '内容
	  private String memoIdStr;//随手记id字符串
	  private String strTime;// 时间字符串
	
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
	public Long getmId() {
		return mId;
	}
	public void setmId(Long mId) {
		this.mId = mId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemoIdStr() {
		return memoIdStr;
	}
	public void setMemoIdStr(String memoIdStr) {
		this.memoIdStr = memoIdStr;
	}
	public String getStrTime() {
		return strTime;
	}
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	  
	  
}
