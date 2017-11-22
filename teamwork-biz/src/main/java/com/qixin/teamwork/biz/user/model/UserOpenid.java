package com.qixin.teamwork.biz.user.model;

import java.sql.Timestamp;

/**
 * 用户openid表实体类
 * @author zyting
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V1.0.3.1
 */
public class UserOpenid {

	private Long id;
	private String openId;     //'微信openId'
	private String wxLogo;     //微信头像'
	private String wxNice;     //'微信昵称',
	private Timestamp createTime; //'创建时间'
	private Byte isValid;       //'是否有效'
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getWxLogo() {
		return wxLogo;
	}
	public void setWxLogo(String wxLogo) {
		this.wxLogo = wxLogo;
	}
	public String getWxNice() {
		return wxNice;
	}
	public void setWxNice(String wxNice) {
		this.wxNice = wxNice;
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
