package com.qixin.teamwork.biz.light.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 轻企业认证实体
 * @author wuchao
 * @date 2017年9月6日
 * @time 上午10:11:28
 * @version V0.0.3
 */
public class LightAuth extends BaseModel{
	
	 private Long  id;
	 private Long    leId;//'轻企id',
	 private String  phone; //'电话号',
	 private String  identity; //身份证号
	 private String  identityLogoFront;//身份证照片正面
	 private String  identityLogoBack;//身份证照片背面
	 private BigDecimal  money;//认证金额
	 private Byte state;//'认证状态: 0 不通过 、1 通过  、2 等待审核
	 private Timestamp  create_time ;
	 private Long createBy;//创建人
	 private Timestamp updateTime;//更新时间
	 private Byte  isValid ;// '是否有效:0 否 1 是',
	 private Byte  type;// '认证类型： 0 认证 、1 年审',
	 private Byte isPay;//是否支付： 0未支付  1已支付
	 private Timestamp term;//有效截止时间
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIdentityLogoFront() {
		return identityLogoFront;
	}
	public void setIdentityLogoFront(String identityLogoFront) {
		this.identityLogoFront = identityLogoFront;
	}
	public String getIdentityLogoBack() {
		return identityLogoBack;
	}
	public void setIdentityLogoBack(String identityLogoBack) {
		this.identityLogoBack = identityLogoBack;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
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
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getIsPay() {
		return isPay;
	}
	public void setIsPay(Byte isPay) {
		this.isPay = isPay;
	}
	public Timestamp getTerm() {
		return term;
	}
	public void setTerm(Timestamp term) {
		this.term = term;
	}
	
	 
	 
}
