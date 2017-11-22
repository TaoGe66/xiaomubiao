package com.qixin.teamwork.biz.group.svc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.group.dao.FriendGroupDao;
import com.qixin.teamwork.biz.group.dao.GroupDao;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.model.Group;
import com.qixin.teamwork.biz.group.svc.GroupSvc;

/**
 * 好友组实现
 * @author wuchao
 * @date 2017年6月14日
 * @time 上午9:36:20
 * @version V0.0.1
 */
@Service("groupSvc")
public class GroupSvcImpl implements GroupSvc{

	@Resource
	private GroupDao groupDao;
	@Autowired
	private FriendGroupDao friendGroupDao;
	/**
	 * 新增组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:38:06
	 * @version V0.0.1
	 * @param group
	 */
	@Override
	public void saveGroup(Group group) {
		groupDao.saveGroup(group);
		FriendGroup friendGroup = new FriendGroup();
		friendGroup.setUserId(group.getCreateBy());
		 List<FriendGroup> l = friendGroupDao.getGroupByUserId(friendGroup);
		 friendGroup.setState((byte)1);
		 for(int i=0;i<l.size();i++){
			 friendGroup.setgId(l.get(i).getgId());
			 if(friendGroupDao.getFriendGroupById(friendGroup).size()==0){
				 Group g = new Group();
				 g.setId(l.get(i).getgId());
				 g.setIsValid((byte)0);
				 groupDao.updateGroup(g);
			 }
		 }
		
	}

	/**
	 * 修改组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:38:34
	 * @version V0.0.1
	 * @param group
	 */
	@Override
	public void updateGroup(Group group) {
		groupDao.updateGroup(group);
	}

	/**
	 * 用户组列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:20:50
	 * @version V0.0.1
	 * @param group
	 */
	@Override
	public List<Group> listGroup(Group group) {
		return groupDao.listGroup(group);
	}

	/**
	 * 根据用户id和组名称查询组是否存在
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午1:39:08
	 * @version V0.0.1
	 * @param group
	 * @return
	 */
	@Override
	public Group infoGroup(Group group) {
		return groupDao.infoGroup(group);
	}

}
