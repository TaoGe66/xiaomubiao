package com.qixin.teamwork.biz.envet.svc;

import java.util.List;
import java.util.Map;

import com.qixin.teamwork.biz.dto.JoinEventDto;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.group.model.FriendGroup;

/**
 * 用户参与接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午4:26:17
 * @version V0.0.1
 */
public interface JoinEventSvc {

	/**
	 *  保存用户参与记录
	 * @author xiehuilin
	 * @param joinEvent 用户参与实体
	 * @return  map集合
	 * @version V0.0.1
	 * @date 2017/6/13 14:47
	 */
	Map<String,Object> insert(JoinEvent joinEvent);

	/**
	 * 我的事件列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:27:55
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	 List<JoinEvent> listJoinEvent(JoinEvent joinEvent);
	
	/**
	 * 删除我的事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:46:12
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void deleteJoinEvent(JoinEvent joinEvent);
    /**
    * 根据事件id获取事件下参与用户列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/6/12 18:01
    */
	List<JoinEvent> listJoinEventByEid(JoinEvent joinEvent);
	
	/**
	 * 根据事件id和用户id查询事项责任人是否存在
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午2:07:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public JoinEvent JoinEventInfo(JoinEvent joinEvent);
	
	/**
	 * 修改我的事件推送时间
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午4:46:12
	 * @version V0.0.1
	 * @param joinEvent
	 */
	public void updateJoinEvent(JoinEvent joinEvent);

	/* 1:服务请求记录选中责任人记录
	*   1.1:根据事件id获取事件下所有参选人
	*   1.2:更新选中者记录,为该选中者生成待办任务,其他参与者生产落选消息提醒
	* @description
	* @author xiehuilin
	* @param  joinEvent 事件参与者实体
	* @version V0.0.1
	* @date 2017/6/14 13:29
	*/
	void updateScreenDuty(JoinEvent joinEvent);

	/**
	 * 根据id获取参与者信息
	 * @author xiehuilin
	 * @param  joinEvent 参与者实体
	 * @return joinEvent 参与者实体
	 * @version V0.0.1
	 * @date 2017/6/14 19:18
	 */
	JoinEvent getJoinEventInfo(JoinEvent joinEvent);
	/**
	*  根据事先id和参与者id获取参与者信息
	* @author xiehuilin
	* @param   joinEvent 参与者实体
	* @return
	* @version V0.0.1
	* @date 2017/6/21 16:21
	*/
   JoinEventDto getStandData(JoinEvent joinEvent);
	/**
	* 签到:更新参与表,更新待办任务表，生成待办历史
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/24 8:29
	*/
   void update(JoinEvent joinEvent);

   /**
    * 根据事件id和用户id查询参与者身份为事项责任人的参与者信息
    * @author wuchao
    * @date 2017年6月27日
    * @time 下午3:41:04
    * @version V0.0.1
    * @param joinEvent
    * @return
    */
	public JoinEvent itemJoinInfo(JoinEvent joinEvent);
	
	/**
	 * 根据事件id和用户id查询用户是否是事件责任人
	 * @author xiehuilin
	 * @date 2017年6月27日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	JoinEvent getByUserIdOrEidDuty(JoinEvent joinEvent);

	
	/**
	 * 判断好友关系是否存在项目
	 * @author zyting
	 * @date 2017年6月27日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> getDelStatus(FriendGroup friendGroup);


	/**
	 * 获取事件责任人信息
	 * @author wuchao
	 * @date 2017年7月6日
	 * @time 上午10:02:23
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	 JoinEvent  dutyInfo(JoinEvent joinEvent);
	 
	 /**
	  * 根据用户id和事件id修改提醒时间
	  * @author wuchao
	  * @date 2017年8月18日
	  * @time 下午4:18:21
	  * @version V0.0.1
	  * @param joinEvent
	  */
	 void updateEventTime(JoinEvent joinEvent);
	 
	 //修改目标责任人脚本
	 void updateCreate();
	 
	 /**
	  * 根据用户id和轻企id查询用户轻企的项目
	  * @author wuchao
	  * @date 2017年9月7日
	  * @time 下午6:24:40
	  * @version V0.0.3
	  * @param joinEvent
	  * @return
	  */
	 public List<JoinEvent> listProject(JoinEvent joinEvent);
	 
	 /**
	  * 根据用户id和轻企id查询用户在轻企的角色
	  * @author zyting
	  * @date 2017年9月7日
	  * @time 下午6:24:40
	  * @version V0.0.3
	  * @param joinEvent
	  * @return
	  */
	 List<JoinEvent> eventJoinList(JoinEvent joinEvent);
	 
	 /**
	 * 项目描述  参与人
	 * @author zyting
	 * @date 2017年10月26日
	 * @time 下午3:42:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	 List<JoinEvent> joinEventPeople(JoinEvent joinEvent);
	 
	 /**
	 *  参与人退出行动
	 * @author zyting
	 * @date 2017年10月26日
	 * @time 下午4:19:50
	 * @version V0.0.1
	 * @param joinEvent
	 */
	 void outItemEven(JoinEvent joinEvent);

	/**
	 *@Description: 根据行动id或项目id批量更新参与表记录
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	void updateBacth(JoinEvent joinEvent);

    /**
     *@Description: 根据任务id和参与id更新项目参与人记录
     * 1：将该用户属于项目参与人的角色更新为无效
     * 2：将该用户属于行动参与人或责任人更新为无效
     *@author:xiehuilin
     *@param:joinEvent
     *@return:void
     *@date:2017/10/26 17:02
     *@version V 0.0.7
     */
	void updateByEidAndUserId(JoinEvent joinEvent);
	/**
	 *@Description:项目描述保存行动参与者记录
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	void saveEventJoinUser(JoinEvent joinEvent);
	/**
	 *@Description:保存行动参与者记录
	 *@author:zyting
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	void saveEventItemJoinUser(JoinEvent joinEvent);
	
	/**
	 * 根据用户id和轻企id，获取用户在轻企内负责未完成的目标
	 *@author wuchao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listDignity(JoinEvent joinEvent);
	
	/**
	 *  根据用户id和轻企id，获取用户在轻企内负责未完成的行动（非自己创建的目标）
	 *@author wuchao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	public List<JoinEvent> listItemDignity(JoinEvent joinEvent);

	/**
	 *@Description:根据任务id和用户id获取用户角色身份列表
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:java.util.List<com.qixin.teamwork.biz.envet.model.JoinEvent>
	 *@date:2017/11/2 10:02
	 *@version V 0.0.7
	 */
	List<JoinEvent> listGetJoinUserType(JoinEvent joinEvent);
	
	/**
	 * 目标的所有参与人
	 *@author wuchao
	 *@data 2017年11月2日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	List<JoinEvent> listEventPartake(JoinEvent joinEvent);
}
