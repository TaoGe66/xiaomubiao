package com.qixin.teamwork.biz.order.svc;

import java.util.Map;

import com.qixin.teamwork.biz.order.model.OrderPay;

/**
 * 支付流水接口
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:51:07
 * @version V0.0.3
 */
public interface OrderPaySvc {

	/**
	 * 保存交易流水信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:58:43
	 * @version V0.0.3
	 * @param orderPay
	 */
	public void saveOrderPay(OrderPay orderPay);
	
	/**
	 * 修改交易流水信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午1:59:03
	 * @version V0.0.3
	 * @param orderPay
	 */
	public void updateOrderPay(OrderPay orderPay);
	
	
	/**
	 * 支付流水详情
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午10:40:41
	 * @version V0.0.3
	 * @param orderPay
	 * @return
	 */
	public OrderPay infoOrderPay(String orderPayNo);

	/**
	 * 支付完成
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午11:20:23
	 * @version V0.0.3
	 * @param map
	 */
	public void payNotice(Map<Object, Object> map);
}
