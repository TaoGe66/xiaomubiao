package com.qixin.teamwork.controller.envet;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyRuleSvc;
import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.comment.svc.CommentSvc;
import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterprise.svc.EnterpriseSvc;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.follow.svc.FollowSvc;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.*;

/**
 * 事件服务api接口   --已废弃
 * Created by
 * Author:xiehuilin
 * Date:2017/6/12 14:01
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/taskEvent")
public class NewEventController {
    @Autowired
    private LogWriter logWriter;
    @Autowired
    private EventSvc eventSvc;
    @Autowired
    private EventItemSvc eventItemSvc;
    @Autowired
    private JoinEventSvc joinEventSvc;
    @Autowired
    private CommentSvc commentSvc;
    @Autowired
    private AwardPenaltyRuleSvc awardPenaltyRuleSvc;
    @Autowired
    private WxuserSvc wxuserSvc;
    @Autowired
    private TrackRecordSvc trackRecordSvc;
    @Autowired
    private FollowSvc followSvc;
    @Autowired
    private EnterpriseSvc enterpriseSvc;

    /**
     * 目标管理详情
     * 1：获取项目详情
     * 是否是轻企项目
     * 否：
     * 1.1：根据用户id、项目id获取用户参与及公开的行动列表、获取行动跟踪记录
     * 1.2：校验用户在项目中的身份
     * 是：
     * 是否关注项目创建或项目责任人
     * 是：
     * 1.3：获取项目创建人或项目责任下的所有行动记录、获取行动跟踪记录
     * 1.4:校验当前用户在项目中的身份
     * 否：
     * 1.5：根据用户id获取我的及我在当前轻企下关注人的行动激励、获取行动跟踪记录
     * 1.6：校验用户在项目中的身份
     * 2：计算项目健康度
     * 3:项目结束提供用户评论入口,在事项详情页显示事件评价数
     *
     * @param
     * @return
     * @author xiehuilin
     * @version V0.0.3
     * @date 2017/9/13 17:08
     */
    @RequestMapping(path = "/timeManag", method = RequestMethod.GET)
    public Map<String, Object> timeManag(HttpServletRequest request, Event event, PaginationParameter parameter) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Date beginDate = new Date();
        resMap = this.checkPara(event, true);
        boolean isNull = resMap.size() > 0 ? false : true;
        if (isNull) {
            // 0 时间管理即是时间创建人也是事件责任人   1 事项责任人,3轻企成员关注非项目创建人(项目责任人),4轻企成员关注轻企项目创建(项目责任人)
            Integer isEventDuty = 0;
            //是否已填写奖惩规则  0 否 1 是
            Integer isRule = 0;
            //事件是否结束 1 是 0 否
            Integer isend = 0;
            //责任人列表,剔除自己
            List<User> users = new ArrayList<User>();
            List<EventItem> eventItems = new ArrayList();
            Long createBy = event.getCreateBy();
            event.setCreateBy(null);
            Event envetInfo = eventSvc.getEnvetInfo(event);
            Integer isThisYear = DateUtils.isThisYear(envetInfo.getStartTime()) && DateUtils.isThisYear(envetInfo.getEndTime()) ? 0 : 1;
            Integer comments = null;
            EventItem eventItem = new EventItem();
            eventItem.setReqType(event.getReqType());
            eventItem.seteId(envetInfo.geteId());
            isEventDuty = createBy.equals(envetInfo.getCreateBy()) ? 0 : 1;
            //是否是轻企项目
            if (envetInfo.getLeId() != null) {
                eventItem.setLeId(envetInfo.geteId());
                //根据轻企id和用户id、项目责任人/创建人id获取用户关注是否关注创建人/责任人
                Follow follow = new Follow();
                follow.setCreateBy(createBy);
                follow.setUserId(envetInfo.getCreateBy());
                follow.setLeId(envetInfo.getLeId());
                Follow followInfo = followSvc.getFollowInfo(follow);
                eventItem.setLeId(envetInfo.getLeId());
                Boolean falgOne = (null != followInfo || createBy.equals(envetInfo.getCreateBy())) && event.getReqType() != 0;
                Boolean falgTwo = !event.getUserId().equals(envetInfo.getCreateBy()) || event.getReqType() == 0;
                //根据轻企id获取轻企详情
                Enterprise enterprise = enterpriseSvc.getEnterpriseInfo(envetInfo.getLeId());
                envetInfo.setLeName(enterprise.getName());
                //当前用户关注项目创建人或项目责任人
                if (falgOne) {
                    if (falgTwo) {
                        isEventDuty = createBy.equals(envetInfo.getCreateBy()) ? 0 : 1;
                        //更新当前行动责任行动跟踪记录为已读
                        tackRecordUpdate(event, isEventDuty, envetInfo);
                    }
                    // 获取事件下的所有事项
                    eventItem.setPaper(parameter);
                    eventItem.setStateStr(null);
                    eventItems = eventItemSvc.listEventItem(eventItem);
                    //获取事件下用户参与的及公开的责任人列表
                    List<EventItem> itemUserList = eventItemSvc.listEventItemJoinUser(eventItem);
                    Map<Long, Object> map = new HashMap<Long, Object>();
                    //add by xiehuilin 20170809 项目行动责任人
                    if (itemUserList != null && itemUserList.size() > 0 && parameter.getPageNum() == 1) {
                        for (EventItem item : itemUserList) {
                            if (null != followInfo) {
                                followEventItemDuty(event, users, item, createBy);
                            } else {
                                eventItemDuty(event, users, item);
                            }
                        }
                    }
                    isEventDuty = map.containsKey(createBy) ? 1 : 4;
                    isEventDuty = !createBy.equals(envetInfo.getCreateBy()) ? isEventDuty : 0;
                    isEventDuty = followInfo != null ? 4 : isEventDuty;
                    //add by xiehuilin 20170930 行动数据列表封装
                    Items(eventItems);
                } else {
                    //查看自己及关注人他人的行动:reqType为 1 或2 根据用户id获取用户关注成员查询我的行动及我关注他人的行动列表
                    // reType 为 0 跟用户id查询该负责的行动列表
                    // 获取事件下的所有事项
                    eventItem.setPaper(parameter);
                    eventItem.setStateStr(null);
                    eventItem.setDutyId(event.getUserId());
                    eventItem.setIsEventDuty(isEventDuty);
                    eventItems = event.getReqType() == 0?eventItemSvc.listEventItem(eventItem) : eventItemSvc.listLightEventItem(eventItem);
                    //获取事件下用户参与的及公开的责任人列表
                    List<EventItem> itemUserList = eventItemSvc.listLightEventItemJoinUser(eventItem);
                    Map<Long, Object> map = new HashMap<Long, Object>();
                    //add by xiehuilin 20170809 项目行动责任人
                    if (itemUserList != null && itemUserList.size() > 0 && parameter.getPageNum() == 1) {
                        for (EventItem item : itemUserList) {
                            eventItemDuty(event, users, item);
                            //用户是否是关注者
                            map.put(item.getDutyId(), item);

                        }
                    }
                    isEventDuty = map.containsKey(createBy) ? 1 : 3;
                    isEventDuty = !createBy.equals(envetInfo.getCreateBy()) ? isEventDuty : 0;
                    isEventDuty = followInfo != null ? 4 : isEventDuty;
                    //add by xiehuilin 20170930 行动数据列表封装
                    Items(eventItems);
                }
                if (isEventDuty == 0 || isEventDuty == 4) {
                    //add  by xiehuilin 2017/07/11 根据事件id查询责任人是否填写奖惩规则
                    AwardPenaltyRule rule = new AwardPenaltyRule();
                    rule.seteId(envetInfo.geteId());
                    List<AwardPenaltyRule> awardPenaltyRuleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(rule);
                    isRule = null != awardPenaltyRuleList && awardPenaltyRuleList.size() > 0 ? 1 : isRule;

                }
            }
            //非轻企项目
            if (envetInfo.getLeId() == null) {
                Boolean flag = !event.getUserId().equals(envetInfo.getCreateBy()) || event.getReqType() == 0;
                // @ 2.1：事件责任人:根据事件id和用户id获取责任人事项列表
                if (flag) {
                    eventItem.setDutyId(event.getUserId());
                    //更新当前行动责任行动跟踪记录为已读
                    tackRecordUpdate(event, isEventDuty, envetInfo);
                }
                //add  by xiehuilin 2017/07/11 根据事件id查询责任人是否填写奖惩规则
                if (isEventDuty == 0 || isEventDuty == 1) {
                    AwardPenaltyRule rule = new AwardPenaltyRule();
                    rule.seteId(event.geteId());
                    List<AwardPenaltyRule> awardPenaltyRuleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(rule);
                    isRule = null != awardPenaltyRuleList && awardPenaltyRuleList.size() > 0 ? 1 : isRule;
                }
                eventItems = OrdinaryItem(event, parameter, isEventDuty, isRule, users, createBy, envetInfo, eventItem, flag);
            }
            returnData(event, resMap, isEventDuty, isRule, isend, users, eventItems, envetInfo, isThisYear, comments, eventItem);


        }
        logWriter.saveSystemLog(request, "", "", beginDate, "", "获取事件详情", 0);
        return resMap;

    }


    /**
     * 服务项目详情
     * 事件创建查看报告
     * 事件责任人打分
     * (时间管理/服务请求选中责任人)获取事件详情
     * 1:根据登录用户获取事件详情
     * 2：获取事件下的事项列表
     * 2.1：确认用户者身份
     * 事件创建者:根据事件id获取所有事项列表
     * 事件责任人:根据事件id和用户id获取责任人事项列表
     * 3:计算事件完成率
     * 4:事件结束提供用户评论入口,在事项详情页显示事件评价数
     *
     * @param
     * @return
     * @author xiehuilin    待优化
     * @version V0.0.1
     * @date 2017/6/24 9:02
     */
    @RequestMapping(path = "/svcManag", method = RequestMethod.GET)
    public Map<String, Object> svcManag(HttpServletRequest request, Event event, PaginationParameter parameter) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Date beginDate = new Date();
        resMap = this.checkPara(event, true);
        boolean isNull = resMap.size() > 0 ? false : true;
        if (isNull) {
            Integer isEventDuty = 1; // 0 事件责任人   1 事项责任人   2事件创建人 、3轻企成员关注非项目创建人(项目责任人),4轻企成员关注轻企项目创建(项目责任人)
            //事件是否结束 1 是 0 否
            Integer isend = 0;
            Integer isRule = 0;//是否已填写奖惩规则  0 否 1 是
            //责任人列表,剔除自己
            List<User> users = new ArrayList<User>();
            Integer comments = null;
            List<EventItem> eventItems = new ArrayList();
            Long createBy = event.getCreateBy();
            event.setCreateBy(null);
            // 获取事件详情
            Event envetInfo = eventSvc.getEnvetInfo(event);
            Integer isThisYear = DateUtils.isThisYear(envetInfo.getStartTime()) && DateUtils.isThisYear(envetInfo.getEndTime()) ? 0 : 1;
            JoinEvent j = new JoinEvent();
            j.seteId(envetInfo.geteId());
            j.setType((byte) 1);
            j.setState(new Byte("1"));
            //获取项目责任人
            JoinEvent je = joinEventSvc.JoinEventInfo(j);
            String dutyName = envetInfo.getUserName();
            if (je != null) {
                envetInfo.setUserNameTwo(je.getUserName());
                dutyName = je.getUserName();
            }
            //是否是项目责任人
            if (je != null && je.getUserId().equals(createBy)) {
                isEventDuty = 0;
            }
            //是否是项目创建人
            if (createBy.equals(envetInfo.getCreateBy())) {
                isEventDuty = 2;
            }
            EventItem eventItem = new EventItem();
            eventItem.seteId(envetInfo.geteId());
            eventItem.setReqType(event.getReqType());
            //是否是轻企项目
            if (envetInfo.getLeId() != null) {
                //根据轻企id和用户id、项目责任人/创建人id获取用户关注是否关注创建人/责任人
                Follow creator=null;
                Follow duty=null;
                if(isEventDuty!=0&&isEventDuty!=2){
                    Follow follow = new Follow();
                    follow.setCreateBy(createBy);
                    follow.setUserId(envetInfo.getCreateBy());
                    follow.setLeId(envetInfo.getLeId());
                    creator= followSvc.getFollowInfo(follow);
                    follow.setUserId(je.getUserId());
                    duty = followSvc.getFollowInfo(follow);
                    eventItem.setLeId(envetInfo.getLeId());
                }
                //根据轻企id获取轻企详情
                Enterprise enterprise = enterpriseSvc.getEnterpriseInfo(envetInfo.getLeId());
                envetInfo.setLeName(enterprise.getName());
                //当前用户关注项目创建人或项目责任人
                if ((null != creator || null != duty || createBy.equals(envetInfo.getCreateBy())||createBy.equals(je.getUserId())) && event.getReqType() != 0) {
                        // @ 2.1：事件责任人:根据事件id和用户id获取责任人事项列表
                        if (!event.getUserId().equals(je.getUserId()) && !event.getUserId().equals(envetInfo.getCreateBy()) || event.getReqType() == 0) {
                            if (isEventDuty == 0&& event.getReqType() == 0) {
                                //更新当前行动责任行动跟踪记录为已读
                                tackRecordUpdate(event, isEventDuty, envetInfo);
                            }
                        }
                        // @ 2：获取事件下的事项列表
                        // 获取事件下的所有事项
                        eventItem.setPaper(parameter);
                        eventItem.setStateStr(null);
                        eventItems = eventItemSvc.listEventItem(eventItem);
                        //获取事项责任人
                        List<EventItem> itemUserList = eventItemSvc.listEventItemJoinUser(eventItem);
                        Map<Long, Object> map = new HashMap<Long, Object>();
                        if (itemUserList != null && eventItems.size() > 0 && parameter.getPageNum() == 1) {
                            for (EventItem item : itemUserList) {
                                //add by xiehuilin 20170809 项目行动责任人
                                followEventItemDuty(event, users, item, createBy);
                                //用户是否是关注者
                                map.put(item.getDutyId(), item);
                            }
                        }
                        isEventDuty = creator != null||duty!=null ? 4 : isEventDuty;
                        //add by xiehuilin 20170930 行动数据列表封装
                        Items(eventItems);
                } else {
                    //查看自己及关注人他人的行动:reqType为 1 或2 根据用户id获取用户关注成员查询我的行动及我关注他人的行动列表
                    // reType 为 0 跟用户id查询该负责的行动列表
                    eventItem.setLeId(envetInfo.getLeId());
                    eventItem.setDutyId(event.getUserId());
                    // 获取事件下的所有事项
                    eventItem.setPaper(parameter);
                    eventItem.setStateStr(null);
                    eventItem.setIsEventDuty(isEventDuty);
                    eventItems = event.getReqType() == 0 ? eventItemSvc.listEventItem(eventItem) : eventItemSvc.listLightEventItem(eventItem);
                    //获取事件下用户参与的及公开的责任人列表
                    List<EventItem> itemUserList = eventItemSvc.listLightEventItemJoinUser(eventItem);
                    Map<Long, Object> map = new HashMap<Long, Object>();
                    //add by xiehuilin 20170809 项目行动责任人
                    if (itemUserList != null && itemUserList.size() > 0 && parameter.getPageNum() == 1) {
                        for (EventItem item : itemUserList) {
                            eventItemDuty(event, users, item);
                            //用户是否是关注者
                            map.put(item.getDutyId(), item);

                        }
                    }
                     isEventDuty = map.containsKey(createBy) ? 1 : 3;
                     isEventDuty = !createBy.equals(envetInfo.getCreateBy()) ? isEventDuty : 0;
                     isEventDuty = creator != null||duty!=null ? 4 : isEventDuty;
                    //add by xiehuilin 20170930 行动数据列表封装
                    Items(eventItems);
                }
                if (isEventDuty == 0 || isEventDuty == 4) {
                    //add  by xiehuilin 2017/07/11 根据事件id查询责任人是否填写奖惩规则
                    AwardPenaltyRule rule = new AwardPenaltyRule();
                    rule.seteId(event.geteId());
                    List<AwardPenaltyRule> awardPenaltyRuleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(rule);
                    isRule = null != awardPenaltyRuleList && awardPenaltyRuleList.size() > 0 ? 1 : isRule;

                }
            }
            //非轻企
            if (envetInfo.getLeId() == null) {
                // @ 2.1：事件责任人:根据事件id和用户id获取责任人事项列表
                Boolean flag = !event.getUserId().equals(je.getUserId()) && !event.getUserId().equals(envetInfo.getCreateBy()) || event.getReqType() == 0;
                if (flag) {
                    eventItem.setDutyId(event.getUserId());
                    //更新当前行动责任行动跟踪记录为已读
                    tackRecordUpdate(event, isEventDuty, envetInfo);
                }
                //add  by xiehuilin 2017/07/11 根据事件id查询责任人是否填写奖惩规则
                if (isEventDuty == 0 || isEventDuty == 1 || isEventDuty == 3) {
                    AwardPenaltyRule rule = new AwardPenaltyRule();
                    rule.seteId(event.geteId());
                    List<AwardPenaltyRule> awardPenaltyRuleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(rule);
                    isRule = null != awardPenaltyRuleList && awardPenaltyRuleList.size() > 0 ? 1 : isRule;
                }
                eventItems = OrdinaryItem(event, parameter, isEventDuty, isRule, users, createBy, envetInfo, eventItem, flag);

            }
            returnData(event, resMap, isEventDuty, isRule, isend, users, eventItems, envetInfo, isThisYear, comments, eventItem);
        }
        logWriter.saveSystemLog(request, "", "", beginDate, "", "获取事件详情", 0);
        return resMap;

    }

    /**
     * 校验客户端请求参数合法性
     *
     * @param event
     * @param flag  是否开启参数校验   true 是  fasle 否
     * @return
     */
    private Map<String, Object> checkPara(Event event, boolean flag) {
        String start = "";
        String end = "";
        Map<String, Object> reslutMap = new HashMap<String, Object>();
        String errorCode = "";
        String msg = "success";
        String state = "1";  // 1 成功  0 失败
        if (null == event) {
            errorCode = APIErrorMap.errorMap.get("0");
            state = "0";
            msg = "error";
            return returnMap(reslutMap, errorCode, msg, state, false);
        }
        //事件id为空
        if (event.geteId() == null || StrUtils.isEmpty(String.valueOf(event.geteId()))) {
            errorCode = APIErrorMap.errorMap.get("2");
            state = "0";
            msg = "error";
            return returnMap(reslutMap, errorCode, msg, state, false);
        }
        //用户id
        if (flag && StrUtils.isEmpty(String.valueOf(event.getUserId()))) {
            errorCode = APIErrorMap.errorMap.get("3");
            state = "0";
            msg = "error";
            return returnMap(reslutMap, errorCode, msg, state, false);
        }
        return reslutMap;
    }

    private Map<String, Object> returnMap(Map<String, Object> reslutMap, String errorCode, String msg, String state, boolean isface) {
        reslutMap.put("errorCode", errorCode);
        reslutMap.put("msg", msg);
        reslutMap.put("state", state);
        return reslutMap;
    }


    /**
     * 计算项目健康度
     *
     * @param envetInfo 事件实体
     * @param eventItem 事项实体
     * @return
     * @author xiehuilin
     * @version V0.0.1
     * @date 2017/7/17 12:03
     */
    private void computeRate(Event envetInfo, EventItem eventItem) {
        envetInfo.setCompRate("100");
        EventItem newEventItem = new EventItem();
        newEventItem.seteId(eventItem.geteId());
        List<EventItem> allEventList = eventItemSvc.listEventItem(newEventItem);
        if (null != allEventList && allEventList.size() > 0) {
            //获取事件下已完成的事项
            newEventItem.setStateStr("3,7");
            newEventItem.setRank(2);
            Double d = 0D;
            List<EventItem> finshEventList = eventItemSvc.listhealth(newEventItem);
            Double finshCount = finshEventList != null ? Double.valueOf(String.valueOf(finshEventList.size())) : 0D;
            if (envetInfo.getState() == 0 || envetInfo.getState() == 1 || envetInfo.getState() == 3) {
                newEventItem.setStateStr("0,1,6");
                newEventItem.setRank(null);
                newEventItem.setFinishTime(new Timestamp(new Date().getTime()));
                d = Double.valueOf(String.valueOf(eventItemSvc.listhealth(newEventItem).size()));
            }
            finshCount += d;
            // @ 3:计算事件完成率
            Double percent = finshCount / Double.valueOf(String.valueOf(allEventList.size()));
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(0);
            envetInfo.setCompRate(nt.format(percent).replace("%", ""));
        }

    }


    /**
     * 获取项目行动责任人
     *
     * @param
     * @return
     * @author xiehuilin
     * @version V0.0.1
     * @date 2017/8/9 15:11
     */
    private void eventItemDuty(Event event, List<User> users, EventItem item) {
        User user;
        if (null != item.getDutyId() && !event.getUserId().equals(item.getDutyId())) {
            user = new User();
            user.setUserId(item.getUserId());
            user.setHeadUrl(item.getHeadUrl());
            user.setUserName(item.getUserName());
            user.setEiId(item.getEiId());
            TrackRecord tr = new TrackRecord();
            tr.seteId(event.geteId());
            //tr.setEiId(u.getEiId());
            tr.setCreateBy(user.getUserId());
            tr.setIsRed(new Byte("0"));
            Byte isRed = trackRecordSvc.listTrackRecord(tr).size() > 0 ? new Byte("0") : new Byte("1");
            user.setNewPushRecord(isRed);
            users.add(user);
        }
    }


    /**
     * 轻企获取项目行动责任人
     *
     * @param
     * @return
     * @author xiehuilin
     * @version V0.0.3
     * @date 2017/9/13 14:20
     */
    private void followEventItemDuty(Event event, List<User> users, EventItem item, Long createBy) {
        if (!createBy.equals(item.getDutyId())) {
            User user;
            user = new User();
            user.setUserId(item.getUserId());
            user.setHeadUrl(item.getHeadUrl());
            user.setUserName(item.getUserName());
            user.setEiId(item.getEiId());
            TrackRecord tr = new TrackRecord();
            tr.seteId(event.geteId());
            //tr.setEiId(u.getEiId());
            tr.setCreateBy(user.getUserId());
            tr.setIsRed(new Byte("0"));
            Byte isRed = trackRecordSvc.listTrackRecord(tr).size() > 0 ? new Byte("0") : new Byte("1");
            user.setNewPushRecord(isRed);
            users.add(user);
        }
    }

    private void tackRecordUpdate(Event event, Integer isEventDuty, Event envetInfo) {
        if (isEventDuty == 0 && event.getReqType() == 0) {
            //更新当前行动责任行动跟踪记录为已读
            TrackRecord record = new TrackRecord();
            record.setCreateBy(event.getUserId());
            record.seteId(envetInfo.geteId());
            record.setIsRed(new Byte("1"));
            trackRecordSvc.updateTrackRecord(record);
        }
    }

    /**
     * 非轻企项目-行动记录数据封装
     *
     * @param event       项目实体
     * @param parameter   分页实体
     * @param isEventDuty 当前用户在项目中的角色
     * @param isRule      是否填写奖惩规则
     * @param users       行动参与人实体
     * @param createBy    创建人
     * @param envetInfo   项目明细实体
     * @param eventItem   行动记录实体
     * @param flag        角色列表
     * @author：xiehuilin
     * @return:java.util.List<com.qixin.teamwork.biz.envet.model.EventItem>
     * @date:2017/9/30 12:58
     * @version:V0.0.1
     */
    private List<EventItem> OrdinaryItem(Event event, PaginationParameter parameter, Integer isEventDuty, Integer isRule, List<User> users, Long createBy,
                                         Event envetInfo, EventItem eventItem, Boolean flag) {
        List<EventItem> eventItems = new ArrayList<EventItem>();
        Integer isEventItemDuty;
        eventItem.setReqType(event.getReqType());
        // @ 2：获取事件下的事项列表
        // 获取事件下的所有事项
        eventItem.setPaper(parameter);
        eventItem.setStateStr(null);
        eventItem.setIsEventDuty(isEventDuty);
        eventItems = eventItemSvc.listEventItem(eventItem);

        //获取事项责任人
        List<EventItem> itemUserList = eventItemSvc.listEventItemJoinUser(eventItem);
        if (itemUserList != null && eventItems.size() > 0 && parameter.getPageNum() == 1) {
            for (EventItem item : itemUserList) {
                //add by xiehuilin 20170809 项目行动责任人
                eventItemDuty(event, users, item);
            }
        }
        Map<Long, String> cycleMap = new HashMap<Long, String>();
        if (eventItems.size() > 0) {
            for (EventItem item : eventItems) {
                item.setfTime(StrUtils.getDateNowHour(new Date(item.getFinishTime().getTime()), Constant.DEFAULT_DATE_HHmm_FORMAT_PATTERN));
                item.setfTimeStr(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getFinishTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setStartStrTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getStartTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setShowType(DateUtils.dateCompareTo(item.getStartTime(), StrUtils.getNowTimestamp()));
                if (null != item.getfTimestamp()) {
                    item.setuTime(StrUtils.getDateHour(new Date(item.getfTimestamp().getTime())));
                }
                TrackRecord record = new TrackRecord();
                record.setEiId(item.getEiId());
                //add by xiehuilin 20170809 获取事项跟踪记录
                item.setRecordList(trackRecordSvc.listTrackRecord(record));
            }
        }

        return eventItems;
    }

    private void Items(List<EventItem> eventItems) {
        if (eventItems.size() > 0) {
            for (EventItem item : eventItems) {
                item.setfTime(StrUtils.getDateNowHour(new Date(item.getFinishTime().getTime()), Constant.DEFAULT_DATE_HHmm_FORMAT_PATTERN));
                item.setfTimeStr(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getFinishTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setStartStrTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getStartTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setShowType(DateUtils.dateCompareTo(item.getStartTime(), StrUtils.getNowTimestamp()));
                if (null != item.getfTimestamp()) {
                    item.setuTime(StrUtils.getDateHour(new Date(item.getfTimestamp().getTime())));
                }
                TrackRecord record = new TrackRecord();
                record.setEiId(item.getEiId());
                //add by xiehuilin 20170809 获取事项跟踪记录
                item.setRecordList(trackRecordSvc.listTrackRecord(record));
            }

        }
    }

    private void returnData(Event event, Map<String, Object> resMap, Integer isEventDuty, Integer isRule, Integer isend, List<User> users, List<EventItem> eventItems, Event envetInfo, Integer isThisYear, Integer comments, EventItem eventItem) {
        //edit by xiehuilin 2017/07/17 计算健康度=(正常完成-提前完成-一星差评)/所有行动
        computeRate(envetInfo, eventItem);
        //@ 4:事件结束提供用户评论入口,在事项详情页显示事件评价数  start
        if (2 == envetInfo.getState() || 6 == envetInfo.getState() || envetInfo.getState() == 7 || envetInfo.getState() == 8) {
            isend = 1;
            Comment comment = new Comment();
            comment.seteId(event.geteId());
            comments = commentSvc.listCommentByEid(comment).size();
        }
        Date statrDate = new Date(envetInfo.getStartTime().getTime());
        Date endDate = new Date(envetInfo.getEndTime().getTime());
        envetInfo.setsTime(StrUtils.getDate(statrDate));
        envetInfo.seteTime(StrUtils.getDate(endDate));
        //@ 4:事件结束提供用户评论入口,在事项详情页显示事件评价数  end
        User queryUser = new User();
        queryUser.setUserId(envetInfo.getCreateBy());
        User dutyInfo = wxuserSvc.getUserInfo(queryUser);
        resMap.put("event", envetInfo);
        resMap.put("item", eventItems);
        resMap.put("msg", "success");
        resMap.put("errorCode", "");
        resMap.put("users", users);
        resMap.put("isend", isend);
        resMap.put("isEventDuty", isEventDuty);
        resMap.put("comments", comments);
        resMap.put("isRule", isRule);
        resMap.put("userName", dutyInfo.getUserName());
        resMap.put("isThisYear", isThisYear);
    }
}