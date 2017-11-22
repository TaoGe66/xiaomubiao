package com.qixin.teamwork.biz.envet.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

/**
 * 用户参与实体
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午4:24:34
 * @version V0.0.1
 */
public class JoinEvent extends BaseModel{

	private Long id;
	private Long eId;//事件id
	private Long userId;//用户id
	private Long createBy;//创建人
	private Byte isValid;//是否有效:0  否 1 是
	private Byte type;//角色: 0 事件创建人、1事件责任人、 2 事项责任人
	private Byte state;//状态:0 未选择 1 选中
	private Integer frequency;//提醒设置;0:30 分钟前、1:1小时前(10分钟前)、2:正点、3:一天前(10分钟后)、4:不提现(20分钟后)
	
	private String name;//名称
	private Timestamp startTime;//开始时间
	private Timestamp endTime;//结束时间
	private String    advantage;//个人优势
	private double percent;//完成度
	private PaginationParameter paper; //分页实体
	private String userName;//用户昵称
	private String headUrl;//用户头像
	private Timestamp createTime;
	private Byte isSign;//是否签到:0 否 1是
	private String hisSign;//签到记录：多次签到记录以json格式存储
	private  String address;//签到地址
	private  String time;//签到时间
	private  String lng;//经度
	private  String lat;//纬度
	private Byte eventType;//事件类型:0 时间管理 、1 服务请求、2 组织活动
	private String dutyName;//责任人姓名
	private Long eiId;  // '事项id',
	private Long isRecord;  //是否有跟踪者(最新的跟踪者id)
	private Long leId;//轻企id
	private String leName;      //轻企简称
	private String strEventType;//事件类型
	private String strEventState;//事件状态
	private Long cuserId;   //事件创建人
	private String typeStr;
	private String isSelect; //能否被选
	private Long tId ;// '待办 id'
	private String joinUserIds;//行动参与人,用逗号分隔
	private Byte isJoinUser;//是否加入
	private Long duytItemCount;//参与人负责的行动数
	private String strType;//字符串角色类型
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}
	public Long getDuytItemCount() {
		return duytItemCount;
	}
	public void setDuytItemCount(Long duytItemCount) {
		this.duytItemCount = duytItemCount;
	}
	public Byte getIsJoinUser() {
		return isJoinUser;
	}
	public void setIsJoinUser(Byte isJoinUser) {
		this.isJoinUser = isJoinUser;
	}
	public String getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public Long getCuserId() {
		return cuserId;
	}
	public void setCuserId(Long cuserId) {
		this.cuserId = cuserId;
	}
	public Long getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(Long isRecord) {
		this.isRecord = isRecord;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long geteId() {
		return eId;
	}
	public void seteId(Long eId) {
		this.eId = eId;
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
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}

	public PaginationParameter getPaper() {
		return paper;
	}

	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Byte getIsSign() {
		return isSign;
	}

	public void setIsSign(Byte isSign) {
		this.isSign = isSign;
	}

	public String getHisSign() {
		return hisSign;
	}

	public void setHisSign(String hisSign) {
		this.hisSign = hisSign;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	public Byte getEventType() {
		return eventType;
	}
	public void setEventType(Byte eventType) {
		this.eventType = eventType;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public Long getEiId() {
		return eiId;
	}
	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public String getLeName() {
		return leName;
	}
	public void setLeName(String leName) {
		this.leName = leName;
	}
	public String getStrEventType() {
		return strEventType;
	}
	public void setStrEventType(String strEventType) {
		this.strEventType = strEventType;
	}
	public String getStrEventState() {
		return strEventState;
	}
	public void setStrEventState(String strEventState) {
		this.strEventState = strEventState;
	}
	public Long gettId() {
		return tId;
	}
	public void settId(Long tId) {
		this.tId = tId;
	}

	public String getJoinUserIds() {
		return joinUserIds;
	}

	public void setJoinUserIds(String joinUserIds) {
		this.joinUserIds = joinUserIds;
	}
}
