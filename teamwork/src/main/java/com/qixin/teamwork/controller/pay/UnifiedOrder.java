package com.qixin.teamwork.controller.pay;

import org.framework.utils.CommConstants;

import com.qixin.payment.utils.config.WeiXinMpConfig;


public class UnifiedOrder {
	
	private String appid; //是	String(32)	wxd678efh567hg6787	微信分配的公众账号ID（企业号corpid即为此appId）
	private String mch_id; //是	String(32)	1230000109	微信支付分配的商户号
	private String nonce_str; //是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
	private String sign;   //是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
	private String body; //是	String(32)	Ipad mini  16G  白色	商品或支付单简要描述
	private String out_trade_no; //dingdan
	private int total_fee; //订单总金额，单位为分，详见支付金额
	private String spbill_create_ip; //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String notify_url; //是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址
	private String trade_type; //是	String(16)	JSAPI	取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
	private String openId;
	private String device_info;
	

	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	
	 /**
	 * 创建统一下单的xml的java对象
	 * @param bizOrder 系统中的业务单号
	 * @param ip 用户的ip地址
	 * @param openId 用户的openId
	 * @return
	 */
	 public UnifiedOrder createUnifiedOrder(String nonceStr,String sign,int fee,String orderNo,String ip,String openId,String body) {
	 UnifiedOrder payInfo = new UnifiedOrder();
	  payInfo.setAppid(WeiXinMpConfig.APP_ID);//
	  payInfo.setDevice_info("WEB");//
	  payInfo.setMch_id(WeiXinMpConfig.PARTNER_ID);//
	  payInfo.setNonce_str(nonceStr);//
	  payInfo.setBody(body);//
	  payInfo.setSign(sign);
	  payInfo.setOut_trade_no(orderNo);
	  payInfo.setTotal_fee(fee);//
	  payInfo.setSpbill_create_ip(ip);//
	  payInfo.setNotify_url(CommConstants.TEAM_WEB+"wxnotice/notice");//
	  payInfo.setTrade_type("JSAPI");//
	  payInfo.setOpenId(openId);//
	  return payInfo;
	 }
	 
	 
	 public String getUnfiedXml(UnifiedOrder unifiedOrder){
		 StringBuffer sb = new StringBuffer();
		 sb.append("<xml>");
			 sb.append("<appid>"+unifiedOrder.getAppid()+"</appid>");
			 sb.append("<body>"+unifiedOrder.getBody()+"</body>");
			 sb.append("<device_info>"+unifiedOrder.getDevice_info()+"</device_info>");
			 sb.append("<mch_id>"+unifiedOrder.getMch_id()+"</mch_id>");
			 sb.append("<nonce_str>"+unifiedOrder.getNonce_str()+"</nonce_str>");
			 sb.append("<notify_url>"+unifiedOrder.getNotify_url()+"</notify_url>");
			 sb.append("<openid>"+unifiedOrder.getOpenId()+"</openid>");
			 sb.append("<out_trade_no>"+unifiedOrder.getOut_trade_no()+"</out_trade_no>");
			 sb.append("<spbill_create_ip>"+unifiedOrder.getSpbill_create_ip()+"</spbill_create_ip>");
			 sb.append("<total_fee>"+unifiedOrder.getTotal_fee()+"</total_fee>");
			 sb.append("<trade_type>"+unifiedOrder.getTrade_type()+"</trade_type>");
			 sb.append("<sign>"+unifiedOrder.getSign()+"</sign>");
		 sb.append("</xml>");
		return sb.toString();
	 }


}
