package com.qixin.teamwork.biz.envet.svc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.envet.dao.EventTypeDao;
import com.qixin.teamwork.biz.envet.model.EventType;
import com.qixin.teamwork.biz.envet.svc.EventTypeSvc;

/**
 * 事件类型实现
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午11:46:20
 * @version V1.0.3.1
 */
@Service("eventTypeSvc")
public class EventTypeSvcImpl implements EventTypeSvc{

	@Resource
	private EventTypeDao eventTypeDao;
	
	/**
	 * 事件类型列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午11:58:58
	 * @version V0.0.1
	 * @param eventType
	 * @return
	 */
	@Override
	public List<EventType> listEventType(EventType eventType) {
		return eventTypeDao.listEventType(eventType);
	}

}
