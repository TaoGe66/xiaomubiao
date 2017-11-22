package com.qixin.teamwork.biz.news.model;

import java.sql.Timestamp;

import org.framework.utils.PaginationParameter;

/**
 * 消息实体类
 * @author zyting
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V1.0
 */
public class News {

	private Long nId;         //'主键',
	private String nTitle;     //'消息标题',
	private String nContent;     //'消息内容',
	private Timestamp nTime;       //'消息时间',
	private String nLogo;          //'消息logo',
	private Long createBy;            //'创建人',
	private Timestamp createTime;      //'创建时间',
	private Byte isValid;           //'是否有效:0 否 1 是
	private Long sendId;          //'接收人',
	private Byte nType;         // '消息类型：0 好友消息 1 事件消息
	private Byte isRed;       //'是否已阅： 0 否 1 是',
	private String nName;//消息名称
	private Byte nMold;//消息状态：1 拒绝委托   2 异常终止  3 事务待关闭、4事务待计划、5事务签到  6进度待完成、7进度待接受、8进度未接受',
	private Long eiId;//事项id
	private Long eId;//事件id
	private PaginationParameter paper;
	private String eName;  //事件名称
	private String nTimeStr;       //'消息时间',
	private String eiDesc;    //事项描述
	private  String nMoldStr;
	private Long leId;//轻企id
	private String strType;//消息类型字符串
	public String getnMoldStr() {
		return nMoldStr;
	}

	public void setnMoldStr(String nMoldStr) {
		this.nMoldStr = nMoldStr;
	}
	public String getEiDesc() {
		return eiDesc;
	}
	public void setEiDesc(String eiDesc) {
		this.eiDesc = eiDesc;
	}
	public String getnTimeStr() {
		return nTimeStr;
	}
	public void setnTimeStr(String nTimeStr) {
		this.nTimeStr = nTimeStr;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public Byte getIsRed() {
		return isRed;
	}
	public void setIsRed(Byte isRed) {
		this.isRed = isRed;
	}
	public Long getnId() {
		return nId;
	}
	public void setnId(Long nId) {
		this.nId = nId;
	}
	public String getnTitle() {
		return nTitle;
	}
	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	public String getnContent() {
		return nContent;
	}
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	public Timestamp getnTime() {
		return nTime;
	}
	public void setnTime(Timestamp nTime) {
		this.nTime = nTime;
	}
	public String getnLogo() {
		return nLogo;
	}
	public void setnLogo(String nLogo) {
		this.nLogo = nLogo;
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
	public Long getSendId() {
		return sendId;
	}
	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}
	public Byte getnType() {
		return nType;
	}
	public void setnType(Byte nType) {
		this.nType = nType;
	}
	public String getnName() {
		return nName;
	}
	public void setnName(String nName) {
		this.nName = nName;
	}
	public Byte getnMold() {
		return nMold;
	}
	public void setnMold(Byte nMold) {
		this.nMold = nMold;
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

	public Long getLeId() {
		return leId;
	}

	public void setLeId(Long leId) {
		this.leId = leId;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}
	
}
