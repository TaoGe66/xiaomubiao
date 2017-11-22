package com.qixin.teamwork.biz.light.svc;

import com.qixin.teamwork.biz.light.model.LightAuth;

/**
 * 轻企业认证接口
 * @author wuchao
 * @date 2017年9月6日
 * @time 上午10:09:32
 * @version V0.0.3
 */
public interface LightAuthSvc {

	/**
	 * 保存轻企业认证信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 上午10:46:52
	 * @version V0.0.3
	 * @param lightAuth
	 */
	public void saveAuth(LightAuth lightAuth);
	
	
	/**
	 * 修改轻企业认证信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 上午10:47:46
	 * @version V0.0.3
	 * @param lightAuth
	 */
	public void updateAuth(LightAuth lightAuth);
	
	/**
	 * 轻企列表认证信息
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午3:29:37
	 * @version V0.0.3
	 * @param lightAuth
	 * @return
	 */
	public LightAuth infoAuth(LightAuth lightAuth);
	
	/**
	 * 根据id查询认证信息
	 * @author wuchao
	 * @date 2017年9月20日
	 * @time 下午1:36:57
	 * @version V0.0.3
	 * @param id
	 * @return
	 */
	public LightAuth getAuthInfo(Long id);
}
