package com.qixin.teamwork.biz.order.svc;

import com.qixin.teamwork.biz.order.model.Order;

/**
 * 订单接口
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午12:01:04
 * @version V0.0.3
 */
public interface OrderSvc {

	/**
	 * 保存订单信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:53:03
	 * @version V0.0.3
	 * @param order
	 * @param ordertype 订单类型 0 年审  1 奖罚
	 */
	public Order saveOrder(Order order,Integer ordertype);
	
	/**
	 * 修改订单信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:53:21
	 * @version V0.0.3
	 * @param order
	 */
	public void updateOrder(Order order);
	
	/**
	 * 订单详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:29:47
	 * @version V0.0.3
	 * @param order
	 * @return
	 */
	public Order infoOrder(Order order);
	
	/**
	 * 根据订单支付流水号更新支付状态
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午11:29:18
	 * @version V0.0.3
	 * @param order
	 */
	public void updateOrderNo(Order order);
}
