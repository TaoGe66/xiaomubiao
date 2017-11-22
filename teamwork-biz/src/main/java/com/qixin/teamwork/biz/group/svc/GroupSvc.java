package com.qixin.teamwork.biz.group.svc;

import java.util.List;

import com.qixin.teamwork.biz.group.model.Group;

/**
 * 好友组接口
 * @author wuchao
 * @date 2017年6月14日
 * @time 上午9:36:33
 * @version V0.0.1
 */
public interface GroupSvc {

	/**
	 * 新增组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:38:06
	 * @version V0.0.1
	 * @param group
	 */
	public void saveGroup(Group group);
	
	/**
	 * 修改组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午9:38:34
	 * @version V0.0.1
	 * @param group
	 */
	public void updateGroup(Group group);
	
	/**
	 * 用户组列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:20:50
	 * @version V0.0.1
	 * @param group
	 */
	public List<Group> listGroup(Group group);
	
	/**
	 * 根据用户id和组名称查询组是否存在
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午1:39:08
	 * @version V0.0.1
	 * @param group
	 * @return
	 */
	public Group infoGroup(Group group);
}
