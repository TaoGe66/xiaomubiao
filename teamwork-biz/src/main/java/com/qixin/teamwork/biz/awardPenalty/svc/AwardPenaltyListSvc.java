package com.qixin.teamwork.biz.awardPenalty.svc;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;

import java.util.List;

/**
 * 奖惩名单接口
 * @author wuchao
 * @date 2017年7月6日
 * @time 下午3:26:19
 * @version V0.0.1
 */
public interface AwardPenaltyListSvc {

	/**
	 * 保存惩罚名单信息
	 * @author wuchao
	 * @date 2017年7月6日
	 * @time 下午3:27:14
	 * @version V0.0.1
	 * @param awardPenaltyList
	 */
	public void save(AwardPenaltyList awardPenaltyList);


	/**
	 * 根据事件id和奖惩类型获取事件下奖惩名单
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/7/6 17:03
	 */
	 List<AwardPenaltyList> getPenaltyList(AwardPenaltyList awardPenaltyList);

	/**
	 * 根据行动id和奖惩类型获取奖惩详情
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.03
	 * @date 2017/9/11 20:26
	 */
	 AwardPenaltyList getAwardPenaltyListInfo(AwardPenaltyList awardPenaltyList);

	/**
	 * 更新奖惩名单
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.3
	 * @date 2017/9/11 20:44
	 */
	  void update(AwardPenaltyList awardPenaltyList);
}
