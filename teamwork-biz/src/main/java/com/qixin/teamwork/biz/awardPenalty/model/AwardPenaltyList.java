package com.qixin.teamwork.biz.awardPenalty.model;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** 奖惩名单
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 14:00
 * version:V0.0.1
 */
public class AwardPenaltyList extends BaseModel {
     private  Long id   ;// '主键',
     private  Long   createBy;// '创建人',
     private Timestamp createTime;//'创建时间',
     private  Byte   isValid;//'是否有效: 0 否 1 是',
     private  String rplReason;//'奖惩原因',
     private BigDecimal rplMoney;//'奖惩金额',
     private Timestamp taskTime;//'进度时间',
     private  Long   eId;//'事件id',
     private  Long eiId;//'事项id',
     private  Long   userId;//'用户id',
     private  Long  aprId;//'奖惩规则id'
	 private  Byte rpType;//奖惩类型 0 罚 1 奖
	private  String userName;//用户名称
	private  String cTime;//创建时间
	private  String tTime;//进度时间
	private  Timestamp updateTime;//更新时间
	private  Byte isFinish;//是否已执行 0 否 1是
	private  Byte rpCategory;// 奖罚类别:0 滞后完成 罚 、1最低分罚、2最高分奖
	private PaginationParameter paper;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getRplReason() {
		return rplReason;
	}
	public void setRplReason(String rplReason) {
		this.rplReason = rplReason;
	}
	public BigDecimal getRplMoney() {
		return rplMoney;
	}
	public void setRplMoney(BigDecimal rplMoney) {
		this.rplMoney = rplMoney;
	}
	public Timestamp getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Timestamp taskTime) {
		this.taskTime = taskTime;
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
	public Long getAprId() {
		return aprId;
	}
	public void setAprId(Long aprId) {
		this.aprId = aprId;
	}

	public Byte getRpType() {
		return rpType;
	}

	public void setRpType(Byte rpType) {
		this.rpType = rpType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PaginationParameter getPaper() {
		return paper;
	}

	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String gettTime() {
		return tTime;
	}

	public void settTime(String tTime) {
		this.tTime = tTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Byte isFinish) {
		this.isFinish = isFinish;
	}

	public Byte getRpCategory() {
		return rpCategory;
	}

	public void setRpCategory(Byte rpCategory) {
		this.rpCategory = rpCategory;
	}
}
