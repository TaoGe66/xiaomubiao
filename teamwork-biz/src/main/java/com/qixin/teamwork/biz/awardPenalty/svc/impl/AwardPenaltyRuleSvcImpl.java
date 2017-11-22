package com.qixin.teamwork.biz.awardPenalty.svc.impl;

import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyRuleSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 14:07
 * version:V0.0.1
 */
@Service("awardPenaltyRuleSvc")
public class AwardPenaltyRuleSvcImpl implements AwardPenaltyRuleSvc {
    @Autowired
    private AwardPenaltyRuleDao awardPenaltyRuleDao;
    /**
    * 存储事务奖惩规则
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/7/6 14:09
    */
    @Override
    public void insert(AwardPenaltyRule penaltyRule) {
         awardPenaltyRuleDao.insert(penaltyRule);
    }

    @Override
    public List<AwardPenaltyRule> getAwardPenaltyRuleList(AwardPenaltyRule awardPenaltyRule) {
        return awardPenaltyRuleDao.getAwardPenaltyRuleList(awardPenaltyRule);
    }

    /**
     * 规则详细 信息
     * @author wuchao
     * @date 2017年7月6日
     * @time 下午3:42:24
     * @version V0.0.1
     * @param penaltyRule
     * @return
     */
	@Override
	public AwardPenaltyRule infoAward(AwardPenaltyRule penaltyRule) {
		return awardPenaltyRuleDao.infoAward(penaltyRule);
	}
}
