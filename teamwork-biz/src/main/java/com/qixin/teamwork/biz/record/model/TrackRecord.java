package com.qixin.teamwork.biz.record.model;

import org.framework.utils.PaginationParameter;
import org.omg.CORBA.TIMEOUT;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/8/9 11:31
 * version:V0.0.1
 */
public class TrackRecord {
    private  Long id;
    private  String content;
    private  Byte isValid;
    private Timestamp createTime;
    private Timestamp updateTime;
    private  Long createBy; //创建人
    private  Long eiId; //事项id
    private  Long eId; //事件id
    private  Byte isRed; //'是否已阅： 0 否 1 是
    private  Timestamp itemColseTime;//行动截止时间
    private Long donotUser;  
    private Long ctId;//'周期模板id',
	private PaginationParameter paper;
	private List<Reply> replyList;  //跟踪留言列表
	private String  userName;//用户名
	private String  headUrl;//头像
	private String  strCreateTime;
	private Byte trType; //跟踪类型：0 发布进度、1 标记完成

	public Byte getTrType() {
		return trType;
	}

	public void setTrType(Byte trType) {
		this.trType = trType;
	}

	public String getStrCreateTime() {
		return strCreateTime;
	}

	public void setStrCreateTime(String strCreateTime) {
		this.strCreateTime = strCreateTime;
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

	public PaginationParameter getPaper() {
		return paper;
	}

	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}

	public Long getDonotUser() {
		return donotUser;
	}
	public void setDonotUser(Long donotUser) {
		this.donotUser = donotUser;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
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
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
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
	public Byte getIsRed() {
		return isRed;
	}
	public void setIsRed(Byte isRed) {
		this.isRed = isRed;
	}
	public Timestamp getItemColseTime() {
		return itemColseTime;
	}
	public void setItemColseTime(Timestamp itemColseTime) {
		this.itemColseTime = itemColseTime;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
}
