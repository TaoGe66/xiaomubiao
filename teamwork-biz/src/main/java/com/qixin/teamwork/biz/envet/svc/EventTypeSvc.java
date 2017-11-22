package com.qixin.teamwork.biz.envet.svc;

import java.util.List;

import com.qixin.teamwork.biz.envet.model.EventType;

/**
 * 事件类型接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午11:46:02
 * @version V0.0.1
 */
public interface EventTypeSvc {

	/**
	 * 事件类型列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午11:56:59
	 * @version V0.0.1
	 * @param eventType
	 * @return
	 */
	public List<EventType> listEventType(EventType eventType);
}
