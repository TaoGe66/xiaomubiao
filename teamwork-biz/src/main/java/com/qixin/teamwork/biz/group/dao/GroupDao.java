package com.qixin.teamwork.biz.group.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.group.model.Group;

/**
 * 好友组dao
 * @author wuchao
 * @date 2017年6月14日
 * @time 上午9:36:09
 * @version V0.0.1
 */
@Repository
public class GroupDao extends BaseDao{

	/**
	 * 新增组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:41:41
	 * @version V0.0.1
	 * @param group
	 */
	public void saveGroup(Group group) {
		insert("com.qixin.teamwork.biz.group.dao.GroupDao.insert", group);
	}

	/**
	 * 修改组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:41:50
	 * @version V0.0.1
	 * @param group
	 */
	public void updateGroup(Group group) {
		update("com.qixin.teamwork.biz.group.dao.GroupDao.update", group);
	}

	/**
	 *  用户组列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:23:25
	 * @version V0.0.1
	 * @param group
	 * @return
	 */
	public List<Group> listGroup(Group group) {
		return queryForList("com.qixin.teamwork.biz.group.dao.GroupDao.listGroup", group);
	}

	/**
	 *  根据用户id和组名称查询组是否存在
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午1:40:27
	 * @version V0.0.1
	 * @param group
	 * @return
	 */
	public Group infoGroup(Group group) {
		return (Group) queryForObject("com.qixin.teamwork.biz.group.dao.GroupDao.infoGroup", group);
	}

}
