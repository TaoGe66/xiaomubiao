package com.qixin.teamwork.biz.group.svc;

import java.util.List;
import java.util.Map;

import com.qixin.teamwork.biz.group.model.FriendGroup;


/**
 * 我的好友接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */
public interface FriendGroupSvc {

	/**
	 *  保存好友记录
	 * @author xiehuilin
	 * @param group  好友实体
	 * @version V0.0.1
	 * @date 2017/6/14 10:20
	 */
	  Map<String,Object> insert(FriendGroup group);

	/**
	 * 获取我的分组好友列表
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	public List<FriendGroup> getFriendGroupById(FriendGroup friendGroup);
	

	/**
	 * 获取我的分组
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	public List<FriendGroup> getGroupByUserId(FriendGroup friendGroup);

	 Map<String,Object> update(FriendGroup friendGroup);


	/**
	 * 修改我的好友分组关系
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:24:12
	 * @version V0.0.1
	 * @param friendGroup
	 */
	public void updateFriendGroup(FriendGroup friendGroup);

	/**
	 * 添加好友消息列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 16:35
	 */
	List<FriendGroup>  getFriendsList(FriendGroup friendGroup);
	
	/**
	 * 删除好友关系
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	void savedelFriends(FriendGroup friendGroup);
	
	/**
   	* 获取最近好友列表
   	* @author zyting
   	* @param
   	* @return
   	* @version V0.0.1
   	* @date 2017/08/07 16:51
   	*/
	List<FriendGroup> getLatelyFriendList(FriendGroup friendGroup);
	
	/**
	 * 修改好友最新联系时间
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 上午10:53:49
	 * @version V0.0.1
	 * @param friendGroup
	 */
	void updateUnused(FriendGroup friendGroup);

	void updateInfo(FriendGroup friendGroup);
}
