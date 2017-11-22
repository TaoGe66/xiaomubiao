package com.qixin.teamwork.biz.awardPenalty.svc.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyListSvc;

import java.util.List;

/**
 * 惩罚名单实现层
 * @author wuchao
 * @date 2017年7月6日
 * @time 下午3:29:58
 * @version V0.0.1
 */
@Service("aplSvc")
public class AwardPenaltyListSvcImpl implements AwardPenaltyListSvc{

	@Resource
	private AwardPenaltyListDao aplDao;
	
	/**
	 * 保存惩罚名单信息
	 * @author wuchao
	 * @date 2017年7月6日
	 * @time 下午3:32:28
	 * @version V0.0.1
	 * @param awardPenaltyList
	 */
	@Override
	public void save(AwardPenaltyList awardPenaltyList) {
		// TODO Auto-generated method stub
		aplDao.save(awardPenaltyList);
	}

	@Override
	public List<AwardPenaltyList> getPenaltyList(AwardPenaltyList awardPenaltyList) {
		return aplDao.getPenaltyList(awardPenaltyList);
	}

	@Override
	public AwardPenaltyList getAwardPenaltyListInfo(AwardPenaltyList awardPenaltyList) {
		return aplDao.getAwardPenaltyListInfo(awardPenaltyList);
	}

	@Override
	public void update(AwardPenaltyList awardPenaltyList) {
		aplDao.update(awardPenaltyList);
	}
}
