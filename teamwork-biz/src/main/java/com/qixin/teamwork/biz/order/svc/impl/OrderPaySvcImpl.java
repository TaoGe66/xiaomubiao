package com.qixin.teamwork.biz.order.svc.impl;

import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.light.dao.LightAuthDao;
import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.order.dao.OrderDao;
import com.qixin.teamwork.biz.order.dao.OrderPayDao;
import com.qixin.teamwork.biz.order.model.Order;
import com.qixin.teamwork.biz.order.model.OrderPay;
import com.qixin.teamwork.biz.order.svc.OrderPaySvc;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.svc.TodoSvc;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 支付流水实现
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:51:29
 * @version V0.0.3
 */
@Service("orderPaySvc")
public class OrderPaySvcImpl implements OrderPaySvc{

	@Resource
	private OrderPayDao orderPayDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private LightAuthDao authDao;
	@Autowired
	private EventItemSvc eventItemSvc;
	@Autowired
	private AwardPenaltyListDao awardPenaltyListSvc;
	@Autowired
	private TodoSvc todoSvc;
	/**
	 * 保存交易流水信息
	 */
	@Override
	public void saveOrderPay(OrderPay orderPay) {
		orderPayDao.saveOrderPay(orderPay);		
	}

	/**
	 * 修改交易流水信息
	 */
	@Override
	public void updateOrderPay(OrderPay orderPay) {
		orderPayDao.updateOrderPay(orderPay);
	}

	/**
	 * 交易流水详情
	 */
	@Override
	public OrderPay infoOrderPay(String orderPayNo) {
		return orderPayDao.infoOrderPay(orderPayNo);
	}

	/**
	 * 支付成功
	 */
	@Override
	public void payNotice(Map<Object, Object> map) {
		String pay_type = (String) map.get("pay_type");
		byte payType = new Byte(pay_type);
		String orderNo = map.get("out_trade_no").toString();
		System.out.println("orederNo========================"+orderNo);
		//OrderPay pay=orderPayDao.infoOrderPay(orderNo);
		//根据流水号获取订单详情
		Order order=new Order();
		order.setOrderPayNo(orderNo);
		Order reslutOrder = orderDao.infoOrder(order);
		String trade_no = map.get("transaction_id").toString();
		String total_fee = map.get("cash_fee").toString();
		OrderPay orderPay=new OrderPay();
		orderPay.setOrderPayNo(orderNo);
		orderPay.setTradeNo(trade_no);
		orderPay.setPayType(payType);
		orderPay.setFactFee( new BigDecimal(total_fee).divide(new BigDecimal("100")));
		orderPay.setStatus((byte)1);
		orderPayDao.updateOrderPay(orderPay);
		order.setPayState((byte)1);
		orderDao.updateOrderNo(order);
		System.out.println("getOrderType================"+reslutOrder.getOrderType());
		//add  by xiehuilin 20170919 如果是奖惩类型
		if(reslutOrder.getOrderType()!=0){

			//根据行动id获取行动详情
			EventItem eventItem=new EventItem();
			eventItem.setEiId(reslutOrder.getEiId());
			EventItem eInfo = eventItemSvc.getByEiIdInfo(eventItem);
			//完成状态
			//boolean sameday = StrUtils.isSameday(eInfo.getfTimestamp(), new Date());
			//if(!sameday&&eInfo.getfTimestamp().compareTo(StrUtils.getNowTimestamp())<0){
					//eInfo.setState(new Byte("4"));
			//}else if(!sameday&&eInfo.getfTimestamp().compareTo(StrUtils.getNowTimestamp())>0){
				//eInfo.setState(new Byte("7"));
			//}else {
				eInfo.setState(new Byte("3"));
			//}
			//根据行动id更新行动记录
			eInfo.setfTimestamp(StrUtils.getNowTimestamp());
			eventItemSvc.updateItemState(eInfo);
			//更新奖惩名单
			AwardPenaltyList penaltyList=new AwardPenaltyList();
			System.out.println("getEiId====================="+eInfo.getEiId());
			System.out.println("eInfo.getRpCategory()====================="+eInfo.getRpCategory());
			penaltyList.setEiId(eInfo.getEiId());
			penaltyList.setRpCategory(eInfo.getRpCategory());
			AwardPenaltyList reAward=awardPenaltyListSvc.getAwardPenaltyListInfo(penaltyList);
			reAward.setIsFinish(new Byte("1"));
			awardPenaltyListSvc.update(reAward);
			//根据行动id获取待办记录
			Todo todo=new Todo();
			todo.setEiId(eInfo.getEiId());
			Todo todoInfo = todoSvc.getTodoInfo(todo);
			//更新待办记录
			todoInfo.setFinishTime(StrUtils.getNowTimestamp());
			todoInfo.setIsFinish(new Byte("1"));
			todoSvc.updateTodo(todoInfo);
		}else{
			//支付成功 认证(年审)到期时间
			long nowadays=new Date().getTime();
			LightAuth au=authDao.getAuthInfo(reslutOrder.getAuthId());
			LightAuth light=authDao.infoAuth(au);
			LightAuth auth=new LightAuth();
			if (light==null) {
				auth.setTerm(ToDoRemindUtils.getLagAge(new Timestamp(nowadays), 1));
			}else if (light.getTerm().getTime()<=nowadays) {
				auth.setTerm(ToDoRemindUtils.getLagAge(new Timestamp(nowadays), 1));
			}else if (light.getTerm().getTime()>nowadays) {
				auth.setTerm(ToDoRemindUtils.getLagAge(light.getTerm(), 1));
			}
			auth.setMoney( new BigDecimal(total_fee).divide(new BigDecimal("100")));
			auth.setState((byte)1);
			auth.setId(reslutOrder.getAuthId());
			auth.setIsPay((byte)1);
			authDao.updateAuth(auth);
		}
	}

}
