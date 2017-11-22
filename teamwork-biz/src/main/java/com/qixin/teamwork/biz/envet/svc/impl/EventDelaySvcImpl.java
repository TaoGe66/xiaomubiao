package com.qixin.teamwork.biz.envet.svc.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.envet.dao.EventDelayDao;
import com.qixin.teamwork.biz.envet.model.EventDelay;
import com.qixin.teamwork.biz.envet.svc.EventDelaySvc;

/**
 * 延期记录实现层
 *@author wuchao
 *@data 2017年10月27日
 *@version V0.0.5
 */
@Service("eventDelaySvc")
public class EventDelaySvcImpl implements EventDelaySvc{

	@Resource
	private EventDelayDao delayDao;
	
	/**
	 * 保存延期记录
	 *@author wuchao
	 *@data 2017年10月27日
	 *@version V0.0.5
	 * @param eventDelay
	 */
	@Override
	public void saveEventDelay(EventDelay eventDelay) {
		delayDao.saveEventDelay(eventDelay);
	}

}
