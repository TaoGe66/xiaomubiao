package com.qixin.teamwork.biz.awardPenalty.dao;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 事件奖惩规则dao
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 14:10
 * version:V0.0.1
 */
@Repository
public class AwardPenaltyRuleDao  extends BaseDao {
    /**
    *  存储事件奖惩规则激励
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/7/6 14:43
    */
    public  void insert(AwardPenaltyRule awardPenaltyRule){
        this.insert("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao.insert",awardPenaltyRule);
    }
    /**
    * 根据事件id获取奖惩记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/7/6 15:49
    */
    public List<AwardPenaltyRule>  getAwardPenaltyRuleList(AwardPenaltyRule awardPenaltyRule){
       return  this.queryForList("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao.getAwardPenaltyRuleList",awardPenaltyRule);

    }

    /**
     * 规则详细
     * @author wuchao
     * @date 2017年7月6日
     * @time 下午3:44:37
     * @version V0.0.1
     * @param penaltyRule
     * @return
     */
	public AwardPenaltyRule infoAward(AwardPenaltyRule penaltyRule) {
		return (AwardPenaltyRule) queryForObject("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao.infoAward", penaltyRule);
	}
}
