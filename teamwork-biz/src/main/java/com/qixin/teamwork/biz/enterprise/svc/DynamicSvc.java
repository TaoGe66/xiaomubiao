package com.qixin.teamwork.biz.enterprise.svc;

import java.util.List;

import com.qixin.teamwork.biz.enterprise.model.Dynamic;

/**
 * 动态接口
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:03:23
 * @version V0.0.1
 */
public interface DynamicSvc {

	/**
	  * 轻企动态列表
	  * @author zyting
	  * @date 2017年9月6日
	  * @time 下午4:08:50
	  * @version V0.0.1
	  */
	List<Dynamic> dynamicList(Dynamic dynamic);
	
	/**
	 * 轻企动态详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:53:40
	 * @version V0.0.3
	 * @param dynamic
	 * @return
	 */
	Dynamic infoDynamic(Dynamic dynamic);
	
	/**
	 * 保存动态信息
	 * @author wuchao
	 * @date 2017年9月14日
	 * @time 下午1:49:11
	 * @version V0.0.3
	 * @param dynamic
	 */
	public void saveDynamic(Dynamic dynamic);
	
	Dynamic dynamic(Dynamic dynamic);
	
	
}
