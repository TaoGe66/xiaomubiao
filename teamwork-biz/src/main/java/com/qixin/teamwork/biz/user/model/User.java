package com.qixin.teamwork.biz.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 用户实体类
 * @author zyting
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V1.0.3.1
 */
public class User {
	private Long userId;       //'用户Id',
	private String userName;   //'用户名称'
	private String tel;       //'手机号',
	private String headUrl;    //'头像'
	private Byte sex;          //'性别-0女1男2其他'
	private String birthday;   //生日
	private String company;    //'公司',
	private String title;      //'职务'
	private String openId;     //'微信openId'
	private BigDecimal account; //'个人账户'
	private String province;    //'省'
	private String city ;       // '市'
	private String district;    //'区'
	private String street;      //'街道'
	private String address;     //'详细地址'
	private Long createBy;      //'创建人'
	private Timestamp createTime; //'创建时间'
	private Long updateBy;        //'更新人'
	private Timestamp updateTime; //''更新时间''
	private Byte isValid;       //'是否有效'
	private String qrcodeUrl;   //'二维码地址',
	private String note;      //'个人说明'
	private Long friendId;     //好友id
	private String groupName; //组名
	private Long fgId;   
	private String typeUserId;
	private Byte newPushRecord;//用户是否产生新记录
	private Long eiId;//事项id
	private Long leId;  //轻企Id
	
	private String userIdStr;//用户id字符串
	
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Byte getIsValid() {
		return isValid;
	}
	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getFriendId() {
		return friendId;
	}
	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getFgId() {
		return fgId;
	}
	public void setFgId(Long fgId) {
		this.fgId = fgId;
	}
	public String getTypeUserId() {
		return typeUserId;
	}
	public void setTypeUserId(String typeUserId) {
		this.typeUserId = typeUserId;
	}

	public Byte getNewPushRecord() {
		return newPushRecord;
	}

	public void setNewPushRecord(Byte newPushRecord) {
		this.newPushRecord = newPushRecord;
	}

	public Long getEiId() {
		return eiId;
	}

	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	
}

