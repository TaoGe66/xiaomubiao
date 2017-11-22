package com.qixin.teamwork.controller.pay;

import java.io.IOException;   
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.CommConstants;
import org.framework.utils.StrUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.payment.utils.config.WeiXinMpConfig;
import com.qixin.payment.utils.other.WeiXinUtil;
import com.qixin.payment.utils.other.wx.HttpKit;
import com.qixin.payment.utils.other.wx.Sha1Util;
import com.qixin.payment.utils.other.wx.WxSign;
import com.qixin.payment.utils.other.wx.XMLUtil;
import com.qixin.teamwork.biz.order.model.Order;
import com.qixin.teamwork.biz.order.model.OrderPay;
import com.qixin.teamwork.biz.order.svc.OrderPaySvc;
import com.qixin.teamwork.biz.order.svc.OrderSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/wxutil")
public class WxUtilController {
	
    @Autowired
    private LogWriter log;
    @Autowired
    private OrderSvc orderSvc;
	@Autowired
	private WxuserSvc userSvc;
	@Autowired
	private OrderPaySvc paySvc;
	
	/**
	 * 订单支付
	 * @param request
	 * @param response
	 * @param userId
	 * @param activityId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "order_submit",method = RequestMethod.GET)
	public Map<String, Object> getOrderSubmit(HttpServletRequest request,HttpServletResponse response, String openID,Long order_id,String thisUrl) throws Exception{
		Map<String, Object> reslutMap = new HashMap<String,Object>();
		Date beginDate=new Date();
		String msg = "success";
		if(StrUtils.isNotEmpty(openID)&& order_id!=null){
			//用户的openId
			Order order=new Order();
			order.setOrderId(order_id);
			Order orderPay = orderSvc.infoOrder(order);
			User user = new User();
			user.setUserId(orderPay.getCreateBy());
			User suser = userSvc.getUserInfo(user);
			if(StrUtils.isNotEmpty(suser.getOpenId())){
				SortedMap<Object,Object> map = get_wx_map(orderPay.getOrderPayNo(), request, openID,thisUrl);
				//订单id
				reslutMap.put("orderId", orderPay.getOrderNo());
				reslutMap.put("appId", WeiXinMpConfig.APP_ID);
				reslutMap.put("timeStamp", map.get("timestamp"));
				reslutMap.put("nonceStr", map.get("nonceStr"));
				reslutMap.put("packageValue", map.get("packageValue"));
				reslutMap.put("signType", map.get("signType"));
				reslutMap.put("signature", map.get("signature"));
				reslutMap.put("paySign", map.get("paySign"));
			}else{
				msg="error";
			}
		}else{
			msg="error";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,"", "订单支付", 0);
		return reslutMap;
	}
	
	
	/**
	 * 订单支付工具类
	 * @param orderNum
	 * @param request
	 * @param openId
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	private SortedMap<Object,Object> get_wx_map(String orderNum, HttpServletRequest request,String openId,String thisUrl) throws JDOMException, IOException{
		SortedMap<Object,Object> map = new TreeMap<Object,Object>();
		//交易流水信息
		OrderPay orderPay=paySvc.infoOrderPay(orderNum);
		String product_name = orderPay.getOrderName().replace("\r", "").replace("\n", "");
		// 金额,微信支付单位是分,将元转为分 (必填参数)
		String order_price = orderPay.getFactFee().multiply(new BigDecimal(100)).longValue() + "";
		// 时间戳,当前时间的毫秒数 (必填参数)
		String timeStamp = Sha1Util.getTimeStamp();
		// 随机字符串 ,随机生成 (必填参数)
		String nonceStr = Sha1Util.getNonceStr();
	    //生成sign签名
	    SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
	    parameters.put("appid", WeiXinMpConfig.APP_ID); 
	    parameters.put("mch_id", WeiXinMpConfig.PARTNER_ID);
	    parameters.put("device_info", "WEB");
	    parameters.put("nonce_str", nonceStr);
	    parameters.put("body", product_name);
	    parameters.put("out_trade_no", orderNum);
	    parameters.put("total_fee", order_price);
	    parameters.put("spbill_create_ip", WeiXinUtil.getIpAddr(request));
	    parameters.put("notify_url", CommConstants.TEAM_WEB+"wxnotice/notice");
	    parameters.put("trade_type", "JSAPI");
	    parameters.put("openid", openId);
	    String sign = WxSign.createSign(parameters,WeiXinMpConfig.PARTNER_KEY);
		//------------------------------------统一支付
		UnifiedOrder unifiedOrder = new UnifiedOrder();
		UnifiedOrder createUnifiedOrder = unifiedOrder.createUnifiedOrder(nonceStr,sign ,Integer.valueOf(order_price), orderNum, WeiXinUtil.getIpAddr(request), openId,product_name);
		String unfiedXml = unifiedOrder.getUnfiedXml(createUnifiedOrder);
		String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String returnStr = HttpKit.post(orderUrl ,unfiedXml);
		Map mapRerturn = XMLUtil.doXMLParse(returnStr);
		
		map.put("orderId", orderNum);
		map.put("appId", WeiXinMpConfig.APP_ID);
		map.put("timestamp", timeStamp);
		map.put("nonceStr", nonceStr);
		// package
		System.out.println(mapRerturn.get("prepay_id"));
		map.put("packageValue", "prepay_id="+mapRerturn.get("prepay_id"));
		// 增加非参与签名的额外参数
		map.put("signType", "MD5");
		//获取access_token
		String access_token = WeiXinUtil.getAccessToken();
		String ticket = WeiXinUtil.getjsTicket(access_token);
		String weixinUrl = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
		String signature = WeiXinUtil.getSignature(ticket,timeStamp,nonceStr,weixinUrl);
		map.put("signature", signature);
		String pay_sign="appId="+WeiXinMpConfig.APP_ID+"&nonceStr="+nonceStr+"&package=prepay_id="+mapRerturn.get("prepay_id")+"&signType=MD5&timeStamp="+timeStamp+"&key="+WeiXinMpConfig.PARTNER_KEY;
		map.put("paySign", WeiXinUtil.stringMD5(pay_sign));
		return map;
	}
	
}
