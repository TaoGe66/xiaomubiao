package com.qixin.teamwork.biz.enterprise.model;

import java.sql.Timestamp;
import java.util.List;

import org.framework.utils.PaginationParameter;

import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.light.model.LightInteract;

/**
 * 动态表
 * @author zyting
 * @date 2017年9月7日
 * @time 下午2:14:41
 * @version V0.0.3
 */
public class Dynamic {
	
	 private Long id;// '主键',
	 private Long  leId;//'轻企id',
	 private Long  eId;// '项目id'
	 private Long  eiId;//'行动id',
	 private Long  userId;// '用户id',
	 private String title; //标题
	 private String content;//内容
	 private Timestamp  createTime ;
	 private Long createBy;//创建人
	 private Timestamp updateTime;//更新时间
	 private Byte  isValid ;// '是否有效:0 否 1 是',
	 private String name;   //'名称'
	 private String userName; //用户昵称
	 private String headUrl;
	 private String createTimeStr;
	 private String eventName;  //项目名称
	 private Timestamp startTime;
	 private String startTimeStr;
	 private Timestamp enEndTime;   //event计划完成时间
	 private String enEndTimeStr;
	 private Timestamp enFinishTime; //event完成时间
	 private String enFinishTimeStr;
	 private Timestamp finishTime; //行动完成时间
	 private String finishTimeStr;
	 private String eiDesc;  //描述
	 private List<LightInteract> interactList;//动态互动
	 private Integer laudCount;//点赞数
	 private Byte isLaud;  //是否点赞  0 否  1 是
	 private String laudName;//点赞人员名称集合
	 private Byte state;//1,完成行动  2，接受指派  3，跟踪行动  4，完成目标  5，拒绝指派
	 private Timestamp delegateTime;//委托时间
	 private String delegateTimeStr;
	 private Timestamp receTime;//接受时间
	 private String receTimeStr;
	 private Timestamp rejecTime;//拒绝时间
	 private String rejecTimeStr;
	 private Timestamp fTime; //完成时间
	 private String fTimeStr;
	 private Event event;//项目实体
	 private EventItem item;//行动实体
	 private String remark;   //标记描述
	 private String summary;   //'总结内容',
	 private PaginationParameter paper;
	 private Long dotLaud;//我点赞的互动id
	 
	 
	 
	 
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEnEndTimeStr() {
		return enEndTimeStr;
	}
	public void setEnEndTimeStr(String enEndTimeStr) {
		this.enEndTimeStr = enEndTimeStr;
	}
	public String getEnFinishTimeStr() {
		return enFinishTimeStr;
	}
	public void setEnFinishTimeStr(String enFinishTimeStr) {
		this.enFinishTimeStr = enFinishTimeStr;
	}
	public String getFinishTimeStr() {
		return finishTimeStr;
	}
	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
	}
	public String getDelegateTimeStr() {
		return delegateTimeStr;
	}
	public void setDelegateTimeStr(String delegateTimeStr) {
		this.delegateTimeStr = delegateTimeStr;
	}
	public String getReceTimeStr() {
		return receTimeStr;
	}
	public void setReceTimeStr(String receTimeStr) {
		this.receTimeStr = receTimeStr;
	}
	public String getRejecTimeStr() {
		return rejecTimeStr;
	}
	public void setRejecTimeStr(String rejecTimeStr) {
		this.rejecTimeStr = rejecTimeStr;
	}
	public String getfTimeStr() {
		return fTimeStr;
	}
	public void setfTimeStr(String fTimeStr) {
		this.fTimeStr = fTimeStr;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEnEndTime() {
		return enEndTime;
	}
	public void setEnEndTime(Timestamp enEndTime) {
		this.enEndTime = enEndTime;
	}
	public Timestamp getEnFinishTime() {
		return enFinishTime;
	}
	public void setEnFinishTime(Timestamp enFinishTime) {
		this.enFinishTime = enFinishTime;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public String getEiDesc() {
		return eiDesc;
	}
	public void setEiDesc(String eiDesc) {
		this.eiDesc = eiDesc;
	}
	public List<LightInteract> getInteractList() {
		return interactList;
	}
	public void setInteractList(List<LightInteract> interactList) {
		this.interactList = interactList;
	}
	public Integer getLaudCount() {
		return laudCount;
	}
	public void setLaudCount(Integer laudCount) {
		this.laudCount = laudCount;
	}
	public Byte getIsLaud() {
		return isLaud;
	}
	public void setIsLaud(Byte isLaud) {
		this.isLaud = isLaud;
	}
	public String getLaudName() {
		return laudName;
	}
	public void setLaudName(String laudName) {
		this.laudName = laudName;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Timestamp getDelegateTime() {
		return delegateTime;
	}
	public void setDelegateTime(Timestamp delegateTime) {
		this.delegateTime = delegateTime;
	}
	public Timestamp getReceTime() {
		return receTime;
	}
	public void setReceTime(Timestamp receTime) {
		this.receTime = receTime;
	}
	public Timestamp getRejecTime() {
		return rejecTime;
	}
	public void setRejecTime(Timestamp rejecTime) {
		this.rejecTime = rejecTime;
	}
	public Timestamp getfTime() {
		return fTime;
	}
	public void setfTime(Timestamp fTime) {
		this.fTime = fTime;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public EventItem getItem() {
		return item;
	}
	public void setItem(EventItem item) {
		this.item = item;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public Long getDotLaud() {
		return dotLaud;
	}
	public void setDotLaud(Long dotLaud) {
		this.dotLaud = dotLaud;
	}
	
}
