package com.qixin.teamwork.biz.enterprise.model;

import java.sql.Timestamp;

/**
 * 轻企
 * @author zyting
 * @date 2017年9月7日
 * @time 下午2:14:41
 * @version V0.0.3
 */
public class Enterprise {
	
	private Long leId;      
	private String leLogo;    //logo
	private String name;      //简称
	private String fullName;  //全称
	private String leDetails; //详情
	private Long createBy;    //创建人
	private Timestamp createTime;
	private Timestamp updateTime;
	private Byte isValid;
	private Long userId;
	private Byte roleType;  //角色类型  0 创建人、1管理员 、2 普通用户
	private Timestamp term; //有效期
	private Byte type;      //认证类型
	private String userName;
	private int memberCount;  //人员数量
	private Byte isTerm;   //是否去年审
	private String usercName;
	
	
	public String getUsercName() {
		return usercName;
	}
	public void setUsercName(String usercName) {
		this.usercName = usercName;
	}
	public Byte getIsTerm() {
		return isTerm;
	}
	public void setIsTerm(Byte isTerm) {
		this.isTerm = isTerm;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Timestamp getTerm() {
		return term;
	}
	public void setTerm(Timestamp term) {
		this.term = term;
	}
	public Byte getRoleType() {
		return roleType;
	}
	public void setRoleType(Byte roleType) {
		this.roleType = roleType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public String getLeLogo() {
		return leLogo;
	}
	public void setLeLogo(String leLogo) {
		this.leLogo = leLogo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLeDetails() {
		return leDetails;
	}
	public void setLeDetails(String leDetails) {
		this.leDetails = leDetails;
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
