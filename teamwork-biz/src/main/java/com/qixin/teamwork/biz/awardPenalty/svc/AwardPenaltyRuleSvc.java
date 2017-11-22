package com.qixin.teamwork.biz.awardPenalty.svc;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;

import java.util.List;
import java.util.Map;

/** 奖罚规则svc
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 14:07
 * version:V0.0.1
 */
public interface AwardPenaltyRuleSvc {
    /**
    *  存储事务奖惩规则
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/7/6 14:08
    */
    void insert(AwardPenaltyRule penaltyRule);

    /**
     * 根据事件id获取奖惩记录
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/7/6 15:49
     */
    List<AwardPenaltyRule> getAwardPenaltyRuleList(AwardPenaltyRule awardPenaltyRule);

    /**
     * 规则详细 信息
     * @author wuchao
     * @date 2017年7月6日
     * @time 下午3:42:24
     * @version V0.0.1
     * @param penaltyRule
     * @return
     */
    public AwardPenaltyRule infoAward(AwardPenaltyRule penaltyRule);
}
