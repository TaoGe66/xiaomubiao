package com.qixin.teamwork.biz.enterprise.svc;

import java.util.List;

import com.qixin.teamwork.biz.enterprise.model.Enterprise;

/**
 * 轻企模板接口
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:03:23
 * @version V0.0.1
 */
public interface EnterpriseSvc {
	/**
	  * 新增轻企
	  * @author zyting
	  * @date 2017年9月6日
	  * @time 下午4:08:50
	  * @version V0.0.1
	  */
	 void insertEnterprise(Enterprise enterprise);
	 
	 /**
	  * 编辑轻企
	  * @author zyting
	  * @date 2017年9月6日
	  * @time 下午4:08:50
	  * @version V0.0.1
	  */
	 void updateEnterprise(Enterprise enterprise);
	 
	 /**
		 * 我的轻企列表
		 * @author zyting
	     * @date 2017年9月6日
	     * @time 下午4:08:50
		 * @param enterprise
		 * @return
		 */
	 List<Enterprise> enterpriseMyList(Enterprise enterprise);
	 
	 /**
		 * 轻企详情
		 * @author zyting
	     * @date 2017年9月6日
	     * @time 下午4:08:50
		 * @param enterprise
		 * @return
		 */
	 Enterprise enterpriseInfo(Enterprise enterprise);

	/**
	 * 根据轻企id获取轻企详情
	 * @author xiehuilin
	 * @param  leId
	 * @return com.qixin.teamwork.biz.enterprise.model.Enterprise
	 * @version V0.0.1
	 * @date 2017/9/7 18:21
	 */
	 Enterprise  getEnterpriseInfo(Long leId);
	 
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
	 List<Enterprise> selectEnterprise(Enterprise enterprise);
	 
	 /**
	   * 解散轻企
	   * @author zyting
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	 void updateisEnterpriseDel(Enterprise enterprise);
}
