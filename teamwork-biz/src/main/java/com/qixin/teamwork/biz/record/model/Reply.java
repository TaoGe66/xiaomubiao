package com.qixin.teamwork.biz.record.model;

import org.framework.utils.BaseModel;

import java.sql.Timestamp;

/**
 * @version V0.0.7
 * @Description: 行动跟踪回复
 * @author: xiehuilin
 * @date:2017/10/25 16:32
 */
public class Reply extends BaseModel {
    private Long id;
    private Long userId; //用户id
    private Long eId; // 事件id
    private Long eiId;//事项id
    private String content; //回复内容
    private Byte isValid;//是否有效 0 否 1 是
    private Long createBy;//创建人
    private Timestamp createTime; //创建时间
    private Long trId;//行动跟踪记录
    private Timestamp updateTime;//更新时间
    private String strCreteTime;
    private String userName;//用户名称
    private String headUrl;//用户头像
    public String getStrCreteTime() {
        return strCreteTime;
    }

    public void setStrCreteTime(String strCreteTime) {
        this.strCreteTime = strCreteTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getTrId() {
        return trId;
    }

    public void setTrId(Long trId) {
        this.trId = trId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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
}
