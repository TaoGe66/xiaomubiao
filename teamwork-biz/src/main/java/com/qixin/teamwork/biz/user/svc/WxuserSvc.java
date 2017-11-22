package com.qixin.teamwork.biz.user.svc;

import java.util.List;

import com.qixin.teamwork.biz.user.model.User;



/**
 * 用户接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0.3.1
 */
public interface WxuserSvc {
	
	public User relevanceWx(User user, String oId) ;
	
	public User getUserInfo(User user);
	
	public void updateUser(User user);
	
	public void insert(User user);
	
	public User registerUser(User user);
	public List<User> getUserAll();
}
