package com.qixin.teamwork.biz.group.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.group.model.FriendGroup;

/**
 * 我的好友dao
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:33
 * @version V1.0
 */

@Repository
public class FriendGroupDao extends BaseDao{

	/**
	*  保存好友记录
	* @author xiehuilin
	* @param group  好友实体
	* @version V0.0.1
	* @date 2017/6/14 10:20
	*/
	public  void  insert(FriendGroup group){
		this.insert("com.qixin.teamwork.biz.group.dao.FriendGroupDao.insert",group);
	}

	/**
	 * 获取我的分组好友列表
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	public List<FriendGroup> getFriendGroupById(FriendGroup friendGroup){
		return queryForList("com.qixin.teamwork.biz.group.dao.FriendGroupDao.getFriendGroupById", friendGroup);
	}
	
	/**
	 * 获取我的分组
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	public List<FriendGroup> getGroupByUserId(FriendGroup friendGroup){
		return queryForList("com.qixin.teamwork.biz.group.dao.FriendGroupDao.getGroupByUserId", friendGroup);
	}

	
	public void update(FriendGroup friendGroup){
		update("com.qixin.teamwork.biz.group.dao.FriendGroupDao.update", friendGroup);
	}

	/**
	 * 删除好友关系
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
	 */
	public void delFriends(FriendGroup friendGroup){
		update("com.qixin.teamwork.biz.group.dao.FriendGroupDao.delFriends", friendGroup);
	}
	/**
	 *修改我的好友分组关系
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:25:09
	 * @version V0.0.1
	 * @param friendGroup
	 */
	public void updateFriendGroup(FriendGroup friendGroup) {
		update("com.qixin.teamwork.biz.group.dao.FriendGroupDao.updateFriendGroup", friendGroup);
	}
	/**
	* 添加好友消息列表
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 16:35
	*/
	public  List<FriendGroup>  getFriendsList(FriendGroup friendGroup){
		return this.queryForList("com.qixin.teamwork.biz.group.dao.FriendGroupDao.getFriendsList",friendGroup);
	}
	/**
	* 校验是否是好友关系
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/2 10:42
	*/
	public  List<FriendGroup> getFriendnexus(FriendGroup friendGroup){
		return this.queryForList("com.qixin.teamwork.biz.group.dao.FriendGroupDao.getFriendnexus",friendGroup);
	}
	
   /**
   	* 获取最近好友列表
   	* @author zyting
   	* @param
   	* @return
   	* @version V0.0.1
   	* @date 2017/08/07 16:51
   	*/
	public  List<FriendGroup> getLatelyFriendList(FriendGroup friendGroup){
		return this.queryForList("com.qixin.teamwork.biz.group.dao.FriendGroupDao.getLatelyFriendList",friendGroup);
	}

	/**
	 * 修改好友最新联系时间
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 上午10:56:45
	 * @version V0.0.1
	 * @param friendGroup
	 */
	public void updateUnused(FriendGroup friendGroup) {
		update("com.qixin.teamwork.biz.group.dao.FriendGroupDao.updateUnused", friendGroup);
	}
	
}
