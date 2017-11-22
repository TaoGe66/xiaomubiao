package com.qixin.teamwork.biz.order.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.order.model.Order;

/**
 * 订单Dao
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:26:21
 * @version V0.0.3
 */
@Repository
public class OrderDao extends BaseDao{

	/**
	 * 保存订单信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:55:38
	 * @version V0.0.3
	 * @param order
	 */
	public void saveOrder(Order order) {
		insert("com.qixin.teamwork.biz.order.dao.OrderDao.insert", order);
	}

	/**
	 * 修改订单信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:55:17
	 * @version V0.0.3
	 * @param order
	 */
	public void updateOrder(Order order) {
	update("com.qixin.teamwork.biz.order.dao.OrderDao.update", order);	
	}

	/**
	 * 订单详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:30:47
	 * @version V0.0.3
	 * @param order
	 * @return
	 */
	public Order infoOrder(Order order) {
		return (Order) queryForObject("com.qixin.teamwork.biz.order.dao.OrderDao.infoOrder", order);
	}

	/**
	 * 根据订单支付流水号更新支付状态
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午11:41:08
	 * @version V0.0.3
	 * @param order
	 */
	public void updateOrderNo(Order order) {
		update("com.qixin.teamwork.biz.order.dao.OrderDao.updateOrderNo", order);
	}
}
