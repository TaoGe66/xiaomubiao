package com.qixin.teamwork.biz.enterpriseMember.model;

import org.framework.utils.PaginationParameter;

import java.sql.Timestamp;

public class Member {

	private Long id;
	private Long leId;        //轻企id
	private Long userId;      //用户id
	private Byte lemState;    //状态 关注状态 0 拒绝 1 接受 2 等待审核
	private Long createBy;    //创建人
	private Timestamp createTime;
	private Byte roleType;    //角色类型: 0 创建人、1管理员 、2 普通用户
	private Byte isValid;     //
	private Long inviteId;    //邀约人
	private Timestamp updateTime;
	private  String userName;//用户名称
	private  String headUrl;//用户头像
	private  Byte state;//关注状态 0 拒绝 1 接受 2 等待审核
	private Integer is_enter;//是否进入  0 否  1是
	private Long eId;
	private String qrcodeUrl; //二维码地址
	private String leLogo;    //logo
	private Byte authState;//'轻企认证状态: 0 不通过 、1 通过  
	private String note;      //'个人说明'
	private String leName;//轻企简称
	private String roleTypeStr;    //角色类型: 0 创建人、1管理员 、2 普通用户
	private String lemStateStr;    //状态 关注状态 0 拒绝 1 接受 2 等待审核
	private Byte isJoinUser;//是否已是参与人  0 否 1是
	private PaginationParameter parameter;

	public PaginationParameter getParameter() {
		return parameter;
	}

	public void setParameter(PaginationParameter parameter) {
		this.parameter = parameter;
	}

	public Byte getIsJoinUser() {
		return isJoinUser;
	}

	public void setIsJoinUser(Byte isJoinUser) {
		this.isJoinUser = isJoinUser;
	}

	public String getLemStateStr() {
		return lemStateStr;
	}
	public void setLemStateStr(String lemStateStr) {
		this.lemStateStr = lemStateStr;
	}
	public String getRoleTypeStr() {
		return roleTypeStr;
	}
	public void setRoleTypeStr(String roleTypeStr) {
		this.roleTypeStr = roleTypeStr;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getLemState() {
		return lemState;
	}
	public void setLemState(Byte lemState) {
		this.lemState = lemState;
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
	public Byte getRoleType() {
		return roleType;
	}
	public void setRoleType(Byte roleType) {
		this.roleType = roleType;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public Long getInviteId() {
		return inviteId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}
	public Integer getIs_enter() {
		return is_enter;
	}
	public void setIs_enter(Integer is_enter) {
		this.is_enter = is_enter;
	}
	public String getLeLogo() {
		return leLogo;
	}
	public void setLeLogo(String leLogo) {
		this.leLogo = leLogo;
	}
	public Byte getAuthState() {
		return authState;
	}
	public void setAuthState(Byte authState) {
		this.authState = authState;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLeName() {
		return leName;
	}
	public void setLeName(String leName) {
		this.leName = leName;
	}
	
}
