package com.qixin.teamwork.biz.dto;

import org.framework.utils.BaseModel; 

import java.sql.Timestamp;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 11:12
 * version:V0.0.1
 */
public class CommentDto  extends BaseModel{
    private  String content;//评论内容
    private  Timestamp createTime;//评论事件
    private  String userName;//用户昵称
    private  Long userId;//用户id
    private  String headUrl;//用户头像
    private  Byte identity;//评论者身份 0 参与者 1创建者
    private  String Ctime;//评论时间

    public String getContent() {
        return content;
    }

    public Byte getIdentity() {
        return identity;
    }

    public void setIdentity(Byte identity) {
        this.identity = identity;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getCtime() {
        return Ctime;
    }

    public void setCtime(String ctime) {
        Ctime = ctime;
    }
}
