package com.qixin.teamwork.biz.census.model;

import java.sql.Timestamp;
import java.util.List;

import org.framework.utils.BaseModel;

/**
 * 周报实体
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午4:10:20
 * @version V0.0.3
 */
public class LightWeekly extends BaseModel{

	  private Long id;        
	  private Long  userId;  //用户id         
	  private Long  leId ;    //轻企业id          
	  private Long  eId  ;   //项目id  
	  private Long  eiId  ;  //行动id    
	  private Timestamp createTime;// '创建时间',
	  private Byte  isValid ;// '是否有效: 0 否 1 是',
	  private Long  createBy ; //'创建人',
	  private Timestamp startTime;//'开始时间'
	  private Byte  isFinish;//项目/行动是否完成：0 否 1 是
	  private Timestamp mondayTime;//周一开始时间
	  private Timestamp endTime;//未完成截止时间
	  
	  private Integer totalItem;//全部项目
	  private Integer  finishItem;//已完成项目
	  private Integer totalAction;//全部行动
	  private Integer finishAction;//	已完成的行动
	  private Integer bestNumber;//五星好评数量
	  private Integer worstNumber;//一星评价数量
	  private Integer totalAssess;//全部评价数量
	  
	  private Integer advanceItem;//项目提前完成数
	  private Integer normalItem;//项目正常完成数
	  private Integer lagItem;//项目滞后完成数
	  private Integer  unusualItem;//项目异常数
	  private Integer advanceAction;//行动提前完成数
	  private Integer normalAction;//	正常完成的行动
	  private Integer lagAction;//行动滞后完成数
	  private Integer unusualAction;//行动异常数
	  private Byte stateItem;//项目状态
	  private Byte stateAction;//行动状态
	  private Byte rank;//行动星级
	  
	  private List<WeeklyList> listWeeklyList;//清单列表
	  private Long strTime;//long类型时间
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

	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	public Integer getFinishItem() {
		return finishItem;
	}
	public void setFinishItem(Integer finishItem) {
		this.finishItem = finishItem;
	}
	public Integer getTotalAction() {
		return totalAction;
	}
	public void setTotalAction(Integer totalAction) {
		this.totalAction = totalAction;
	}
	public Integer getFinishAction() {
		return finishAction;
	}
	public void setFinishAction(Integer finishAction) {
		this.finishAction = finishAction;
	}
	public Integer getBestNumber() {
		return bestNumber;
	}
	public void setBestNumber(Integer bestNumber) {
		this.bestNumber = bestNumber;
	}
	public Integer getWorstNumber() {
		return worstNumber;
	}
	public void setWorstNumber(Integer worstNumber) {
		this.worstNumber = worstNumber;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Byte getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Byte isFinish) {
		this.isFinish = isFinish;
	}
	public Timestamp getMondayTime() {
		return mondayTime;
	}
	public void setMondayTime(Timestamp mondayTime) {
		this.mondayTime = mondayTime;
	}
	public Integer getAdvanceItem() {
		return advanceItem;
	}
	public void setAdvanceItem(Integer advanceItem) {
		this.advanceItem = advanceItem;
	}
	public Integer getNormalItem() {
		return normalItem;
	}
	public void setNormalItem(Integer normalItem) {
		this.normalItem = normalItem;
	}
	public Integer getLagItem() {
		return lagItem;
	}
	public void setLagItem(Integer lagItem) {
		this.lagItem = lagItem;
	}
	public Integer getUnusualItem() {
		return unusualItem;
	}
	public void setUnusualItem(Integer unusualItem) {
		this.unusualItem = unusualItem;
	}
	public Integer getAdvanceAction() {
		return advanceAction;
	}
	public void setAdvanceAction(Integer advanceAction) {
		this.advanceAction = advanceAction;
	}
	public Integer getNormalAction() {
		return normalAction;
	}
	public void setNormalAction(Integer normalAction) {
		this.normalAction = normalAction;
	}
	public Integer getLagAction() {
		return lagAction;
	}
	public void setLagAction(Integer lagAction) {
		this.lagAction = lagAction;
	}
	public Integer getUnusualAction() {
		return unusualAction;
	}
	public void setUnusualAction(Integer unusualAction) {
		this.unusualAction = unusualAction;
	}
	public Byte getStateItem() {
		return stateItem;
	}
	public void setStateItem(Byte stateItem) {
		this.stateItem = stateItem;
	}
	public Byte getStateAction() {
		return stateAction;
	}
	public void setStateAction(Byte stateAction) {
		this.stateAction = stateAction;
	}
	public Byte getRank() {
		return rank;
	}
	public void setRank(Byte rank) {
		this.rank = rank;
	}
	public Integer getTotalAssess() {
		return totalAssess;
	}
	public void setTotalAssess(Integer totalAssess) {
		this.totalAssess = totalAssess;
	}
	public List<WeeklyList> getListWeeklyList() {
		return listWeeklyList;
	}
	public void setListWeeklyList(List<WeeklyList> listWeeklyList) {
		this.listWeeklyList = listWeeklyList;
	}
	public Long getStrTime() {
		return strTime;
	}
	public void setStrTime(Long strTime) {
		this.strTime = strTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
}
