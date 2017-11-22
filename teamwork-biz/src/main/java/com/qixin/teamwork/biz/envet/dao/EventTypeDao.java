package com.qixin.teamwork.biz.envet.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.envet.model.EventType;

/**
 * 事件类型dao
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午11:45:33
 * @version V0.0.1
 */
@Repository
public class EventTypeDao extends BaseDao {

	/**
	 * 事件类型列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午11:58:58
	 * @version V0.0.1
	 * @param eventType
	 * @return
	 */
	public List<EventType> listEventType(EventType eventType) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventTypeDao.listEventType", eventType);
	}
	
	

}
