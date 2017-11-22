package com.qixin.teamwork.biz.envet.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.envet.model.CycleTemplet;

/**
 * 事项周期模板DAO层
 * @author wuchao
 * @date 2017年8月9日
 * @time 下午4:08:50
 * @version V0.0.1
 */
@Repository("cycleTempletDao")
public class CycleTempletDao extends BaseDao{

	/**
	 * 保存事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:14:22
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	public void saveCycleTemplet(CycleTemplet cycleTemplet) {
		insert("com.qixin.teamwork.biz.envet.dao.CycleTempletDao.insert", cycleTemplet);
	}

	/**
	 *  修改事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:31:51
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	public void updateCycleTemplet(CycleTemplet cycleTemplet) {
		update("com.qixin.teamwork.biz.envet.dao.CycleTempletDao.update", cycleTemplet);
	}

	/**
	* 根据id获取周期模板详情
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/10 16:38
	*/
	public  CycleTemplet getCtempletInfo(CycleTemplet cycleTemplet){
		return (CycleTemplet) this.queryForObject("com.qixin.teamwork.biz.envet.dao.CycleTempletDao.getCtempletInfo",cycleTemplet);
	}

	/**
	 * 查询所有有效的模板id
	 * @author wuchao
	 * @date 2017年8月12日
	 * @time 下午12:12:51
	 * @version V0.0.1
	 * @param cycleTemplet
	 * @return
	 */
	public List<CycleTemplet> listCtemplet(CycleTemplet cycleTemplet) {
		return  queryForList("com.qixin.teamwork.biz.envet.dao.CycleTempletDao.listCtemplet",cycleTemplet);
	}
}
