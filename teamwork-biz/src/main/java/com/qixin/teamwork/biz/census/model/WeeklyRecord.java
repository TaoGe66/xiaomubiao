package com.qixin.teamwork.biz.census.model;

import java.sql.Timestamp;

/**
 * 周报清单记录
 *@author wuchao
 *@data 2017年10月30日
 *@version V0.0.5
 */
public class WeeklyRecord {

	private Long  id;//'主键',
	  private Long  leId ;//轻企id',
	  private Long  eId;// '项目id',
	  private Long  eiId;//'行动id',
	  private Long  userId;//'用户id',
	  private Long  createBy;//创建人',
	  private Long  trackRecordId; //'行动跟踪id',
	  private Timestamp createTime;//'创建时间',
	  private Timestamp  updateTime;//'更新时间',
	  private Byte isRecord;//'是否标记为周报记录：0 否 1 是 '
	  private Byte type;//类型 ： 0 项目  1 行动  2 跟踪
	  private Byte isWeeklyPsuh;//'是否发布周报:0 否 1是',
	  private Timestamp  weeklyDate;//'周报时间'
	  private Long strTime;//long类型时间
	  private Byte  isValid ;// '是否有效: 0 否 1 是',
	  private String summary;   //总结内容
	  private String eName;//项目名称
	  private String eiDesc;//描述
	  private  String trContent;//跟踪内容
	  private Timestamp startTime;//'开始时间'
	  private Timestamp mondayTime;//周一开始时间
	  
	  private String allBill;//所有清单
	  private String bill;//选中清单
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
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getTrackRecordId() {
		return trackRecordId;
	}
	public void setTrackRecordId(Long trackRecordId) {
		this.trackRecordId = trackRecordId;
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
	public Byte getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(Byte isRecord) {
		this.isRecord = isRecord;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getIsWeeklyPsuh() {
		return isWeeklyPsuh;
	}
	public void setIsWeeklyPsuh(Byte isWeeklyPsuh) {
		this.isWeeklyPsuh = isWeeklyPsuh;
	}
	public Timestamp getWeeklyDate() {
		return weeklyDate;
	}
	public void setWeeklyDate(Timestamp weeklyDate) {
		this.weeklyDate = weeklyDate;
	}
	public Long getStrTime() {
		return strTime;
	}
	public void setStrTime(Long strTime) {
		this.strTime = strTime;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getEiDesc() {
		return eiDesc;
	}
	public void setEiDesc(String eiDesc) {
		this.eiDesc = eiDesc;
	}
	public String getTrContent() {
		return trContent;
	}
	public void setTrContent(String trContent) {
		this.trContent = trContent;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getMondayTime() {
		return mondayTime;
	}
	public void setMondayTime(Timestamp mondayTime) {
		this.mondayTime = mondayTime;
	}
	public String getAllBill() {
		return allBill;
	}
	public void setAllBill(String allBill) {
		this.allBill = allBill;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	  
}
