package com.qixin.teamwork.biz.envet.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 事项周期模板表
 * @author wuchao
 * @date 2017年8月9日
 * @time 上午9:35:33
 * @version V0.0.1
 */

public class CycleTemplet extends BaseModel {

	private Long   id;
	private Byte   cycleState;//'周期状态: 0 激活  1 冻结',
	private Byte   cycleFreq;//'周期频率:0 天、1 周',
	private String cycle;// '重复周期:MO(周一),TU(周二),WE(周三),TH(周四),FR(周五),SA(周六),SU(周日)',
	private Byte   isValid;// '是否有效: 0  否 1 是',
	private Timestamp   updateTime;//'更新时间',
	private Timestamp   createTime ;//'创建时间',
	private Long   createBy;//'创建人',
	private Timestamp  nextStartTime;//'下一周期开始时间'
	private Long satrtLength;//开始时长
	private Long untilLength;//截止时长
	private String  cycleContent;// '周期内容',
	private Long  dutyId;// '责任人'
	private Byte isFinish;
	
	public Byte getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Byte isFinish) {
		this.isFinish = isFinish;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getCycleState() {
		return cycleState;
	}
	public void setCycleState(Byte cycleState) {
		this.cycleState = cycleState;
	}
	public Byte getCycleFreq() {
		return cycleFreq;
	}
	public void setCycleFreq(Byte cycleFreq) {
		this.cycleFreq = cycleFreq;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
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
	public Timestamp getNextStartTime() {
		return nextStartTime;
	}
	public void setNextStartTime(Timestamp nextStartTime) {
		this.nextStartTime = nextStartTime;
	}
	public Long getSatrtLength() {
		return satrtLength;
	}
	public void setSatrtLength(Long satrtLength) {
		this.satrtLength = satrtLength;
	}
	public Long getUntilLength() {
		return untilLength;
	}
	public void setUntilLength(Long untilLength) {
		this.untilLength = untilLength;
	}
	public String getCycleContent() {
		return cycleContent;
	}
	public void setCycleContent(String cycleContent) {
		this.cycleContent = cycleContent;
	}
	public Long getDutyId() {
		return dutyId;
	}
	public void setDutyId(Long dutyId) {
		this.dutyId = dutyId;
	}

	
	
}
