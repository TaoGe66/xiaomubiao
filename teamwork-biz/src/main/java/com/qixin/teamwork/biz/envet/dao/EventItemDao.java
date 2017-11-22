package com.qixin.teamwork.biz.envet.dao;

import com.qixin.teamwork.biz.census.model.LightWeekly;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.group.model.FriendGroup;

import java.util.List;

/**
 *  事项dao
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:22:37
 * @version V0.0.1
 */
@Repository
public class EventItemDao extends BaseDao{

	/**
	 * 保存事项
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:26:04
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void saveEventItem(EventItem eventItem) {
		insert("com.qixin.teamwork.biz.envet.dao.EventItemDao.insert", eventItem);
	}

	/**
	 * 修改事项
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:26:15
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateEventItem(EventItem eventItem) {
		update("com.qixin.teamwork.biz.envet.dao.EventItemDao.update", eventItem);
	}
	/**
	 * 获取事件下的事项列表
	 * @author xiehuilin
	 * @param  eventItem 事项实体
	 * @return  事项列表
	 * @version V0.0.1
	 * @date 2017/6/12 14:35
	 */
	public List<EventItem> listEventItem(EventItem eventItem){
	 return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listEventItem",eventItem);
	}
	/**
	 * 姓名
	 * @author zyting
	 * @param  eventItem 事项实体
	 * @return  事项列表
	 * @version V0.0.1
	 * @date 2017/6/12 14:35
	 */
	public List<EventItem> listUserName(EventItem eventItem){
	 return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listUserName",eventItem);
	}
	/**
	 * 事项详情
	 * @author wuchao
	 * @date 2017年6月15日
	 * @time 下午4:38:21
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem getItemInfo(EventItem eventItem) {
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.getItemInfo", eventItem);
	}

	/**
	 * 校验事项完成时间
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午5:04:18
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem checkTime(EventItem eventItem) {
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.checkTime", eventItem);
	}

	/**
	 * 获取事件下的事项列表
	 * @author wuchao
	 * @date 2017年6月22日
	 * @time 下午4:06:25
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listItem(EventItem eventItem){
		 return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listItem",eventItem);
	}
	
	public void updateItem(Long eId){
		this.update("com.qixin.teamwork.biz.envet.dao.EventItemDao.updateItem",eId);
	}
	/**
	*
	* 根据事件id获取未出完成的行动
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/7/27 14:32
	*/
	public  List<EventItem> listUnfinishedEventItem(EventItem eventItem){
		return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listUnfinishedEventItem",eventItem);
	}


	/**
	 *
	 * 据项目id和责任id获取行动列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/7/27 14:32
	 */
	public  List<EventItem> listEventItemByeIdAndDutyId(EventItem eventItem){
		return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listEventItemByeIdAndDutyId",eventItem);
	}
	
	/**
	 * 判断好友关系是否存在项目
	 * @author zyting
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/3 14:32
	 */
	public  List<EventItem> getDelEventStatus(FriendGroup friendGroup){
		return this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.getDelEventStatus",friendGroup);
	}

	/**
	 * 进度列表
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 下午2:17:31
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listItemInfo(EventItem eventItem) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listItemInfo", eventItem);
	}

	/**
	 * 根据模板id获取事项的最大id
	 * @author wuchao
	 * @date 2017年8月12日
	 * @time 下午1:56:49
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem getMAXItem(EventItem eventItem) {
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.getMAXItem", eventItem);
	}

	/**
	 * 获取最近的应被激活的项目
	 * @author zyting
	 * @date 2017年8月16日
	 * @time 下午1:56:49
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem getCycleByTime(EventItem eventItem) {
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.getCycleByTime", eventItem);
	}


	/**
	 * 根据周期模板id更新行动的状态(激活、冻结)
	 * @author wuchao
	 * @date 2017年8月15日
	 * @time 下午8:06:28
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateTempletState(EventItem eventItem) {
		update("com.qixin.teamwork.biz.envet.dao.EventItemDao.updateTempletState", eventItem);
	}

	/**
	* add by xiehuilin 20170816 周期行动根据事件查询比当前事项开始时间小的行动
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:25
	*/
	public  List<EventItem> getCycleByTimeLessThan(EventItem eventItem){
		return  queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.getCycleByTimeLessThanList", eventItem);
	}
	/**
	* 根据行动id获取行动详情
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:46
	*/
	public  EventItem getByEiIdInfo(EventItem eventItem){
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.getByEiIdInfo", eventItem);
	}
	
	/**
	* 首页委托弹框详情
	* @author zyting
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:46
	*/
	public EventItem getTemWinInfo(EventItem eventItem){
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.getTemWinInfo", eventItem);
	}
	/**
	 * add by xiehuilin 20170816 获取事项下的参与人列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/16 13:25
	 */
	public  List<EventItem> listEventItemJoinUser(EventItem eventItem){
		return  queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listEventItemJoinUser", eventItem);
	}

	/**
	 * 健康度
	 * @author wuchao
	 * @date 2017年8月23日
	 * @time 上午10:53:27
	 * @version V0.0.2
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listhealth(EventItem eventItem) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listhealth", eventItem);
	}

	/**
	 * 周报评星
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 下午6:09:08
	 * @version V0.0.3
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listTswkRank(EventItem eventItem) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listTswkRank", eventItem);
	}
	/** 
	* 获取轻企下我的及我的关注的事项列表
	* @author xiehuilin    
	* @param  
	* @return  
	* @version V0.0.3
	* @date 2017/9/13 11:35
	*/
	public  List<EventItem>  listLightEventItem(EventItem eventItem){
		return  this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listLightEventItem",eventItem);
	}

	/**
	* 获取轻企下我关注的事项列表参与人
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.3
	* @date 2017/9/13 16:45
	*/
	public  List<EventItem> listLightEventItemJoinUser(EventItem eventItem){
		return  this.queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listLightEventItemJoinUser",eventItem);

	}

	/**
	 * 获取目标下未冻结的周期模板id
	 *@author wuchao
	 *@data 2017年10月27日
	 *@version V0.0.5
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listEventCycThaw(EventItem eventItem) {
		return queryForList("com.qixin.teamwork.biz.envet.dao.EventItemDao.listEventCycThaw", eventItem);
	}

	/**
	 * 周期行动是否有显示
	 *@author wuchao
	 *@data 2017年11月1日
	 *@version V0.0.5
	 * @param eventItem
	 * @return
	 */
	public EventItem isShowItem(EventItem eventItem) {
		return (EventItem) queryForObject("com.qixin.teamwork.biz.envet.dao.EventItemDao.isShowItem", eventItem);
	}

}
