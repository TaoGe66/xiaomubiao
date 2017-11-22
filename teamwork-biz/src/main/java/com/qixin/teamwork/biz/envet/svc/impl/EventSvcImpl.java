package com.qixin.teamwork.biz.envet.svc.impl;

import java.sql.Timestamp;    
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.CycleTemplet;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventDelay;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import org.framework.utils.Constant;
import org.framework.utils.StrUtils;
import org.springframework.stereotype.Service;
import com.qixin.teamwork.biz.base.EventMap;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.LightWeeklyDao;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.envet.dao.CycleTempletDao;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventDelayDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.todo.dao.TodoDao;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.wx.Templet;

/**
 * 事件实现层
 * @author wuchao
 * @date 2017年6月12日
 * @time 上午10:04:59
 * @version V0.0.1
 */
@Service("eventSvc")
public class EventSvcImpl implements EventSvc{

	@Resource
	private EventDao envetDao;
	@Resource
	private TodoDao todoDao;
	@Resource
	private WxuserDao userDao;
	@Resource
	private JoinEventDao joinEventDao;
	@Resource
	private NewsDao newsDao;
	@Resource
	private LightWeeklyDao weeklyDao;
	@Resource
	private  WeeklyListDao weeklyListDao;
	@Resource
	private EventItemDao eventItemDao;
    @Resource
    private AwardPenaltyListDao awardPenaltyListDao;
    @Resource
    private EventDelayDao delayDao;
    @Resource
	private CycleTempletDao cycleTempletDao;
    
	/**
	 * 新增事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:25:01
	 * @version V0.0.1
	 * @param envet
	 */
	@Override
	public Event saveEnvet(Event envet) {
		//保存事件信息
		Event en=envetDao.infoEnvet(envet);
		if (en==null) {
			envetDao.saveEnvet(envet);
			saveDuty(envet);
		}else{
			envet.seteId(en.geteId());
		}
			return envet;
	}

	/**
	 * 修改事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 上午10:25:01
	 * @version V0.0.1
	 * @param envet
	 */
	@Override
	public void updateEnvet(Event envet) {
		envetDao.updateEnvet(envet);
		if (envet.getType() !=0 && envet.getIsValid()==1) {
			saveDuty(envet);
		}
	}
	/** 获取事件信息
	 * @description
	 * @author xiehuilin
	 * @param  envet 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/12 13:48
	 */
	@Override
	public Event getEnvetInfo(Event event) {
		return envetDao.getEnvetInfo(event);
	}
	
	/**
	 * 保存待办事件信息
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 上午11:48:29
	 * @version V0.0.1
	 * @param envet
	 */
	public void saveDuty(Event envet){
		Event event=envetDao.getEnvetInfo(envet);
		if (!StrUtils.isEmpty(envet.getEventPartake())) {
			savePartake(envet);
		}
		String event_State=EventMap.aimStateMap.get("0");
		String colse_State=EventMap.aimStateMap.get("3");
		Todo todo =new Todo();
		todo.setUserId(event.getCreateBy());
		todo.setCreateBy(event.getCreateBy());
		todo.seteId(event.geteId());
		todo.settSubjType((byte)0);
		todo.setIsValid((byte)1);
		todo.setIsBlock((byte)0);
		todo.setIsSend((byte)0);
		Calendar c = Calendar.getInstance();
		//Timestamp time=ToDoRemindUtils.getToDoRemindTime(event.getStartTime(), event.getFrequency());
		Timestamp pushTime=ToDoRemindUtils.getToDoRemindTime(event.getStartTime(), Constant.TIME_DELAY_MINUTE);
		
		todo.setPushTime(pushTime);
		User user=new User();
	    user.setUserId(event.getCreateBy());
	    User use=userDao.getUserInfo(user);	
		// 添加消息提醒
		News news = new News();
		news.setnTitle("项目名称："+event.getName());
		// edit by xiehuilin 20170809 目标待计划消息两个小时后推送
		news.setnTime(event.getStartTime());
		news.setCreateBy(event.getCreateBy());
		news.setCreateTime(new Timestamp((new Date()).getTime()));
		news.setIsValid((byte) 1);
		news.setSendId(event.getCreateBy());
		news.setnType((byte) 1);
		news.setnContent(EventMap.newsMap.get("0"));
		news.setnName(event.getName());
		news.setnMold((byte) 4);
		news.seteId(event.geteId());
		if (event.getEndTime() !=null) {
			Timestamp fulfilTime=ToDoRemindUtils.getToDoRemindTime(event.getEndTime(), 0);
			if (event.getType() == 0 && pushTime.getTime() < fulfilTime.getTime()) {
				newsDao.insert(news);
			}
		}
		news.setnTime(event.getEndTime());
		news.setnContent(EventMap.newsMap.get("2"));
		news.setnMold((byte) 3);
		if (event.getEndTime()!=null ) {
			newsDao.insert(news);
			}
		
			
		long now_time=c.getTime().getTime();
		long start_time=envet.getStartTime().getTime();
		if ((now_time>start_time || now_time==start_time) && event.getType()==0) {
			todo.setIsSend((byte)1);
		       if (!StrUtils.isEmpty(use.getOpenId())) {
		    	   Templet.mould("",event.getName(), event_State,"时间："+StrUtils.getDate(event.getStartTime()) +"~"+StrUtils.getDate(event.getEndTime()), use.getOpenId());
		        }
		    }
		envetDao.updateEnvet(envet);
		
		JoinEvent join=new JoinEvent();
		join.seteId(event.geteId());
		join.setUserId(event.getCreateBy());
		join.setCreateBy(event.getCreateBy());
		join.setIsValid((byte)1);
		join.setType((byte)0);
		join.setState((byte)1);
		//join.setFrequency(event.getFrequency());
		join.setIsSign((byte)0);
		JoinEvent jo=joinEventDao.itemJoinInfo(join);
		if (jo==null) {
			joinEventDao.insert(join);
		} 
		
		//保存待办任务信息
		todo.settState((byte)3);
		todo.settName(colse_State);
		todo.settExplained(EventMap.eventDesMap.get("2"));
		//保存周报内容
		if (event.getLeId() !=null) {
			saveWeekly(event);
		}
		if (event.getEndTime()!=null ) {//||event.getType()==1
			//保存待关闭待办任务信息
			todo.setRemindTime(event.getEndTime());
			todo.setPushTime(event.getEndTime());
			todo.setFinishTime(event.getEndTime());
			todo.setIsSend((byte)0);
		todoDao.saveTodo(todo);
		}
	}

	/** 获取事件列表
	 * @description
	 * @author zyting
	 * @param  Event 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/14 15:48
	 * state  '状态:0  待计划、1待执行、2 已完成、3待关闭 、4 待签到 5 已签到
	 * pub_range '可见范围: 0 好友可以见、1平台公开，2 自己可见',
	 */
	@Override
	public List<Event> getEventList(Event envet) {
		return envetDao.getEventList(envet);
	}

	/** 
	 * 获取好友下的事件列表
	 * @description
	 * @author zyting
	 * @param  Event 事件实体
	 * @return envet 事件实体
	 * @version V0.0.1
	 * @date 2017/6/14 15:48
	 * userId
	 */
	@Override
	public List<Event> getFriendEventList(Event envet) {
		return envetDao.getFriendEventList(envet);
	}

	/**
	 * 根据事件id查询事件详情
	 * @author wuchao
	 * @date 2017年6月26日
	 * @time 上午9:37:43
	 * @version V0.0.1
	 * @param envet
	 * @return
	 */
	@Override
	public Event getInfoEvent(Event envet) {
		return envetDao.getInfoEvent(envet);
	}

	@Override
	public List<Event> findEvenList(Event envet,Long userId) {
		 List<Event> l = envetDao.findEvenList(envet);
		 Iterator<Event> it = l.iterator();
         while(it.hasNext()){
        	 Event t = it.next();
        	 
        	 JoinEvent joinEvent = new JoinEvent();
        	 joinEvent.seteId(t.geteId());
        	 joinEvent.setUserId(userId);
        	 JoinEvent j = joinEventDao.JoinEventInfo(joinEvent);
        	 if(j !=null){
        		 it.remove(); 
        	 }
         }
		return l;
	}

	/**
	 * 获取事件详情
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 上午11:54:44
	 * @version V0.0.1
	 * @param event
	 * @return
	 */
	@Override
	public Event infoEnvet(Event event) {
		return envetDao.infoEnvet(event);
	}
	
	/**
	 * 添加周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午6:16:33
	 * @version V0.0.3
	 * @param event
	 */
	public void saveWeekly(Event event){
		  LightWeekly lightWeekly=new LightWeekly();
		  lightWeekly.setUserId(event.getCreateBy());
		  lightWeekly.setLeId(event.getLeId());
		  lightWeekly.seteId(event.geteId());
		  lightWeekly.setIsFinish((byte)0);
		  lightWeekly.setCreateBy(event.getCreateBy());
		  lightWeekly.setIsValid((byte)1);
		  lightWeekly.setStartTime(event.getStartTime());
		weeklyDao.saveWeekly(lightWeekly);
	}

	/**
	 * 设置目标结束时间
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param event
	 */
	@Override
	public void updatePutUp(Event event) {
		envetDao.updateEnvet(event);
		Event enent=envetDao.getEnvetInfo(event);
		//Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(enent.getEndTime(), enent.getFrequency());
		//String event_State=EventMap.aimStateMap.get("0");
		String colse_State=EventMap.aimStateMap.get("3");
		Todo todo =new Todo();
		todo.setUserId(event.getCreateBy());
		todo.seteId(event.geteId());
		todo.settState((byte)3);
		todo.settName(colse_State);
		todo.settExplained(EventMap.eventDesMap.get("2"));
		todo.setIsBlock((byte)0);
		todo.setIsSend((byte)0);
		todo.setRemindTime(enent.getEndTime());
		todo.setCreateBy(event.getCreateBy());
		todo.settSubjType((byte)0);
		todo.setIsValid((byte)1);
		todo.setFinishTime(event.getEndTime());
		todo.setPushTime(event.getEndTime());
		//保存待办任务信息
		todoDao.saveTodo(todo);
		
		News news = new News();
		news.setnTitle("项目名称："+enent.getName());
		news.setnTime(enent.getEndTime());
		news.setCreateBy(enent.getCreateBy());
		news.setCreateTime(new Timestamp((new Date()).getTime()));
		news.setIsValid((byte) 1);
		news.setSendId(enent.getCreateBy());
		news.setnType((byte) 1);
		news.setnContent(EventMap.newsMap.get("2"));
		news.setnName(enent.getName());
		news.setnMold((byte) 4);
		news.seteId(enent.geteId());
		newsDao.insert(news);
	}
	/**
	 *@Description: 删除目标
	 * 				 更新项目记录、将该项目下的所有清单更新为无效
     * 				 将该目标下的所有奖惩名单更新为无效
     * 				 将消息表中未推送的更新为无效
	 *@author:xiehuilin
	 *@param:event
     *@param : type 0 删除项目  1 终止目标
	 *@return:void
	 *@date:2017/10/25 14:54
	 *@version V 0.0.7
	 */
	@Override
	public void delEvent(Event event,Integer type) {

		//轻企项目根据项目id获取清单下与该项目相关的记录
        Event envetInfo = envetDao.infoEnvet(event);
        if(null!=envetInfo.getLeId()){
            WeeklyList weeklyList=new WeeklyList();
            weeklyList.setEiId(event.geteId());
            List<WeeklyList> weeklyLists = weeklyListDao.listWeeklistByEid(weeklyList);
            if(!weeklyLists.isEmpty()){
                weeklyListDao.updateWeeklyListBatch(weeklyList);
            }
        }
        //将奖惩名单更新无效
        AwardPenaltyList penaltyList=new AwardPenaltyList();
        penaltyList.seteId(envetInfo.geteId());
        List<AwardPenaltyList> reslutPenaltyList= awardPenaltyListDao.getPenaltyList(penaltyList);
        if(!reslutPenaltyList.isEmpty()){
            for(AwardPenaltyList ap:reslutPenaltyList){
                ap.setIsValid(new Byte("0"));
                awardPenaltyListDao.update(ap);
            }
        }
        //根据行动id获取未推送的消息记录
        News news=new News();
        news.seteId(event.geteId());
        List<News> reslutNews = newsDao.listGetNotPush(news);
        if(!reslutNews.isEmpty()){
            for(News  n:reslutNews){
                n.setIsValid(new Byte("0"));
                //将消息表中未推送的更新为无效
                newsDao.update(n);
            }
        }
        //终止项目将项目下未完成的行动置为异常终止,将未推送的待办置为无效
        if(type==1){
            EventItem eventItem=new EventItem();
            eventItem.seteId(event.geteId());
            eventItem.setStateStr("6");
            List<EventItem> eventItems = eventItemDao.listItem(eventItem);
            if(!eventItems.isEmpty()){
                for(EventItem ei:eventItems){
                    ei.setState(new Byte("5"));
                    eventItemDao.updateEventItem(ei);
                }
            }
            //获取未操作的所有待办记录，并置为无效
			Todo todo=new Todo();
            todo.seteId(event.geteId());
            todo.setIsFinish(new Byte("0"));
            List<Todo> todoRecordList = todoDao.getTodoRecordList(todo);
            if(!todoRecordList.isEmpty()){
                for(Todo td:todoRecordList){
                    td.setIsValid(new Byte("0"));
                    todoDao.updateTodo(td);
                }
            }
        }
        //删除任务
		if(type==0){
			 //将该项目所有参与者置为无效
			 JoinEvent joinEvent=new JoinEvent();
			 joinEvent.seteId(event.geteId());
			 joinEventDao.updateBacth(joinEvent);
			 //将所有待办记录置为无效
			 Todo todo=new Todo();
			 todo.seteId(event.geteId());
			 todoDao.updateBatch(todo);
		}

        envetDao.updateEnvet(event);
	}

	/**
	 * 保存目标参与人身份
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param event
	 */
	public void savePartake(Event event){
		User user=new User();
        user.setUserId(event.getCreateBy());
        User use=userDao.getUserInfo(user);
		String cy = event.getEventPartake();
		String[] cyList = cy.split(",");
		for (int i = 0; i < cyList.length; i++){
			JoinEvent join=new JoinEvent();
			join.seteId(event.geteId());
			join.setUserId( Long.valueOf(cyList[i]));
			join.setCreateBy(event.getCreateBy());
			join.setIsValid((byte)1);
			join.setType((byte)3);
			join.setState((byte)1);
			//join.setFrequency(event.getFrequency());
			join.setIsSign((byte)0);
			joinEventDao.insert(join);
			News news = new News();
			news.setnTitle("项目名称："+event.getName());
			// edit by xiehuilin 20170809 目标待计划消息两个小时后推送
			news.setnTime(new Timestamp((new Date()).getTime()));
			news.setCreateBy(event.getCreateBy());
			news.setCreateTime(new Timestamp((new Date()).getTime()));
			news.setIsValid((byte) 1);
			news.setSendId( Long.valueOf(cyList[i]));
			news.setnType((byte) 2);
			news.setnContent(use.getUserName()+EventMap.newsMap.get("10"));
			news.setnName(event.getName());
			news.setnMold((byte) 4);
			news.seteId(event.geteId());
			newsDao.insert(news);
		}
	}

	/**
	 * 目标延期
	 *@author wuchao
	 *@data 2017年10月26日
	 *@version V0.0.5
	 * @param event
	 */
	@Override
	public void updateDelay(Event event) {
		long presentTime = new Timestamp((new Date()).getTime()).getTime();// 当前时间
		Event envetInfo = envetDao.getEnvetInfo(event);
		event.setDelayCount(envetInfo.getDelayCount()+1);
		long deferTime=event.getEndTime().getTime();//延期时间
		long endTime=envetInfo.getEndTime().getTime();//结束时间
		long day = org.framework.utils.StrUtils.numDateBetween(event.getEndTime().toString(),
				envetInfo.getEndTime().toString());
		long delayCount=0l;
		if (envetInfo.getDelayTime()!=null) {
			delayCount=envetInfo.getDelayTime();
		}
		event.setDelayTime(delayCount+day);
		envetDao.updateEnvet(event);
		EventDelay eventDelay=new EventDelay();
		eventDelay.setCreateBy(envetInfo.getCreateBy());
		eventDelay.seteId(envetInfo.geteId());
		eventDelay.setUserId(envetInfo.getCreateBy());
		eventDelay.setDelayTime(day);
		delayDao.saveEventDelay(eventDelay);
		//Event envet = envetDao.getEnvetInfo(event);
		if ((presentTime>endTime && presentTime<deferTime)||(presentTime<endTime && ToDoRemindUtils.isSameDate(new Date(presentTime), new Date(endTime))) ) {
			//下周一零点零分
			long next_Start = ToDoRemindUtils.getNowWeekMonday(new Timestamp(presentTime)).getTime();
			next_Start = ToDoRemindUtils.getWeeHoursTime(new Timestamp(next_Start)).getTime();
			
			EventItem eventItem=new EventItem();
			eventItem.seteId(envetInfo.geteId());
			List<EventItem>  itemList=eventItemDao.listEventCycThaw(eventItem);
			if (itemList.size()>0) {
				for (int i = 0; i < itemList.size(); i++) {
					EventItem item=itemList.get(i);
					CycleTemplet templet=new CycleTemplet();
					templet.setId(item.getCtId());
					long startTime=ToDoRemindUtils.getNowWeekMonday(new Timestamp(presentTime)).getTime();
					templet.setNextStartTime(ToDoRemindUtils.getWeeHoursTime(new Timestamp(startTime)));
					cycleTempletDao.updateCycleTemplet(templet);
					Todo todo = new Todo();
					todo.seteId(eventItem.geteId());
					todo.setUserId(eventItem.getDutyId());
					todo.setCreateBy(eventItem.getCreateBy());
					todo.settSubjType((byte) 1);
					EventItem itemInfo= eventItemDao.getItemInfo(item);
					CycleTemplet templetInfo=cycleTempletDao.getCtempletInfo(templet);
					itemInfo.setCircleStartTime(templetInfo.getSatrtLength());
					itemInfo.setCircleFinishTime(templetInfo.getUntilLength());
					envetInfo.setEndTime(new Timestamp(deferTime));
					if (presentTime>endTime) {
						envetInfo.setStartTime(new Timestamp(presentTime));
					}else{
						envetInfo.setStartTime(new Timestamp(endTime));
					}
						if (item.getCycleFreq() == 0) {
							// 每天
							meltPeriodDay(itemInfo, todo, envetInfo);
						} else {
							// 选择性周几
							itemInfo.setCycle(templetInfo.getCycle());
							 meltRevolution(itemInfo, todo, envetInfo);
						}
				}
			}
		}
		
	}
	
	
	/**
	 * 激活周期选择
	 *@author wuchao
	 *@data 2017年10月26日
	 *@version V0.0.5
	 * @param eventItem
	 * @param todo
	 * @param envet
	 * @param succeed
	 * @return
	 */
	public void meltRevolution(EventItem eventItem, Todo todo, Event envet) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		Long event_Start = envet.getStartTime().getTime();
		Long event_end = envet.getEndTime().getTime();
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		if (event_Start <= present_time.getTime() && event_end>present_time.getTime()) {
			int day = ToDoRemindUtils.getYTDWeeks(new Date());
			String cy = eventItem.getCycle();
			String[] cyList = cy.split(",");
			for (int i = 0; i < cyList.length; i++) {
				String cyDay = EventMap.itemDate.get(cyList[i]);
				int a = Integer.parseInt(cyDay);
				// 对应日期的零点零分
				long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(a).getTime()))
						.getTime();
				// 进度的完成时间
				long finish = aTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = aTime + eventItem.getCircleStartTime();
				if (a > day && startTime >= present_time.getTime()) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					if (startTime <= event_end) {
						savePeriodDay(eventItem, todo, envet);
					}
				}
			}
		}
	}
	/**
	 * 激活每天
	 *@author wuchao
	 *@data 2017年10月26日
	 *@version V0.0.5
	 * @param eventItem
	 * @param todo
	 * @param envet
	 * @return
	 */
	public void meltPeriodDay(EventItem eventItem, Todo todo, Event envet) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		Long event_Start = envet.getStartTime().getTime();
		Long event_end = envet.getEndTime().getTime();
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		if (event_Start < present_time.getTime()) {
			int day = ToDoRemindUtils.getYTDWeeks(new Date());
			day=day++;
			for (int i = day; i < 8; i++) {
				// 对应日期的零点零分
				long dayTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(i).getTime()))
						.getTime();
				// 进度的完成时间
				long finish = dayTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = dayTime + eventItem.getCircleStartTime();
				eventItem.setFinishTime(new Timestamp(finish));
				eventItem.setStartTime(new Timestamp(startTime));
				if ( startTime <= event_end) {
					savePeriodDay(eventItem, todo, envet);
				}
			}
		}
	}

	
	/**
	 * 周期性添加
	 * @author wuchao
	 * @date 2017年8月10日
	 * @time 下午1:53:17
	 * @version V0.0.1
	 * @param eventItem
	 * @param cycleTemplet
	 * @param todo
	 * @param envet
	 */
	public void savePeriodDay(EventItem eventItem,Todo todo,Event envet){
		Timestamp present_time=new Timestamp((new Date()).getTime());//当前时间
		Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(eventItem.getFinishTime(),eventItem.getFrequency());
		Calendar c = Calendar.getInstance();
		eventItem.setIsAccept((byte) 1);
		eventItem.setCtId(eventItem.getCtId());
		eventItem.setIsReward((byte) 0);
		EventItem item=new EventItem();
		item.setIsShow((byte)1);
		item.setCtId(eventItem.getCtId());
		EventItem ei=eventItemDao.isShowItem(item);
		if (ei ==null) {
			eventItem.setIsShow((byte)0);
		}else{
			eventItem.setIsShow((byte)1);
		}
		eventItemDao.saveEventItem(eventItem);
		long now_time = c.getTime().getTime();
		long start_time = timeOne.getTime();
		todo.setIsSend((byte) 1);
		User user = new User();
		user.setUserId(eventItem.getDutyId());
		User use = userDao.getUserInfo(user);
		News news = new News();
		news.setnTitle("行动名称："+eventItem.getEiDesc());
		if (now_time > start_time || now_time == start_time) {
			news.setnTime(present_time);
			if (!StrUtils.isEmpty(use.getOpenId())) {
				Templet.mould(eventItem.getEiDesc(), envet.getName(), EventMap.itemStateMap.get("0"),
						"时间：" + StrUtils.getDateHour(eventItem.getFinishTime()), use.getOpenId());
			}
		} else {
			news.setnTime(timeOne);
			todo.setIsSend((byte) 0);
		}
		news.setCreateBy(eventItem.getCreateBy());
		news.setCreateTime(present_time);
		news.setIsValid((byte) 1);
		news.setSendId(eventItem.getCreateBy());
		news.setnType((byte) 1);
		news.setnContent(EventMap.newsMap.get("4"));
		news.setnName(envet.getName());
		news.setnMold((byte)6);
		news.seteId(eventItem.geteId());
		news.setEiId(eventItem.getEiId());
		newsDao.insert(news);

		todo.setRemindTime(timeOne);
		todo.setFinishTime(eventItem.getFinishTime());
		todo.setPushTime(eventItem.getFinishTime());
		todo.settExplained(EventMap.desMap.get("0"));
		todo.settState((byte) 16);
		todo.settName(EventMap.itemStateMap.get("0"));
		
		todo.setEiId(eventItem.getEiId());
		todo.seteId(eventItem.geteId());
		todo.setIsValid((byte) 1);
		todo.setIsBlock((byte) 0);
		todo.setIsFinish((byte) 0);
		todo.settSubjType((byte) 1);
		// 保存待办任务信息
		todoDao.saveTodo(todo);
		JoinEvent join = new JoinEvent();
		join.settId(todo.getId());
		join.seteId(envet.geteId());
		join.setUserId(eventItem.getDutyId());
		join.setIsValid((byte) 1);
		join.setType((byte) 2);
		join.setState((byte) 1);
		//join.setFrequency(envet.getFrequency());
		join.setIsSign((byte) 0);
		join.setEiId(eventItem.getEiId());
		join.setCreateBy(eventItem.getCreateBy());
		JoinEvent jo = joinEventDao.itemJoinInfo(join);
		if (jo == null) {
			joinEventDao.insert(join);
		}
		// 添加周报内容
		if (envet.getLeId() != null) {
			saveItemWeekly(eventItem, envet);
		}
		
	}
	
	/**
	 * 添加周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午6:16:33
	 * @version V0.0.3
	 * @param event
	 */
	public void saveItemWeekly(EventItem eventItem,Event event){
		  LightWeekly lightWeekly=new LightWeekly();
		  lightWeekly.setUserId(eventItem.getDutyId());
		  lightWeekly.setLeId(event.getLeId());
		  lightWeekly.seteId(event.geteId());
		  lightWeekly.setEiId(eventItem.getEiId());
		  lightWeekly.setIsFinish((byte)0);
		  lightWeekly.setCreateBy(eventItem.getCreateBy());
		  lightWeekly.setIsValid((byte)1);
		  lightWeekly.setStartTime(eventItem.getStartTime());
		weeklyDao.saveWeekly(lightWeekly);
	}

}


