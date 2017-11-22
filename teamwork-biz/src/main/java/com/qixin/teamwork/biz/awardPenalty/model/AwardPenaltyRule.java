package com.qixin.teamwork.biz.awardPenalty.model;

import org.framework.utils.BaseModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 奖惩规则
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 13:53
 * version:V0.0.1
 */
public class AwardPenaltyRule  extends BaseModel{
    private  Long id;
    private  Long eId;//事件id
    private  Byte rpType;//奖惩类型  0 罚  1 奖
    private  BigDecimal rpMoney;//奖惩金额
    private  Byte isValid;//是否有效 0 否 1 是
    private  Long createBy;//创建人
    private  Timestamp CreateTime;//创建时间
    private  Byte rpCategory;//奖罚类别:0 滞后完成 罚 、1最低分罚、2最高分奖

    private  List<AwardPenaltyRule> ruleList;
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Byte getRpType() {
        return rpType;
    }

    public void setRpType(Byte rpType) {
        this.rpType = rpType;
    }

    public BigDecimal getRpMoney() {
        return rpMoney;
    }

    public void setRpMoney(BigDecimal rpMoney) {
        this.rpMoney = rpMoney;
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
        return CreateTime;
    }

    public void setCreateTime(Timestamp createTime) {
        CreateTime = createTime;
    }

    public Byte getRpCategory() {
        return rpCategory;
    }

    public void setRpCategory(Byte rpCategory) {
        this.rpCategory = rpCategory;
    }

    public List<AwardPenaltyRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<AwardPenaltyRule> ruleList) {
        this.ruleList = ruleList;
    }

}
