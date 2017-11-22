package com.qixin.teamwork.biz.user.model;

import java.sql.Timestamp;

/**
 * 验证码实体类
 * @author zyting
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V1.0.3.1
 */	
public class Validated {
	private Long id;
	private String tel;       //'注册手机号',
	private String validated; //'验证码'
	private Long createBy;      //'创建人'
	private Timestamp createTime; //'创建时间'
	private Long updateBy;        //'更新人'
	private Timestamp updateTime; //''更新时间''
	private Byte isValid;       //'是否有效'
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
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
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
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
