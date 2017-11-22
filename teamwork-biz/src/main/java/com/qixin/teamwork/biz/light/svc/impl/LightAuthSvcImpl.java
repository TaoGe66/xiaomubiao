package com.qixin.teamwork.biz.light.svc.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.light.dao.LightAuthDao;
import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.light.svc.LightAuthSvc;

/**
 * 轻企业认证实现层
 * @author wuchao
 * @date 2017年9月6日
 * @time 上午10:10:00
 * @version V0.0.3
 */
@Service("lightAuthSvc")
public class LightAuthSvcImpl implements LightAuthSvc{

	@Resource
	private LightAuthDao authDao;
	
	/**
	 * 保存轻企业认证信息
	 */
	@Override
	public void saveAuth(LightAuth lightAuth) {
		authDao.saveAuth(lightAuth);
		
	}

	/**
	 * 修改轻企业认证信息
	 */
	@Override
	public void updateAuth(LightAuth lightAuth) {
		authDao.updateAuth(lightAuth);
	}

	/**
	 * 轻企列表认证信息
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午3:29:37
	 * @version V0.0.3
	 * @param lightAuth
	 * @return
	 */
	@Override
	public LightAuth infoAuth(LightAuth lightAuth) {
		LightAuth auth=new LightAuth();
		LightAuth au=authDao.infoAuth(lightAuth);
		if (au !=null) {
			auth= authDao.getAuthInfo(au.getId());
		}
		return auth;
	}

	/**
	 * 根据id查询认证信息
	 * @author wuchao
	 * @date 2017年9月20日
	 * @time 下午1:36:57
	 * @version V0.0.3
	 * @param id
	 * @return
	 */
	@Override
	public LightAuth getAuthInfo(Long id) {
		return authDao.getAuthInfo(id);
	}

}
