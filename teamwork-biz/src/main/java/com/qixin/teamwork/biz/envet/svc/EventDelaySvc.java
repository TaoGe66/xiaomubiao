package com.qixin.teamwork.biz.envet.svc;

import com.qixin.teamwork.biz.envet.model.EventDelay;

/**
 * 延期记录接口
 *@author wuchao
 *@data 2017年10月27日
 *@version V0.0.5
 */
public interface EventDelaySvc {

	/**
	 * 保存延期记录
	 *@author wuchao
	 *@data 2017年10月27日
	 *@version V0.0.5
	 * @param eventDelay
	 */
	public void saveEventDelay(EventDelay eventDelay);
}
