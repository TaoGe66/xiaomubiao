package com.qixin.teamwork.controller.envet;

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyRuleSvc;
import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.comment.svc.CommentSvc;
import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterprise.svc.EnterpriseSvc;
import com.qixin.teamwork.biz.envet.model.CycleTemplet;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.CycleTempletSvc;
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
 * 事件服务api接口
 * Created by
 * Author:xiehuilin
 * Date:2017/6/12 14:01
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/newEvent")
public class TaskController {
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
    @Autowired
    private CycleTempletSvc cycleTempletSvc;

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
            // 0 时间管理即是时间创建人也是事件责任人   1 事项责任人,3轻企成员关注非项目创建人(项目责任人),4轻企成员关注轻企项目创建(项目责任人),5 事件参与人 ,6 行动参与人
            Integer isEventDuty = 0;
            //事件是否结束 1 是 0 否
            Integer isend = 0;
            //责任人列表
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
            //根据用户id和事件id获取用户角色身份
            if(event.getReqType()==1){
            JoinEvent joinEvent=new JoinEvent();
            joinEvent.seteId(envetInfo.geteId());
            joinEvent.setUserId(event.getUserId());
            List<JoinEvent> joinEvents = joinEventSvc.listDignity(joinEvent);
            if(!joinEvents.isEmpty()){
                for(JoinEvent je:joinEvents){
                    if(je.getType()==0||je.getType()==1){
                        isEventDuty=0;
                        break;
                    } else if(je.getType()==2) {
                        isEventDuty=1;
                        break;
                    }else if(je.getType()==3){
                        isEventDuty=5;
                    }else if(isEventDuty!=5&&je.getType()==4){
                        isEventDuty=6;
                    }
                }
             }
        }
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
                    }
                    // 获取事件下的所有事项
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
                   /* isEventDuty = map.containsKey(createBy) ? 1 : 4;
                    isEventDuty = !createBy.equals(envetInfo.getCreateBy()) ? isEventDuty : 0;
                    isEventDuty = followInfo != null ? 4 : isEventDuty;*/
                    //add by xiehuilin 20170930 行动数据列表封装
                    Items(eventItems);
                } else {
                    //查看自己及关注人他人的行动:reqType为 1 或2 根据用户id获取用户关注成员查询我的行动及我关注他人的行动列表
                    // reType 为 0 跟用户id查询该负责的行动列表
                    // 获取事件下的所有事项
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
                   /* isEventDuty = map.containsKey(createBy) ? 1 : 3;
                    isEventDuty = !createBy.equals(envetInfo.getCreateBy()) ? isEventDuty : 0;
                    isEventDuty = followInfo != null ? 4 : isEventDuty;*/
                    //add by xiehuilin 20170930 行动数据列表封装
                    Items(eventItems);
                }

            }
            //非轻企项目
            if (envetInfo.getLeId() == null) {
                Boolean flag = !event.getUserId().equals(envetInfo.getCreateBy()) || event.getReqType() == 0;
                // @ 2.1：事件责任人:根据事件id和用户id获取责任人事项列表
                if (flag) {
                    eventItem.setDutyId(event.getUserId());
                }
                eventItems = OrdinaryItem(event, parameter, isEventDuty,users, createBy, envetInfo, eventItem);
            }
            returnData(event, resMap, isEventDuty, isend, users, eventItems, envetInfo, isThisYear, comments, eventItem);
        }
        logWriter.saveSystemLog(request, "", "", beginDate, "", "获取事件详情", 0);
        return resMap;

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
            User user=new User();
            user.setUserId(item.getUserId());
            user.setHeadUrl(item.getHeadUrl());
            user.setUserName(item.getUserName());
            user.setEiId(item.getEiId());
            users.add(user);
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
    /**
     * 非轻企项目-行动记录数据封装
     *
     * @param event       项目实体
     * @param parameter   分页实体
     * @param isEventDuty 当前用户在项目中的角色
     * @param users       行动参与人实体
     * @param createBy    创建人
     * @param envetInfo   项目明细实体
     * @param eventItem   行动记录实体
     * @author：xiehuilin
     * @return:java.util.List<com.qixin.teamwork.biz.envet.model.EventItem>
     * @date:2017/9/30 12:58
     * @version:V0.0.1
     */
    private List<EventItem> OrdinaryItem(Event event, PaginationParameter parameter, Integer isEventDuty,List<User> users, Long createBy,
                                         Event envetInfo, EventItem eventItem) {
        List<EventItem> eventItems = new ArrayList<EventItem>();
        Integer isEventItemDuty;
        eventItem.setReqType(event.getReqType());
        // @ 2：获取事件下的事项列表
        // 获取事件下的所有事项
        //eventItem.setPaper(parameter);
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
            List<EventItem> removeList=new ArrayList<EventItem>();;
            Map<Long, Object> map = new HashMap<Long, Object>();
            Iterator<EventItem> it = eventItems.iterator();
            while (it.hasNext()) {
                EventItem item = it.next();
                item.setfTime(StrUtils.getDateNowHour(new Date(item.getFinishTime().getTime()), Constant.DEFAULT_DATE_HHmm_FORMAT_PATTERN));
                item.setfTimeStr(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getFinishTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setStartStrTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getStartTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setShowType(DateUtils.dateCompareTo(item.getStartTime(), StrUtils.getNowTimestamp()));
               if (null != item.getfTimestamp()) {
                    item.setuTime(StrUtils.getDateHour(new Date(item.getfTimestamp().getTime())));
               }
               //根据周期id获取周期是否已完成
                if(null!=item.getCtId()){
                   //根据周期id获取该周期是否已完成
                    CycleTemplet templet=new CycleTemplet();
                    templet.setId(item.getCtId());
                    CycleTemplet ctempletInfo = cycleTempletSvc.getCtempletInfo(templet);
                    if(map.containsKey(item.getCtId())){
                        Timestamp startTime = item.getStartTime();
                        EventItem  mei= (EventItem) map.get(item.getCtId());
                        Timestamp startTime1 = mei.getStartTime();
                        if(startTime1.compareTo(startTime)<0&&item.getState()==0&&mei.getState()==0){
                            map.put(item.getCtId(),item);
                            removeList.add(item);
                        }else if(startTime.compareTo(startTime1)>0&&item.getState()!=0&&mei.getState()!=0){
                            map.put(item.getCtId(),item);
                            removeList.add(mei);
                       }else  if(startTime1.compareTo(startTime)<0&&item.getState()!=0&&mei.getState()==0){
                            removeList.add(item);
                        }
                        else  if(startTime1.compareTo(startTime)<0&&item.getState()==0&&mei.getState()!=0){
                            map.put(item.getCtId(),item);
                            removeList.add(mei);
                        }
                    }else {
                        map.put(item.getCtId(),item);
                    }
                }
                TrackRecord record = new TrackRecord();
                record.setEiId(item.getEiId());
                record.setIsRed(new Byte("0"));
                PaginationParameter parameter1=new PaginationParameter();
                item.setrCount(trackRecordSvc.listTrackRecord(record).size());
                //add by xiehuilin 20170809 获取事项跟踪记录
                parameter1.setNumPerPage(0);
                parameter1.setPageNum(1);
                item.setRecordList(trackRecordSvc.listTrackRecord(record));
            }
            if(removeList.size()>0){
            eventItems.removeAll(removeList);
            }
        }
        return eventItems;
    }

    private void Items(List<EventItem> eventItems) {
        if (eventItems.size() > 0) {
            List<EventItem> removeList=new ArrayList<EventItem>();;
            Map<Long, Object> map = new HashMap<Long, Object>();
            Iterator<EventItem> it = eventItems.iterator();
            while (it.hasNext()) {
                EventItem item = it.next();
                item.setfTime(StrUtils.getDateNowHour(new Date(item.getFinishTime().getTime()), Constant.DEFAULT_DATE_HHmm_FORMAT_PATTERN));
                item.setfTimeStr(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getFinishTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setStartStrTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(item.getStartTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                item.setShowType(DateUtils.dateCompareTo(item.getStartTime(), StrUtils.getNowTimestamp()));
                if (null != item.getfTimestamp()) {
                    item.setuTime(StrUtils.getDateHour(new Date(item.getfTimestamp().getTime())));
                }
                //根据周期id获取周期是否已完成
                if(null!=item.getCtId()){
                    //根据周期id获取该周期是否已完成
                    CycleTemplet templet=new CycleTemplet();
                    templet.setId(item.getCtId());
                    CycleTemplet ctempletInfo = cycleTempletSvc.getCtempletInfo(templet);
                    if(map.containsKey(item.getCtId())){
                        Timestamp startTime = item.getStartTime();
                        EventItem  mei= (EventItem) map.get(item.getCtId());
                        Timestamp startTime1 = mei.getStartTime();
                        if(startTime1.compareTo(startTime)<0&&item.getState()==0&&mei.getState()==0){
                            map.put(item.getCtId(),item);
                            removeList.add(item);
                        }else if(startTime.compareTo(startTime1)>0&&item.getState()!=0&&mei.getState()!=0){
                            map.put(item.getCtId(),item);
                            removeList.add(mei);
                        }else  if(startTime1.compareTo(startTime)<0&&item.getState()!=0&&mei.getState()==0){
                            removeList.add(item);
                        }
                        else  if(startTime1.compareTo(startTime)<0&&item.getState()==0&&mei.getState()!=0){
                            map.put(item.getCtId(),item);
                            removeList.add(mei);
                        }
                    }else {
                        map.put(item.getCtId(),item);
                    }
                }
              /*  TrackRecord record = new TrackRecord();
                record.setEiId(item.getEiId());
                record.setIsRed(new Byte("0"));
                PaginationParameter parameter1=new PaginationParameter();
                record.setPaper(parameter1);
                //add by xiehuilin 20170809 获取事项跟踪记录
                item.setRecordList(trackRecordSvc.listTrackRecord(record));*/
                TrackRecord record = new TrackRecord();
                record.setEiId(item.getEiId());
                record.setIsRed(new Byte("0"));
                PaginationParameter parameter1=new PaginationParameter();
                item.setrCount(trackRecordSvc.listTrackRecord(record).size());
                //add by xiehuilin 20170809 获取事项跟踪记录
                parameter1.setNumPerPage(1);
                parameter1.setPageNum(1);
                record.setPaper(parameter1);
                item.setRecordList(trackRecordSvc.listTrackRecord(record));
            }
             if(removeList.size()>0){
                eventItems.removeAll(removeList);
             }
            }

    }

    private void returnData(Event event, Map<String, Object> resMap, Integer isEventDuty, Integer isend, List<User> users, List<EventItem> eventItems, Event envetInfo, Integer isThisYear, Integer comments, EventItem eventItem) {
        //@ 4:事件结束提供用户评论入口,在事项详情页显示事件评价数  start
       this.getDatePoor(envetInfo.getEndTime(),new Date(),envetInfo);
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
        resMap.put("userName", dutyInfo.getUserName());
        resMap.put("isThisYear", isThisYear);
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

    private  void getDatePoor(Date endDate, Date nowDate,Event event) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.after(nowDate)?endDate.getTime() - nowDate.getTime():nowDate.getTime()-endDate.getTime();
        Integer isDelay=nowDate.after(endDate)?1:0;
        event.setIsDelay(isDelay);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
       // day=day>0&&hour>0?day+1:day;
        StringBuilder builder=new StringBuilder();
        if(day>0){
            builder.append(day).append("天");
        }else{
            builder.append(hour).append("小时");
        }
        event.setDuration(builder.toString());
    }



}