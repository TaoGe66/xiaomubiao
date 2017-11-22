package com.qixin.teamwork.biz.envet.dao;

import java.util.List;

import com.qixin.teamwork.biz.dto.JoinEventDto;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.group.model.FriendGroup;

@Repository
public class JoinEventDao extends BaseDao{

	/**
	*  保存用户参与记录
	* @author xiehuilin
	* @param joinEvent 用户参与实体
	* @version V0.0.1
	* @date 2017/6/13 14:47
	*/
	public  void insert(JoinEvent joinEvent){
		this.insert("com.qixin.teamwork.biz.envet.dao.JoinEventDao.insert",joinEvent);
	}

	/**
	 * 我的事件列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:30:43
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listJoinEvent(JoinEvent joinEvent) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listJoinEvent", joinEvent);
	}

	/**
	 *  删除我的事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:48:39
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void deleteJoinEvent(JoinEvent joinEvent) {
		update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.deleteJoinEvent", joinEvent);
	}

	/**
	 * 根据事件id获取事件下参与用户列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/12 18:01
	 */
	public  List<JoinEvent> listJoinEventByEid(JoinEvent joinEvent){
		return this.queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listJoinEventByEid",joinEvent);
	}

	/**
	 * 根据事件id和用户id查询事项责任人是否存在
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午2:09:20
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public JoinEvent JoinEventInfo(JoinEvent joinEvent) {
		return (JoinEvent) queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.JoinEventInfo", joinEvent);
	}

	/**
	 * 修改我的事件
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午2:31:31
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void updateJoinEvent(JoinEvent joinEvent) {
		update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.update", joinEvent);
	}
	/**
	* 根据id获取参与者信息
	* @author xiehuilin
	* @param  joinEvent 参与者实体
	* @return joinEvent 参与者实体
	* @version V0.0.1
	* @date 2017/6/14 19:18
	*/
	public  JoinEvent getJoinEventInfo(JoinEvent joinEvent){
		return (JoinEvent) this.queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.getJoinEventInfo",joinEvent);
	}
	/**
	 *  根据事先id和参与者id获取参与者信息
	 * @author xiehuilin
	 * @param   joinEvent 参与者实体
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/21 16:21
	 */
	public JoinEventDto getStandData(JoinEvent joinEvent){
		return (JoinEventDto) this.queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.getStandData",joinEvent);
	}
	
	public List<JoinEvent> eventPeopleList(Long eId){
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.eventPeopleList", eId);
	}
	
	/**
	 * 根据事件id和用户id查询参与者身份为事项责任人的参与者信息
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public JoinEvent itemJoinInfo(JoinEvent joinEvent) {
		return (JoinEvent) queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.itemJoinInfo", joinEvent);
	}
	
	/**
	 * 根据事件id和用户id查询用户是否是事件责任人
	 * @author xiehuilin
	 * @date 2017年6月27日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public  JoinEvent getByUserIdOrEidDuty(JoinEvent joinEvent) {
		return (JoinEvent) queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.getByUserIdOrEidDuty", joinEvent);
	}

	public List<JoinEvent> eventJoinList(JoinEvent joinEvent){
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.eventJoinList", joinEvent);
	}
	
	/**
	 * 项目描述  参与人
	 * @author zyting
	 * @date 2017年10月26日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> joinEventPeople(JoinEvent joinEvent){
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.joinEventPeople", joinEvent);
	}
	
	/**
	 * 获取事件责任人信息
	 * @author wuchao
	 * @date 2017年7月6日
	 * @time 上午10:02:23
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public JoinEvent  dutyInfo(JoinEvent joinEvent){
		return (JoinEvent) queryForObject("com.qixin.teamwork.biz.envet.dao.JoinEventDao.dutyInfo", joinEvent);
	}
	
	/**
	 * 判断好友关系是否存在项目
	 * @author zyting
	 * @date 2017年6月27日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> getDelStatus(FriendGroup friendGroup){
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.getDelStatus", friendGroup);
	}
	
	/**
	 * 修改目标责任人脚本
	 * @author wuchao
	 * @date 2017年8月3日
	 * @time 下午4:57:58
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void updateCreate(JoinEvent joinEvent) {
		update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.updateCreate", joinEvent);
	}

	/**
	 * 根据用户id和事件id修改提醒时间
	 * @author wuchao
	 * @date 2017年8月18日
	 * @time 下午4:19:50
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void updateEventTime(JoinEvent joinEvent) {
		update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.updateEventTime", joinEvent);
	}

	/**
	 * 根据用户id和轻企id查询用户轻企的项目
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午6:26:51
	 * @version V0.0.3
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listProject(JoinEvent joinEvent) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listProject", joinEvent);
	}
	
	/**
	 *  参与人退出行动
	 * @author zyting
	 * @date 2017年10月26日
	 * @time 下午4:19:50
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void outItemEven(JoinEvent joinEvent) {
		update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.outItemEven", joinEvent);
	}
	
	/**
	 *@Description: 根据行动id或项目id批量更新参与表记录
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	public  void updateBacth(JoinEvent joinEvent){
		this.update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.updateBacth",joinEvent);
	}
	/**
	 *@Description: 根据任务id和参与id更新项目参与人记录
	 * 1：将该用户属于项目参与人的角色更新为无效
	 * 2：将该用户属于行动参与人或责任人更新为无效
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/30 17:02
	 *@version V 0.0.7
	 */
	public  void  updateByEidAndUserId(JoinEvent joinEvent){
		this.update("com.qixin.teamwork.biz.envet.dao.JoinEventDao.updateByEidAndUserId",joinEvent);
	}

	/**
	 *根据用户id和轻企id，获取用户在轻企内负责未完成的目标
	 *@author wuChao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listDignity(JoinEvent joinEvent) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listDignity", joinEvent);
	}

	/**
	 * 根据用户id和轻企id，获取用户在轻企内负责未完成的行动（非自己创建的目标）
	 *@author wuchao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listItemDignity(JoinEvent joinEvent) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listItemDignity", joinEvent);
	}
	/**
	 *@Description:根据任务id和用户id获取用户角色身份列表
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:java.util.List<com.qixin.teamwork.biz.envet.model.JoinEvent>
	 *@date:2017/11/2 10:02
	 *@version V 0.0.7
	 */
	public  List<JoinEvent> listGetJoinUserType(JoinEvent joinEvent){
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listGetJoinUserType", joinEvent);
	}

	/**
	 * 目标的所有参与者
	 *@author wuchao
	 *@data 2017年11月2日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listEventPartake(JoinEvent joinEvent) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.JoinEventDao.listEventPartake", joinEvent);
	}

}
