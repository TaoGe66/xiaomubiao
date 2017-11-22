package com.qixin.teamwork.biz.awardPenalty.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;

import java.util.List;

/**
 * 惩罚名单DAO
 * @author wuchao
 * @date 2017年7月6日
 * @time 下午3:30:21
 * @version V0.0.1
 */
@Repository
public class AwardPenaltyListDao extends BaseDao{

	/**
	 * 保存惩罚名单信息
	 * @author wuchao
	 * @date 2017年7月6日
	 * @time 下午3:32:28
	 * @version V0.0.1
	 * @param awardPenaltyList
	 */
	public void save(AwardPenaltyList awardPenaltyList) {
		insert("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao.insert", awardPenaltyList);
	}

	/** 
	* 根据事件id和奖惩类型获取事件下奖惩名单
	* @author xiehuilin    
	* @param  
	* @return  
	* @version V0.0.1
	* @date 2017/7/6 17:11
	*/
	public List<AwardPenaltyList>  getPenaltyList(AwardPenaltyList awardPenaltyList){
		return  this.queryForList("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao.getPenaltyList",awardPenaltyList);
	}
	/**
	* 根据行动id和奖惩类型获取奖惩详情
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.03
	* @date 2017/9/11 20:26
	*/
	public  AwardPenaltyList getAwardPenaltyListInfo(AwardPenaltyList awardPenaltyList){
		return (AwardPenaltyList) this.queryForObject("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao.getAwardPenaltyListInfo",awardPenaltyList);
	}
	/**
	* 更新奖惩名单
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.3
	* @date 2017/9/11 20:44
	*/
	public  void update(AwardPenaltyList awardPenaltyList){
		this.update("com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao.update",awardPenaltyList);
	}
}
