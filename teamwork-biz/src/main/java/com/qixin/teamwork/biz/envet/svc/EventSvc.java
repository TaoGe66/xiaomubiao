package com.qixin.teamwork.biz.envet.svc;

import java.util.List;

import com.qixin.teamwork.biz.envet.model.Event;

/**
 * 事件接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V0.0.1
 */
public interface EventSvc {

	/**
	 * 新增事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:24:45
	 * @version V0.0.1
	 * @param envet
	 */
	public Event saveEnvet(Event envet);
	
	/**
	 * 修改事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:25:01
	 * @version V0.0.1
	 * @param envet
	 */
	public void updateEnvet(Event envet);
	/** 获取事件信息
	* @description
	* @author xiehuilin
	* @param  Event 事件实体
	* @return envet 事件实体
	* @version V0.0.1
	* @date 2017/6/12 13:48
	*/
	 Event  getEnvetInfo(Event event);
	 
	 /** 获取事件列表
	 * @description
	 * @author zyting
	 * @param  Event 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/14 15:48
	 * state  '状态:0  待计划、1待执行、2 已完成、3待关闭 、4 待签到 5 已签到
	 * pub_range '可见范围: 0 好友可以见、1平台公开，2 自己可见',
	 */
	public List<Event> getEventList(Event envet);

	/** 
	 * 获取好友下的事件列表
	 * @description
	 * @author zyting
	 * @param  Event 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/14 15:48
	 * userId
	 */
	public List<Event> getFriendEventList(Event envet);
	
	/**
	 * 根据事件id查询事件详情
	 * @author wuchao
	 * @date 2017年6月26日
	 * @time 上午9:37:43
	 * @version V0.0.1
	 * @param envet
	 * @return
	 */
	public Event getInfoEvent(Event envet);
	
	/**
	 * 邀你参与
	 * @author zyting
	 * @date 2017年6月26日
	 * @time 上午9:37:43
	 * @version V0.0.1
	 * @param envet
	 * @return
	 */
	public List<Event> findEvenList(Event envet,Long userId);
	
	/**
	 * 获取事件详情
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 上午11:53:23
	 * @version V0.0.1
	 * @param event
	 * @return
	 */
	Event  infoEnvet(Event event);
	/**
	 *@Description: 删除目标
	 *@author:xiehuilin
	 *@param:event
	 *@param: type 0 删除项目  1 终止项目
	 *@return:void
	 *@date:2017/10/25 14:51
	 *@version V 0.0.7
	 */
	void delEvent(Event event,Integer type);

	
	/**
	 * 设置目标结束时间
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param event
	 */
	public void updatePutUp(Event event);
	
	/**
	 * 目标延期
	 *@author wuchao
	 *@data 2017年10月26日
	 *@version V0.0.5
	 * @param event
	 */
	public void updateDelay(Event event);
	
}
