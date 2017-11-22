package com.qixin.teamwork.biz.user.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.user.model.User;

/**
 * 用户dao
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:33
 * @version V1.0
 */

@Repository
public class WxuserDao extends BaseDao{

	public void insert(User user){
		insert("com.qixin.teamwork.biz.user.dao.WxuserDao.insert", user);
	}
	
	public User getUserInfo(User user){
		return (User)queryForObject("com.qixin.teamwork.biz.user.dao.WxuserDao.getUserInfo", user);
	}
	
	public void updateUser(User user){
		update("com.qixin.teamwork.biz.user.dao.WxuserDao.updateUser", user);
	}

	  public List<User> getUserAll() {
		    return queryForList("com.qixin.teamwork.biz.user.dao.WxuserDao.getUserAll");
		  }
}
