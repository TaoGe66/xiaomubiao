package com.qixin.teamwork.biz.envet.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.envet.model.EventDelay;

/**
 * 延期记录DAO
 *@author wuchao
 *@data 2017年10月27日
 *@version V0.0.5
 */
@Repository
public class EventDelayDao extends BaseDao{

	/**
	 * 保存延期记录
	 *@author wuchao
	 *@data 2017年10月27日
	 *@version V0.0.5
	 * @param eventDelay
	 */
	public void saveEventDelay(EventDelay eventDelay) {
		insert("com.qixin.teamwork.biz.envet.dao.EventDelayDao.insert", eventDelay);
	}

}
