package com.qixin.teamwork.controller.order;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.order.model.Order;
import com.qixin.teamwork.biz.order.svc.OrderSvc;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/order")
public class OrderController {

	@Autowired
    private LogWriter logWriter;
	@Autowired
    private OrderSvc orderSvc;
	
	/**
	 * 下订单
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:16:18
	 * @version V0.0.3
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping(path = "createdOrderPay",method = RequestMethod.POST)
	public Map<String, Object> createdOrderPay(HttpServletRequest request,Order order){
		Map<String, Object> reslutMap = new HashMap<String,Object>();
		String msg = "success";
		if(order.getLeId() !=null){
			Order od=orderSvc.saveOrder(order,0);
			reslutMap.put("order",od);
		}else{
			msg = "error";
		}
		reslutMap.put("msg",msg);
		return reslutMap;
	}
	/**
	 * 奖惩生成订单
	 *@Author:xiehuilin
	 *@param request
	 *@param order 订单实体
	 *@Date :2017/9/21 13:23
	 *@Version:V0.0.3
	java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(path = "awardCreateOrderPay",method = RequestMethod.POST)
	public  Map<String,Object> awardCreateOrderPay(HttpServletRequest request,@RequestBody Order order){
		Map<String, Object> reslutMap = new HashMap<String,Object>();
		String msg = null!=order.getEiId()&&!"".equals(order.getEiId())?"success":"error";
		if("success".equals(msg)){
			Order od=orderSvc.saveOrder(order,1);
			reslutMap.put("order",od);
		}
		reslutMap.put("msg",msg);
		return reslutMap;
	}
}
