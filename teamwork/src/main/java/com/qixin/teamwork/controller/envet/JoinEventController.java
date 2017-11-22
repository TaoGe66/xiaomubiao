package com.qixin.teamwork.controller.envet;

import com.qixin.teamwork.biz.base.APIErrorMap; 
import com.qixin.teamwork.biz.dto.JoinEventDto;
import com.qixin.teamwork.biz.enterpriseMember.model.Member;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.model.JsonSing;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;

import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.svc.FriendGroupSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.JSONUtils;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/** 事件用户参与api接口
 * Created by
 * Author:xiehuilin
 * Date:2017/6/14 9:47
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/join")
public class JoinEventController {
    @Autowired
    private LogWriter logWriter;
    @Autowired
    private JoinEventSvc joinEventSvc;
    @Autowired
    private EventSvc eventSvc;
    @Autowired
    private MemberSvc memberSvc;
    @Autowired
    private FriendGroupSvc  friendGroupSvc;
    @Autowired
    private  EventItemSvc eventItemSvc;

    /**
     * 保存用户参与记录
     * @author xiehuilin
     * @param  joinEvent 参与实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
    @RequestMapping(path ="/save",method = RequestMethod.POST)
    public Map<String,Object> save(HttpServletRequest request, JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(joinEvent);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            Map<String, Object> map = joinEventSvc.insert(joinEvent);
            return map;
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "用户参与记录", 0);
        return resMap;
    }
    /**
    *  1:服务请求记录选中责任人记录
    *   1.1:根据事件id获取事件下所有参选人
    *   1.2:更新选中者记录,为该选中者生成待办任务,其他参与者生产落选消息提醒
     *  2.2：生成周报记录
    * @author xiehuilin
    * @param  joinEvent 参与实体
    * @return
    * @version V0.0.1
    * @date 2017/09/08 9:52  新增周报
    */
    @RequestMapping(path ="/screenDuty",method = RequestMethod.POST)
    public  Map<String,Object>  screenDuty(HttpServletRequest request, JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();

        Date beginDate=new Date();
        resMap= this.checkPara(joinEvent);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            joinEventSvc.updateScreenDuty(joinEvent);
            resMap.put("msg","success");
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "筛选参与者", 0);
        return  resMap;
    }
    /**
    * 签到
    * 1:根据事件id获取事件信息
    * 2:根据id获取签到记录,生成新的签到记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/6/14 15:58
    */
    @RequestMapping(path ="/sign",method = RequestMethod.POST)
    public Map<String,Object> sign(HttpServletRequest request, JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(joinEvent);
        String msg="success";
        String errorCode="";
        boolean isNull=resMap.size()>0?false:true;
        //活动是否关闭
        Integer isClose=0;
        if(isNull){
          /*  //根据id获取签到记录,签到记录已json格式存储
            //签到次数
            Integer count=0;
            JoinEvent joinEventInfo = joinEventSvc.getJoinEventInfo(joinEvent);
            List<SingData> list=new ArrayList<SingData>();
            SingData data=null;
            if(!"".equals(joinEventInfo.getHisSign())) {
                JSONArray jsonArray = JSONUtils.toJSONArray(joinEventInfo.getHisSign());
                for(int i=0 ; i < jsonArray.size() ;i++) {
                    //获取每一个JsonObject对象
                    JSONObject myjObject = jsonArray.getJSONObject(i);
                    count=myjObject.getInt("count");
                    JSONArray ja = myjObject.getJSONArray("data");
                    for (int x = 0; x < ja.size(); x++) {
                        data=new SingData();
                        JSONObject result = ja.getJSONObject(x);
                        data.setTime(result.getString("lng"));
                        data.setLng(result.getString("lng"));
                        data.setLat(result.getString("lat"));
                        data.setAddress( result.getString("address"));
                        list.add(data);
                    }
                }
            }
            count++;
            data=new SingData();
            BeanUtils.copyProperties(data,joinEvent);
            data.setTime(StrUtils.dateToStrLong(new Date()));
            list.add(data);
           //jsonSing.setCount(count);
           //jsonSing.setData(list);
            */
            Event event=new Event();
            event.seteId(joinEvent.geteId());
            Event envetInfo = eventSvc.getEnvetInfo(event);
            isClose=null!=envetInfo&&envetInfo.getEndTime().getTime()>=new Date().getTime()?0:1;
            if(isClose==0) {
                JsonSing jsonSing = new JsonSing();
                joinEvent.setIsSign(new Byte("1"));
                joinEvent.setHisSign(JSONUtils.toJSONString(jsonSing));
                JoinEvent joinEventInfo = joinEventSvc.getJoinEventInfo(joinEvent);
                if (joinEventInfo == null) {
                    joinEventSvc.update(joinEvent);
                } else {
                    msg = "error";
                    errorCode = APIErrorMap.errorMap.get("4");
                }
            }else {
                msg = "error";
                errorCode = APIErrorMap.errorMap.get("6");
            }

        }
        resMap.put("msg",msg);
        resMap.put("isClose",isClose);
        resMap.put("errorCode",errorCode);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "签到", 0);
        return  resMap;
    }

    /**
    *  根据事件id和参与者id获取参与者资料
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/6/21 16:10
    */
    @RequestMapping(path ="/getStandData",method = RequestMethod.GET)
    public  Map<String,Object> getStandData(HttpServletRequest request, JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(joinEvent);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            JoinEventDto standData = joinEventSvc.getStandData(joinEvent);
            resMap.put("msg","success");
            resMap.put("standData",standData);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取参选人资料", 0);
        return resMap;
    }

    /**
     * 根据id获取参与者信息
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/6/21 16:10
     */
     @RequestMapping(path ="/infoJoin",method = RequestMethod.GET)
     public  Map<String,Object> infoJoin(HttpServletRequest request, JoinEvent joinEvent){
         Map<String,Object> resMap=new HashMap<String, Object>();
         Date beginDate=new Date();
         if(joinEvent.getId() !=null){
        	 JoinEvent standData = joinEventSvc.getJoinEventInfo(joinEvent);
             resMap.put("msg","success");
             resMap.put("join",standData);
         }
         logWriter.saveSystemLog(request,"", "",beginDate, "", " 根据id获取参与者信息", 0);
         return resMap;
     }

     /**
      * 根据事件id和用户id查询参与者身份为事项责任人的参与者信息
      * @author wuchao
      * @date 2017年6月27日
      * @time 下午3:38:56
      * @version V0.0.1
      * @param request
      * @param joinEvent
      * @return
      */
     @RequestMapping(path ="/itemJoinInfo",method = RequestMethod.GET)
     public  Map<String,Object> itemJoinInfo(HttpServletRequest request, JoinEvent joinEvent){
         Map<String,Object> resMap=new HashMap<String, Object>();
         Date beginDate=new Date();
         if(joinEvent.geteId() !=null && joinEvent.getUserId() !=null){
        	 joinEvent.setType((byte)2);
        	 JoinEvent standData = joinEventSvc.itemJoinInfo(joinEvent);
             resMap.put("msg","success");
             resMap.put("join",standData);
         }
         logWriter.saveSystemLog(request,"", "",beginDate, "", " 根据id获取参与者信息", 0);
         return resMap;
     }


    /**
     * 校验客户端请求参数合法性
     * @param joinEvent
     * @return
     */
    private Map<String, Object> checkPara(JoinEvent joinEvent) {
        String start="";
        String end="";
        Map<String, Object>  reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg="success";
        String state="1";  // 1 成功  0 失败
        if(null==joinEvent){
            errorCode= APIErrorMap.errorMap.get("0");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //事件id为空
        if (StrUtils.isEmpty(String.valueOf(joinEvent.geteId()))){
            errorCode= APIErrorMap.errorMap.get("2");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //用户id
        if (StrUtils.isEmpty(String.valueOf(joinEvent.getUserId()))){
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
     * 获取行动参与人列表
     * @author zyting
     * @date 2017年10月26日
     * @time 下午3:38:56
     * @version V0.0.1
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path ="/itemJoinPeople",method = RequestMethod.GET)
    public  Map<String,Object> itemJoinPeople(HttpServletRequest request, JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        if(joinEvent.geteId() !=null && joinEvent.getEiId() !=null){
       	 joinEvent.setTypeStr("4");
       	List<JoinEvent> l = joinEventSvc.joinEventPeople(joinEvent);
            resMap.put("msg","success");
            resMap.put("join",l);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "  获取行动参与人列表", 0);
        return resMap;
    }

    /**
     * 参与人退出行动
     * @author zyting
     * @date 2017年8月14日
     * @time 下午5:30:41
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/outItemEven",method = RequestMethod.POST)
    public Map<String,Object> outItemEven(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(joinEvent.getEiId() !=null && joinEvent.geteId() !=null && joinEvent.getUserId() !=null){
        	joinEventSvc.outItemEven(joinEvent);
            msg="success";
        }
        resMap.put("msg", msg);
        return resMap;
    }
    /**
     *@Description:获取项目参与人列表
     *@author:xiehuilin
     *@param:request
     *@param:joinEvent
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     */
    @RequestMapping(path ="/listEventJoinUser",method = RequestMethod.GET)
    public  Map<String,Object> listEventJoinUser(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.geteId()){
            msg="success";
            joinEvent.setTypeStr("3");
            List<JoinEvent> joinEvents = joinEventSvc.joinEventPeople(joinEvent);
            //根据参与id获取参与人负责的行动数量
            if(!joinEvents.isEmpty()){
                for(JoinEvent je:joinEvents){
                    //获取参与人负责的行动数
                    EventItem eventItem=new EventItem();
                    eventItem.seteId(joinEvent.geteId());
                    eventItem.setDutyId(je.getUserId());
                    eventItem.setStateStr("0,3,4");
                    List<EventItem> eventItems = eventItemSvc.listItem(eventItem);
                    Long itemCount=null!=eventItems?eventItems.size():0L;
                    //获取参与人参与的行动数
                    joinEvent.setTypeStr("4");
                    List<JoinEvent> joinEventItem = joinEventSvc.joinEventPeople(joinEvent);
                    itemCount+=null!=joinEventItem?joinEventItem.size():0L;
                    je.setDuytItemCount(itemCount);
                }
            }
            resMap.put("joinEvents",joinEvents);
            resMap.put("msg",msg);
        }
        return  resMap;
    }
    
    /**
     * 项目活动的参与人  (添加参与人)
     * 判断此人是否是此活动的参与人
     * @author zyting
     * @date 2017年8月14日
     * @time 下午5:30:41
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return eId eiId
     */
    @RequestMapping(path ="/joinPeopleAll",method = RequestMethod.GET)
    public Map<String,Object> joinPeopleAll(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(joinEvent.getEiId() !=null && joinEvent.geteId() !=null){
        	joinEvent.setTypeStr("3,4");
        	List<JoinEvent> l = joinEventSvc.joinEventPeople(joinEvent);
        	for(int i=0;i<l.size();i++){
        		joinEvent.setUserId(l.get(i).getUserId());
        		JoinEvent j = joinEventSvc.getJoinEventInfo(joinEvent);
        		l.get(i).setIsSelect("0");
        		if(null ==j){
        			//可以被选
        			l.get(i).setIsSelect("1");
        		}
        	}
            msg="success";
            resMap.put("peopleList", l);
        }
        resMap.put("msg", msg);
        return resMap;
    }
    /**
     *添加/删除 活动参与人
     * @author zyting
     * @date 2017年8月14日
     * @time 下午5:30:41
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return userId eiId
     */
    @RequestMapping(path ="/updatejoinItem",method = RequestMethod.GET)
    public Map<String,Object> updatejoinItem(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(joinEvent.getEiId() !=null && joinEvent.geteId() !=null){
        	
            msg="success";
        }
        resMap.put("msg", msg);
        return resMap;
    }

    /**
     *@Description:删除项目参与人
     * 1：将该用户属于项目参与人的角色更新为无效
     * 2：将该用户属于行动参与人或责任人更新为无效
     *@author:xiehuilin
     *@param:request
     *@param:joinEvent
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     */
    @RequestMapping(path ="/delEventJoinUser",method = RequestMethod.POST)
    public  Map<String,Object> delEventJoinUser(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.getJoinUserIds()&&""!=joinEvent.getJoinUserIds()){
            msg="success";
            String[] split = joinEvent.getJoinUserIds().split(",");
            for(int x=0;x<split.length;x++){
                String s = split[x];
                joinEvent.setUserId(Long.valueOf(s));
                joinEventSvc.updateByEidAndUserId(joinEvent);
            }
            resMap.put("msg",msg);
        }
        logWriter.saveSystemLog(request,String.valueOf(joinEvent.getUserId()), "",beginDate, "", "项目描述删除项目参与人", 0);
        return  resMap;
    }

    /**
     *@Description:项目描述添加参与人获取好友列表、通讯录列表
     *@author:xiehuilin
     *@param:request
     *@param:joinEvent
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     */
    @RequestMapping(path ="/getEventJoinUser",method = RequestMethod.GET)
    public  Map<String,Object> getEventJoinUser(HttpServletRequest request, JoinEvent joinEvent, PaginationParameter parameter){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.geteId()){
            List<FriendGroup> friendGroups=null;
            List<Member> members=null;
            //根据事件id校验校验是否是轻企项目
            Event event=new Event();
            event.seteId(joinEvent.geteId());
            //获取项目详情
            Event envetInfo = eventSvc.getEnvetInfo(event);
            //获取项目参与人
            joinEvent.setTypeStr("3");
            List<JoinEvent> joinEvents = joinEventSvc.joinEventPeople(joinEvent);
            Integer count=0;
            //获取轻企成员列表
            if(null!=envetInfo.getLeId()){
                //轻企成员列表
                Member m = new Member();
                m.setLeId(envetInfo.getLeId());
                m.setUserId(joinEvent.getUserId());
                m.setParameter(parameter);
                members = memberSvc.listGetMemberExceptMy(m);
                m.setParameter(null);
                count=memberSvc.listGetMemberExceptMy(m).size();
                if(!joinEvents.isEmpty()){
                 for(int x=0;x<members.size();x++){
                     for(int y=0;y<joinEvents.size();y++){
                         if(members.get(x).getUserId().equals(joinEvents.get(y).getUserId())){
                             members.get(x).setIsJoinUser(new Byte("1"));
                         }
                     }
                 }
                }
            }
            //获取好友列表
            else{
                FriendGroup friendGroup=new FriendGroup();
                friendGroup.setPaper(parameter);
                friendGroup.setUserId(joinEvent.getUserId());
                friendGroup.setState(new Byte("1"));
                friendGroups= friendGroupSvc.getFriendGroupById(friendGroup);
                friendGroup.setPaper(null);
                 count=friendGroupSvc.getFriendGroupById(friendGroup).size();
                if(!joinEvents.isEmpty()){
                    for(int x=0;x<friendGroups.size();x++){
                        for(int y=0;y<joinEvents.size();y++){
                            if(friendGroups.get(x).getfId().equals(joinEvents.get(y).getUserId())){
                                friendGroups.get(x).setIsJoinUser(new Byte("1"));
                            }
                        }
                    }
                }
            }
            msg="success";

            resMap.put("members",members);
            resMap.put("friendGroups",friendGroups);
            resMap.put("count",count);

        }
        resMap.put("msg",msg);
        logWriter.saveSystemLog(request,String.valueOf(joinEvent.getUserId()), "",beginDate, "", "项目描述获取项目参与人", 0);
        return  resMap;
    }

    /**
     *@Description:项目描述添加参与人
     *  更新参与人记录
     *  保存消息记录
     *@author:xiehuilin
     *@param:request
     *@param:joinEvent
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     */
    @RequestMapping(path ="/addEventJoinUser",method = RequestMethod.POST)
    public  Map<String,Object> addEventJoinUser(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.getJoinUserIds()&&""!=joinEvent.getJoinUserIds()){
                msg="success";
                String[] split = joinEvent.getJoinUserIds().split(",");
                for(int x=0;x<split.length;x++){
                    String s = split[x];
                    joinEvent.setUserId(Long.valueOf(s));
                    joinEvent.setState(new Byte("0"));
                    joinEvent.setType(new Byte("3"));
                    joinEventSvc.saveEventJoinUser(joinEvent);
                }
                resMap.put("msg",msg);
        }
        logWriter.saveSystemLog(request,String.valueOf(joinEvent.getUserId()), "",beginDate, "", "项目描述添加参与人", 0);
        return  resMap;
    }
  

    /**
     *@Description:添加行动参与人   展示所有参与人
     *@author:zyting
     *@param:request
     *@param:joinEvent
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     *eId eiId
     */
    @RequestMapping(path ="/getEventUserList",method = RequestMethod.GET)
    public  Map<String,Object> getEventUserList(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.geteId()){
            //获取项目参与人
        	Long eiId =joinEvent.getEiId();
        	joinEvent.setEiId(null);
            joinEvent.setTypeStr("3,4");
            List<JoinEvent> joinEvents = joinEventSvc.joinEventPeople(joinEvent);
            joinEvent.setEiId(eiId);
            joinEvent.setTypeStr("4");
            List<JoinEvent> l = joinEventSvc.joinEventPeople(joinEvent);
            if(!l.isEmpty()){
                for(int x=0;x<joinEvents.size();x++){
                    for(int y=0;y<l.size();y++){
                        if(joinEvents.get(x).getUserId().equals(l.get(y).getUserId())){
                        	joinEvents.get(x).setIsJoinUser(new Byte("1"));
                        }
                    }
                }
            }
            msg="success";
            resMap.put("joinList",joinEvents);
        }
        resMap.put("msg",msg);
        return  resMap;
    }
    
    /**
     *@Description:添加行动参与人   展示轻企人员  /通讯录人员
     *@author:zyting
     *@param:request
     *@param:joinEvent
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     *eId eiId
     */
    @RequestMapping(path ="/getQinqiUserList",method = RequestMethod.GET)
    public  Map<String,Object> getQinqiUserList(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.geteId()){
            
            List<FriendGroup> friendGroups=null;
            List<Member> members=null;
            //根据事件id校验校验是否是轻企项目
            Event event=new Event();
            event.seteId(joinEvent.geteId());
            //获取项目详情
            Event envetInfo = eventSvc.getEnvetInfo(event);
            //获取项目参与人
            joinEvent.setTypeStr("4");
            List<JoinEvent> joinEvents = joinEventSvc.joinEventPeople(joinEvent);
            //获取轻企成员列表
            if(null!=envetInfo.getLeId()){
                //轻企成员列表
                Member m = new Member();
                m.setLeId(envetInfo.getLeId());
                m.setUserId(joinEvent.getUserId());
                members = memberSvc.listGetMemberExceptMy(m);
                if(!joinEvents.isEmpty()){
                 for(int x=0;x<members.size();x++){
                     for(int y=0;y<joinEvents.size();y++){
                         if(members.get(x).getUserId().equals(joinEvents.get(y).getUserId())){
                             members.get(x).setIsJoinUser(new Byte("1"));
                         }
                     }
                 }
                }
                resMap.put("members",members);
            }
            //获取好友列表
            else{
                FriendGroup friendGroup=new FriendGroup();
                friendGroup.setUserId(joinEvent.getUserId());
                friendGroup.setState(new Byte("1"));
                friendGroups= friendGroupSvc.getFriendGroupById(friendGroup);
                if(!joinEvents.isEmpty()){
                    for(int x=0;x<friendGroups.size();x++){
                        for(int y=0;y<joinEvents.size();y++){
                            if(friendGroups.get(x).getUserId().equals(joinEvents.get(y).getUserId())){
                                friendGroups.get(x).setIsJoinUser(new Byte("1"));
                            }
                        }
                    }
                }
                resMap.put("friendGroups",friendGroups);
            }
            resMap.put("isLeId",envetInfo.getLeId());
            msg="success";
        }
        resMap.put("msg",msg);
        return  resMap;
    }
    
    /**
     *@Description:行动添加参与人
     *@author:zyting
     *@param:request
     *@param:joinEvent
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 16:46
     *@version V 0.0.7
     */
    @RequestMapping(path ="/addEventItemJoinUser",method = RequestMethod.POST)
    public  Map<String,Object> addEventItemJoinUser(HttpServletRequest request,JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(null!=joinEvent.getJoinUserIds()&&""!=joinEvent.getJoinUserIds()){
                msg="success";
                String[] split = joinEvent.getJoinUserIds().split(",");
                for(int x=0;x<split.length;x++){
                    String s = split[x];
                    joinEvent.setUserId(Long.valueOf(s));
                    joinEventSvc.saveEventItemJoinUser(joinEvent);
                }
                resMap.put("msg",msg);
        }
        logWriter.saveSystemLog(request,String.valueOf(joinEvent.getUserId()), "",beginDate, "", "项目描述添加参与人", 0);
        return  resMap;
    }
    
    /**
     * 校验是否是创建人
     *@author wuchao
     *@data 2017年11月2日
     *@version V0.0.5
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path="/checkFounder",method = RequestMethod.GET)
    public Map<String,Object> checkEvent(HttpServletRequest request, JoinEvent joinEvent){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        if(joinEvent.getUserId()!=null && joinEvent.geteId()!=null ){
        	joinEvent.setType((byte)0);
        	JoinEvent join= joinEventSvc.JoinEventInfo(joinEvent);
            msg="success";
            reslutMap.put("item", join);
        }
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,joinEvent.getUserId()+"", "校验是否是创建人", 0);
        return reslutMap;
    }
    
    /**
     * 目标的所有参与者
     *@author wuchao
     *@data 2017年11月2日
     *@version V0.0.5
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path="/listEventPartake",method = RequestMethod.GET)
    public Map<String,Object> listEventPartake(HttpServletRequest request, JoinEvent joinEvent){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg="error";
        if(joinEvent.geteId()!=null ){
        	List<JoinEvent> joinList= joinEventSvc.listEventPartake(joinEvent);
            msg="success";
            reslutMap.put("list", joinList);
        }
        reslutMap.put("msg", msg);
        logWriter.saveSystemLog(request,beginDate,joinEvent.geteId()+"", "目标的所有参与者", 0);
        return reslutMap;
    }
}
