package com.qixin.teamwork.biz.enterprise.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.enterprise.model.Dynamic;

/**
 * 动态DAO层
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:08:50
 * @version V0.0.1
 */
@Repository("dynamicDao")
public class DynamicDao extends BaseDao{
	
	public List<Dynamic> dynamicListOne(Dynamic dynamic){
		return queryForList("com.qixin.teamwork.biz.enterprise.dao.DynamicDao.dynamicList", dynamic);
	}

	/**
	 * 轻企动态详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:54:33
	 * @version V0.0.3
	 * @param dynamic
	 * @return
	 */
	public Dynamic infoDynamic(Dynamic dynamic) {
		return (Dynamic) queryForObject("com.qixin.teamwork.biz.enterprise.dao.DynamicDao.infoDynamic", dynamic);
	}

	/**
	 * 保存动态信息
	 * @author wuchao
	 * @date 2017年9月14日
	 * @time 下午1:48:50
	 * @version V0.0.3
	 * @param dynamic
	 */
	public void saveDynamic(Dynamic dynamic) {
		insert("com.qixin.teamwork.biz.enterprise.dao.DynamicDao.insert", dynamic);
	}
	
	/*public void insert(Dynamic dynamic){
		this.insert("com.qixin.teamwork.biz.enterprise.dao.DynamicDao.insert", dynamic);
	}*/

}
