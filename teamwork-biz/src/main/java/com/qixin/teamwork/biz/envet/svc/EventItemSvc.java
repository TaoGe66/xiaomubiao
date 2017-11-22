package com.qixin.teamwork.biz.envet.svc;

import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.group.model.FriendGroup;

import java.util.List;

/**
 * 事项接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:22:04
 * @version V0.0.1
 */
public interface EventItemSvc {

	/**
	 * 保存事项(单次)
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:23:15
	 * @version V0.0.1
	 * @param EventItem
	 */
	public void saveEventItem(EventItem eventItem);
	
	/**
	 * 重新编辑进度内容(单次)
	 * @author wuchao
	 * @date 2017年7月27日
	 * @time 上午11:05:05
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateItem(EventItem eventItem);
	
	 /**
	  * 保存周期行动
	  * @author wuchao
	  * @date 2017年8月9日
	  * @time 上午11:47:05
	  * @version V0.0.1
	  * @param eventItem
	  */
	 public Integer saveCycItem(EventItem eventItem);

	 /**
	  *  重新编辑进度内容(周期)
	  * @author wuchao
	  * @date 2017年8月11日
	  * @time 上午10:48:27
	  * @version V0.0.1
	  * @param eventItem
	  * @return
	  */
	public Integer updateCycItem(EventItem eventItem);
	
	/**
	 * 修改事项
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:23:56
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateEventItem(EventItem eventItem);
	/** 
	* 获取事件下的事项列表
	* @author xiehuilin    
	* @param  eventItem 事项实体
	* @return  事项列表
	* @version V0.0.1
	* @date 2017/6/12 14:35
	*/
	List<EventItem> listEventItem(EventItem eventItem);
	
	/**
	 * 修改事项状态
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午6:02:13
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateItemState(EventItem eventItem);
	
	/**
	 * 事项详情
	 * @author wuchao
	 * @date 2017年6月15日
	 * @time 下午4:37:06
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem getItemInfo(EventItem eventItem);

	/**
	 * 校验事项完成时间
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午5:03:09
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem checkTime(EventItem eventItem);
	
	/**
	 * 获取事件下的事项列表(不包含异常中止)
	 * @author wuchao
	 * @date 2017年6月22日
	 * @time 下午4:14:38
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listItem(EventItem eventItem);

	/**
	*  评分
	*   1:更新事项记录
	 *   1.1：评价最低分和最高分生成奖惩记录
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/7/6 13:49
	*/
	void  updateEventItemCreateAwardpenalty(EventItem eventItem);
	
	
	/**
	 * 事项详情
	 * @author wuchao
	 * @date 2017年7月27日
	 * @time 上午11:19:34
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public EventItem getActionItem(EventItem eventItem);

	/**
	 *
	 * 根据事件id获取未出完成的行动
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/7/27 14:32
	 */
	List<EventItem> listUnfinishedEventItem(EventItem eventItem);


	/**
	 *
	 * 据项目id和责任id获取行动列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/7/27 14:32
	 */
	List<EventItem> listEventItemByeIdAndDutyId(EventItem eventItem);
	
	/**
	 * 判断好友关系是否存在项目
	 * @author zyting
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/3 14:32
	 */
	 List<EventItem> getDelEventStatus(FriendGroup friendGroup);
	
	/**
	 * 进度列表
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 下午2:16:02
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public List<EventItem> listItemInfo(EventItem eventItem);
	
	/**
	 * 根据模板id获取事项的最大id
	 * @author wuchao
	 * @date 2017年8月12日
	 * @time 下午1:54:30
	 * @version V0.0.1
	 * @return
	 */
	public EventItem getMAXItem(EventItem eventItem);
	
	/**
	 * 修改周期模板状态
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午6:02:13
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateCycleState(EventItem eventItem);
	
	/**
	 * 根据周期模板id更新行动的状态(激活、冻结)
	 * @author wuchao
	 * @date 2017年8月15日
	 * @time 下午8:03:55
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void updateTempletState(EventItem eventItem);

	/**
	 * 周期行动根据事件查询比当前事项开始时间小的行动
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/16 13:25
	 */
	List<EventItem> getCycleByTimeLessThan(EventItem eventItem);

	/**
	* 周期性行动更新行动编辑限权
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:30
	*/
	  void  updateCycleEdit(EventItem eventItem);


	/**
	 * 根据行动id获取行动详情
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/16 13:46
	 */
	EventItem getByEiIdInfo(EventItem eventItem);
	
	/**
	* 首页委托弹框详情
	* @author zyting
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:46
	*/
	EventItem getTemWinInfo(EventItem eventItem);

	/**
	 * add by xiehuilin 20170816 获取事项下的参与人列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/16 13:25
	 */
	  List<EventItem> listEventItemJoinUser(EventItem eventItem);
	  
	  /**
	   * 健康度
	   * @author wuchao
	   * @date 2017年8月23日
	   * @time 上午10:52:38
	   * @version V0.0.2
	   * @param eventItem
	   * @return
	   */
	  List<EventItem>  listhealth(EventItem eventItem);
	  
	  /**
		 * 姓名
		 * @author zyting
		 * @param  eventItem 事项实体
		 * @return  事项列表
		 * @version V0.0.1
		 * @date 2017/6/12 14:35
	  */
	  List<EventItem> listUserName(EventItem eventItem);
	  
	  /**
	   * 周报评星
	   * @author wuchao
	   * @date 2017年9月12日
	   * @time 下午6:07:53
	   * @version V0.0.3
	   * @param eventItem
	   * @return
	   */
	 public List<EventItem> listTswkRank(EventItem eventItem);


	/**
	 * 获取轻企下我的及我的关注的事项列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.3
	 * @date 2017/9/13 11:35
	 */
	List<EventItem>  listLightEventItem(EventItem eventItem);

	/**
	 * 获取轻企下我关注的事项列表参与人
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.3
	 * @date 2017/9/13 16:45
	 */
	 List<EventItem> listLightEventItemJoinUser(EventItem eventItem);

	 /**
	   *@Description:删除行动
	   *@author:xiehuilin
	   *@param:eventItem
	   *@return:
	   *@date:2017/10/26 14:43
	   *@version V 0.0.7
	   */
	 void  delEventItem(EventItem eventItem);

	 /**
	  * 激活周期行动
	  *@author wuchao
	  *@data 2017年10月26日
	  *@version V0.0.5
	  * @param eventItem
	  */
	 public void updateActivateCyc(EventItem eventItem);
	 
	 /**
	  * 获取目标下未冻结的周期模板id
	  *@author wuchao
	  *@data 2017年10月27日
	  *@version V0.0.5
	  * @param eventItem
	  * @return
	  */
	 public List<EventItem> listEventCycThaw(EventItem eventItem);
	 
	 /**
	  * 周期行动是否有显示
	  *@author wuchao
	  *@data 2017年11月1日
	  *@version V0.0.5
	  * @param eventItem
	  * @return
	  */
	 public EventItem isShowItem(EventItem eventItem);
	 
}
