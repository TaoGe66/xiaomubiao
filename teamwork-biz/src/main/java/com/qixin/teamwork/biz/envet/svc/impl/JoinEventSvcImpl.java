package com.qixin.teamwork.biz.envet.svc.impl;

import com.qixin.teamwork.biz.base.EventMap; 
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.census.svc.LightWeeklySvc;
import com.qixin.teamwork.biz.dto.JoinEventDto;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import com.qixin.teamwork.biz.todo.dao.TodoDao;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.svc.TodoSvc;
import com.qixin.teamwork.biz.user.model.User;

import org.framework.utils.Constant;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.*;

@Service("joinEventSvc")
public class JoinEventSvcImpl  implements JoinEventSvc{

	@Resource 
	private JoinEventDao joinEventDao;
	@Resource
	private EventItemDao eventItemDao;
	@Resource
	private TodoSvc todoSvc;
	@Autowired
	private EventDao eventDao;
	@Autowired
	private TodoDao todoDao;
	@Autowired
	private NewsDao newsDao;
	@Resource
	private TrackRecordSvc trackRecordSvc;
	@Autowired
	private LightWeeklySvc lightWeeklySvc;

	/**
	 * 我的事件列表
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:30:43
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	@Override
	public List<JoinEvent> listJoinEvent(JoinEvent joinEvent) {
			List<JoinEvent> listJoinEventList=new ArrayList<JoinEvent>();
			List<JoinEvent> joinEventList=joinEventDao.listJoinEvent(joinEvent);
			if (joinEventList.size()>0) {
				for (int i = 0; i < joinEventList.size(); i++) {
					JoinEvent join=joinEventList.get(i);
					EventItem eventItem=new EventItem();
					Event event=new Event();
					event.seteId(join.geteId());
					Event eventInfo=eventDao.infoEnvet(event);
					join.setCuserId(eventInfo.getCreateBy());
					//责任人信息
					if (join.getEventType()==0 || join.getEventType()==2) {
						JoinEvent je=new JoinEvent();
						je.setType((byte)0);
						je.seteId(join.geteId());
						JoinEvent jet=joinEventDao.dutyInfo(je);
						join.setUserName(jet.getUserName());
						join.setHeadUrl(jet.getHeadUrl());
					}else{
						JoinEvent je=new JoinEvent();
						je.setType((byte)1);
						je.seteId(join.geteId());
						je.setState((byte)1);
						JoinEvent jet=joinEventDao.dutyInfo(je);
						if (jet !=null) {
							join.setDutyName(jet.getUserName());
							join.setHeadUrl(jet.getHeadUrl());
						}else{
							je.setType((byte)0);
							JoinEvent jt=joinEventDao.dutyInfo(je);
							join.setUserName(jt.getUserName());
							join.setHeadUrl(jt.getHeadUrl());
						}
					}
					//创建人
						eventItem.seteId(join.geteId());
						int s=eventItemDao.listEventItem(eventItem).size();
						eventItem.setStateStr("3,7");
						eventItem.setRank(2);
					    int x=eventItemDao.listhealth(eventItem).size();
					    if (eventInfo.getState()==0 || eventInfo.getState()==1 || eventInfo.getState()==3) {
					    	eventItem.setStateStr("0,1,6");
					    	eventItem.setFinishTime(new Timestamp(new Date().getTime()));
					    	int k=eventItemDao.listhealth(eventItem).size();
					    	x=x+k;
						}
						//完成率
						double c = 1; 
						if(s!=0){
							c= (double)x/s; 
						}
						join.setPercent(c);
						
					if (join.getType()==0) {
						TrackRecord record = new TrackRecord();
						record.seteId(join.geteId());
						record.setIsRed((byte) 0);
						record.setDonotUser(joinEvent.getUserId());
						List<TrackRecord> recordList= trackRecordSvc.listTrackRecord(record);
			        	if(recordList.size()>0){
			        		join.setIsRecord(recordList.get(0).getCreateBy());
			        	}
						
						listJoinEventList.add(join);
					}else{
						//add by zyting 2017/08/09责任人 获取最新行动跟踪者
						if(join.getType()==1){
							TrackRecord record = new TrackRecord();
							record.seteId(join.geteId());
							record.setIsRed((byte) 0);
							record.setDonotUser(joinEvent.getUserId());
							List<TrackRecord> recordList= trackRecordSvc.listTrackRecord(record);
				        	if(recordList.size()>0){
				        		join.setIsRecord(recordList.get(0).getCreateBy());
				        	}
						}
						
						listJoinEventList.add(join);
						
					}
				}
			}
		
		return listJoinEventList;
	}

	/**
	 * 删除我的事件
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午4:46:12
	 * @version V0.0.1
	 * @param joinEvent
	 */
	@Override
	public void deleteJoinEvent(JoinEvent joinEvent) {
		 joinEventDao.deleteJoinEvent(joinEvent);
	}
	/**
	 * 根据事件id获取事件下参与用户列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/12 18:01
	 */
	@Override
	public List<JoinEvent> listJoinEventByEid(JoinEvent joinEvent) {
		return joinEventDao.listJoinEventByEid(joinEvent);
	}

	/**
	 * 根据事件id和用户id查询事项责任人是否存在
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午2:07:02
	 * @version V0.0.1
	 * @param joinEvent
	 * @return
	 */
	@Override
	public JoinEvent JoinEventInfo(JoinEvent joinEvent) {
		return joinEventDao.JoinEventInfo(joinEvent);
	}

	/**
	 *  修改我的事件推送时间
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午4:46:12
	 * @version V0.0.1
	 * @param joinEvent
	 */
	@Override
	public void updateJoinEvent(JoinEvent joinEvent) {
		joinEventDao.updateEventTime(joinEvent);
		Event envet=new Event();
		envet.seteId(joinEvent.geteId());
		Event even=eventDao.getEnvetInfo(envet);
		//更新事项
		EventItem eventItem=new EventItem();
		eventItem.seteId(joinEvent.geteId());
		eventItem.setDutyId(joinEvent.getUserId());
		List<EventItem> itemList=eventItemDao.listEventItem(eventItem);
		Todo todo=new Todo();
		todo.setUserId(joinEvent.getUserId());
		todo.seteId(joinEvent.geteId());
		todo.settSubjType((byte)0);
		//待办事项列表
		List<Todo> todoList=todoSvc.listItemTodo(todo);
		//修改事项信息和待办任务信息
		saveItemToDo(joinEvent,even,itemList,todoList);
	}
	/**
	 * 1：保存用户参与记录
     *  1.1根据事件id获取事件详情
	 *  1.2 组织活动:每个参与者都是该事件下事项的负责人,每个参与生成待办任务
	 *	1.3 服务请求:只生成参与者记录
	 * @author xiehuilin
	 * @param joinEvent 用户参与实体
     * @return  map集合
	 * @version V0.0.1
	 * @date 2017/6/13 14:47
	 */
	@Override
	public Map<String,Object> insert(JoinEvent joinEvent) {
	    Map<String,Object> map=new HashMap<String, Object>();
        String msg="error";
        String errorCode="";
		Event event=new Event();
		event.seteId(joinEvent.geteId());
		// @ 1.1根据事件id获取事件详情
        Event envetInfo = eventDao.getEnvetInfo(event);
		List<JoinEvent> joinEvents = joinEventDao.listJoinEventByEid(joinEvent);
		if(null==joinEvents||joinEvents.size()<=0){
			if(null!=envetInfo){
				// 生成参与记录
				joinEvent.setCreateBy(envetInfo.getCreateBy());
				Byte type=envetInfo.getType()==1?new Byte("1"):new Byte("2");
				joinEvent.setType(type);
				Byte b=envetInfo.getType()==1?new Byte("0"):new Byte("1");
				joinEvent.setState(b);
				joinEvent.setFrequency(envetInfo.getFrequency());
				//组织活动更新参与人数
				if(envetInfo.getType()==2){
				  if(envetInfo.getMaxCount()!=envetInfo.getLessCount()){	
						eventDao.updateEnvetPoll(envetInfo);
						joinEventDao.insert(joinEvent);
						//@ 1.2 组织活动:每个参与者都是该事件下事项的负责人,每个参与生成待办任务和事项记录
						//生成待办任务
						Todo todo=new Todo();
						todo.setUserId(joinEvent.getUserId());
						todo.seteId(envetInfo.geteId());
						todo.settSubjType(new Byte("0"));
						todo.settState(new Byte("4"));
						todo.settName(EventMap.stateMap.get("4"));
						todo.settExplained(EventMap.eventDesMap.get("4"));
						todo.setIsBlock(new Byte("0"));
						todo.setIsSend(new Byte("0"));
						todo.setCreateBy(joinEvent.getUserId());
						Timestamp toDoRemindTime = ToDoRemindUtils.getToDoRemindTime(envetInfo.getStartTime(), envetInfo.getFrequency());
						todo.setRemindTime(toDoRemindTime);
					    todo.setPushTime(toDoRemindTime);
					    todo.setFinishTime(envetInfo.getEndTime());
					    todo.setIsFinish(new Byte("0"));
						//设置待办提醒时间
						todoDao.saveTodo(todo);
					  //add by xiehuilin 2017109 生成待办信息
					  News sNews =new News();
					  sNews.setnTitle("项目名称:"+envetInfo.getName());
					  sNews.setnTime(toDoRemindTime);
					  sNews.setCreateBy(envetInfo.getCreateBy());
					  sNews.setCreateTime(new Timestamp((new Date()).getTime()) );
					  sNews.setIsValid((byte)1);
					  sNews.setSendId(envetInfo.getCuserId());
					  sNews.setnType((byte)1);
					  sNews.seteId(envetInfo.geteId());
					  sNews.setnContent(EventMap.newsMap.get("7"));
					  sNews.setnName(envetInfo.getName());
					  sNews.setnMold((byte)4);
					  sNews.seteId(event.geteId());
					  newsDao.insert(sNews);

                      //@1.4：用户是否关注公众号
                      saveTodaoToNews(joinEvent, envetInfo,"4");
				  }else{
					  errorCode="报名人数已满"; 
					  msg="error";
				  }

			}else{
				joinEventDao.insert(joinEvent);
			}
			}else{
				errorCode="您已参与本活动";
			}
		}
        msg=null!=joinEvent.getId()&&joinEvent.getId()>0?"success":msg;
        map.put("msg",msg);
		map.put("errorCode",errorCode);
        return map;
    }


	/**
	 * 修改事项信息和待办任务信息 	type : 2 组织活动   0 时间管理  1 服务请求
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午5:08:40
	 * @version V0.0.1
	 */
	public void saveItemToDo(JoinEvent joinEvent,Event even,List<EventItem> itemList,List<Todo> todoList){
		/*	for (int i = 0; i < itemList.size(); i++) {
				
				EventItem item=itemList.get(i);
				Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(item.getFinishTime(), joinEvent.getFrequency());
				item.setStartTime(timeOne);
				//修改事项信息
				eventItemDao.updateEventItem(item);
			}
			*/
			for (int j = 0; j < todoList.size(); j++) {
				Todo tod=todoList.get(j);
				Todo to=new Todo();
				Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(tod.getFinishTime(),joinEvent.getFrequency());
				to.setRemindTime(timeOne);
				to.setId(tod.getId());
				//待办任务信息
				todoSvc.updateTodo(to);
			}
	}
	/* 1:服务请求记录选中责任人记录
	*   1.1:根据事件id获取事件详情,更新事件可见范围
	*   1.2:根据事件id获取事件下所有参选人
	*   1.3:更新选中者记录
	*   	 是否生成项目待计划记录
	*   	  事件开始事件往后推迟两个小时是否小于事件结束时间
	*   	  	是:生成待计划记录
	*   	 	否：不生成
	*   1.4：生成周报记录
	* @description
	* @author xiehuilin
	* @param  joinEvent 事件参与者实体
	* @version V0.0.1
	* @date 2017/07/19 13:29
	*/
	@Override
	public void updateScreenDuty(JoinEvent joinEvent) {
        // @ 1.1根据事件id获取事件详情
        Event event=new Event();
        event.seteId(joinEvent.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        envetInfo.setPubRange(new Byte("2"));
        eventDao.updateEnvet(envetInfo);
	    //@ 1.2:根据事件id获取事件下所有参选人
		JoinEvent queryJoinEvent=new JoinEvent();
		queryJoinEvent.seteId(joinEvent.geteId());
        List<JoinEvent> joinEvents = joinEventDao.listJoinEventByEid(queryJoinEvent);
        //@ 1.2:更新选中者记录,为该选中者生成待办任务,其他参与者生产落选消息提醒,将落选参与者更新为无效
        joinEvent.setState(new Byte("1"));
        joinEvent.setType(new Byte("1"));
        joinEventDao.updateJoinEvent(joinEvent);
		Timestamp pushTime=ToDoRemindUtils.getToDoRemindTime(envetInfo.getStartTime(), Constant.TIME_DELAY_MINUTE);
		Timestamp fulfilTime=ToDoRemindUtils.getToDoRemindTime(envetInfo.getEndTime(), 0);
		if(pushTime.getTime() < fulfilTime.getTime()){
			//生成待办任务
			Todo todo=new Todo();
			todo.setUserId(joinEvent.getUserId());
			todo.seteId(envetInfo.geteId());
			todo.settSubjType(new Byte("0"));
			todo.settState(new Byte("0"));
			todo.settName(EventMap.stateMap.get("0"));
			todo.setIsBlock(new Byte("0"));
			todo.setIsSend(new Byte("0"));
			todo.setCreateBy(joinEvent.getUserId());
			todo.settExplained(EventMap.eventDesMap.get("1"));
			todo.setIsFinish(new Byte("0"));
			todo.setPushTime(new Timestamp((new Date()).getTime()) );
			todo.setFinishTime(new Timestamp((new Date()).getTime()));
			Timestamp toDoRemindTime = ToDoRemindUtils.getToDoRemindTime(envetInfo.getStartTime(), envetInfo.getFrequency());
			todo.setRemindTime(toDoRemindTime);
			//设置待办提醒时间
			todoDao.saveTodo(todo);
		}

		News sNews =new News();
		sNews.setnTitle("项目名称:"+envetInfo.getName());
		sNews.setnTime(new Timestamp((new Date()).getTime()));
		sNews.setCreateBy(envetInfo.getCreateBy());
		sNews.setCreateTime(new Timestamp((new Date()).getTime()) );
		sNews.setIsValid((byte)1);
		sNews.setSendId(joinEvent.getUserId());
		sNews.setnType((byte)1);
		sNews.seteId(joinEvent.geteId());
		sNews.setnContent(EventMap.newsMap.get("0"));
		sNews.setnName(envetInfo.getName());
		sNews.setnMold((byte)4);
		sNews.seteId(event.geteId());
		newsDao.insert(sNews);
        News news=new News();
        news.setnTitle("项目名称:"+envetInfo.getName());
        news.setnName(envetInfo.getName());
        news.setnContent("抱歉，创建人已选出责任人");
        news.seteId(event.geteId());
        news.setnMold((byte)11);
        news.setCreateBy(envetInfo.getCreateBy());
        news.setnTime(new Timestamp(new  Date().getTime()));
        if(null!=joinEvents&&joinEvents.size()>0){
            for(JoinEvent je:joinEvents){
					if(!je.getUserId().equals(envetInfo.getCreateBy())&&!je.getUserId().equals(joinEvent.getUserId())){
						news.setSendId(je.getUserId());
						news.setCreateBy(envetInfo.getCreateBy());
						news.seteId(joinEvent.geteId());
						news.setnType(new Byte("2"));
						newsDao.insert(news);

						je.setIsValid(new Byte("0"));
						joinEventDao.updateJoinEvent(je);
					}
			}
        }
        //@1.5: 创建人下的关闭需求转移到责任人身上
        Todo todo1 = new Todo(); 
		Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(envetInfo.getEndTime(), envetInfo.getFrequency());
        todo1.setUserId(joinEvent.getUserId());
        todo1.seteId(joinEvent.geteId());
        todo1.settState((byte)3);
        todo1.settName(EventMap.stateMap.get("3"));
        todo1.settExplained(EventMap.eventDesMap.get("3"));
        todo1.setIsBlock((byte)0);
        todo1.setIsSend((byte)0);
    	todo1.setRemindTime(timeOne);
    	todo1.setCreateBy(envetInfo.getCreateBy());
    	todo1.settSubjType((byte)0);
    	todo1.setFinishTime(envetInfo.getEndTime());
    	todo1.setPushTime(envetInfo.getEndTime());
    	
        todoSvc.saveTodo(todo1);
       // todoSvc.updateUserTodo(todo1);

		//add by xiehuilin 20170908 轻企需求为选中人生成周报记录
		if(envetInfo.getLeId()!=null){
			LightWeekly weekly=new LightWeekly();
			weekly.seteId(envetInfo.geteId());
			weekly.setLeId(envetInfo.getLeId());
			LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
			weeklyInfo.setUserId(joinEvent.getUserId());
			lightWeeklySvc.updateWeekly(weeklyInfo);
		}
    }


    /**
     * 根据id获取参与者信息
     * @author xiehuilin
     * @param  joinEvent 参与者实体
     * @return joinEvent 参与者实体
     * @version V0.0.1
     * @date 2017/6/14 19:18
     */
    @Override
    public JoinEvent getJoinEventInfo(JoinEvent joinEvent) {
        return joinEventDao.getJoinEventInfo(joinEvent);
    }

	/**
	 *  根据事先id和参与者id获取参与者信息
	 * @author xiehuilin
	 * @param   joinEvent 参与者实体
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/21 16:21
	 */
	@Override
	public JoinEventDto getStandData(JoinEvent joinEvent) {
		return joinEventDao.getStandData( joinEvent);
	}
	/**
	* 签到:更新参与表,更新待办任务表，生成待办历史
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/24 8:30
	*/
	@Override
	public void update(JoinEvent joinEvent) {
        joinEventDao.updateJoinEvent(joinEvent);
		Todo todo=new Todo();
		todo.setUserId(joinEvent.getUserId());
		todo.seteId(joinEvent.geteId());
        Todo todoInfo = todoDao.getTodoInfo(todo);
        /*TodoHistory todoHistory=new TodoHistory();
        BeanUtils.copyProperties(todoInfo,todoHistory);
        todoHistoryDao.saveTodoHistory(todoHistory);*/
        todoInfo.setPushTime(StrUtils.getNowTimestamp());
		todoInfo.setIsFinish(new Byte("1"));
        todoDao.updateTodo(todoInfo);

    }

	/**
	    * 根据事件id和用户id查询参与者身份为事项责任人的参与者信息
	    * @author wuchao
	    * @date 2017年6月27日
	    * @time 下午3:41:04
	    * @version V0.0.1
	    * @param joinEvent
	    * @return
	    */
	@Override
	public JoinEvent itemJoinInfo(JoinEvent joinEvent) {
		return joinEventDao.itemJoinInfo(joinEvent);
	}

	@Override
	public JoinEvent getByUserIdOrEidDuty(JoinEvent joinEvent) {
		// TODO Auto-generated method stub
		return  joinEventDao.getByUserIdOrEidDuty(joinEvent);
	}

    /**
    *
    *  将待办消息同步到系统消息
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/7/7 10:51
    */
    private void saveTodaoToNews(JoinEvent joinEvent, Event envetInfo, String state) {
        	User user=new User();
        	user.setUserId(joinEvent.getUserId());
            News n=new News();
            n.setSendId(joinEvent.getUserId());
            n.setnTitle(EventMap.stateMap.get(state));
            n.setnName(envetInfo.getName());
            n.setnContent(EventMap.eventDesMap.get(state));
            n.setnTime(envetInfo.getSignTime());
            n.setCreateBy(joinEvent.getCreateBy());
            n.seteId(envetInfo.geteId());
			n.setnMold(new Byte("11"));
            n.setIsRed(new Byte("0"));
            n.setnType(new Byte("1"));
            newsDao.insert(n);


    }

	@Override
	public List<JoinEvent> getDelStatus(FriendGroup friendGroup) {
		return joinEventDao.getDelStatus(friendGroup);
	}

	@Override
	public JoinEvent dutyInfo(JoinEvent joinEvent) {
		return joinEventDao.dutyInfo(joinEvent);
	}

	//修改目标责任人脚本
	@Override
	public void updateCreate() {
		Event envet=new Event();
		envet.setType((byte)0);
		List<Event> eventList= eventDao.listEvenInfo(envet);
		for (int i = 0; i < eventList.size(); i++) {
			Event ev=eventList.get(i);
			JoinEvent joinEvent=new JoinEvent();
			joinEvent.seteId(ev.geteId());
			joinEvent.setCreateBy(ev.getCreateBy());
			System.out.println(ev.geteId()+"-----"+ev.getCreateBy());
			joinEventDao.updateCreate(joinEvent);
		}
	}

	/**
	 * 根据用户id和事件id修改提醒时间
	 * @author wuchao
	 * @date 2017年8月18日
	 * @time 下午4:19:50
	 * @version V0.0.1
	 * @param joinEvent
	 */
	@Override
	public void updateEventTime(JoinEvent joinEvent) {
		joinEventDao.updateEventTime(joinEvent);
	}

	 /**
	  * 根据用户id和轻企id查询用户轻企的项目
	  * @author wuchao
	  * @date 2017年9月7日
	  * @time 下午6:24:40
	  * @version V0.0.3
	  * @param joinEvent
	  * @return
	  */
	@Override
	public List<JoinEvent> listProject(JoinEvent joinEvent) {
		List<JoinEvent> listJoinEventList=new ArrayList<JoinEvent>();
		List<JoinEvent> joinEventList=joinEventDao.listProject(joinEvent);
		if (joinEventList.size()>0) {
			for (int i = 0; i < joinEventList.size(); i++) {
				JoinEvent join=joinEventList.get(i);
				Event event=new Event();
				event.seteId(join.geteId());
				Event eventInfo=eventDao.infoEnvet(event);
				EventItem eventItem=new EventItem();
				eventItem.seteId(join.geteId());
				int s=eventItemDao.listEventItem(eventItem).size();
				eventItem.setStateStr("3,7");
				eventItem.setRank(2);
			    int x=eventItemDao.listhealth(eventItem).size();
			    if (eventInfo.getState()==0 || eventInfo.getState()==1 || eventInfo.getState()==3) {
			    	eventItem.setStateStr("0,1,6");
			    	eventItem.setFinishTime(new Timestamp(new Date().getTime()));
			    	int k=eventItemDao.listhealth(eventItem).size();
			    	x=x+k;
				}
				//完成率
				double c = 1; 
				if(s!=0){
					c= (double)x/s; 
				}
				join.setPercent(c);
				
				//责任人信息
				if (join.getEventType()==0 || join.getEventType()==2) {
					JoinEvent je=new JoinEvent();
					je.setType((byte)0);
					je.seteId(join.geteId());
					JoinEvent jet=joinEventDao.dutyInfo(je);
					join.setUserName(jet.getUserName());
					join.setHeadUrl(jet.getHeadUrl());
					listJoinEventList.add(join);
				}else{
					JoinEvent je=new JoinEvent();
					je.setType((byte)1);
					je.seteId(join.geteId());
					je.setState((byte)1);
					JoinEvent jet=joinEventDao.dutyInfo(je);
					if (jet !=null) {
						join.setDutyName(jet.getUserName());
						join.setHeadUrl(jet.getHeadUrl());
					}else{
						je.setType((byte)0);
						JoinEvent jt=joinEventDao.dutyInfo(je);
						join.setUserName(jt.getUserName());
						join.setHeadUrl(jt.getHeadUrl());
					}
					listJoinEventList.add(join);
				}
				
				
			}
		}
		return listJoinEventList;
	}

	@Override
	public List<JoinEvent> eventJoinList(JoinEvent joinEvent) {
		return joinEventDao.eventJoinList(joinEvent);
	}

	@Override
	public List<JoinEvent> joinEventPeople(JoinEvent joinEvent) {
		return joinEventDao.joinEventPeople(joinEvent);
	}

	@Override
	public void outItemEven(JoinEvent joinEvent) {
		joinEventDao.outItemEven(joinEvent);
	}

	/**
	 *@Description: 根据行动id或项目id批量更新参与表记录
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	@Override
	public void updateBacth(JoinEvent joinEvent) {
		joinEventDao.updateBacth(joinEvent);
	}


	/**
	 *@Description: 根据任务id和参与id更新项目参与人记录
	 * 1：将该用户属于项目参与人的角色更新为无效
	 * 2：将该用户属于行动参与人或责任人更新为无效
	 * 		如果有没为完成的更新则更新为异常终止
	 *@author:xiehuilin
	 *@param:joinEvent
	 *@return:void
	 *@date:2017/10/26 17:02
	 *@version V 0.0.7
	 */
	@Override
	public  void updateByEidAndUserId(JoinEvent joinEvent){
		//根据参与id获取是否有负责未完成的项目，有则置为无效
		EventItem item=new EventItem();
		item.setStateStr("0");
		item.setDutyId(joinEvent.getUserId());
        List<EventItem> eventItems = eventItemDao.listItem(item);
        if(!eventItems.isEmpty()){
            for(EventItem ei:eventItems){
                ei.setState(new Byte("5"));
                eventItemDao.updateEventItem(ei);
            }
        }
        joinEvent.setIsValid(new Byte("0"));
        joinEventDao.updateByEidAndUserId(joinEvent);
	}
    /**
     *@Description:项目描述保存行动参与者记录
     * 保存项目参与者记录
     * 保存消息记录
     *@author:xiehuilin
     *@param:joinEvent
     *@return:void
     *@date:2017/10/26 17:02
     *@version V 0.0.7
     */
    @Override
    public void saveEventJoinUser(JoinEvent joinEvent) {
        joinEvent.setState(new Byte("3"));
        joinEvent.setCreateBy(joinEvent.getCreateBy());
        //保存项目和用户参与记录
        joinEventDao.insert(joinEvent);
        //保存消息记录
        News news=new News();
        //根据项目id获取项目信息
        Event event=new Event();
        event.seteId(joinEvent.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        StringBuilder builder=new StringBuilder().append("任务名称：").append(envetInfo.getName());
        news.setnTitle(builder.toString());
        news.setnTime(StrUtils.getNowTimestamp());
        news.setCreateBy(joinEvent.getCreateBy());
        news.setSendId(joinEvent.getUserId());
        news.setnType(new Byte("1"));
        news.seteId(envetInfo.geteId());
        StringBuilder builder1=new StringBuilder().append(joinEvent.getUserName()).append("邀请你成为任务参与人");
        news.setnContent(builder1.toString());
        newsDao.insert(news);

    }
    
    
    /**
     *@Description:项目描述保存行动参与者记录
     * 保存项目参与者记录
     * 保存消息记录
     *@author:xiehuilin
     *@param:joinEvent
     *@return:void
     *@date:2017/10/26 17:02
     *@version V 0.0.7
     */
    @Override
    public void saveEventItemJoinUser(JoinEvent joinEvent) {
    	 //保存项目和用户参与记录
    	joinEvent.setType(new Byte("4"));
    	joinEvent.setState(new Byte("1"));
        joinEvent.setCreateBy(joinEvent.getCreateBy());
        joinEventDao.insert(joinEvent);
        
        //查询是否是项目参与人，不是则添加
        joinEvent.setType(new Byte("3"));
        JoinEvent j = joinEventDao.getJoinEventInfo(joinEvent);
        if(j==null){
        	joinEventDao.insert(joinEvent);
        }
    }

    /**
	 * 根据用户id和轻企id，获取用户在轻企内负责未完成的目标
	 *@author wuchao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	@Override
	public List<JoinEvent> listDignity(JoinEvent joinEvent) {
		return  joinEventDao.listDignity(joinEvent);
	}

	/**
	 * 根据用户id和轻企id，获取用户在轻企内负责未完成的行动（非自己创建的目标）
	 *@author wuchao
	 *@data 2017年10月31日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	@Override
	public List<JoinEvent> listItemDignity(JoinEvent joinEvent) {
		return joinEventDao.listItemDignity(joinEvent);
	}

    /**
     *@Description:根据任务id和用户id获取用户角色身份列表
     *@author:xiehuilin
     *@param:joinEvent
     *@return:java.util.List<com.qixin.teamwork.biz.envet.model.JoinEvent>
     *@date:2017/11/2 10:02
     *@version V 0.0.7
     */
    @Override
    public List<JoinEvent> listGetJoinUserType(JoinEvent joinEvent) {
        return joinEventDao.listGetJoinUserType(joinEvent);
    }

	/**
	 * 目标的所有参与人
	 *@author wuchao
	 *@data 2017年11月2日
	 *@version V0.0.5
	 * @param joinEvent
	 * @return
	 */
	@Override
	public List<JoinEvent> listEventPartake(JoinEvent joinEvent) {
		return joinEventDao.listEventPartake(joinEvent);
	}
}
