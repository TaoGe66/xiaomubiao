package com.qixin.teamwork.biz.envet.model;

import java.sql.Timestamp;

import org.framework.utils.BaseModel;
import org.framework.utils.PaginationParameter;

/**
 * 事件实体类
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午9:51:01
 * @version V0.0.1
 */
public class Event extends BaseModel{

    private Long eId;
    private String name;//名称
    private String target;//目标
    private String promise;//承诺
    private Timestamp startTime;//开始时间
    private Timestamp endTime;//结束时间
    private Timestamp closeTime;//截止时间
    private Long createBy;//创建人
    private Byte isValid;//是否有效：0 否 1 是
    private Byte pubRange;//可见范围: 0 好友可以见、1平台公开
    private String province;//省
    private String city;//市
    private String district;//区
    private String address;//详细地址
    private String lng;//经度
    private String lat;//纬度
    private String logo;//封面
    private Long maxCount;//最大票数
    private String content;//内容
    private Integer frequency;//0:30 分钟前、1:1小时前(10分钟前)、2:正点、3:一天前(10分钟后)、4:不提现(20分钟后)
    private Byte state;//状态:0  待计划、1待执行、2 已完成、3待关闭
    private Byte type;//事件类型:0 时间管理 、1 服务请求、2 组织活动
    private Long lessCount;//剩余票数
    private Long userId;//当前登录者id
    private String compRate;//事件完成率
    private String sTime;//开始时间
    private String eTime;//结束时间
    private PaginationParameter paper;
    private String encryptEventId;//加密事件id
    private String strCloseTime;//字符串截至时间
    private String summary;   //总结内容
    private Timestamp finishTime;  //完成时间
    private Timestamp signTime;//签到提醒时间
    private Byte reqType;
    private String strStartTime;//字符串开始时间
    private String strEndTime;//字符串结束时间
    private String strFinishTime;//字符串结束时间
    private String typeStr;
    private String stateStr;
    private Long cuserId;
    private String userName;
    private String userNameTwo;
    private String headUrl;
    private Long leId;//轻企id
    private String leName;//轻企名称
    private Long delayCount;//延长次数
    private Long delayTime;//延长时间
    private Timestamp delayDate;//结束时间初始日期
    private String eventPartake;//项目参与人字符串
    private String duration;//时长
    private String stopCauses; //终止原因
    private Integer  isDelay;//任务是否延期

    public Integer getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Integer isDelay) {
        this.isDelay = isDelay;
    }

    public String getStopCauses() {
		return stopCauses;
	}

	public void setStopCauses(String stopCauses) {
		this.stopCauses = stopCauses;
	}

	public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getUserNameTwo() {
		return userNameTwo;
	}
	public void setUserNameTwo(String userNameTwo) {
		this.userNameTwo = userNameTwo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getCuserId() {
		return cuserId;
	}
	public void setCuserId(Long cuserId) {
		this.cuserId = cuserId;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public PaginationParameter getPaper() {
		return paper;
	}
	public void setPaper(PaginationParameter paper) {
		this.paper = paper;
	}
	public Long geteId() {
        return eId;
    }
    public void seteId(Long eId) {
        this.eId = eId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getPromise() {
        return promise;
    }
    public void setPromise(String promise) {
        this.promise = promise;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public Timestamp getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }
    public Long getCreateBy() {
        return createBy;
    }
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    public Byte getIsValid() {
        return isValid;
    }
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }
    public Byte getPubRange() {
        return pubRange;
    }
    public void setPubRange(Byte pubRange) {
        this.pubRange = pubRange;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public Long getMaxCount() {
        return maxCount;
    }
    public void setMaxCount(Long maxCount) {
        this.maxCount = maxCount;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Byte getState() {
        return state;
    }
    public void setState(Byte state) {
        this.state = state;
    }
    public Long getLessCount() {
        return lessCount;
    }
    public void setLessCount(Long lessCount) {
        this.lessCount = lessCount;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getCompRate() {
        return compRate;
    }
    public void setCompRate(String compRate) {
        this.compRate = compRate;
    }
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
    public String getEncryptEventId() {
        return encryptEventId;
    }
    public void setEncryptEventId(String encryptEventId) {
        this.encryptEventId = encryptEventId;
    }
	public String getStrCloseTime() {
		return strCloseTime;
	}
	public void setStrCloseTime(String strCloseTime) {
		this.strCloseTime = strCloseTime;
	}
	public Timestamp getSignTime() {
		return signTime;
	}
	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}
	public Byte getReqType() {
		return reqType;
	}
	public void setReqType(Byte reqType) {
		this.reqType = reqType;
	}
	public String getStrStartTime() {
		return strStartTime;
	}
	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}
	public String getStrEndTime() {
		return strEndTime;
	}
	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}
	public Long getLeId() {
		return leId;
	}
	public void setLeId(Long leId) {
		this.leId = leId;
	}
    public String getLeName() {
        return leName;
    }
    public void setLeName(String leName) {
        this.leName = leName;
    }
	public String getStrFinishTime() {
		return strFinishTime;
	}
	public void setStrFinishTime(String strFinishTime) {
		this.strFinishTime = strFinishTime;
	}
	public Long getDelayCount() {
		return delayCount;
	}
	public void setDelayCount(Long delayCount) {
		this.delayCount = delayCount;
	}
	public Long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}
	public String getEventPartake() {
		return eventPartake;
	}
	public void setEventPartake(String eventPartake) {
		this.eventPartake = eventPartake;
	}
	public Timestamp getDelayDate() {
		return delayDate;
	}
	public void setDelayDate(Timestamp delayDate) {
		this.delayDate = delayDate;
	}
    
}
