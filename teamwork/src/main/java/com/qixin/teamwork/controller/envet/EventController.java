package com.qixin.teamwork.controller.envet;

import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.comment.svc.CommentSvc;
import com.qixin.teamwork.biz.dto.CommentDto;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.*;

/** 事件服务api接口
 * Created by
 * Author:xiehuilin
 * Date:2017/6/12 14:01
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/event")
public class EventController {
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
    private WxuserSvc  wxuserSvc;

    /** 1：服务请求、组织活动事件详情   --已废弃
     *  2:根据登录用户获取事件详情
     *     2.1：确认当前登录者身份
     *         事件创建者:根据事件id获取所有参与人数
     *         参与者:校验是否参与
     *  3: 服务请求事件结束，提供用户评论入口,在事项详情页显示事件评价数
     * @author xiehuilin
     * @param  event 事件实体
     * @return  Map 信息集合
     * @version V0.0.1
     * @date 2017/6/12 16:52
     */
    @RequestMapping(path ="/svcPartyEventInfo",method = RequestMethod.GET)
    public Map<String,Object> getEventInfo(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,false);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            Event envetInfo = eventSvc.getEnvetInfo(event);
            List<JoinEvent> joinEvents =new ArrayList<JoinEvent>();
            List<CommentDto> comments=new ArrayList<CommentDto>();
            Date statrDate=new Date(envetInfo.getStartTime().getTime());
            Date endDate=new Date(envetInfo.getEndTime().getTime());
            envetInfo.setsTime(StrUtils.getDate(statrDate));
            Long signTime=envetInfo.getSignTime().getTime();
            envetInfo.seteTime(StrUtils.getDate(endDate));
            Integer isend=0;
            //add by xiehuilin 2017/07/18 是否到结束时间
            boolean flag=false;
            //add by xiehuilin 2017/07/18 是否到截止时间
            boolean cflag=false;
            JoinEvent joinEvent=new JoinEvent();
         //   User user=new User();
            if(envetInfo.getType()==2){
                //活动结束时间
                String endTime=StrUtils.dateToStrLong(new Timestamp(envetInfo.getEndTime().getTime()));
                String closeTime=StrUtils.dateToStrLong(new Timestamp(envetInfo.getCloseTime().getTime()));
                String currentDate = StrUtils.dateToStrLong(new Date());
                flag=StrUtils.compareDate(currentDate,endTime);
                cflag=StrUtils.compareDate(currentDate,closeTime);

            }
            //envetInfo.setEncryptEventId(new DesUtils().encrypt(String.valueOf(envetInfo.geteId())));
            // 事件是否选中责任人
            Integer isSelected=0;//0 否 1 是
            // 角色类型 1 创建人  0 其他(参与者),2 责任人
            Integer roleType=0;
            //用户是否是项目责任人  0 否 1 是
            Integer isDuty=0;
            joinEvent.seteId(event.geteId());

            //确认当前用户身份
            if(null!=envetInfo&&null!=event.getUserId()&&event.getUserId().equals(envetInfo.getCreateBy())){
                roleType=1;
                joinEvents= joinEventSvc.listJoinEventByEid(joinEvent);
            }
            if(roleType!=1&&null!=event.getUserId()){
                joinEvent.setUserId(event.getUserId());
                joinEvents= joinEventSvc.listJoinEventByEid(joinEvent);
            }
            if(envetInfo.getType()==1){
                JoinEvent queryJoinEvent=new JoinEvent();
                queryJoinEvent.seteId(joinEvent.geteId());
                List<JoinEvent> joinEvents1 = joinEventSvc.listJoinEventByEid(queryJoinEvent);
                if(null!=joinEvents1&&joinEvents1.size()>0){
                    for (JoinEvent je:joinEvents1){
                        if(isSelected==0){
                            isSelected=je.getState()==1?1:isSelected;
                        }


                    }
                }
                joinEvent.setUserId(event.getUserId());
                List<JoinEvent>  joinEvent2= joinEventSvc.listJoinEventByEid(joinEvent);
                if(null!=joinEvent2&&joinEvent2.size()>0){
                    for (JoinEvent je2:joinEvent2){
                        isDuty=je2.getState()==1?1:0;
                    }
                }
            }
            resMap.put("event",envetInfo);
            resMap.put("isSelected",isSelected);
            resMap.put("joinEvents",joinEvents);
            resMap.put("roleType",roleType);

            if(2==envetInfo.getState()||(envetInfo.getType()==2&&flag)){
                isend=1;
                Comment comment=new Comment();
                comment.seteId(event.geteId());
                comments= commentSvc.listCommentByEid(comment);
            }
            resMap.put("comments",comments);
            resMap.put("msg","success");
            resMap.put("errorCode","");
            resMap.put("isend",isend);
            resMap.put("cflag",cflag);
            resMap.put("signTime",signTime);
            resMap.put("isDuty",isDuty);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件详情", 0);
        return resMap;
    }



    /**
     *  根据事件id获取事件下的参与者列表
     * @author xiehuilin
     * @param  event 事件实体
     * @param  parameter 分页实体
     * @return  map 集合
     * @version V0.0.1
     * @date 2017/6/13 11:25
     */
    @RequestMapping(path ="/joinEventUserList",method = RequestMethod.GET)
    public  Map<String,Object> joinEventUserList(HttpServletRequest request, Event event, PaginationParameter parameter){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,false);
        boolean isNull=resMap.size()>0?false:true;
        List<JoinEvent> joinEvents=new ArrayList<JoinEvent>();
        if(isNull){
            JoinEvent joinEvent=new JoinEvent();
            joinEvent.seteId(event.geteId());
            joinEvents= joinEventSvc.listJoinEventByEid(joinEvent);
            resMap.put("msg","success");

        }
        resMap.put("joinEvents",joinEvents);
        resMap.put("errorCode","");
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件参与者列表", 0);
        return resMap;
    }



    /**
     * 根据事件id获取事件描述
     * 1：获取事件信息
     * 2：组织活动:根据事件id和用户id获取事项提现
     * @author xiehuilin
     * @param  event 事件实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
    @RequestMapping(path ="/getEventDes",method = RequestMethod.GET)
    public  Map<String,Object>  getEventDes(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,false);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            Event envetInfo = eventSvc.getEnvetInfo(event);
            envetInfo.setsTime(StrUtils.getDate(new Date(envetInfo.getStartTime().getTime())));
            envetInfo.seteTime(StrUtils.getDate(new Date(envetInfo.getEndTime().getTime())));
            User user=new User();
            //事件是否结束  0 否 1 是
            Integer isEnd=0;
            // 角色类型 1 创建人  0 责任人 2 参与者
            Integer roleType=2;
            //是否选中责任人 0 否 1 是
            Integer isSelected=0;
            if(null!=envetInfo){
                if(event.getUserId()!=null&&event.getUserId().equals(envetInfo.getCreateBy())){
                    roleType=envetInfo.getType()==0?0:1;
                }
                //@ 2：组织活动:根据事件id和用户id获取事项提现
                //if(envetInfo.getType()==0){
                JoinEvent joinEvent=new JoinEvent();
                joinEvent.setUserId(event.getUserId());
                joinEvent.seteId(event.geteId());
                List<JoinEvent> joinEvents = joinEventSvc.listJoinEventByEid(joinEvent);
                if(null!=joinEvents&&joinEvents.size()>0){
                    JoinEvent dto = joinEvents.get(0);
                    //envetInfo.setFrequency(dto.getFrequency());
                    if(envetInfo.getType()==1){
                        if(dto.getUserId().equals(event.getUserId())&&dto.getState()==1&&dto.getType()==1){
                            roleType=0;
                        }
                    }
                }
                //}

                isEnd= envetInfo.getState()==2||envetInfo.getState()==6||envetInfo.getState()==7||envetInfo.getState()==8?1:0;
                //add by xiehuilin 20170727 活动、目标创建人即责任人
                if(envetInfo.getType()==2||envetInfo.getType()==0){
                    user.setUserId(envetInfo.getCreateBy());
                    user=wxuserSvc.getUserInfo(user);
                }else{
                    //add by xiehuilin 20170727 获取需求责任人
                    JoinEvent je=new JoinEvent();
                    je.seteId(envetInfo.geteId());
                    je.setType(new Byte("1"));
                    je.setState(new Byte("1"));
                    JoinEvent relust = joinEventSvc.JoinEventInfo(je);
                    isSelected=null!=relust?1:0;
                    Long userId=null!=relust?relust.getUserId():envetInfo.getCreateBy();
                    user.setUserId(userId);
                    user=wxuserSvc.getUserInfo(user);
                }

            }
            resMap.put("msg","success");
            resMap.put("errorCode","");
            resMap.put("roleType",roleType);
            resMap.put("envetInfo",envetInfo);
            resMap.put("isEnd",isEnd);
            resMap.put("user",user);
            resMap.put("isSelected",isSelected);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件描述", 0);
        return resMap;
    }
    
    
    /**
     * 目标详情
     * @author zyting
     * @param  event 事件实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
    @RequestMapping(path ="/getEventInfoDes",method = RequestMethod.GET)
    public  Map<String,Object>  getEventInfoDes(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        if(event.geteId()!=null){
            Event envetInfo = eventSvc.getEnvetInfo(event);
            envetInfo.setsTime(StrUtils.getDate(new Date(envetInfo.getStartTime().getTime())));
            if(envetInfo.getEndTime()!=null){
            	envetInfo.seteTime(StrUtils.getDate(new Date(envetInfo.getEndTime().getTime())));
            }
            //事件是否结束  0 否 1 是
            Integer isEnd=0;
                   isEnd= envetInfo.getState()==2||envetInfo.getState()==6||envetInfo.getState()==7||envetInfo.getState()==8?1:0;
                JoinEvent joinEvent=new JoinEvent();
                joinEvent.setUserId(event.getUserId());
                joinEvent.setState((byte)1);
                joinEvent.seteId(event.geteId());
                List<JoinEvent> joinEvents = joinEventSvc.eventJoinList(joinEvent);
              //我的角色
              Byte  roleType = joinEvents.get(0).getType();
              
            //目标参与人
             // if(roleType==0){
            	  joinEvent.setTypeStr("2,3,4");
            	  resMap.put("joinPeople",joinEventSvc.joinEventPeople(joinEvent));
           //   }
             //统计行动
              EventItem eventItem = new EventItem();
              eventItem.seteId(event.geteId());
              //全部
              resMap.put("countAll",eventItemSvc.listEventItem(eventItem).size()); 
              //未完成
              eventItem.setStateStr("6");
              resMap.put("countSix",eventItemSvc.listEventItem(eventItem).size()); 
              //已完成
              eventItem.setStateStr("3");
              resMap.put("countThree",eventItemSvc.listEventItem(eventItem).size()); 
              //已终止
              eventItem.setStateStr("5");
              resMap.put("countFive",eventItemSvc.listEventItem(eventItem).size()); 
              //延期完成
              eventItem.setStateStr("4");
              resMap.put("countFour",eventItemSvc.listEventItem(eventItem).size()); 
            resMap.put("msg","success");
            resMap.put("errorCode","");
            resMap.put("roleType",roleType);
            resMap.put("envetInfo",envetInfo);
            resMap.put("isEnd",isEnd);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取目标详情", 0);
        return resMap;
    }
    
    
    
    
    

    /**
     * 更新事件
     * @author xiehuilin
     * @param  event 事件实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
    @RequestMapping(path ="/updateEvetn",method = RequestMethod.POST)
    public  Map<String,Object> update(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,false);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            eventSvc.updateEnvet(event);
            resMap.put("msg","success");
            resMap.put("errorCode","");
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件描述", 0);
        return resMap;
    }
    /**
     * 事项列表
     * @author xiehuilin
     * @param  event 事件实体
     * @param  paper 分页实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
    @RequestMapping(path ="/listEventItem",method = RequestMethod.GET)
    public Map<String,Object> listEventItem(HttpServletRequest request, Event event,PaginationParameter paper){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,false);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            EventItem eventItem=new EventItem();
            eventItem.seteId(event.geteId());
            eventItem.setPaper(paper);
            List<EventItem> eventItems = eventItemSvc.listEventItem(eventItem);
            resMap.put("item",eventItems);
            resMap.put("msg","success");
            resMap.put("errorCode","");
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件下事项列表", 0);
        return resMap;
    }


    /**
     * 服务请求根据事件id获取该事件是否已选中责任人
     * @author xiehuilin
     * @param [, event]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version V0.0.1
     * @date 2017/6/20 20:50
     */
    @RequestMapping(path ="/joinEventIsSelect",method = RequestMethod.GET)
    public  Map<String,Object> joinEventIsSelect(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,true);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            Integer isSelected=0;//0 否 1 是
            Integer roleType=0;//0 创建人 1 责任人
            JoinEvent joinEvent=new JoinEvent();
            joinEvent.seteId(event.geteId());
            List<JoinEvent> joinEvents = joinEventSvc.listJoinEventByEid(joinEvent);
            if(null!=joinEvents&&joinEvents.size()>0){
                for (JoinEvent je:joinEvents){
                    isSelected=je.getState()==1?1:isSelected;
                    if(je.getState()==1){
                        isSelected=1;
                        roleType=event.getUserId()==je.getUserId()?1:roleType;
                    }
                }
            }
            resMap.put("isSelected",isSelected);
            resMap.put("roleType",roleType);
            resMap.put("msg","success");
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "事件是否选中责任人", 0);
        return resMap;
    }



    /**
     * 校验客户端请求参数合法性
     * @param event
     * @param flag 是否开启参数校验   true 是  fasle 否
     * @return
     */
    private Map<String, Object> checkPara(Event event,boolean flag) {
        String start="";
        String end="";
        Map<String, Object>  reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg="success";
        String state="1";  // 1 成功  0 失败
        if(null==event){
            errorCode= APIErrorMap.errorMap.get("0");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //事件id为空
        if (event.geteId()==null||StrUtils.isEmpty(String.valueOf(event.geteId()))){
            errorCode= APIErrorMap.errorMap.get("2");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //用户id
        if (flag&&StrUtils.isEmpty(String.valueOf(event.getUserId()))){
            errorCode= APIErrorMap.errorMap.get("3");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        return reslutMap;
    }
    private Map<String, Object> returnMap(Map<String, Object> reslutMap,String errorCode, String msg, String state,boolean isface) {
        reslutMap.put("errorCode", errorCode);
        reslutMap.put("msg", msg);
        reslutMap.put("state", state);
        return reslutMap;
    }

    /**
     * 新增事件
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午2:20:10
     * @version V0.0.1
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path ="/saveEvent",method = RequestMethod.POST)
    public Map<String,Object> saveEvent(HttpServletRequest request,@RequestBody Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(event.getType() !=null && event.getCreateBy() !=null){
            event.setStartTime(DateUtils.StrToDateOne(event.getStrStartTime()));
            if (!StrUtils.isEmpty(event.getStrEndTime())) {
            	event.setEndTime(ToDoRemindUtils.getWeeHoursEndTime(DateUtils.StrToDateOne(event.getStrEndTime())));
                event.setCloseTime(ToDoRemindUtils.getWeeHoursEndTime(DateUtils.StrToDateOne(event.getStrEndTime())));
            }
            eventSvc.saveEnvet(event);
            msg="success";
            resMap.put("info", event);
        }
        resMap.put("msg", msg);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "新增事件", 0);
        return resMap;
    }

    /**
     * 修改事件
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午2:24:45
     * @version V0.0.1
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path ="/updateEnvet",method = RequestMethod.POST)
    public Map<String,Object> updateEvent(HttpServletRequest request,@RequestBody Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(event.getType() !=null && event.geteId() !=null){
            if (!StrUtils.isEmpty(event.getStrCloseTime())) {
                event.setCloseTime(DateUtils.StrToDate(event.getStrCloseTime()));
            }
            eventSvc.updateEnvet(event);
            msg="success";
        }
        resMap.put("msg", msg);
        logWriter.saveSystemLog(request,"", "",beginDate, "", " 修改事件", 0);
        return resMap;
    }

    /**
     * 我的事件列表
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午3:19:50
     * @version V0.0.1
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path="/listJoin",method = RequestMethod.GET)
    public Map<String,Object> listJoin(HttpServletRequest request,JoinEvent joinEvent,PaginationParameter page){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        List<JoinEvent> joinList = new ArrayList<JoinEvent>();
        joinEvent.setPaper(page);
        if(joinEvent.getUserId()!=null){
            joinList=joinEventSvc.listJoinEvent(joinEvent);
            msg="success";
        }
        reslutMap.put("list", joinList);
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,joinEvent.getUserId()+"", "我的事件列表", joinList.size());
        return reslutMap;
    }

    /**
     * 我的事件列表
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午3:19:50
     * @version V0.0.1
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path="/listJoinTwo",method = RequestMethod.GET)
    public Map<String,Object> listJoinTwo(HttpServletRequest request,JoinEvent joinEvent,PaginationParameter page){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        List<JoinEvent> joinList = new ArrayList<JoinEvent>();
        if(joinEvent.getUserId()!=null){
            joinList=joinEventSvc.listJoinEvent(joinEvent);
            msg="success";
        }
        reslutMap.put("list", joinList);
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,joinEvent.getUserId()+"", "我的事件列表", joinList.size());
        return reslutMap;
    }
    /**
     * 删除我的事件
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午3:24:56
     * @version V0.0.1
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path="/deleteJoin",method = RequestMethod.GET)
    public Map<String,Object> deleteJoin(HttpServletRequest request,JoinEvent joinEvent){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        if(joinEvent.getId() !=null){
            joinEventSvc.deleteJoinEvent(joinEvent);
            msg="success";
        }
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,joinEvent.getId()+"", "删除我的事件", 0);
        return reslutMap;
    }

    /**
     * 校验事件是否存在	count=0 不存在 	count=1 存在
     * @author wuchao
     * @date 2017年6月16日
     * @time 上午11:01:32
     * @version V0.0.1
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path="/checkEvent",method = RequestMethod.GET)
    public Map<String,Object> checkEvent(HttpServletRequest request, Event event){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        int count=0;
        if(event.getCreateBy() !=null &&  !StrUtils.isEmpty(event.getName()) ){
            Event even=eventSvc.getEnvetInfo(event);
            if (even !=null) {
                count=1;
            }
            msg="success";
        }
        reslutMap.put("count", count);
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,event.getCreateBy()+"", "校验事件是否存在", 0);
        return reslutMap;
    }

    /**
     * 事件报告记录
     * @author wuchao
     * @date 2017年6月22日
     * @time 下午4:12:33
     * @version V0.0.1
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path="/sumUp",method = RequestMethod.GET)
    public Map<String,Object> sumUp(HttpServletRequest request, Event event){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        if(event.geteId() !=null ){
            Event even=eventSvc.getEnvetInfo(event);
            EventItem eventItem=new EventItem();
            eventItem.seteId(event.geteId());

            int s=eventItemSvc.listItem(eventItem).size();
            eventItem.setStateStr("5");
            int o=eventItemSvc.listItem(eventItem).size();

            eventItem.setStateStr("3,7");
            eventItem.setRank(2);
            int x=eventItemSvc.listhealth(eventItem).size();
            if (even.getState()==0 || even.getState()==1 || even.getState()==3) {
	            EventItem item=new EventItem();
			    item.seteId(event.geteId());
			    item.setStateStr("0,1,6");
			    item.setFinishTime(new Timestamp(new Date().getTime()));
			    int k=eventItemSvc.listhealth(item).size();
					x=x+k;
            }
            double c =1;
            //完成率
            if (s!=0) {
                c = (double)x/s;
                even.setCompRate(String.valueOf(c));
            }else{
                even.setCompRate("1");
            }
            eventItem.setStateStr(null);
            eventItem.setRank(1);
            reslutMap.put("oneRank", eventItemSvc.listUserName(eventItem));
            eventItem.setRank(5);
            reslutMap.put("fiveRank", eventItemSvc.listUserName(eventItem));
            eventItem.setRank(null);
            eventItem.setStateStr("4");
            reslutMap.put("statefour", eventItemSvc.listUserName(eventItem));
            reslutMap.put("stateFive", o);
            msg="success";
            reslutMap.put("even", even);
        }
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,event.getCreateBy()+"", "事件报告记录", 0);
        return reslutMap;
    }



    @RequestMapping(path ="/eventInfo",method = RequestMethod.GET)
    public Map<String,Object> eventInfo(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(event,true);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){

            Event envetInfo = eventSvc.getEnvetInfo(event);
            Integer roleType=event.getUserId().equals(envetInfo.getCreateBy())?1:0;
            resMap.put("event",envetInfo);
            resMap.put("msg","success");
            resMap.put("errorCode","");
            resMap.put("roleType",roleType);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件详情", 0);
        return resMap;
    }

    /**
     *  根据事件id查询事件详情
     * @author wuchao
     * @date 2017年6月26日
     * @time 上午9:49:56
     * @version V0.0.1
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path ="/getInfoEvent",method = RequestMethod.GET)
    public Map<String,Object> getInfoEvent(HttpServletRequest request, Event event){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        if(event.geteId() !=null){

            Event envetInfo = eventSvc.getInfoEvent(event);
            resMap.put("event",envetInfo);
            resMap.put("msg","success");
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件详情", 0);
        return resMap;
    }
    /**
     *@Description: 删除项目
     *               更新项目记录、将该项目下的所有清单更新为无效
     *@author:xiehuilin
     *@param:request
     *@param:event
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/25 14:18
     *@version V 0.0.7
     */
    @RequestMapping(path ="/delEvent",method = RequestMethod.POST)
    public  Map<String,Object> delEvent(HttpServletRequest request, Event event){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(event.geteId())?"error":"success";
        if("success".equals(msg)){
            event.setIsValid(new Byte("0"));
            eventSvc.delEvent(event,0);
        }
        reslutMap.put("msg",msg);
        return  reslutMap;
   }
    /**
     *@Description: 终止项目
     *               1:更新项目记录
     *               2:将该项目下的所有清单更新为无效
     *               3:将未完成的行动置为异常终止
     *               4：将消息表中未推送的更新为无效
     *@author:xiehuilin
     *@param:request
     *@param:event
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/25 14:18
     *@version V 0.0.7
     */
    @RequestMapping(path ="/stopEvent",method = RequestMethod.POST)
    public  Map<String,Object> stopEvent(HttpServletRequest request, Event event){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(event.geteId())?"error":"success";
        if("success".equals(msg)){
            eventSvc.delEvent(event,1);
        }
        reslutMap.put("msg",msg);
        return  reslutMap;
    }
    
    /**
     * 设置目标结束时间
     *@author wuchao
     *@data 2017年11月7日
     *@version V0.0.5
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path ="/updatePutUp",method = RequestMethod.POST)
    public  Map<String,Object> updatePutUp(HttpServletRequest request, Event event){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(event.geteId())?"error":"success";
        if("success".equals(msg)){
            eventSvc.updatePutUp(event);
        }
        reslutMap.put("msg",msg);
        return  reslutMap;
    }

    /**
     * 目标延期
     *@author wuchao
     *@data 2017年11月7日
     *@version V0.0.5
     * @param request
     * @param event
     * @return
     */
    @RequestMapping(path ="/updateDelay",method = RequestMethod.POST)
    public  Map<String,Object> updateDelay(HttpServletRequest request, Event event){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(event.geteId())?"error":"success";
        if("success".equals(msg)){
        	Timestamp strTime=ToDoRemindUtils.getWeeHoursEndTime(DateUtils.StrToDateOne(event.getStrEndTime()));
        	event.setEndTime(strTime);
            eventSvc.updateDelay(event);
        }
        reslutMap.put("msg",msg);
        return  reslutMap;
    }
}