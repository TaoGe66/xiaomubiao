package com.qixin.teamwork.biz.comment.model;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

import java.sql.Timestamp;


/** 评论实体
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 10:24
 * version:V0.0.1
 */
public class Comment extends BaseModel {
    private  Long id;//主键
    private  String content;//内容
    private  Long   createBy;
    private  Timestamp createtime;//创建时间
    private  Byte isvalid;//是否有效 0 否 1 是
    private  Long userId;//用户id
    private  Long  eId;//事件id
    private  Byte identity;//评论者身份 0 参与者 1 创建者
    private  PaginationParameter paper; //分页实体

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Byte getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Byte isvalid) {
        this.isvalid = isvalid;
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

    public PaginationParameter getPaper() {
        return paper;
    }

    public void setPaper(PaginationParameter paper) {
        this.paper = paper;
    }

    public Byte getIdentity() {
        return identity;
    }

    public void setIdentity(Byte identity) {
        this.identity = identity;
    }
}
