package com.qixin.teamwork.biz.notice.model;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;
import org.omg.CORBA.TIMEOUT;

import java.sql.Timestamp;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/6 16:07
 * version:V0.0.1
 */
public class Notice extends BaseModel {
    private  Long id;
    private  String title;//标题
    private  String content;//内容
    private  Long leId; //轻企id
    private  Byte isUrgent;//是否加急：0 否 1 是
    private  Timestamp createTime;//创建时间
    private  Byte isValid;//是否有效  0 否 1 是
    private  Timestamp updateTime;//更新时间
    private  PaginationParameter paper;
    private  Long createBy;
    private  String  pushTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public Byte getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(Byte isUrgent) {
		this.isUrgent = isUrgent;
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
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	
  
}
