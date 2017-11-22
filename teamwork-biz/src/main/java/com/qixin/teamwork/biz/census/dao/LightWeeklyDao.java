package com.qixin.teamwork.biz.census.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.census.model.LightWeekly;

/**
 * 周报DAO
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午4:11:28
 * @version V0.0.3
 */
@Repository
public class LightWeeklyDao extends BaseDao{

	/**
	 * 保存周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午4:21:48
	 * @version V0.0.3
	 * @param lightWeekly
	 */
	public void saveWeekly(LightWeekly lightWeekly) {
		insert("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.insert", lightWeekly);
	}

	/**
	 * 修改周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午4:22:04
	 * @version V0.0.3
	 * @param lightWeekly
	 */
	public void updateWeekly(LightWeekly lightWeekly) {
		update("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.update", lightWeekly);
	}

	/**
	 * 将对应的周报内容置为无效
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 上午10:18:43
	 * @version V0.0.3
	 * @param lightWeekly
	 */
	public void deleteWeekly(LightWeekly lightWeekly) {
		update("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.deleteWeekly", lightWeekly);
	}

	/**
	 * 周报内容列表
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午1:39:57
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listWeekly(LightWeekly lightWeekly) {
		return queryForList("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.listWeekly", lightWeekly);
	}

	/**
	 * 已完成的项目
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:57:39
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listFinishItem(LightWeekly lightWeekly) {
		return queryForList("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.listFinishItem", lightWeekly);
	}

	/**
	 * 已完成的行动
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:57:44
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listFinishAction(LightWeekly lightWeekly) {
		return queryForList("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.listFinishAction", lightWeekly);
	}
	/**
	* 根据轻企id和行动id获取周报详情
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/9/11 17:04
	*/
	public LightWeekly getWeeklyInfo(LightWeekly weekly){
		return (LightWeekly) this.queryForObject("com.qixin.teamwork.biz.census.dao.LightWeeklyDao.getWeeklyInfo", weekly);
	}
}
