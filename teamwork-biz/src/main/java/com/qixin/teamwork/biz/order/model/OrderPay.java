package com.qixin.teamwork.biz.order.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 订单交易流水实体
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:29:40
 * @version V0.0.3
 */
public class OrderPay extends BaseModel {

	private String   orderPayNo;//'订单支付流水号',
	private String   orderName;// '订单支付名称',
	private BigDecimal   factFee;//'订单实付总额',
	private String   tradeNo;// '在线支付-交易流水号',
	private Byte   status;// '状态：0 未成功  1成功',
	private Long    createBy;// '创建人',
	private Timestamp   createTime;// '创建时间',
	private Byte   isValid;//'是否有效：0 无效  1 有效',
	private Byte payType;//支付方式：1微信；2支付宝；
	private Long authId;//轻企认证、年审id
	private Byte orderType;//订单类型： 0 认证 、1 奖励、2 惩罚
	public String getOrderPayNo() {
		return orderPayNo;
	}
	public void setOrderPayNo(String orderPayNo) {
		this.orderPayNo = orderPayNo;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public BigDecimal getFactFee() {
		return factFee;
	}
	public void setFactFee(BigDecimal factFee) {
		this.factFee = factFee;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public Long getAuthId() {
		return authId;
	}
	public void setAuthId(Long authId) {
		this.authId = authId;
	}
	public Byte getOrderType() {
		return orderType;
	}
	public void setOrderType(Byte orderType) {
		this.orderType = orderType;
	}
	
	
}
