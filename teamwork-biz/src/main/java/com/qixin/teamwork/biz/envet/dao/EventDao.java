package com.qixin.teamwork.biz.envet.dao;

import java.util.List;

import com.qixin.teamwork.biz.envet.model.Event;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 事件dao
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午10:05:33
 * @version V0.0.1
 */
@Repository
public class EventDao extends BaseDao{

	/**
	 * 新增事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:27:14
	 * @version V0.0.1
	 * @param envet
	 */
	public void saveEnvet(Event envet) {
		insert("com.qixin.teamwork.biz.envet.dao.EventDao.insert", envet);
	}

	/**
	 * 修改事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:27:25
	 * @version V0.0.1
	 * @param envet
	 */
	public void updateEnvet(Event envet) {
		update("com.qixin.teamwork.biz.envet.dao.EventDao.update", envet);
	}
	/** 获取事件信息
	 * @description
	 * @author xiehuilin
	 * @param  Event 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/12 13:48
	 */
	public Event getEnvetInfo(Event event) {
		return (Event) this.queryForObject("com.qixin.teamwork.biz.envet.dao.EventDao.getEnvetInfo",event);
	}
	
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
	public List<Event> getEventList(Event envet){ 
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventDao.getEventList", envet);
	}
	
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
	public List<Event> getFriendEventList(Event envet){
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventDao.getFriendEventList", envet);
	}

	/**
	 * 根据事件id查询事件详情
	 * @author wuchao
	 * @date 2017年6月26日
	 * @time 上午9:39:20
	 * @version V0.0.1
	 * @param envet
	 * @return
	 */
	public Event getInfoEvent(Event envet) {
		return (Event) queryForObject("com.qixin.teamwork.biz.envet.dao.EventDao.getInfoEvent", envet);
	}
	/**
	 * 组织活动更新参与人数
	 * @author wuchao
	 * @date 2017年6月26日
	 * @time 上午9:39:20
	 * @version V0.0.1
	 * @param envet
	 * @return
	 */
	public void updateEnvetPoll(Event envet) {
		update("com.qixin.teamwork.biz.envet.dao.EventDao.updateEnvetPoll", envet);
	}
	
	
	public List<Event> findEvenList(Event envet){
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventDao.findEvenList", envet);
	}
	
	public List<Event> listEvenInfo(Event envet){
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventDao.listEvenInfo", envet);
	}

	/**
	 * 获取事件详情
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 上午11:54:44
	 * @version V0.0.1
	 * @param event
	 * @return
	 */
	public Event infoEnvet(Event event) {
		return (Event) queryForObject("com.qixin.teamwork.biz.envet.dao.EventDao.infoEnvet", event);
	}
	
	/**
	 * 解散轻企
	 * @author zyting
	 * @date 2017年8月14日
	 * @time 上午11:54:44
	 * @version V0.0.1
	 * @param event
	 * @return
	 */
	public void isDelLeid(Event event) {
		update("com.qixin.teamwork.biz.envet.dao.EventDao.isDelLeid", event);
	}
}
