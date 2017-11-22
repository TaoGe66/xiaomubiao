package com.qixin.teamwork.biz.user.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.user.model.UserOpenid;
import com.qixin.teamwork.biz.user.model.Validated;

/**
 * 验证码dao
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:33
 * @version V1.0
 */

@Repository
public class ValidatedDao extends BaseDao{

	public void insertValidated(Validated v){
		insert("com.qixin.teamwork.biz.user.dao.ValidatedDao.insertValidated", v);
	}
	
	public Validated getValidated(Validated v){
		return (Validated)queryForObject("com.qixin.teamwork.biz.user.dao.ValidatedDao.getValidated", v);
	}
	
	public void updateValidated(Validated v){
		update("com.qixin.teamwork.biz.user.dao.ValidatedDao.updateValidated", v);
	}
	
	public void insertUserOpenid(UserOpenid userOpenid){
		insert("com.qixin.teamwork.biz.user.dao.ValidatedDao.insertUserOpenid", userOpenid);
	}
	
	public UserOpenid getUserOpenid(UserOpenid userOpenid){
		return (UserOpenid)queryForObject("com.qixin.teamwork.biz.user.dao.ValidatedDao.getUserOpenid", userOpenid);
	}
	
}
