package com.qixin.teamwork.biz.envet.model;

import com.qixin.teamwork.biz.record.model.TrackRecord;
import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 事项实体
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:19:22
 * @version V0.0.1
 */
public class EventItem extends BaseModel{

	private static final long serialVersionUID = -2265838327675549582L;
	
	private Long eiId;
	private Timestamp startTime;//开始时间=事项完成时间-事件提醒时间
	private Timestamp finishTime;//完成时间
	private Timestamp createTime;//创建时间
	private String eiDesc;//描述
	private Long createBy;//创建人
	private Byte isValid;//是否有效:0 否 1 是
	private Long dutyId;//责任人
	private Byte isAccept;//是否接受：0 否 1 是
	private	Byte  state;//'事项状态：0 待完成、1待接受 、 2 未接受、3正常完成，4 延期完成、5取消、6 未完成',
	private Long eId;//事件id
	private String eiNote;//多次签到记录以json格式存储
	private String  headUrl;//用户头像
	private String  note;
	private Long assignId;//指派人id
	private Long userId;//用户id
	private String userName;//用户昵称
	private String fTime;//完成时间
	private String startStrTime;//开始时间
	private Integer rank;//评价打分
	private String remark;//备注
	private PaginationParameter paper; //分页实体
	private Integer isEventItemDuty;//0 创建人 1 责任人 2 即是创建人也是责任人
	private String strFinishTime;//字符串截至时间
	private Timestamp updateTime;//更新时间
	private String  uTime;//更新时间字符串
	private String stateStr;
	private Byte isCycle;//是否是周期行动
	private Byte cycleFreq;//周期频率:0 单次  1 周期
	private String cycle;//重复周期
	private Long ctId;//周期Id
	private Byte   cycleState;//'周期状态: 0 激活  1 冻结',
	private List<TrackRecord> recordList;//行动跟踪记录列表
	private Long circleStartTime;//周期开始时间
	private Long circleFinishTime;//周期完成时间
	private Timestamp colseTime;//截止时间
	private String strStartTime;//字符串开始时间
	private Byte isInvalid;//是否失效 0 否 1是
	private Integer showType;// 时间抽展示规则  0 过去  1 现在 2 将来
	private Byte  showActivate;// 是否开启激活、冻结权限
	private Byte isCycleEdit;//周期行动是否有编辑权限
	private Timestamp endTime;
	private Byte isRange;//可见范围: 0 私有  1 公开
	private Timestamp fTimestamp; //完成时间
	private Long strfTime;
	private Byte isReward;//是否是奖惩行动  0 否 1 是
	private Byte rpCategory;// 奖罚类别:0 滞后完成 罚 、1最低分罚、2最高分奖
	private Byte reqType;
	private Long leId;//轻企id
	private Long beneficiary;//奖惩收益人
	private Integer isEventDuty;//项目者身份
	private String strFtimestamp; //完成时间字符串
	private String fTimeStr;//截止时间
	private Integer frequency;//分钟
	private Byte isShow;//周期默展示:0 否 1 是'

	private String cUserName;//行动创建人昵称
	private String dUserName;//行动责任昵称
	private Integer rCount;//未阅行动跟踪数

	public Integer getrCount() {
		return rCount;
	}

	public void setrCount(Integer rCount) {
		this.rCount = rCount;
	}

	public String getcUserName() {
		return cUserName;
	}

	public void setcUserName(String cUserName) {
		this.cUserName = cUserName;
	}

	public String getdUserName() {
		return dUserName;
	}

	public void setdUserName(String dUserName) {
		this.dUserName = dUserName;
	}

	public String getfTimeStr() {
		return fTimeStr;
	}

	public void setfTimeStr(String fTimeStr) {
		this.fTimeStr = fTimeStr;
	}

	public Integer getIsEventDuty() {
		return isEventDuty;
	}

	public void setIsEventDuty(Integer isEventDuty) {
		this.isEventDuty = isEventDuty;
	}

	public Timestamp getfTimestamp() {
		return fTimestamp;
	}
	public void setfTimestamp(Timestamp fTimestamp) {
		this.fTimestamp = fTimestamp;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Byte getIsCycleEdit() {
		return isCycleEdit;
	}
	public void setIsCycleEdit(Byte isCycleEdit) {
		this.isCycleEdit = isCycleEdit;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getEiId() {
		return eiId;
	}
	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getEiDesc() {
		return eiDesc;
	}
	public void setEiDesc(String eiDesc) {
		this.eiDesc = eiDesc;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public Long getDutyId() {
		return dutyId;
	}
	public void setDutyId(Long dutyId) {
		this.dutyId = dutyId;
	}
	public Byte getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(Byte isAccept) {
		this.isAccept = isAccept;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}
	public String getEiNote() {
		return eiNote;
	}
	public void setEiNote(String eiNote) {
		this.eiNote = eiNote;
	}
	public Long getAssignId() {
		return assignId;
	}
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getfTime() {
		return fTime;
	}
	public void setfTime(String fTime) {
		this.fTime = fTime;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public Integer getIsEventItemDuty() {
		return isEventItemDuty;
	}
	public void setIsEventItemDuty(Integer isEventItemDuty) {
		this.isEventItemDuty = isEventItemDuty;
	}
	public String getStrFinishTime() {
		return strFinishTime;
	}
	public void setStrFinishTime(String strFinishTime) {
		this.strFinishTime = strFinishTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}
	public Byte getIsCycle() {
		return isCycle;
	}
	public void setIsCycle(Byte isCycle) {
		this.isCycle = isCycle;
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
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<TrackRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<TrackRecord> recordList) {
		this.recordList = recordList;
	}
	public Byte getCycleState() {
		return cycleState;
	}
	public void setCycleState(Byte cycleState) {
		this.cycleState = cycleState;
	}
	public Long getCircleStartTime() {
		return circleStartTime;
	}
	public void setCircleStartTime(Long circleStartTime) {
		this.circleStartTime = circleStartTime;
	}
	public Long getCircleFinishTime() {
		return circleFinishTime;
	}
	public void setCircleFinishTime(Long circleFinishTime) {
		this.circleFinishTime = circleFinishTime;
	}

	public Timestamp getColseTime() {
		return colseTime;
	}

	public void setColseTime(Timestamp colseTime) {
		this.colseTime = colseTime;
	}
	public String getStrStartTime() {
		return strStartTime;
	}
	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}
	public Byte getIsInvalid() {
		return isInvalid;
	}
	public void setIsInvalid(Byte isInvalid) {
		this.isInvalid = isInvalid;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getStartStrTime() {
		return startStrTime;
	}

	public void setStartStrTime(String startStrTime) {
		this.startStrTime = startStrTime;
	}

	public Byte getShowActivate() {
		return showActivate;
	}

	public void setShowActivate(Byte showActivate) {
		this.showActivate = showActivate;
	}
	
	public Byte getIsRange() {
		return isRange;
	}
	public void setIsRange(Byte isRange) {
		this.isRange = isRange;
	}
	public Long getStrfTime() {
		return strfTime;
	}

	public void setStrfTime(Long strfTime) {
		this.strfTime = strfTime;
	}

	public Byte getIsReward() {
		return isReward;
	}

	public void setIsReward(Byte isReward) {
		this.isReward = isReward;
	}

	public Byte getRpCategory() {
		return rpCategory;
	}

	public void setRpCategory(Byte rpCategory) {
		this.rpCategory = rpCategory;
	}

	public Byte getReqType() {
		return reqType;
	}

	public void setReqType(Byte reqType) {
		this.reqType = reqType;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}

	public Long getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Long beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getStrFtimestamp() {
		return strFtimestamp;
	}

	public void setStrFtimestamp(String strFtimestamp) {
		this.strFtimestamp = strFtimestamp;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Byte getIsShow() {
		return isShow;
	}

	public void setIsShow(Byte isShow) {
		this.isShow = isShow;
	}
	
}
