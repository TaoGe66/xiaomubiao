package com.qixin.teamwork.biz.order.svc.impl;

import com.qixin.payment.utils.config.WeiXinMpConfig;
import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.light.dao.LightAuthDao;
import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.order.dao.OrderDao;
import com.qixin.teamwork.biz.order.dao.OrderPayDao;
import com.qixin.teamwork.biz.order.model.Order;
import com.qixin.teamwork.biz.order.model.OrderPay;
import com.qixin.teamwork.biz.order.svc.OrderSvc;
import org.framework.utils.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 订单实现层
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:26:56
 * @version V0.0.3
 */
@Service("orderSvc")
public class OrderSvcImpl implements OrderSvc{

	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderPayDao orderPayDao;
	@Resource
	private LightAuthDao authDao;
	@Autowired
	private EventItemDao eventItemDao;
	@Autowired
	private AwardPenaltyListDao awardPenaltyListDao;
	/**
	 *  保存订单信息
	 */
	@Override
	public Order saveOrder(Order order,Integer ordertype) {
		if(ordertype==0){
			LightAuth auth=new LightAuth();
			auth.setLeId(order.getLeId());
			LightAuth lightAuth=authDao.infoAuth(auth);
			String type=lightAuth.getType()==0?"认证":"年审";

			String orderNo =Function.creatOrderNum(order.getUserId());
			BigDecimal count=new BigDecimal(WeiXinMpConfig.AUTH_COST);
			order.setPayFee(count);
			order.setOrderPayNo(orderNo);
			order.setPayState((byte)2);
			order.setUserId(order.getUserId());
			order.setLeId(order.getLeId());
			order.setOrderType((byte)0);
			order.setIsValid((byte)1);
			order.setCreateBy(order.getUserId());
			orderDao.saveOrder(order);
			OrderPay orderPay=new OrderPay();
			orderPay.setAuthId(order.getAuthId());
			orderPay.setOrderPayNo(orderNo);
			orderPay.setOrderName(type);
			orderPay.setFactFee(count);
			orderPay.setCreateBy(order.getUserId());
			orderPay.setIsValid((byte)1);
			orderPayDao.saveOrderPay(orderPay);
		}
		if(ordertype==1){
			//根据行动id获取行动详情
			EventItem eventItem=new EventItem();
			eventItem.setEiId(order.getEiId());
			EventItem itemInfo = eventItemDao.getItemInfo(eventItem);
			//根据行动id和奖惩类别获取奖惩金额
			AwardPenaltyList awardPenaltyList=new AwardPenaltyList();
			awardPenaltyList.setEiId(order.getEiId());
			awardPenaltyList.setRpCategory(itemInfo.getRpCategory());
			AwardPenaltyList awardPenaltyListInfo = awardPenaltyListDao.getAwardPenaltyListInfo(awardPenaltyList);
			//生成订单
			order.setUserId(itemInfo.getBeneficiary());
			String orderNo =Function.creatOrderNum(order.getUserId());
			order.setOrderPayNo(orderNo);
			order.setPayFee(awardPenaltyListInfo.getRplMoney());
			order.setPayState((byte)2);
			order.setIsValid((byte)1);
			order.setOrderType(itemInfo.getRpCategory());
			order.setRpCategory(itemInfo.getRpCategory());
			orderDao.saveOrder(order);
			//生成支付记录
			OrderPay orderPay=new OrderPay();
			orderPay.setAuthId(order.getAuthId());
			orderPay.setOrderPayNo(orderNo);
			orderPay.setOrderName(itemInfo.getEiDesc());
			orderPay.setFactFee(awardPenaltyListInfo.getRplMoney());
			orderPay.setCreateBy(order.getCreateBy());
			orderPay.setIsValid((byte)1);
			orderPayDao.saveOrderPay(orderPay);
		}
		return order;
	}

	/**
	 *  修改订单信息
	 */
	@Override
	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	/**
	 * 订单详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:29:47
	 * @version V0.0.3
	 * @param order
	 * @return
	 */
	@Override
	public Order infoOrder(Order order) {
		return orderDao.infoOrder(order);
	}

	/**
	 * 根据订单支付流水号更新支付状态
	 */
	@Override
	public void updateOrderNo(Order order) {
		orderDao.updateOrderNo(order);
	}

}
