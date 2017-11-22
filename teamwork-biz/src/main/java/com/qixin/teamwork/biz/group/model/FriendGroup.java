package com.qixin.teamwork.biz.group.model;

import java.sql.Timestamp;

import com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.framework.utils.PaginationParameter;


/**
 * 组实体类
 * @author zyting
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V1.0
 */
public class FriendGroup {

	private Long fgId;    
	private Long fId;     //'好友id'
	private Long gId;     //'组id'
	private Byte isValid; //'是否有效:0 否 1 是',
	private Timestamp createTime; //'创建时间'
	private Long createBy;      //'创建人'
	private Long userId;       //'用户Id',
	private Byte state;       // '状态:0 不同意、1 同意  、2申请中   
	private Timestamp updateTime; //''更新时间''
	
	private String name;      //'名称'
	private String headUrl;    //'头像'
	private String userName;   //'用户名称'
	private String note;     //个人说明
	private PaginationParameter paper;
	private Byte isAgree;//是否同意 0 否 1 是
	private  Byte resState;//应答状态:0 等待接收、1 已接收
	private String friendType;
	private String states;//申请状态,用于查询 by xiehuilin 2017/06/27
	private int count;
	private Byte isJoinUser;//是否已是参与人  0 否 1是

	public Byte getIsJoinUser() {
		return isJoinUser;
	}

	public void setIsJoinUser(Byte isJoinUser) {
		this.isJoinUser = isJoinUser;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFriendType() {
		return friendType;
	}
	public void setFriendType(String friendType) {
		this.friendType = friendType;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFgId() {
		return fgId;
	}
	public void setFgId(Long fgId) {
		this.fgId = fgId;
	}
	public Long getfId() {
		return fId;
	}
	public void setfId(Long fId) {
		this.fId = fId;
	}
	public Long getgId() {
		return gId;
	}
	public void setgId(Long gId) {
		this.gId = gId;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Byte isAgree) {
		this.isAgree = isAgree;
	}

	public Byte getResState() {
		return resState;
	}

	public void setResState(Byte resState) {
		this.resState = resState;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	
	
}
