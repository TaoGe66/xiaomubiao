package com.qixin.teamwork.biz.light.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.light.model.LightAuth;

/**
 * 轻企业认证DAO
 * @author wuchao
 * @date 2017年9月6日
 * @time 上午10:12:23
 * @version V0.0.3
 */
@Repository
public class LightAuthDao extends BaseDao{

	/**
	 * 保存轻企业认证信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 上午10:51:24
	 * @version V0.0.3
	 * @param lightAuth
	 */
	public void saveAuth(LightAuth lightAuth) {
		insert("com.qixin.teamwork.biz.light.dao.LightAuthDao.insert", lightAuth);
	}

	/**
	 * 修改轻企业认证信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 上午10:51:32
	 * @version V0.0.3
	 * @param lightAuth
	 */
	public void updateAuth(LightAuth lightAuth) {
		update("com.qixin.teamwork.biz.light.dao.LightAuthDao.update", lightAuth);
	}

	/**
	 * 轻企列表认证信息
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午3:31:18
	 * @version V0.0.3
	 * @param lightAuth
	 * @return
	 */
	public LightAuth infoAuth(LightAuth lightAuth) {
		return (LightAuth) queryForObject("com.qixin.teamwork.biz.light.dao.LightAuthDao.infoAuth", lightAuth);
	}

	/**
	 *  根据id查询认证信息
	 * @author wuchao
	 * @date 2017年9月20日
	 * @time 下午1:38:27
	 * @version V0.0.3
	 * @param id
	 * @return
	 */
	public LightAuth getAuthInfo(Long id) {
		return (LightAuth) queryForObject("com.qixin.teamwork.biz.light.dao.LightAuthDao.getAuthInfo", id);
	}

}
