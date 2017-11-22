package com.qixin.teamwork.biz.todo.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

/**
 * 待办任务实体
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:39:33
 * @version V0.0.1
 */
public class Todo extends BaseModel {

	private Long id;
	private Long userId;//用户id
	private Long eiId;//事项id
	private Long eId;//事件id
	private Byte tState;//事件状态
	private String tName;//环节名称
	private String tExplained;//环节说明
	private String tNote;//备注
	private Timestamp createTime;//创建时间
	private Byte isValid;//是否有效
	private Byte isBlock;//是否屏蔽
	private Byte isSend;//是否发送
	private Timestamp remindTime;//提醒时间
	private Long createBy;//创建人
	private String isnow;
	private Byte isAccept;//'是否接受：0 否 1 是',
	private Byte tSubjType;//主体类型:0 事件  1 事项'
	private Byte isInvalid;//是否失效:0  否  1 是'
	
	private Timestamp finishTime;//完成时间
	private Timestamp startTime;//开始时间
    private Timestamp endTime;//结束时间
    private String openId;     //'微信openId'
	private PaginationParameter paper;
	private String name;   //事件名称
	private Byte type;     //事件类型:0 时间管理 、1 服务请求、2 组织活动',
	private String province;//省
	private String city;//市
	private String district;//区
	private String address;//详细地址
	private String eiDesc;  //事项描述
	private String eiStartTime;//事项开始时间
	private double percent;//完成度
	private String summary; //总结内容
	private String logo;
	private Long cuserId;  //事件创建人Id
	private String target; //事件目标
	private String finishTimeStr;//完成时间    
	private Byte roleType;   
	private Byte isFinish;//是否完成 0 否 1 是
	private	Timestamp pushTime;//首页推送时间
	private int timeOut;
	private Byte overdue;   //签到无效
	private String pushTimeStr;
	private String stateStr;

	private String tContent;//待办内容
	private String userName;
	private String headUrl;
	private Long isTypeTwo; //是否是责任人
	private Byte isIgnore;//是否忽略
	private Long isRecord;  //是否有跟踪者(最新的跟踪者id)
	private Byte isCycle;//是否是周期行动
	private Long leId;//轻企id
	private Byte rpCategory;// 奖罚类别:0 滞后完成 罚 、1最低分罚、2最高分奖
	private Long beneficiary;//奖惩收益人
	private String ligName;  //轻企名称
	private Long ctId;//周期模板id
	private int mCount;
	private String endTimeStr;
	
	
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public int getmCount() {
		return mCount;
	}
	public void setmCount(int mCount) {
		this.mCount = mCount;
	}
	public String getLigName() {
		return ligName;
	}
	public void setLigName(String ligName) {
		this.ligName = ligName;
	}
	public Long getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Long beneficiary) {
		this.beneficiary = beneficiary;
	}
	public Byte getRpCategory() {
		return rpCategory;
	}
	public void setRpCategory(Byte rpCategory) {
		this.rpCategory = rpCategory;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public Long getIsTypeTwo() {
		return isTypeTwo;
	}
	public void setIsTypeTwo(Long isTypeTwo) {
		this.isTypeTwo = isTypeTwo;
	}
	public Long getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(Long isRecord) {
		this.isRecord = isRecord;
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
	public String gettContent() {
		return tContent;
	}
	public void settContent(String tContent) {
		this.tContent = tContent;
	}
	public String getPushTimeStr() {
		return pushTimeStr;
	}
	public void setPushTimeStr(String pushTimeStr) {
		this.pushTimeStr = pushTimeStr;
	}
	public Byte getOverdue() {
		return overdue;
	}
	public void setOverdue(Byte overdue) {
		this.overdue = overdue;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public Byte getRoleType() {
		return roleType;
	}
	public void setRoleType(Byte roleType) {
		this.roleType = roleType;
	}
	public String getFinishTimeStr() {
		return finishTimeStr;
	}
	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Long getCuserId() {
		return cuserId;
	}
	public void setCuserId(Long cuserId) {
		this.cuserId = cuserId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getEiId() {
		return eiId;
	}
	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
	}
	public Byte gettState() {
		return tState;
	}
	public void settState(Byte tState) {
		this.tState = tState;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettExplained() {
		return tExplained;
	}
	public void settExplained(String tExplained) {
		this.tExplained = tExplained;
	}
	public String gettNote() {
		return tNote;
	}
	public void settNote(String tNote) {
		this.tNote = tNote;
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
	public Byte getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(Byte isBlock) {
		this.isBlock = isBlock;
	}
	public Byte getIsSend() {
		return isSend;
	}
	public void setIsSend(Byte isSend) {
		this.isSend = isSend;
	}
	public Timestamp getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Timestamp remindTime) {
		this.remindTime = remindTime;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getIsnow() {
		return isnow;
	}
	public void setIsnow(String isnow) {
		this.isnow = isnow;
	}
	public Byte getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(Byte isAccept) {
		this.isAccept = isAccept;
	}
	public Byte gettSubjType() {
		return tSubjType;
	}
	public void settSubjType(Byte tSubjType) {
		this.tSubjType = tSubjType;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEiDesc() {
		return eiDesc;
	}
	public void setEiDesc(String eiDesc) {
		this.eiDesc = eiDesc;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getEiStartTime() {
		return eiStartTime;
	}
	public void setEiStartTime(String eiStartTime) {
		this.eiStartTime = eiStartTime;
	}

	public Byte getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Byte isFinish) {
		this.isFinish = isFinish;
	}

	public Timestamp getPushTime() {
		return pushTime;
	}

	public void setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
	}
	public Byte getIsInvalid() {
		return isInvalid;
	}
	public void setIsInvalid(Byte isInvalid) {
		this.isInvalid = isInvalid;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	public Byte getIsIgnore() {
		return isIgnore;
	}
	public void setIsIgnore(Byte isIgnore) {
		this.isIgnore = isIgnore;
	}

	public Byte getIsCycle() {
		return isCycle;
	}

	public void setIsCycle(Byte isCycle) {
		this.isCycle = isCycle;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

}
