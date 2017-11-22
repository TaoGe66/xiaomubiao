package com.qixin.teamwork.biz.order.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.order.model.OrderPay;

/**
 * 支付流水DAO
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:51:43
 * @version V0.0.3
 */
@Repository
public class OrderPayDao extends BaseDao{

	/**
	 * 保存交易流水信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午2:01:21
	 * @version V0.0.3
	 * @param orderPay
	 */
	public void saveOrderPay(OrderPay orderPay) {
		insert("com.qixin.teamwork.biz.order.dao.OrderPayDao.insert", orderPay);
	}

	/**
	 * 修改交易流水信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午2:01:25
	 * @version V0.0.3
	 * @param orderPay
	 */
	public void updateOrderPay(OrderPay orderPay) {
		update("com.qixin.teamwork.biz.order.dao.OrderPayDao.update", orderPay);
	}

	/**
	 * 交易流水详情
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午10:42:59
	 * @version V0.0.3
	 * @param orderPay
	 * @return
	 */
	public OrderPay infoOrderPay(String orderPayNo) {
		return (OrderPay) queryForObject("com.qixin.teamwork.biz.order.dao.OrderPayDao.infoOrderPay", orderPayNo);
	}

}
