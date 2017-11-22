package com.qixin.teamwork.controller.eventItem;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.news.svc.NewsSvc;
import com.qixin.teamwork.biz.record.model.Reply;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.ReplySvc;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.envet.model.CycleTemplet;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.CycleTempletSvc;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;

/**
 * 事项控制层
 * @author wuchao
 * @date 2017年6月14日
 * @time 下午2:51:35
 * @version V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/item")
public class EventItemController {

	@Resource
	private LogWriter log;
	@Resource
	private EventItemSvc eventItemSvc;
	@Resource
	private JoinEventSvc joinEventSvc;
	@Resource
	private CycleTempletSvc cycleTempletSvc;
	@Autowired
	private NewsSvc newsSvc;
    @Autowired
    private WxuserSvc wxuserSvc;
    @Autowired
    private TrackRecordSvc trackRecordSvc;
    @Autowired
    private ReplySvc replySvc;


	 /**
     * 新增事项
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午2:20:10
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/saveItem",method = RequestMethod.POST)
    public Map<String,Object> saveItem(HttpServletRequest request,@RequestBody EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.geteId() !=null && eventItem.getCreateBy() !=null){
        	eventItem.setFinishTime(DateUtils.StrToDate(eventItem.getStrFinishTime()));
        	eventItem.setStartTime(DateUtils.StrToDate(eventItem.getStrStartTime()));
        	eventItemSvc.saveEventItem(eventItem);
        	msg="success";
        }
        resMap.put("msg", msg);
        log.saveSystemLog(request,"", "",beginDate, "", "新增事项", 0);
        return resMap;
    }

    /**
     *  修改事项状态
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午3:01:48
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateItemState",method = RequestMethod.POST)
    public Map<String,Object> updateItemState(HttpServletRequest request, EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId()!=null){
            eventItem.setfTimestamp(new Timestamp(eventItem.getStrfTime()));
                //add by xiehuilin 2017/11/02 根据行动id获取行动详情
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            eventItem.seteId(itemInfo.geteId());
            eventItemSvc.updateItemState(eventItem);
        	msg="success";
        }
        resMap.put("msg", msg);
        log.saveSystemLog(request,"", "",beginDate, "", "  修改事项状态", 0);
        return resMap;
    }

    /**
     * 更新事项记录、更新待办任务开始推送时间
     * @author wuchao
     * @date 2017年6月14日
     * @time 下午3:06:06
     * @version V0.0.1
     * @param request
     * @param joinEvent
     * @return
     */
    @RequestMapping(path ="/updateJoin",method = RequestMethod.POST)
    public Map<String,Object> updateJoin(HttpServletRequest request,@RequestBody JoinEvent joinEvent){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(joinEvent.geteId() !=null && joinEvent.getUserId()!=null){
        	joinEventSvc.updateJoinEvent(joinEvent);
        	msg="success";
        }
        resMap.put("msg", msg);
        log.saveSystemLog(request,"", "",beginDate, "", " 更新事项记录、更新待办任务开始推送时间", 0);
        return resMap;
    }


   /**
    * 校验事项完成时间
    * @author wuchao
    * @date 2017年6月19日
    * @time 下午4:59:57
    * @version V0.0.1
    * @param request
    * @param eventItem
    * @return
    */
    @RequestMapping(path="/checkTime",method = RequestMethod.GET)
   	public Map<String,Object> checkEvent(HttpServletRequest request, EventItem eventItem){
  		Date beginDate=new Date();
  		Map<String,Object> reslutMap=new HashMap<String, Object>();
  		String msg="error";
  		int count=0;
  		if(eventItem.geteId() !=null &&  !StrUtils.isEmpty(eventItem.getCreateBy()) ){
  			eventItem.setFinishTime(DateUtils.StrToDate(eventItem.getStrFinishTime()));
  			 String startTimeStr = eventItem.getFinishTime()+":00";
  		    Timestamp startTime = Timestamp.valueOf(startTimeStr);
  		    eventItem.setFinishTime(startTime);
  		    EventItem item=eventItemSvc.checkTime(eventItem);
  		    if (item !=null) {
  		    	count=1;
			}
  			msg="success";
  		}
  		reslutMap.put("count", count);
  		reslutMap.put("msg", msg);
  		log.saveSystemLog(request,beginDate,eventItem.geteId()+"", "校验事项完成时间", 0);
  		return reslutMap;
  	}

    /**
     * 更新事项
     * @author xiehuilin
     * @date 2017年6月14日
     * @time 下午3:06:06
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateEventItem",method = RequestMethod.POST)
    public Map<String,Object> updateEventItem(HttpServletRequest request,EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
        	eventItemSvc.updateEventItemCreateAwardpenalty(eventItem);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", " 事项打分", 0);
        resMap.put("msg", msg);
        return resMap;
    }

   /**
    * 重新编辑进度详情
    * @author wuchao
    * @date 2017年7月27日
    * @time 下午6:18:41
    * @version V0.0.1
    * @param request
    * @param eventItem
    * @return
    */
    @RequestMapping(path ="/getActionItem",method = RequestMethod.GET)
    public Map<String,Object> getActionItem(HttpServletRequest request,EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
        	EventItem item=eventItemSvc.getActionItem(eventItem);
        	if (item.getCtId() !=null) {
        		CycleTemplet cycleTemplet=new CycleTemplet();
        		cycleTemplet.setId(item.getCtId());
        		CycleTemplet templet=cycleTempletSvc.getCtempletInfo(cycleTemplet);
        		resMap.put("templet", templet);
			}
        	msg="success";
        	resMap.put("item", item);
        }
        log.saveSystemLog(request,"", "",beginDate, "", "重新编辑进度详情", 0);
        resMap.put("msg", msg);
        return resMap;
    }

    /**
     *   重新编辑进度内容(单次)
     * @author wuchao
     * @date 2017年7月27日
     * @time 下午6:20:06
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateItem",method = RequestMethod.POST)
    public Map<String,Object> updateItem(HttpServletRequest request,@RequestBody EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
        	//eventItem.setFinishTime(DateUtils.StrToDate(eventItem.getStrFinishTime()));
        	//eventItem.setStartTime(DateUtils.StrToDate(eventItem.getStrStartTime()));
        	eventItemSvc.updateItem(eventItem);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", " 重新编辑进度内容(单次)", 0);
        resMap.put("msg", msg);
        return resMap;
    }
    /**
     * 保存周期行动
     * @author wuchao
     * @date 2017年8月14日
     * @time 下午4:12:27
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/saveCycItem",method = RequestMethod.POST)
    public Map<String,Object> saveCycItem(HttpServletRequest request,@RequestBody EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.geteId() !=null && eventItem.getCreateBy() !=null){
        	Integer succeed= eventItemSvc.saveCycItem(eventItem);
        	resMap.put("succeed", succeed);
        	msg="success";
        }
        resMap.put("msg", msg);
        log.saveSystemLog(request,"", "",beginDate, "", "新增事项", 0);
        return resMap;
    }

    /**
     * 重新编辑进度内容(周期)
     * @author wuchao
     * @date 2017年8月14日
     * @time 下午5:28:06
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateCycItem",method = RequestMethod.POST)
    public Map<String,Object> updateCycItem(HttpServletRequest request,@RequestBody EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
        	Integer succeed= eventItemSvc.updateCycItem(eventItem);
        	resMap.put("succeed", succeed);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "重新编辑进度内容(周期)", 0);
        resMap.put("msg", msg);
        return resMap;
    }

    /**
     * 重新编辑进度委托人(周期)
     * @author wuchao
     * @date 2017年8月14日
     * @time 下午5:29:11
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateCycClient",method = RequestMethod.POST)
    public Map<String,Object> updateCycClient(HttpServletRequest request,@RequestBody EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
        	//eventItemSvc.updateCycClient(eventItem);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "重新编辑进度委托人(周期)", 0);
        resMap.put("msg", msg);
        return resMap;
    }

    /**
     *  修改周期模板状态 (冻结、 激活)
     * @author wuchao
     * @date 2017年8月14日
     * @time 下午5:30:41
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateCycleState",method = RequestMethod.POST)
    public Map<String,Object> updateCycleState(HttpServletRequest request,EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getCtId() !=null && eventItem.getCycleState() !=null){
        	eventItemSvc.updateCycleState(eventItem);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "修改周期模板状态", 0);
        resMap.put("msg", msg);
        return resMap;
    }

/*
    public Map<String,Object>  getErrorNew(HttpServletRequest request, ItemEvent itemEvent){
        TrackRecord record=new TrackRecord();
        record.setCreateBy(event.getUserId());
        record.seteId(envetInfo.geteId());
        record.setIsRed(new Byte("1"));
        trackRecordSvc.updateTrackRecord(record);
    }*/

    /**
     *  更新行动公开范围
     * @author wuchao
     * @date 2017年8月14日
     * @time 下午5:30:41
     * @version V0.0.1
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updatePublicScope ",method = RequestMethod.POST)
    public Map<String,Object> update(HttpServletRequest request, EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getEiId() !=null){
            //非周期行动  updateTempletState
            if(null!=eventItem.getCtId()){
               eventItemSvc.updateEventItem(eventItem);
            }else{
                eventItemSvc.updateTempletState(eventItem);
            }
            msg="success";
        }
        log.saveSystemLog(request,String.valueOf(eventItem.getCreateBy()), "",beginDate, "", "更新行动公开范围", 0);
        resMap.put("msg", msg);
        return resMap;
    }
    /**
     *@Description: 删除行动
     *              更新行动记录、
     *              将该行动下的所有待办更新为无效
     *              将该行动下的所有更新清单记录无效
     *              将该行动下的所有参与记录更新无效
     *@author:xiehuilin
     *@param:request
     *@param:event
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/25 14:18
     *@version V 0.0.7
     */
    @RequestMapping(path ="/delEventItem",method = RequestMethod.POST)
    public  Map<String,Object> delEventItem(HttpServletRequest request, EventItem eventItem){
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(eventItem.getEiId())?"error":"success";
        if("success".equals(msg)){
            //根据行动id获取行动详情
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            eventItem.seteId(itemInfo.geteId());
            eventItem.setCtId(itemInfo.getCtId());
            eventItemSvc.delEventItem(eventItem);
        }
        reslutMap.put("msg",msg);
        log.saveSystemLog(request,String.valueOf(eventItem.getCreateBy()), "",beginDate, "", "删除行动", 0);
        return  reslutMap;
    }

    /**
     *@Description:单次行动
     *  根据行动id获取行动详情
     *  根据行动id获取跟踪记录
     *  根据跟踪记录获取留言列表
     *@author:xiehuilin
     *@param:request
     *@param:eventItem
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/31 15:15
     *@version V 0.0.7
     */
    @RequestMapping(path ="/sinEventItemDetails",method = RequestMethod.GET)
    public  Map<String,Object> sinEventItemDetails(HttpServletRequest request, EventItem eventItem){
        // 0 时间管理即是时间创建人也是事件责任人   1 事项责任人,3轻企成员关注非项目创建人(项目责任人),4轻企成员关注轻企项目创建(项目责任人),5 事件参与人 ,6 行动参与人
        Integer isEventDuty = 0;
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(eventItem.getEiId())?"error":"success";
               msg=StrUtils.isEmpty(eventItem.getUserId())?"error":"success";
        if("success".equals(msg)){
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            //根据事件id获取事件详情
            JoinEvent joinEvent=new JoinEvent();
            joinEvent.seteId(itemInfo.geteId());
            joinEvent.setUserId(eventItem.getUserId());
            List<JoinEvent> joinEvents = joinEventSvc.listGetJoinUserType(joinEvent);
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
                //获取行动参与人数
                Iterator<JoinEvent> it = joinEvents.iterator();
                while (it.hasNext()) {
                    JoinEvent je = it.next();
                    if(je.getType()!=4){
                     it.remove();
                    }
                }

            }

            //根据获取id获取行动创建人昵称和行动责任昵称
            User user=new User();
            //是委派行动只显示责任人
            if(itemInfo.getDutyId()!=itemInfo.getCreateBy()){
                user.setUserId(itemInfo.getCreateBy());
                User userInfo = wxuserSvc.getUserInfo(user);
                itemInfo.setcUserName(userInfo.getUserName());
            }
            user.setUserId(itemInfo.getDutyId());
            User info = wxuserSvc.getUserInfo(user);
            itemInfo.setdUserName(info.getUserName());
            reslutMap.put("itemInfo",itemInfo);
            reslutMap.put("isEventDuty",isEventDuty);
            reslutMap.put("joinEvents",joinEvents);

        }
        reslutMap.put("msg",msg);
        log.saveSystemLog(request,String.valueOf(eventItem.getCreateBy()), "",beginDate, "", "单次行动获取行动详情", 0);
        return  reslutMap;
    }

    /**
     *@Description:周期行动
     *  根据行动id获取行动详情
     *  根据行动id获取跟踪记录
     *  根据跟踪记录获取留言列表
     *@author:xiehuilin
     *@param:request
     *@param:eventItem
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/31 15:15
     *@version V 0.0.7
     */
    @RequestMapping(path ="/clyeEventItemDetails",method = RequestMethod.GET)
    public  Map<String,Object> clyeEventItemDetails(HttpServletRequest request, EventItem eventItem){
        // 0 时间管理即是时间创建人也是事件责任人   1 事项责任人,3轻企成员关注非项目创建人(项目责任人),4轻企成员关注轻企项目创建(项目责任人),5 事件参与人 ,6 行动参与人
        Integer isEventDuty = 0;
        Date beginDate=new Date();
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=StrUtils.isEmpty(eventItem.getEiId())?"error":"success";
        if("success".equals(msg)){
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            //根据周期id获取周期详情
            CycleTemplet templet=new CycleTemplet();
            templet.setId(itemInfo.getCtId());
            CycleTemplet ctempletInfo = cycleTempletSvc.getCtempletInfo(templet);
            //根据周期id获取周期下的所有行动
            eventItem.setCtId(itemInfo.getCtId());
            List<EventItem> eventItems = eventItemSvc.listItemInfo(eventItem);
            if(!eventItems.isEmpty()){
                for(EventItem item:eventItems){
                    //获取行动跟踪记录
                    TrackRecord trackRecord=new TrackRecord();
                    trackRecord.setEiId(item.getEiId());
                    List<TrackRecord> trackRecords = trackRecordSvc.listTrackRecord(trackRecord);
                    item.setRecordList(trackRecords);
                }
            }
            //根据事件id获取事件详情
            JoinEvent joinEvent=new JoinEvent();
            joinEvent.seteId(itemInfo.geteId());
            joinEvent.setUserId(eventItem.getUserId());
            List<JoinEvent> joinEvents = joinEventSvc.listGetJoinUserType(joinEvent);
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
            reslutMap.put("itemInfo",itemInfo);
            reslutMap.put("ctempletInfo",ctempletInfo);
            reslutMap.put("eventItems",eventItems);
            reslutMap.put("isEventDuty",isEventDuty);
        }
        reslutMap.put("msg",msg);
        log.saveSystemLog(request,String.valueOf(eventItem.getCreateBy()), "",beginDate, "", "周期行动获取行动详情", 0);
        return  reslutMap;
    }
    
    /**
     * 激活周期行动
     *@author wuchao
     *@data 2017年11月7日
     *@version V0.0.5
     * @param request
     * @param eventItem
     * @return
     */
    @RequestMapping(path ="/updateActivateCyc",method = RequestMethod.POST)
    public Map<String,Object> updateActivateCyc(HttpServletRequest request,EventItem eventItem){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(eventItem.getCtId() !=null && eventItem.getCycleState() !=null){
        	eventItemSvc.updateActivateCyc(eventItem);
        	msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "激活周期行动", 0);
        resMap.put("msg", msg);
        return resMap;
    }

}
