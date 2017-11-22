package com.qixin.teamwork.biz.census.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.census.model.WeeklyList;

/**
 * 周工作清单DAO
 * @author wuchao
 * @date 2017年9月21日
 * @time 下午3:07:23
 * @version V0.0.3
 */
@Repository
public class WeeklyListDao extends BaseDao{

	/**
	 * 清单列表
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午3:42:50
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	public List<WeeklyList> listWeeklyList(WeeklyList weeklyList) {
		return queryForList("com.qixin.teamwork.biz.census.dao.WeeklyListDao.listWeeklyList", weeklyList);
	}

	/**
	 * 修改清单信息
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午3:42:55
	 * @version V0.0.3
	 * @param weeklyList
	 */
	public void updateWeeklyList(WeeklyList weeklyList) {
		update("com.qixin.teamwork.biz.census.dao.WeeklyListDao.updateWeeklyList", weeklyList);
	}

	/**
	 * 是否生成周报
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午9:30:39
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	public WeeklyList isMakeWeekly(WeeklyList weeklyList) {
		return (WeeklyList) queryForObject("com.qixin.teamwork.biz.census.dao.WeeklyListDao.isMakeWeekly", weeklyList);
	}
	
	public void insert(WeeklyList weeklyList){
		insert("com.qixin.teamwork.biz.census.dao.WeeklyListDao.insert", weeklyList);
	}

	/**
	 * 根据事项id获取清单记录
	 * @author xiehuilin
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList 清单实体
	 */
	public 	WeeklyList  getWeeklyInfo(WeeklyList weeklyList){
		return (WeeklyList) this.queryForObject("com.qixin.teamwork.biz.census.dao.WeeklyListDao.getWeeklyInfo",weeklyList);
	}
	/**
	 *@Description:根据项目id获取清单记录
	 *@author:xiehuilin
	 *@param:event
	 *@return:java.util.List<com.qixin.teamwork.biz.census.model.WeeklyList>
	 *@date:2017/10/25 15:07
	 *@version V 0.0.7
	 */
	public  List<WeeklyList> listWeeklistByEid(WeeklyList weeklyList){
		return  this.queryForList("com.qixin.teamwork.biz.census.dao.WeeklyListDao.listWeeklistByEid",weeklyList);
	}

	/**
	 *@Description:根据行动id或项目id更新清单记录
	 *@author:xiehuilin
	 *@param:weeklyList
	 *@return:void
	 *@date:2017/10/26 15:30
	 *@version V 0.0.7
	 */
	public  void updateWeeklyListBatch(WeeklyList weeklyList){
		this.update("com.qixin.teamwork.biz.census.dao.WeeklyListDao.updateWeeklyListBatch",weeklyList);
	}

}
