package com.qixin.teamwork.biz.order.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.framework.utils.BaseModel;

/**
 * 订单实体类
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午1:26:39
 * @version V0.0.3
 */
public class Order extends BaseModel{

	private Long  orderId;//'订单id',
	private String  orderPayNo;// '支付流水号',
	private BigDecimal   payFee;// '支付金额',
	private Byte   payState;//'支付状态：0，未支付；1，支付成功；2，待支付订单;3,取消订单；4，订单失效；',
	private Timestamp  createTime;//'创建时间',
	private Timestamp   payTime;//'支付时间',
	private Long  userId;// '用户id',
	private String  orderNo; //'订单编号',
	private Timestamp   endTime;//'完成时间',
	private Byte    isValid;// '是否有效: 0 否 1 是',
	private Long    leId;//'轻企id',
	private Long authId;//轻企认证、年审id
	private Byte orderType;//订单类型： 0 认证 、1 奖励、2 惩罚
	private Long eiId;//行动id
	private Byte rpCategory;//奖惩类别：0 滞后完成 罚 、1最低分罚、2最高分奖',
	private Long createBy;
	
	public Byte getRpCategory() {
		return rpCategory;
	}

	public void setRpCategory(Byte rpCategory) {
		this.rpCategory = rpCategory;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderPayNo() {
		return orderPayNo;
	}
	public void setOrderPayNo(String orderPayNo) {
		this.orderPayNo = orderPayNo;
	}
	public BigDecimal getPayFee() {
		return payFee;
	}
	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}
	public Byte getPayState() {
		return payState;
	}
	public void setPayState(Byte payState) {
		this.payState = payState;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getPayTime() {
		return payTime;
	}
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Long getEiId() {
		return eiId;
	}
	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	
	public Byte getIsValid() {
		return isValid;
	}

	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}

	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
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

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
}
