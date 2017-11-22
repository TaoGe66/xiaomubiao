package com.qixin.teamwork.biz.enterprise.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.enterprise.model.Enterprise;


/**
 * 轻企DAO层
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:08:50
 * @version V0.0.1
 */
@Repository("enterpriseDao")
public class EnterpriseDao extends BaseDao{
     
	public void insert(Enterprise enterprise){
    	  this.insert("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.insert",enterprise);
     }
	
	public void update(Enterprise enterprise){
		this.update("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.update", enterprise);
	}


	/**
	 * 根据轻企id获取轻企详情
	 * @author xiehuilin
	 * @param  leId
	 * @return com.qixin.teamwork.biz.enterprise.model.Enterprise
	 * @version V0.0.1
	 * @date 2017/9/7 18:21
	 */
	public Enterprise  getEnterpriseInfo(Long leId){
		return (Enterprise) this.queryForObject("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.getEnterpriseInfo",leId);
	}


	/**
	 * 轻企详情
	 * @author zyting
     * @date 2017年9月6日
     * @time 下午4:08:50
	 * @param enterprise
	 * @return
	 */
	public Enterprise enterpriseInfo(Enterprise enterprise){
		return(Enterprise) this.queryForObject("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.enterpriseInfo", enterprise);
	}
	
	/**
	 * 我的轻企列表
	 * @author zyting
     * @date 2017年9月6日
     * @time 下午4:08:50
	 * @param enterprise
	 * @return
	 */
	public List<Enterprise> enterpriseMyList(Enterprise enterprise){
		return this.queryForList("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.enterpriseMyList", enterprise);
	}
	
	/**
   * 查询简称和全程有没有重复
   * @author zyting
   * @date 2017年9月13日
   * @time 下午7:04:57
   * @version V0.0.3
   * @param request
   * @param member
   * @return
   */
	public List<Enterprise> selectEnterprise(Enterprise enterprise){
		return this.queryForList("com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao.selectEnterprise", enterprise);
	}
	
}
