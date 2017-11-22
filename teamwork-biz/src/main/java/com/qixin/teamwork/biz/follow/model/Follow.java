package com.qixin.teamwork.biz.follow.model;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

import java.sql.Timestamp;

/**
 * 关注实体
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 13:48
 * version:V0.0.1
 */
public class Follow extends BaseModel {

    private  Long   id;//主键
    private  Long leId;//轻企id
    private  Long userId;//用户id
    private  Byte state;//关注状态 0 拒绝 1 接受 2 等待审核
    private  Byte type;//类型： 0 关注他人、1 他人关注
    private  Timestamp createTime;//创建时间
    private  Long createBy;//创建人
    private  Timestamp updateTime;//更新时间
    private  Byte isValid;//是否有效 0 否  1 是
    private  String strState;
    private PaginationParameter paper;
    private String userName;
    private String headUrl;
    private Byte roleType;
    private Long memberId;//轻企成员id
    private String note;      //'个人说明'
    private Byte isRed;//是否已阅 0 否 1是
    private Integer newFollow;//是否有新关注记录 0 否  1 是
    private Byte lemState;
    
    public Byte getLemState() {
		return lemState;
	}

	public void setLemState(Byte lemState) {
		this.lemState = lemState;
	}

	public Byte getRoleType() {
        return roleType;
    }

    public void setRoleType(Byte roleType) {
        this.roleType = roleType;
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeId() {
        return leId;
    }

    public void setLeId(Long leId) {
        this.leId = leId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    public PaginationParameter getPaper() {
        return paper;
    }

    public void setPaper(PaginationParameter paper) {
        this.paper = paper;
    }

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    public Byte getIsRed() {
        return isRed;
    }

    public void setIsRed(Byte isRed) {
        this.isRed = isRed;
    }

	public Integer getNewFollow() {
		return newFollow;
	}

	public void setNewFollow(Integer newFollow) {
		this.newFollow = newFollow;
	}
    
}
