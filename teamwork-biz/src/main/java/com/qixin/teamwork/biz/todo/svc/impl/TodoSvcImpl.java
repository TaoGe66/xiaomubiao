package com.qixin.teamwork.biz.todo.svc.impl;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import javax.naming.CompositeName;
import javax.print.DocFlavor;

import com.qixin.payment.utils.other.WeiXinUtil;
import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.base.EventMap;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.svc.LightWeeklySvc;
import com.qixin.teamwork.biz.comment.dao.CommentDao;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.enterprise.dao.DynamicDao;
import com.qixin.teamwork.biz.enterprise.model.Dynamic;
import com.qixin.teamwork.biz.enterprise.svc.DynamicSvc;
import com.qixin.teamwork.biz.envet.dao.CycleTempletDao;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.model.CycleTemplet;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.group.dao.FriendGroupDao;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import com.qixin.teamwork.biz.todo.dao.TodoHistoryDao;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.todo.dao.TodoDao;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.model.TodoHistory;
import com.qixin.teamwork.biz.todo.svc.TodoSvc;

/**
 * 待办任务实现
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:40:07
 * @version V0.0.1
 */
@Service("todoSvc")
public class TodoSvcImpl implements TodoSvc{

	@Autowired
	private TodoDao todoDao;
	@Resource
	private TodoHistoryDao todoHistoryDao;
	@Autowired
	private EventItemDao eventItemDao;
	@Autowired
	private JoinEventDao joinEventDao;
	@Autowired
	private WxuserDao wxuserDao;
	@Autowired
	private EventDao eventDao;
	@Resource
	private WxuserSvc userSvc;
	@Resource
	private NewsDao newsDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private TrackRecordSvc trackRecordSvc;
	@Autowired
	private CycleTempletDao cycleTempletDao;
	@Autowired
	private FriendGroupDao friendGroupDao;
	@Autowired
	private LightWeeklySvc lightWeeklySvc;
	@Autowired
	private AwardPenaltyListDao awardPenaltyListDao;
	@Autowired
	private DynamicSvc dynamicSvc;
	@Autowired
	private EventItemSvc eventItemSvc;
	@Autowired
	private WeeklyListDao weeklyListDao;
	/**
	 * 保存待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:43:45
	 * @version V0.0.1
	 * @param todo
	 */
	@Override
	public void saveTodo(Todo todo) {
		todoDao.saveTodo(todo);
	}

	/**
	 * 修改待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:44:31
	 * @version V0.0.1
	 * @param todo
	 */
	@Override
	public void updateTodo(Todo todo) {
		todoDao.updateTodo(todo);
	}

	/**
	 * 查询待办任务列表
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午3:27:30
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	@Override
	public List<Todo> listTodo(Todo todo) {
		return todoDao.listTodo(todo);
	}

	@Override
	public List<Todo> listTodoZing(Todo todo) {
		return todoDao.listTodoZing(todo);
	}
	/**
	 * 删除待办任务
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:50:39
	 * @version V0.0.1
	 * @param todo
	 */
	@Override
	public void deleteTodo(Todo todo) {
		todoDao.deleteTodo(todo);
	}

	/**
	 * 待办事项详情
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午11:05:03
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	@Override
	public Todo todoInfo(Todo todo) {
		return todoDao.todoInfo(todo);
	}

	/**
	 * 定时待办任务列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 下午5:36:45
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	@Override
	public List<Todo> taskListTodo(Todo todo) {
		List<Todo> toList = new ArrayList<Todo>();
		List<Todo>  toDoList= todoDao.taskListTodo(todo);
		if (toDoList.size()>0) {
			for (int i = 0; i < toDoList.size(); i++) {
				Todo to=toDoList.get(i);
				 User user=new User();
			        user.setUserId(to.getUserId());
			        User use=userSvc.getUserInfo(user);
			        if (!StrUtils.isEmpty(use.getOpenId())) {
			        	to.setOpenId(use.getOpenId());
					}
			        Event envet=new Event();
			        envet.seteId(to.geteId());
			        //获取事件信息
			        Event en=eventDao.getEnvetInfo(envet);
			        to.settName(en.getName());
			        to.setType(en.getType());
				if (to.gettSubjType()==0) {
					to.settExplained("");
					to.setStartTime(en.getStartTime());
					to.setEndTime(en.getEndTime());
				}else if(to.gettSubjType()==1){
					EventItem item=new EventItem();
					item.setEiId(to.getEiId());
					//获取事项详情
					EventItem itm=eventItemDao.getItemInfo(item);
					to.settExplained(itm.getEiDesc());
					to.setFinishTime(itm.getFinishTime());
				}
				toList.add(to);
			}
		}
		 return toList;
	}

	/**
	 * 关闭事件
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * id  待办任务id
	 */
	@Override
	public void updateTodoCloseEvent(Todo todo) {
		//删除所有事件相关未推送的系统消息
		News news=new News();
		news.seteId(todo.geteId());
		news.setnTime(new Timestamp((new Date()).getTime()));
		newsDao.delete(news);
		Event  event1=new Event();
        event1.seteId(todo.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event1);

        //add by xiehuilin 2017/11/03未设置直接时间,项目关闭事件就是项目结束时间
		if(null==envetInfo.getEndTime()){
			envetInfo.setEndTime(StrUtils.getNowTimestamp());
		}
         //添加清单
         if(envetInfo.getLeId()!=null){
        	 WeeklyList w = new WeeklyList();
        	 w.seteId(todo.geteId());
        	 w.setType((byte)0);
        	 WeeklyList wq = weeklyListDao.getWeeklyInfo(w);
        	 if(wq!=null){
        		 WeeklyList weeklyList = new WeeklyList();
            	 weeklyList.setLeId(envetInfo.getLeId());
            	 weeklyList.seteId(todo.geteId());
            	 weeklyList.setUserId(todo.getUserId());
            	 weeklyList.setCreateBy(todo.getUserId());
            	 weeklyList.setIsRecord((byte)0);
            	 weeklyList.setType((byte)3);
            	 weeklyListDao.insert(weeklyList);
        	 }else{
				 WeeklyList weeklyList = new WeeklyList();
				 weeklyList.setLeId(envetInfo.getLeId());
				 weeklyList.seteId(todo.geteId());
				 weeklyList.setUserId(todo.getUserId());
				 weeklyList.setCreateBy(todo.getUserId());
				 weeklyList.setIsRecord((byte)0);
				 weeklyList.setType((byte)0);
				 weeklyListDao.insert(weeklyList);


            	 weeklyList.setLeId(envetInfo.getLeId());
            	 weeklyList.seteId(todo.geteId());
            	 weeklyList.setUserId(todo.getUserId());
            	 weeklyList.setCreateBy(todo.getUserId());
            	 weeklyList.setIsRecord((byte)0);
            	 weeklyList.setType((byte)3);
            	 weeklyListDao.insert(weeklyList);
        		 

        	 }
        	
         }
	  	//1.关闭事件，填写事件总结
    	Event event = new Event();
    	event.seteId(todo.geteId());
    	event.setFinishTime(todo.getFinishTime());
    	event.setSummary(todo.getSummary());
    	EventItem eventItem=new EventItem();
		eventItem.seteId(todo.geteId());
        eventItem.setStateStr("0,1,2");
        if(envetInfo.getType()==1){
            JoinEvent j = new JoinEvent();
            j.seteId(event.geteId());
            j.setType((byte) 1);
            j.setState(new Byte("1"));
            JoinEvent je =  joinEventDao.JoinEventInfo(j);
            if(je!=null){
            	 eventItem.setCreateBy(je.getUserId());
            }else{
            	eventItem.setCreateBy(envetInfo.getCreateBy());
            }
           
        }else {
            eventItem.setCreateBy(envetInfo.getCreateBy());
        }
        List<EventItem> listUnfinishedEventItem = eventItemDao.listUnfinishedEventItem(eventItem);
    	int itemsize = listUnfinishedEventItem.size();
    	
    	Date etime=DateUtils.getTimestampToDate(envetInfo.getEndTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
    	Date ftime=DateUtils.getTimestampToDate(todo.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);

		StringBuilder builder=new StringBuilder();
    	if(itemsize==0){
    		if(etime.compareTo(ftime)>0){ 
    			event.setState((byte)7);
				builder.append("提前完成了一项");
    		}else if(etime.compareTo(ftime)<0){
    			event.setState((byte)8);
				builder.append("滞后完成了一项");
    		}else if(etime.compareTo(ftime)==0){
    			event.setState((byte)2);
    			builder.append("正常完成了一项");
    		}
    	}else{
    		event.setState((byte)6);
			builder.append("异常终止了一项");
    	}
    	eventDao.updateEnvet(event);
		//2.edit by xiehuilin  关闭事件不更新未完成的事项
    	//eventItemDao.updateItem(todo.geteId());
		//3.查询todo表里所有有关事件的任务
		todo.setIsFinish(new Byte("0"));
		Todo qTodo=new Todo();
		qTodo.seteId(todo.geteId());
        qTodo.setIsFinish(new Byte("0"));
    	List<Todo> l = listTodo(qTodo);
    	//4.放入历史表里 删除todo表里任务
    	for(int i=0;i<l.size();i++){
    	  	//4.2.删除todo表里任务
            Todo relsutTodo = l.get(i);
            Todo todo1 = new Todo();
    		todo1.setId(relsutTodo.getId());
            Timestamp pushTime = relsutTodo.getPushTime();
            Date pDate = DateUtils.getTimestampToDate(pushTime, Constant.DEFAULT_DATE_FORMAT_PATTERN);
            Date cDate=DateUtils.getTimestampToDate(new Timestamp(new Date().getTime()), Constant.DEFAULT_DATE_FORMAT_PATTERN);
			Date pDatehms = DateUtils.getTimestampToDate(pushTime, Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN);
			Date cDatehms=DateUtils.getTimestampToDate(new Timestamp(new Date().getTime()), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN);
            //add by xiehuilin 20170728 当前时间大于推送时间
            if(cDate.compareTo(pDate)>=0){
				if(relsutTodo.gettSubjType()==0&&relsutTodo.gettState()==3){
					todo1.setIsValid(new Byte("1"));
					todo1.setIsFinish(new Byte("1"));
					updateTodo(todo1);
				}else if(cDatehms.compareTo(pDatehms)<0&&relsutTodo.gettSubjType()==0&&relsutTodo.gettState()==0){
					todo1.setIsValid(new Byte("0"));
					updateTodo(todo1);
				}
				else{
					todo1.setIsValid(new Byte("1"));
					todo1.setIsInvalid(new Byte("1"));
					updateTodo(todo1);
				}
            }else{
				todo1.setIsValid(new Byte("0"));
				updateTodo(todo1);
            }
    	}
    	//add by xiehuilin 20170727  为未完成的行动的责任人新增系统消息
        if(null!=listUnfinishedEventItem&&listUnfinishedEventItem.size()>0){
            for(EventItem item:listUnfinishedEventItem){
                item.setStateStr("0, 1, 2");
                int count = eventItemDao.listEventItemByeIdAndDutyId(item).size();
                news=new News();
                news.seteId(todo.geteId());
                news.setnTitle("项目名称"+envetInfo.getName());
                news.setnTime(new Timestamp((new Date()).getTime()));
                news.setnContent(new StringBuilder().append("项目关闭,你所负责的").append(count).append("项行动已失效").toString());
                news.setnName(envetInfo.getName());
                news.setSendId(item.getDutyId());
                news.setnType(new Byte("2"));
                news.seteId(item.geteId());
                news.setEiId(item.getEiId());
                news.setnMold(new Byte("10"));
                newsDao.insert(news);

            }
        }
        //add  by xiehuilin 20170911  根据完成时间区别是新增周报记录还是更新周报记录
        if(envetInfo.getLeId()!=null){
				LightWeekly weekly=new LightWeekly();
				weekly.setLeId(envetInfo.getLeId());
				weekly.seteId(eventItem.geteId());
				weekly.setUserId(envetInfo.getCuserId());
				LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
				//开始时间是否在本周内,是想周报记录更新为完成、否则新增一条
			    if(DateUtils.isThisWeek(weeklyInfo.getStrTime())){
					weeklyInfo.setIsFinish(new Byte("1"));
				}else {
					LightWeekly kw=new LightWeekly();
					BeanUtils.copyProperties(weeklyInfo,kw);
					kw.setCreateBy(eventItem.getCreateBy());
					kw.setStartTime(StrUtils.getNowTimestamp());
					kw.setEndTime(StrUtils.getNowTimestamp());
					kw.setIsFinish(new Byte("1"));
					lightWeeklySvc.saveWeekly(kw);
				}
			 weeklyInfo.setEndTime(StrUtils.getNowTimestamp());
			 lightWeeklySvc.updateWeekly(weeklyInfo);
			//add by xiehuilin 20170914 项目完成生成动态记录
			Dynamic dynamic=new Dynamic();
			dynamic.seteId(envetInfo.geteId());
			dynamic.setLeId(envetInfo.getLeId());
			dynamic.setUserId(todo.getUserId());
            builder= envetInfo.getType() == 0 ? builder.append("目标") : builder.append("需求");
            dynamic.setTitle(builder.toString());
            dynamic.setCreateBy(envetInfo.getUserId());
            dynamic.setState(new Byte("4"));
			dynamicSvc.saveDynamic(dynamic);
        }
    }

	/**
	 * 完成事项 (废弃)
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * id  待办任务id
	 */
	@Override
	public void updateTodoClose(Todo todo) {{
		//更新待办表
	//	Todo td =updateTodoDelAddHis(todo);
    	//4 修改任务信息
    	EventItem eventItem = new EventItem();
    	eventItem.setEiId(todo.getEiId());
    	eventItem =	eventItemDao.getItemInfo(eventItem);
    	Date eDate =DateUtils.getTimestampToDate(eventItem.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
    	Date tDate =DateUtils.getTimestampToDate(todo.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
    	if (eDate.compareTo(tDate)>0){
    		eventItem.setState((byte)7);
		}else if(eDate.compareTo(tDate)<0){
			eventItem.setState((byte)4);
		}else if(eDate.compareTo(tDate)==0){
			eventItem.setState((byte)3);
		}
    	EventItem e = new EventItem();
    	e.setEiId(todo.getEiId());
    	e.setState(eventItem.getState());
    	e.setfTime(todo.getFinishTimeStr());
    	eventItemSvc.updateItemState(e);
    	/*//更新事项表
    	eventItem.setfTimestamp(todo.getFinishTime());
		eventItem.setRemark(todo.getEiDesc());
    	eventItemDao.updateEventItem(eventItem);
    	
    	//关闭行动跟踪
    	Todo rTodo=new Todo();
		rTodo.settState(new Byte("10"));
		rTodo.settSubjType(new Byte("1"));
		rTodo.seteId(td.geteId());
		rTodo.setEiId(td.getEiId());
		rTodo.setIsFinish(new Byte("0"));
		List<Todo> l = todoDao.getTodoRecordList(rTodo);
		if(l.size()>0){
			for(Todo newTodoInfo :l){
				 newTodoInfo.setIsValid(new Byte("0"));
			     todoDao.updateTodo(newTodoInfo);
			}
			 
		}
		if(eventItem.getCtId()!=null){
			//add by zyting 激活最近项目
			EventItem eventItem1 = new EventItem();
			eventItem1 =eventItemDao.getCycleByTime(eventItem);
			if(eventItem1!=null &&eventItem1.getEiId()!=null){
				eventItem1.setIsCycleEdit((byte)1);
				eventItemDao.updateEventItem(eventItem);
			}
		}
		Event event=new Event();
		event.seteId(eventItem.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        //add  by xiehuilin 20170911  根据完成是否区别是新增周报记录还是更新周报记录
		if(envetInfo.getLeId()!=null){
			// 完成时间在行动开始、截止时间范围内为新增 否则为更新
			if(eventItem.getfTimestamp().compareTo(eventItem.getStartTime())>=0&&
					eventItem.getfTimestamp().compareTo(eventItem.getFinishTime())<=0){
				LightWeekly weekly=new LightWeekly();
				weekly.setUserId(eventItem.getDutyId());
				weekly.setLeId(envetInfo.getLeId());
				weekly.seteId(envetInfo.geteId());
				weekly.setCreateBy(eventItem.getCreateBy());
				weekly.setStartTime(StrUtils.getNowTimestamp());
				lightWeeklySvc.saveWeekly(weekly);

			}else{
				LightWeekly weekly=new LightWeekly();
				weekly.setLeId(envetInfo.getLeId());
				weekly.setEiId(eventItem.getEiId());
				LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
				weeklyInfo.setIsFinish(new Byte("1"));
				lightWeeklySvc.updateWeekly(weeklyInfo);
			}


		}
        if(eventItem.getIsReward()==1){
            // add by xiehuilin 20170911 根据行动id和奖惩列表获取奖惩名单详情
            AwardPenaltyList awardPenaltyList=new AwardPenaltyList();
            awardPenaltyList.setEiId(eventItem.getEiId());
            awardPenaltyList.setRpCategory(eventItem.getRpCategory());
            AwardPenaltyList awardPenaltyListInfo = awardPenaltyListDao.getAwardPenaltyListInfo(awardPenaltyList);
            // add by xiehuilin 20170911 更新奖惩名单记录
            awardPenaltyList.setIsFinish(new Byte("1"));
            awardPenaltyListDao.update(awardPenaltyList);
        }
		*/
		
	}}


	/**
	 *  待办任务接受、拒绝(isAccept 0 否 1 是 )
	 *   1: 被邀请人接受，更新事项信息、新增事件参与者记录,邀请者生成待办任务,被邀请待办任务成为历史记录
	 *   2：拒绝，更新事项信息，邀请者生成待办任务，被邀请待办任务成为历史激励
	 * @author xiehuilin
	 * @param  todo
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/15 10:51
	 */
	@Override
	public void updateAcceptOrRefuse(Todo todo){
        Todo reslutTodo = todoDao.todoInfo(todo);
		checkNews(reslutTodo,todo.getIsAccept());
		Long dutyId = todo.getIsAccept() == 1 ? reslutTodo.getUserId() : reslutTodo.getCreateBy();
        //更新事项信息 start
		EventItem item=new EventItem();
		item.setIsAccept(new Byte(todo.getIsAccept()));
		if (todo.getIsAccept() == 1) {
			item.setState(new Byte("0"));
			item.setStartTime(StrUtils.getNowTimestamp());
		}else{
			item.setState(new Byte("2"));
			item.setIsCycleEdit(new Byte("1"));

		}
        item.setDutyId(dutyId);
        item.setEiId(todo.getEiId());
        eventItemDao.updateEventItem(item);
        //获取事项详细信息
        EventItem infoItem=eventItemDao.getItemInfo(item);
        //获取事件详细信息
        Event event=new Event();
        event.seteId(reslutTodo.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(infoItem.getFinishTime(), envetInfo.getFrequency());
        //更新待办记录为已完成
        //更新事项信息 end
		singleItem(todo, reslutTodo, infoItem, event, envetInfo, timeOne,0,0);
		//生成行到记录待办
		long day=org.framework.utils.StrUtils.numDateBetween(infoItem.getFinishTime().toString(), infoItem.getStartTime().toString());
		if (day >1 && infoItem.getStartTime().getTime()<ToDoRemindUtils.getTimeCheck(2)) {
			//添加明天的行踪待办
			Timestamp pTime=new Timestamp(ToDoRemindUtils.getTimeCheck(4));
			saveTrack(envetInfo,infoItem,pTime,dutyId);

		}else if (day >1 && infoItem.getStartTime().getTime()>ToDoRemindUtils.getTimeCheck(2)) {
			//添加开始时间的行踪待办
			Timestamp pTime=ToDoRemindUtils.getWeeHoursTime(infoItem.getStartTime());//推送时间
			saveTrack(envetInfo,infoItem,pTime,dutyId);
		}

	}


	/**
	 * 保存待办行踪
	 * @author xiehuilin
	 * @date 2017年8月9日
	 * @time 下午3:23:18
	 * @version V0.0.1
	 */
	public void saveTrack(Event envet,EventItem eventItem,Timestamp pTime,Long dutyId){
		Todo toItem=new Todo();
		toItem.setUserId(dutyId);
		toItem.seteId(eventItem.geteId());
		toItem.setEiId(eventItem.getEiId());
		toItem.settState((byte)10);
		toItem.settName(envet.getName());
		toItem.settExplained(EventMap.desMap.get("1"));
		toItem.setIsBlock((byte)0);
		toItem.setIsSend((byte)1);
		toItem.setRemindTime(pTime);
		toItem.setCreateBy(eventItem.getCreateBy());
		toItem.settSubjType((byte)1);
		toItem.setFinishTime(eventItem.getFinishTime());
		toItem.setPushTime(pTime);
		toItem.setIsFinish((byte)0);
		toItem.setIsInvalid((byte)1);
		toItem.settContent(eventItem.getEiDesc());
		toItem.setIsIgnore((byte)0);
		todoDao.saveTodo(toItem);
	}


	/**
	 * 待办事项
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
	@Override
	public List<Todo> tobeEventList(Todo todo) {
		List<Todo> l = listTodoZing(todo);
		for(int i=0;i<l.size();i++){
			 l.get(i).setPushTimeStr(DateUtils.getDateDetailDay(l.get(i).getPushTime().toString()));
			 l.get(i).setEndTimeStr(DateUtils.getDateDetailDay(l.get(i).getEndTime().toString()));
			 if(l.get(i).gettSubjType()==1){
				EventItem eventItem = new EventItem();
				eventItem.seteId(l.get(i).geteId());
				eventItem.setEiId(l.get(i).getEiId());
				eventItem = eventItemDao.listEventItem(eventItem).get(0);
				l.get(i).setEiDesc(eventItem.getEiDesc());
				l.get(i).setRpCategory(eventItem.getRpCategory());
				l.get(i).setBeneficiary(eventItem.getBeneficiary());
				l.get(i).setFinishTimeStr(DateUtils.getDateDetailDay(eventItem.getFinishTime().toString()));
			//	l.get(i).setEiStartTime(StrUtils.getWeekDay(eventItem.getStartTime()));
			}
	         
		}
		
		 Iterator<Todo> it = l.iterator();
         while(it.hasNext()){
        	 Todo t = it.next();
             
        	   //add by zyting 20170718  签到提醒
        	  /*  if(t.getType()==2 && t.gettState()==4){
        	   	
        	   	 //结束时间加一天
        	   	 Calendar target = Calendar.getInstance();
        	        target.setTime(t.getEndTime());
        	        target.add(Calendar.DATE,1);
        	        target.set(Calendar.HOUR_OF_DAY, 0);
        	        target.set(Calendar.MINUTE, 0);
        	        target.set(Calendar.SECOND, 0);
        	   	 
        	   	 if(t.getPushTime().before(new Date())){
        	   		//签到提醒正常展示
        	   		 if(t.getFinishTime().after(new Date())){
        	   			 
        	   		 }
        	   		//签到提示无效
        				 if(t.getFinishTime().before(new Date()) && target.getTime().after(new Date())){
        					 t.setOverdue((byte) 1);
        				 }
        	   		 //不展示
        				 if(target.getTime().before(new Date())){
        					 it.remove();
        					 continue;
        				 }
        	   	 // 不展示 
        	   	 }else{
        	   		it.remove();
        	   		 continue;
        	   	 }
        	    }*/
        	  
        	    //add by zyting 20170719 待计划提醒
        	 /*   if(t.gettSubjType()==0 && t.gettState()==0){
	        	   	if(t.getPushTime().after(new Date())){
	        	   		 it.remove();
	        	   		 continue;
	        	   	}
        	    }*/
        	    
        	    //add by zyting 20170807 待关闭提醒
        	    if(t.gettSubjType()==0 && t.gettState()==3){
        	    	EventItem eventItem = new EventItem();
        	    	eventItem.seteId(t.geteId());
        	    	eventItem.setStateStr("0,1,2");
        	    	if(t.getPushTime().after(new Date())){
	        	   		 it.remove();
	        	   		 continue;
	        	   	}else if(eventItemDao.listEventItem(eventItem).size()!=0){
		        	   	 it.remove();
	        	   		 continue;
	        	   	}
        	    }
        	 
         }
		
		return l;
	}
	
	

	/**
	 * 今日事件
	 * @description
	 * @author zyting
	 * @param todo 待办实体
	 * @return Todo  待办实体
	 * @version V0.0.1
	 * @date 2017/6/15 11:20
	 */
	@Override
	public List<Todo> tadayEvent(Todo todo) {
		List<Todo> l = todoDao.tadayEvent(todo);

		Iterator<Todo> it = l.iterator();
		while (it.hasNext()) {
			Todo t = it.next();
			// add by zyting 2017/08/09 项目评价后不展示 start
			/*if (t.gettState() == 2 || t.gettState() == 6 || t.gettState() == 7 || t.gettState() == 8) {
				Comment comment = new Comment();
				comment.seteId(t.geteId());
				comment.setUserId(todo.getUserId());
				int leng = commentDao.listCoyEid(comment).size();
				if (leng > 0) {
					it.remove();
					continue;
				}
			}*/
			// add by zyting 2017/08/09 项目评价后不展示 end

			Event event = new Event();
			event.seteId(t.geteId());
			Event eventInfo = eventDao.infoEnvet(event);

			EventItem eventItem = new EventItem();
			eventItem.setDutyId(t.getUserId());
			eventItem.seteId(t.geteId());
			int s = eventItemDao.listEventItem(eventItem).size();
			eventItem.setStateStr("3,7");
			eventItem.setRank(2);
			//健康度
			/*int x = eventItemDao.listhealth(eventItem).size();
			if (eventInfo.getState() == 0 || eventInfo.getState() == 1 || eventInfo.getState() == 3) {
				EventItem item = new EventItem();
				item.seteId(t.geteId());
				item.setStateStr("0,1,6");
				item.setFinishTime(new Timestamp(new Date().getTime()));
				int k = eventItemDao.listhealth(item).size();
					x = x + k;
			}
			double c = 1;
			// 完成率
			if (s != 0 ) {
				c = (double) x / s;
			}
			t.setPercent(c);*/
			// add by zyting 2017/08/02 更新责任人 start

			Long userIdTwo = t.getCuserId();
			if (t.getType() == 1) {
				JoinEvent je = new JoinEvent();
				je.setType((byte) 1);
				je.seteId(t.geteId());
				je.setState((byte) 1);
				JoinEvent jet = joinEventDao.dutyInfo(je);
				if (jet != null) {
					t.setIsTypeTwo(1l);
					t.setUserName(jet.getUserName());
					t.setHeadUrl(jet.getHeadUrl());
					userIdTwo = jet.getUserId();
				}
			}
			// add by zyting 2017/08/02 更新责任人 end

			// add by zyting 2017/08/09 需求获取最新行动跟踪者
			if (t.getType() == 1) {
				if (t.getIsTypeTwo() != null && t.getIsTypeTwo() == 1 && userIdTwo.equals(todo.getUserId())) {
					TrackRecord record = new TrackRecord();
					record.seteId(t.geteId());
					record.setIsRed((byte) 0);
					record.setDonotUser(todo.getUserId());
					List<TrackRecord> recordList = trackRecordSvc.listTrackRecord(record);
					if (recordList.size() > 0) {
						t.setIsRecord(recordList.get(0).getCreateBy());
					}
				}

			}
			// add by zyting 2017/08/09 目标获取最新行动跟踪者
			if (t.getType() == 0) {
				if (userIdTwo.equals(todo.getUserId())) {
					TrackRecord record = new TrackRecord();
					record.seteId(t.geteId());
					record.setIsRed((byte) 0);
					record.setDonotUser(todo.getUserId());
					List<TrackRecord> recordList = trackRecordSvc.listTrackRecord(record);
					if (recordList.size() > 0) {
						t.setIsRecord(recordList.get(0).getCreateBy());
					}
				}

			}

		}

		return l;
	}

	@Override
	public List<Todo> tobeNumber(Todo todo) {
		return todoDao.tobeNumber(todo);
	}

	@Override
	public void updateCloseEventItem(Todo todo) {
		todo =updateTodoDelAddHis(todo);
    	// 终止事项
    	EventItem eventItem = new EventItem();
    	eventItem.setEiId(todo.getEiId());
    	eventItem.setState((byte)5);
    	eventItem.setEiDesc(todo.getEiDesc());
    	eventItemDao.updateEventItem(eventItem);
        Event event=new Event();
        event.seteId(eventItem.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        //add  by xiehuilin 20170911  根据完成是否区别是新增周报记录还是更新周报记录
        if(envetInfo.getLeId()!=null){
            // 完成时间在行动开始、截止时间范围内为新增 否则为更新
            if(eventItem.getfTimestamp().compareTo(eventItem.getStartTime())>=0&&
                    eventItem.getfTimestamp().compareTo(eventItem.getFinishTime())<=0){
                LightWeekly weekly=new LightWeekly();
                weekly.setUserId(eventItem.getDutyId());
                weekly.setLeId(envetInfo.getLeId());
                weekly.seteId(envetInfo.geteId());
                weekly.setCreateBy(eventItem.getCreateBy());
                weekly.setStartTime(StrUtils.getNowTimestamp());
                lightWeeklySvc.saveWeekly(weekly);

            }else{
                LightWeekly weekly=new LightWeekly();
                weekly.setLeId(envetInfo.getLeId());
                weekly.setEiId(eventItem.getEiId());
                LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
                weeklyInfo.setIsFinish(new Byte("1"));
                lightWeeklySvc.updateWeekly(weeklyInfo);
            }


        }

    	
	}

	/**
	 * 根据事件id查询待办事件信息(根据事件id查询待办计划)
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 上午10:57:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	@Override
	public Todo infoTodo(Todo todo) {
		return todoDao.infoTodo(todo);
	}

	/**
	 * 待办事项列表
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 下午3:16:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	@Override
	public List<Todo> listItemTodo(Todo todo) {
		return todoDao.listItemTodo(todo);
	}
	
	@Override
	public Todo updateTodoDelAddHis(Todo todo1){
		//3更新待办事件信息
    	todo1.setIsFinish((byte)1);
 //   	if(todo1.getFinishTime()==null){
    		todo1.setFinishTime(new Timestamp(System.currentTimeMillis()));
 //   	}
    	updateTodo(todo1);
    	Todo td=new Todo();
    	td.setId(todo1.getId());
    	Todo to=todoDao.todoInfo(td);
    	return to;
	}
	@Override
	public Todo commentTo(Todo todo){
		todo =updateTodoDelAddHis(todo);
		JoinEvent joinEvent = new JoinEvent();
		joinEvent.seteId(todo.geteId());
		joinEvent.setUserId(todo.getUserId());
		List<JoinEvent> l = joinEventDao.eventJoinList(joinEvent);
		Byte type = 0; 
		for(JoinEvent joinEvent1:l){
			if(joinEvent1.getType()==0){
				type = 1;
			}
		}
		todo.setRoleType(type);
		return todo;
	}

	@Override
	public Todo getTodoInfo(Todo todo) {
		return todoDao.getTodoInfo(todo);
	}
	/**
	 * 插入待办任务(事项待完成)
	 * @author wuchao
	 * @date 2017年7月17日
	 * @time 上午11:07:08
	 * @version V0.0.1
	 * @param infoItem
	 * @param todo
	 */
	public void keepTodo(EventItem infoItem,Todo todo,Timestamp until){
		todo.settExplained(EventMap.desMap.get("16"));
		todo.settState((byte)16);
		Long userId=todo.getIsAccept()==0?todo.getCreateBy():todo.getUserId();
		//Timestamp pushTime = infoItem.getCtId()==null ? infoItem.getFinishTime() : until;
		todo.setUserId(userId);
		todo.setCreateBy(todo.getCreateBy());
		todo.setEiId(infoItem.getEiId());
		todo.settName(EventMap.itemStateMap.get("16"));
		todo.setPushTime(until);
		todo.setIsValid((byte)1);
		todo.setIsBlock((byte)0);
		todo.setIsFinish((byte)0);
		todo.settSubjType((byte)1);
		todo.setIsSend((byte)0);
		//保存待办任务信息
		todoDao.saveTodo(todo);
	}
	
	//判断是否已经关注服务号  未关注的写系统消息
	public void checkNews(Todo reslutTodo,Byte isAccept){
		//User user=new User();
       // user.setUserId(reslutTodo.getCreateBy());
       // User use=userSvc.getUserInfo(user);
        User useInfo=new User();
        useInfo.setUserId(reslutTodo.getUserId());
        User userInfo=userSvc.getUserInfo(useInfo);
 	    	News news =new News();
 	    	Event ei=new Event();
			ei.seteId(reslutTodo.geteId());
			Event envet= eventDao.getEnvetInfo(ei);
			EventItem etIm =new EventItem();
			etIm.setEiId(reslutTodo.getEiId());
			EventItem itmeInfo=eventItemDao.getItemInfo(etIm);
 			news.setnTime(new Timestamp((new Date()).getTime()) );
 			news.setCreateBy(reslutTodo.getUserId());
 			news.setCreateTime(new Timestamp((new Date()).getTime()) );
 			news.setIsValid((byte)1);
 			news.setnType((byte)1);
 			news.setnName(envet.getName());
 			news.seteId(reslutTodo.geteId());
 			news.setEiId(reslutTodo.getEiId());
 			//拒绝
 			if (isAccept==0) {
 				news.setSendId(reslutTodo.getCreateBy());
 				news.setnTitle(EventMap.itemStateMap.get("2"));
 				String  content=new StringBuilder().append("你好，").append(userInfo.getUserName()).append("拒绝此项任务,只能本人执行").toString();
 				news.setnMold((byte)8);
 				news.setnContent(content);
			}
			//接受
			else if(isAccept==1){
				news.setnTitle("行动名称："+itmeInfo.getEiDesc());
    			news.setSendId(reslutTodo.getUserId());
    			news.setnContent(EventMap.newsMap.get("4"));
    			news.setnMold((byte)6);
			}

 			newsDao.insert(news);
	}

	@Override
	public List<Todo> listTodoZingAll(Todo todo) {
		List<Todo> l =todoDao.listTodoZingAll(todo);
		Iterator<Todo> it = l.iterator();
         while(it.hasNext()){
        	 Todo t = it.next();
             
        	   //add by zyting 20170718  签到提醒
        	    if(t.getType()==2 && t.gettState()==4){
        	   	
        	   	 //结束时间加一天
        	   	 Calendar target = Calendar.getInstance();
        	        target.setTime(t.getEndTime());
        	        target.add(Calendar.DATE,1);
        	        target.set(Calendar.HOUR_OF_DAY, 0);
        	        target.set(Calendar.MINUTE, 0);
        	        target.set(Calendar.SECOND, 0);
        	   	 
        	   	 if(t.getPushTime().before(new Date())){
        	   		//签到提醒正常展示
        	   		 if(t.getFinishTime().after(new Date())){
        	   			 
        	   		 }
        	   		//签到提示无效
        				 if(t.getFinishTime().before(new Date()) && target.getTime().after(new Date())){
        					 t.setOverdue((byte) 1);
        				 }
        	   		 //不展示
        				 if(target.getTime().before(new Date())){
        					 it.remove();
        					 continue;
        				 }
        	   	 // 不展示 
        	   	 }else{
        	   		it.remove();
        	   		 continue;
        	   	 }
        	    }
        	  
        	    //add by zyting 20170719 待计划提醒
        	    if(t.gettSubjType()==0 && t.gettState()==0){
	        	   	if(t.getPushTime().after(new Date())){
	        	   		 it.remove();
	        	   		 continue;
	        	   	}
        	    }
        	    
        	    //add by zyting 20170807 待关闭提醒
        	    if(t.gettSubjType()==0 && t.gettState()==3){
        	    	EventItem eventItem = new EventItem();
        	    	eventItem.seteId(t.geteId());
        	    	eventItem.setStateStr("0,1,2");
        	    	if(t.getPushTime().after(new Date())){
	        	   		 it.remove();
	        	   		 continue;
	        	   	}else if(eventItemDao.listEventItem(eventItem).size()!=0){
		        	   	 it.remove();
	        	   		 continue;
	        	   	}
        	    }
        	 
         }
		
		return l;
	}

	@Override
	public List<Todo> isInvalidTodo(Todo todo) {
		 List<Todo> l = todoDao.isInvalidTodo(todo);
		
		for(int i=0;i<l.size();i++){
			 l.get(i).setPushTimeStr( DateUtils.getDateDetailDay(l.get(i).getPushTime().toString()));
	         if(l.get(i).gettSubjType()==1){
				EventItem eventItem = new EventItem();
				eventItem.seteId(l.get(i).geteId());
				eventItem.setEiId(l.get(i).getEiId());
				eventItem = eventItemDao.listEventItem(eventItem).get(0);
				l.get(i).setEiDesc(eventItem.getEiDesc());
			//	l.get(i).setEiStartTime(StrUtils.getWeekDay(eventItem.getStartTime()));
			}
	         
		}
		return l;
	}

	@Override
	public void updateTodoTrack(Todo todo) {
		
	   Todo t= todoDao.getTodoInfo(todo);
	   todo.setFinishTime(StrUtils.getNowTimestamp());
		 //推送时间
       Date pDate = DateUtils.getTimestampToDate(t.getPushTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
       //行动截至时间,按天算
       Date  eDate=DateUtils.getTimestampToDate(t.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
       //行动截止时间,减一天后大于当前时间生成行动待办否则不生成
       eDate= ToDoRemindUtils.getToDoRemindTime(new Timestamp(eDate.getTime()), -24 * 60);
       if(eDate.compareTo(pDate)>0){
    	   t.setPushTime(ToDoRemindUtils.getToDoRemindTime(StrUtils.getNowTimestamp(),24*60));
    	  saveTodo(t);
       }
		//是否忽略   1 是 0 否
		if(todo.getIsIgnore()==1){
			todo.setIsFinish((byte)1);
			todoDao.updateTodo(todo);
		}else{
			TrackRecord record = new TrackRecord();
			record.seteId(t.geteId());
			record.setEiId(t.getEiId());
			record.setContent(todo.getEiDesc());
			record.setCreateBy(todo.getCreateBy());
			trackRecordSvc.insertTrackRecord(record);
			todo.setIsFinish((byte)1);
			todoDao.updateTodo(todo);
		}
		Event envetInfo = new Event();
		envetInfo.seteId(t.geteId());
		envetInfo = eventDao.getEnvetInfo(envetInfo);
		if(envetInfo.getLeId()!=null){
			  Dynamic dynamic=new Dynamic();
	          dynamic.seteId(t.geteId());
	          dynamic.setLeId(envetInfo.getLeId());
	          dynamic.setUserId(envetInfo.getUserId());
	          StringBuilder builder=new StringBuilder().append("跟踪了一项行动");
	          dynamic.setTitle(builder.toString());
	          dynamic.setCreateBy(envetInfo.getUserId());
	          dynamic.setState(new Byte("3"));
	          dynamicSvc.saveDynamic(dynamic);
		 }
			
		  
		
	}

	@Override
	public Todo getTodoInfoRecord(Todo todo) {
		return todoDao.getTodoInfoRecord(todo);
	}

  /**
  * 周期性行动接受、拒绝
  *   1:校验行动的有效性
  *   2:接受
  *     2.1:更新当前行动责任人为被委派者、更新待办提醒
   *    2.2:根据周期频率为被委派者生成行动,更新好友为最近联系人
   *
  *   3拒绝:
   *   3.1：更新当前行动责任人为项目责任人、更新待办提醒
   *   3.1：根据周期频率为项目责任人生成行动,更新好友为最近联系人
   *   3.2:生成拒绝消息记录

  * @author xiehuilin
  * @param
  * @return
  * @version V0.0.1
  * @date 2017/8/10 13:14
  */
	@Override
	public Map<String,Object> updateCycleAcceptOrRefuse(Todo todo) {
	    Map<String,Object> map=new HashMap<String, Object>();
	    String msg="success";
	    String errorCode="";
		Todo querytd=new Todo();
		querytd.setId(todo.getId());
        Todo reslutTodo = todoDao.getTodoInfoRecord(querytd);
        //生成消息记录
		checkNews(reslutTodo,todo.getIsAccept());
        //获取项目详情
        Event event=new Event();
        event.seteId(reslutTodo.geteId());
        Event envetInfo = eventDao.getEnvetInfo(event);
        //@ 校验行动的有效性
        if(reslutTodo.getIsInvalid()==1||!reslutTodo.getUserId().equals(todo.getUserId())||(envetInfo.getState()==2||envetInfo.getState()==6||envetInfo.getState()==7||envetInfo.getState()==8)){
            msg="error";
            errorCode= APIErrorMap.errorMap.get("16");
            map.put("msg",msg);
            map.put("errorCode",errorCode);
            return  map;
        }
		cycleIetem(todo, reslutTodo, event, envetInfo,0);
		map.put("msg",msg);
		map.put("errorCode",errorCode);
        return  map;
    }



	@Override
	public List<Todo> listEventItemTodo(Todo todo) {
		return todoDao.listEventItemTodo(todo);
	}

	@Override
	public void updateUserTodo(Todo todo) {
		todoDao.updateUserTodo(todo);
	}

	/**
	*  单次行动接受、拒绝
	* @author xiehuilin
	* @param type  0 接受、拒绝 , 1 定时器
	* @param isInsert  0 更新 , 1 新增
	* @return
	* @version V0.0.1
	* @date 2017/8/14 10:14
	*/
	private void singleItem(Todo todo, Todo reslutTodo, EventItem infoItem, Event event, Event envetInfo, Timestamp timeOne,Integer type,Integer isInsert) {
		Todo newTodo=new Todo();
		if(todo.getIsAccept()==1){
			/*JoinEvent joinEvent=new JoinEvent();
			joinEvent.seteId(event.geteId());
			joinEvent.setUserId(todo.getUserId());
			joinEvent.setCreateBy(envetInfo.getCreateBy());
			joinEvent.setType(new Byte("2"));
			joinEvent.setCreateTime(new Timestamp(new Date().getTime()));
			joinEvent.setEiId(infoItem.getEiId());
			joinEvent.setIsSign(new Byte("0"));
			joinEvent.setFrequency(envetInfo.getFrequency());
			if(envetInfo.getType()==1){
				JoinEvent  je=new JoinEvent();
				je.setState(new Byte("1"));
				je.setType(new Byte("1"));
				je.seteId(envetInfo.geteId());
				JoinEvent reslutJe = joinEventDao.JoinEventInfo(je);
				joinEvent.setCreateBy(reslutJe.getUserId());
			}
			joinEvent.setState((byte)1);
			JoinEvent join= joinEventDao.itemJoinInfo(joinEvent);
			reslutTodo.settState(new Byte("7"));
			reslutTodo.settName(EventMap.itemStateMap.get("7"));
			// reslutTodo.settExplained(EventMap.desMap.get("0"));
			reslutTodo.setIsFinish(new Byte("1"));
			reslutTodo.setPushTime(StrUtils.getNowTimestamp());
			reslutTodo.setFinishTime(new Timestamp((new Date()).getTime()));
			reslutTodo.setRemindTime(timeOne);
			todoDao.updateTodo(reslutTodo);
			//判断我是否已经是此事件的事项参与人 ，如果是：根据我设定的提醒类型提示，否则：按照事件默认提醒类型提示
			if (join==null) {
				joinEventDao.insert(joinEvent);
			}
			keepTodo(infoItem,reslutTodo);*/
			//生成待办和用户参与记录、更新
			//add by xiehuilin 20170914 项目完成生成动态记录  行动失效不生成动态
			if(type==0){
				Long userId=todo.getIsAccept()==0||type==1?todo.getCreateBy():todo.getUserId();
				Dynamic dynamic=new Dynamic();
				dynamic.seteId(envetInfo.geteId());
				dynamic.setLeId(envetInfo.getLeId());
				dynamic.setUserId(envetInfo.getCuserId());
				User user=new User();
				user.setUserId(envetInfo.getCuserId());
				User userInfo = wxuserDao.getUserInfo(user);
				StringBuilder builder=new StringBuilder().append("接受了来自").append(userInfo.getUserName()).append("委托");
				//根据行动id、状态、主题获取行动委派时间
				//Todo info = todoDao.getByEidTodoInfo(reslutTodo);
				dynamic.setDelegateTime(reslutTodo.getCreateTime());
				dynamic.setReceTime(StrUtils.getNowTimestamp());
				dynamic.setTitle(builder.toString());
				dynamic.setContent(builder.toString());
				dynamic.setCreateBy(reslutTodo.getUserId());
				dynamic.setState(new Byte("2"));
				dynamic.setEiId(reslutTodo.getEiId());
				dynamic.setUserId(userId);
				dynamicSvc.saveDynamic(dynamic);
			}
			createTodoAndJoinEvent(todo, reslutTodo, event, envetInfo, infoItem,infoItem,timeOne,type,isInsert);

		}else{
			//add by xieuhilin 2017/07/14 为指派人生成待办消息 start
			BeanUtils.copyProperties(reslutTodo,newTodo);
			User user=new User();
			user.setUserId(reslutTodo.getUserId());
			User userInfo = wxuserDao.getUserInfo(user);
			newTodo.setRemindTime(new Timestamp(new Date().getTime()));

			Byte state=infoItem.getCtId()!=null?new Byte("12"):new Byte("2");
			newTodo.settState(state);
			newTodo.settName(EventMap.itemStateMap.get("2"));
			String  explained=new StringBuilder().append(userInfo.getUserName()).append("拒绝接受!").toString();
			newTodo.settExplained(explained);
			newTodo.setUserId(reslutTodo.getCreateBy());
			newTodo.setFinishTime(StrUtils.getNowTimestamp());
			newTodo.setIsFinish(new Byte("1"));
			todoDao.saveTodo(newTodo);
			newTodo.setRemindTime(timeOne);
			newTodo.settState(new Byte("0"));
			newTodo.setPushTime(infoItem.getFinishTime());
			newTodo.setFinishTime(infoItem.getFinishTime());
			newTodo.settName(EventMap.itemStateMap.get("0"));
			newTodo.settExplained(EventMap.desMap.get("0"));
			todoDao.saveTodo(newTodo);
			//add by xieuhilin 2017/07/14 为指派人生成待办消息 end

			//add by xieuhilin 2017/07/14 为被指派人生成待办消息 start
			Byte reState=infoItem.getCtId()!=null?new Byte("13"):new Byte("6");
			reslutTodo.settState(reState);
			reslutTodo.setIsFinish(new Byte("1"));
			reslutTodo.setPushTime(new Timestamp((new Date()).getTime()));
			reslutTodo.setFinishTime(new Timestamp((new Date()).getTime()));
			todoDao.updateTodo(reslutTodo);
			//add by xieuhilin 2017/07/14 为被指派人生成待办消息 end

			//add by xiehuilin 20170908 轻企行动生成周报记录
			if(envetInfo.getLeId()!=null){
				LightWeekly weekly=new LightWeekly();
				weekly.setLeId(envetInfo.getLeId());
				weekly.setEiId(reslutTodo.getEiId());
				weekly.setUserId(reslutTodo.getUserId());
				LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
				weeklyInfo.setStartTime(StrUtils.getNowTimestamp());
				weeklyInfo.setUserId(reslutTodo.getCreateBy());
				lightWeeklySvc.updateWeekly(weeklyInfo);
				//add by xiehuilin 20170914 项目完成生成动态记录
				Dynamic dynamic=new Dynamic();
				dynamic.seteId(envetInfo.geteId());
				dynamic.setLeId(envetInfo.getLeId());
				dynamic.setUserId(envetInfo.getCuserId());
				User duser=new User();
				user.setUserId(envetInfo.getCuserId());
				User duserInfo = wxuserDao.getUserInfo(user);
				StringBuilder builder=new StringBuilder().append("我拒绝了").append(duserInfo.getUserName()).append("的委托");
				dynamic.setTitle(builder.toString());
				//根据行动id、状态、主题获取行动委派时间
				//Todo TodoInfo = todoDao.getByEidTodoInfo(reslutTodo);
				dynamic.setDelegateTime(reslutTodo.getCreateTime());
				dynamic.setReceTime(StrUtils.getNowTimestamp());
				dynamic.setTitle(builder.toString());
				dynamic.setCreateBy(reslutTodo.getUserId());
				dynamic.setState(new Byte("2"));
				dynamic.setEiId(reslutTodo.getEiId());
				dynamic.setUserId(reslutTodo.getUserId());
				dynamic.setContent(builder.toString());
				dynamicSvc.saveDynamic(dynamic);
			}
			//add by xiehuilin 2017109 生成待办信息
			News sNews =new News();
			sNews.setnTitle("行动名称:"+infoItem.getEiDesc());
			sNews.setnTime(new Timestamp((new Date()).getTime()));
			sNews.setCreateBy(envetInfo.getCreateBy());
			sNews.setCreateTime(new Timestamp((new Date()).getTime()) );
			sNews.setIsValid((byte)1);
			sNews.setSendId(envetInfo.getCuserId());
			sNews.setnType((byte)1);
			sNews.seteId(envetInfo.geteId());
			sNews.setnContent(EventMap.newsMap.get("6"));
			sNews.setnName(envetInfo.getName());
			sNews.setnMold((byte)4);
			sNews.seteId(event.geteId());
			newsDao.insert(sNews);
		}
		//更新好友信息
		FriendGroup friendGroup=new FriendGroup();
		friendGroup.setfId(reslutTodo.getUserId());
		friendGroup.setUserId(reslutTodo.getCreateBy());
		friendGroupDao.updateUnused(friendGroup);
		

	}
	/**
	*  创建待办任务和参与记录、更新待办
	* @author xiehuilin
	* @param type  0 接受、拒绝  、1定时器失效
	* @param isInsert  0  1 
	* @return 
	* @version V0.0.1
	* @date 2017/8/14 11:21
	*/
	private void createTodoAndJoinEvent(Todo todo, Todo reslutTodo, Event event, Event envetInfo, EventItem itemInfo, EventItem newItem, Timestamp until,Integer type,Integer isInsert) {

		if(isInsert==0){
			Byte state=itemInfo.getCtId()!=null?new Byte("14"):new Byte("7");
			reslutTodo.settState(state);
			reslutTodo.settName(EventMap.itemStateMap.get("7"));
		} else  if(isInsert==1){
			reslutTodo.settState(new Byte("0"));
			reslutTodo.settName(EventMap.itemStateMap.get("0"));
		}
		reslutTodo.setIsFinish(new Byte("1"));
		reslutTodo.setPushTime(StrUtils.getNowTimestamp());
		reslutTodo.setFinishTime(new Timestamp((new Date()).getTime()));
		reslutTodo.setRemindTime(until);
		//生成用户参与记录
		JoinEvent joinEvent=new JoinEvent();
		joinEvent.seteId(event.geteId());
		Long userId=todo.getIsAccept()==0||type==1?reslutTodo.getCreateBy():todo.getUserId();
		joinEvent.setUserId(userId);
		joinEvent.setCreateBy(envetInfo.getCreateBy());
		joinEvent.setType(new Byte("2"));
		joinEvent.setCreateTime(new Timestamp(new Date().getTime()));
		joinEvent.setIsSign(new Byte("0"));
		joinEvent.setFrequency(envetInfo.getFrequency());
		joinEvent.setEiId(itemInfo.getEiId());
		if(envetInfo.getType()==1){
			JoinEvent  je=new JoinEvent();
			je.setState(new Byte("1"));
			je.setType(new Byte("1"));
			je.seteId(envetInfo.geteId());
			JoinEvent reslutJe = joinEventDao.JoinEventInfo(je);
			joinEvent.setCreateBy(reslutJe.getUserId());
		}
		joinEvent.setState((byte)1);
		JoinEvent join= joinEventDao.itemJoinInfo(joinEvent);
		//add by xiehuilin 20170818 单次接受更新待办记录
		if(type==0&&isInsert==0){
		    todoDao.updateTodo(reslutTodo);
        }
		// 判断我是否已经是此事件的事项参与人 ，如果是：根据我设定的提醒类型提示，否则：按照事件默认提醒类型提示
		//if (join==null) {
			joinEventDao.insert(joinEvent);
		//}
		reslutTodo.setIsFinish(new Byte("1"));

        reslutTodo.setPushTime(StrUtils.getNowTimestamp());
		reslutTodo.setFinishTime(new Timestamp((new Date()).getTime()));
		reslutTodo.setRemindTime(until);
        reslutTodo.setIsAccept(todo.getIsAccept());
		keepTodo(newItem,reslutTodo,until);
		//add by xiehuilin 20170908 轻企行动生成周报记录
		if(envetInfo.getLeId()!=null){
			LightWeekly weekly=new LightWeekly();
			weekly.setLeId(envetInfo.getLeId());
			Long dutyId=type==0?reslutTodo.getUserId():itemInfo.getDutyId();
			weekly.setEiId(newItem.getEiId());
			weekly.setUserId(dutyId);
			
			weekly.setStartTime(StrUtils.getNowTimestamp());
			LightWeekly weeklyInfo = lightWeeklySvc.getWeeklyInfo(weekly);
			if (weeklyInfo==null){
				weekly.seteId(envetInfo.geteId());
				weekly.setCreateBy(reslutTodo.getCreateBy());
				weekly.setIsFinish(new Byte("0"));
				lightWeeklySvc.saveWeekly(weekly);
			}else{
				weeklyInfo.setUserId(userId);
				lightWeeklySvc.updateWeekly(weeklyInfo);
			}

		}
	}



	@Override
	public List<Todo> listItemWait(Todo todo) {
		return todoDao.listItemWait(todo);
	}
	/**
	 * 行动超时更新
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/14 14:11
	 */
	@Override
	public void updateItemWait(Todo todo) {
		//为项目责任人,生成消息记录
		saveItemWaitNews(todo,new Byte("0"));
		//为行动责任人、生成消息记录
		saveItemWaitNews(todo,new Byte("1"));

		//将委派人任务置为无效,为创建人生成待办
		Todo  newTodo=new Todo();
		BeanUtils.copyProperties(todo,newTodo);

		newTodo.setIsFinish(new Byte("1"));
		newTodo.setIsInvalid(new Byte("1"));
		newTodo.setPushTime(new Timestamp((new Date()).getTime()));
		newTodo.setFinishTime(new Timestamp((new Date()).getTime()));
		todoDao.updateTodo(newTodo);

		//单次行动
		if(todo.getIsCycle()==0){
			//更新事项信息 start
			EventItem item=new EventItem();
			item.setIsAccept(new Byte("0"));
			item.setState(new Byte("2"));
			Long dutyId =todo.getCreateBy();
			item.setDutyId(dutyId);
			item.setEiId(todo.getEiId());
			item.setStartTime(StrUtils.getNowTimestamp());
			item.setIsInvalid(new Byte("1"));
			eventItemDao.updateEventItem(item);
			//获取事项详细信息
			EventItem infoItem=eventItemDao.getItemInfo(item);
			//获取事件详细信息
			Event event=new Event();
			event.seteId(todo.geteId());
			Event envetInfo = eventDao.getEnvetInfo(event);
			Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(infoItem.getFinishTime(), envetInfo.getFrequency());
			todo.setIsAccept(new Byte("1"));
			infoItem.setDutyId(todo.getUserId());
			todo.setUserId(todo.getCreateBy());
			
			
			//为创建人生成待办
			createTodoAndJoinEvent(todo, todo, event, envetInfo, infoItem,infoItem,timeOne,1,0);
			//singleItem(todo, todo,infoItem,envetInfo,envetInfo,timeOne);

		}
		//周期行动
		if(todo.getIsCycle()==1){
			Todo queryParam=new Todo();
			queryParam.setId(todo.getId());
			Todo reslutTodo = new Todo();
			todo.setIsAccept(new Byte("1"));
			todo.setUserId(todo.getCreateBy());
			BeanUtils.copyProperties(todo,reslutTodo);

			//获取项目详情
			Event event=new Event();
			event.seteId(reslutTodo.geteId());
			Event envetInfo = eventDao.getEnvetInfo(event);

			//为创建人生成待办
			cycleIetem(todo, reslutTodo, event, envetInfo,1);
		}

	}

	/**
	* 行动超时生成消息记录
	* @author xiehuilin
	* @param	角色  0 项目责任人   1 行动责任人
	* @version V0.0.1
	* @date 2017/8/14 14:27
	*/
	public void saveItemWaitNews(Todo reslutTodo,Byte role){
		User useInfo=new User();
		Long userId=role==0?reslutTodo.getUserId():reslutTodo.getCreateBy();
		Long createBy=role==0?reslutTodo.getCreateBy():reslutTodo.getUserId();
		useInfo.setUserId(userId);
		User userInfo=userSvc.getUserInfo(useInfo);
		News news =new News();
		Event ei=new Event();
		ei.seteId(reslutTodo.geteId());
		Event envet= eventDao.getEnvetInfo(ei);
		EventItem etIm =new EventItem();
		etIm.setEiId(reslutTodo.getEiId());
		EventItem itmeInfo=eventItemDao.getItemInfo(etIm);
		news.setnTime(new Timestamp((new Date()).getTime()) );
		news.setCreateBy(createBy);
		news.setCreateTime(new Timestamp((new Date()).getTime()) );
		news.setIsValid((byte)1);
		news.setnType((byte)2);
		news.setnName(envet.getName());
		news.seteId(reslutTodo.geteId());
		news.setEiId(reslutTodo.getEiId());
		String str=reslutTodo.getIsCycle()==1?"(周期)":"";
		if (role==0) {
			news.setSendId(reslutTodo.getCreateBy());
			news.setnTitle("行动名称："+itmeInfo.getEiDesc());
			String  content=new StringBuilder()
					.append("很遗憾，").append("对")
					.append(userInfo.getUserName())
					.append(str)
					.append("的行动委托超时,只能您自己亲自执行").toString();
			news.setnMold((byte)12);
			news.setnContent(content);
		}
		else if(role==1){
			news.setnTitle("行动名称："+itmeInfo.getEiDesc());
			news.setSendId(reslutTodo.getUserId());
			String  content=new StringBuilder()
					.append("由于超过接受时间")
					.append(userInfo.getUserName())
					.append(str)
					.append("行动委托给你的行动已失效").toString();
			news.setnContent(content);
			news.setnMold((byte)12);

		}
		newsDao.insert(news);

	}

	/**
	*  周期性行动生成事项、待办、参与记录
	* @author xiehuilin
	* @param  type  0 接受、拒绝  1 定时器失效
	* @return
	* @version V0.0.1
	* @date 2017/8/14 15:16
	*/
	private void cycleIetem(Todo todo, Todo reslutTodo, Event event, Event envetInfo,Integer type) {
		//根据行动id获取行动详情
		EventItem item=new EventItem();
		Byte state=todo.getIsAccept() == 1&&type==0?new Byte("0"):new Byte("2");
		item.setState(state);
		item.setEiId(todo.getEiId());
		EventItem itemInfo = eventItemDao.getItemInfo(item);
		Timestamp timeOne= ToDoRemindUtils.getToDoRemindTime(itemInfo.getFinishTime(), envetInfo.getFrequency());
		Long dutyId = todo.getIsAccept() == 1 &&type==0? reslutTodo.getUserId() : reslutTodo.getCreateBy();
		if(type==1){
			dutyId=reslutTodo.getCreateBy();
			item.setIsAccept(new Byte("0"));
		}
		Byte isCycleEdit=todo.getIsAccept()==1?new Byte("0"):new Byte("1");
		itemInfo.setDutyId(dutyId);
		item.setDutyId(dutyId);
		item.setEiId(todo.getEiId());
		item.setIsInvalid(new Byte(String.valueOf(type)));
		item.setIsAccept(todo.getIsAccept());
		item.setIsCycleEdit(isCycleEdit);
		eventItemDao.updateEventItem(item);
		//将当前委托看成是单次委托
		this.singleItem(todo,reslutTodo,itemInfo, event, envetInfo,  timeOne,type,0);
		//生成除当前委托符合周期范围内的行动 start
		//根据周期模板id获取周期模板频率信息
		CycleTemplet templet=new CycleTemplet();
		templet.setId(itemInfo.getCtId());
		CycleTemplet ctempletInfo = cycleTempletDao.getCtempletInfo(templet);
		//add by xiehuilin 20170818 周期行动拒绝更新行动可编辑状态
        if(todo.getIsAccept()==0){
            EventItem newItem=new EventItem();
            newItem.setCtId(ctempletInfo.getId());
            newItem.setStartTime(itemInfo.getStartTime());
            newItem.setIsCycleEdit(new Byte("1"));
            eventItemDao.updateTempletState(newItem);
        }
		//根据行动周期生成行动计划
		switch (ctempletInfo.getCycleFreq()){
			case 0:
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(itemInfo.getStartTime().getTime());
				//cal.add(Calendar.DAY_OF_MONTH,1);
				//距离本周有多少天
				long days = DateUtils.getWeekDays(cal.getTime());
				EventItem newItem=null;
				Calendar calItem = Calendar.getInstance();
				for(int x=1;x<=days;x++){
					//生成行动待办
                    calItem.setTime(itemInfo.getStartTime());
					calItem.add(Calendar.DAY_OF_MONTH,x);
                    String s1 = StrUtils.dateToStrLong(calItem.getTime());
                    String s2 = StrUtils.timestampToString(envetInfo.getStartTime());
                    String s3 = StrUtils.timestampToString(envetInfo.getEndTime());
                    //判断项目的开始结束时间
					if(!StrUtils.isSameday(calItem.getTime(),itemInfo.getStartTime())&&StrUtils.compareDate(s1,s2)&&StrUtils.compareDate(s3,s1)) {
                       // if (!StrUtils.isSameday(calItem.getTime(), itemInfo.getStartTime())) {
                         if(itemInfo.getState()!=2&&itemInfo.getState()!=6&&itemInfo.getState()!=7&&itemInfo.getState()!=8&&StrUtils.compareDate(StrUtils.dateToStrLong(calItem.getTime()),StrUtils.timestampToString(itemInfo.getStartTime()))){
                            Date date = DateUtils.StringToDate(StrUtils.getDateHour(calItem.getTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN), Constant.DEFAULT_DATE_FORMAT_PATTERN);
                            Long satrtLength = ctempletInfo.getSatrtLength();
                            Long dateLong = Long.valueOf(date.getTime());
                            Long startRelust=dateLong+satrtLength;
                            Timestamp startTime=new Timestamp(startRelust);
                            Long untilLength = ctempletInfo.getUntilLength();
                            Long untilRelust=dateLong+untilLength;
                            Timestamp until= new Timestamp(untilRelust);
                            newItem=new EventItem();
                            BeanUtils.copyProperties(itemInfo,newItem);
                            newItem.setDutyId(dutyId);
                            calItem.setTime(new Date());
                            newItem.setStartTime(startTime);
                            newItem.setState(new Byte("0"));
                            newItem.setFinishTime(until);
                            newItem.setIsAccept(new Byte(todo.getIsAccept()));
                            newItem.setIsCycleEdit(isCycleEdit);
                            eventItemDao.saveEventItem(newItem);
                            //生成待办和用户参与记录
                            createTodoAndJoinEvent(todo, reslutTodo, event, envetInfo, itemInfo, newItem,until,type,1);
                        }
                    }

				}
				break;
			case 1:
				//获取重复提醒周期
				String cycle = ctempletInfo.getCycle();
				String[] weeks = cycle.split(",");
				calItem = Calendar.getInstance();
				for(int y=0;y<weeks.length;y++){
                    String week = weeks[y];
						//根基key获取value
						String s = EventMap.itemDate.get(week);
						Date dayWeeksYTD = ToDoRemindUtils.getDayWeeksYTD(Integer.valueOf(s), new Date());
                    if(!StrUtils.isSameday(dayWeeksYTD,itemInfo.getStartTime())){
                        String s1 = StrUtils.dateToStrLong(dayWeeksYTD);
						String s2 = StrUtils.timestampToString(envetInfo.getStartTime());
						String s3 = StrUtils.timestampToString(envetInfo.getEndTime());
						if (StrUtils.compareDate(s1, s2) && StrUtils.compareDate(s3, s1)) {

							//生成大于指派截止之后的周期行动

							if (itemInfo.getState()!=2&&itemInfo.getState()!=6&&itemInfo.getState()!=7&&itemInfo.getState()!=8&&StrUtils.compareDate(StrUtils.dateToStrLong(dayWeeksYTD), StrUtils.timestampToString(itemInfo.getStartTime()))) {
								//
								Date date = DateUtils.StringToDate(StrUtils.getDateHour(dayWeeksYTD, Constant.DEFAULT_DATE_FORMAT_PATTERN), Constant.DEFAULT_DATE_FORMAT_PATTERN);
								Long satrtLength = ctempletInfo.getSatrtLength();
								Long dateLong = Long.valueOf(date.getTime());
								Long startRelust = dateLong + satrtLength;
								Timestamp startTime = new Timestamp(startRelust);
								Long untilLength = ctempletInfo.getUntilLength();
								Long untilRelust = dateLong + untilLength;
								Timestamp until = new Timestamp(untilRelust);
								newItem = new EventItem();
								BeanUtils.copyProperties(itemInfo, newItem);
								newItem.setDutyId(dutyId);
								calItem.setTime(new Date());
								newItem.setStartTime(startTime);
								newItem.setFinishTime(until);
								newItem.setState(new Byte("0"));
								newItem.setIsAccept(new Byte(todo.getIsAccept()));
								newItem.setIsCycleEdit(isCycleEdit);
								eventItemDao.saveEventItem(newItem);
								//生成待办和用户参与记录
								createTodoAndJoinEvent(todo, reslutTodo, event, envetInfo, itemInfo, newItem, until, type,1);
							}
						}
					}

				}

				break;
		}
		//生成除当前委托符合周期范围内的行动 end
	}

    @Override
    public Todo getByEidTodoInfo(Todo todo) {
        return todoDao.getByEidTodoInfo(todo);
    }

	@Override
	public void updateSixteen(Todo todo) {
		
		Todo t = new Todo();
		t.settState((byte)0);
		todoDao.updateTodo(t);
		EventItem eventItem = new EventItem();
		eventItem.setEiId(todo.getEiId());
		eventItem.setState((byte)8);
		eventItemDao.updateEventItem(eventItem);
		 
		//保存行动参与记录
		TrackRecord record = new TrackRecord();
		record.setContent("开始处理");
		record.setCreateBy(todo.getUserId());
		record.seteId(todo.geteId());
		record.setEiId(todo.getEiId());
		trackRecordSvc.insertTrackRecord(record);
		
	}

	/**
	 * 通过周期模板id修改待办提醒时间
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param todo
	 */
	@Override
	public void updateCycTodo(Todo todo) {
		todoDao.updateCycTodo(todo);
	}


	@Override
	public void updateBatch(Todo todo) {
		todoDao.updateBatch(todo);
	}

	/**
	 * 已完成-->标记未完成
	 *@author zyting
	 *@data 2017年10月27日
	 *@version V0.0.5
	 * @param todo
	 */
	@Override
	public void updateNotCompleted(Todo todo) {
		
		//更新待办记录
		todo.setIsFinish((byte)0);
		todoDao.updateTodo(todo);
		//更新行动记录
		EventItem eventItem = new EventItem();
		eventItem.setState((byte)0);
		eventItem.setEiId(todo.getEiId());
		eventItemSvc.updateEventItem(eventItem);
		//更新周报记录
		LightWeekly weekly = new LightWeekly();
		weekly.setEiId(todo.getEiId());
		weekly.setIsFinish((byte)1);
		weekly =lightWeeklySvc.getWeeklyInfo(weekly);
		if(DateUtils.isThisWeek(weekly.getStartTime().getTime())){
			weekly.setIsFinish((byte)0);
			lightWeeklySvc.updateWeekly(weekly);
		}else{
			LightWeekly w= new LightWeekly();
			w.setIsFinish((byte)0);
			w.setUserId(weekly.getUserId());
			w.seteId(weekly.geteId());
			w.setEiId(weekly.getEiId());
			w.setLeId(weekly.getLeId());
			w.setCreateBy(weekly.getCreateBy());
			w.setStartTime(weekly.getStartTime());
			w.setEndTime(weekly.getEndTime());
			lightWeeklySvc.saveWeekly(weekly);
		}
		
		//更新清单记录
		WeeklyList weeklyList = new WeeklyList();
		weeklyList.setEiId(todo.getEiId());
		weeklyList.setType((byte)4);
		weeklyList = weeklyListDao.getWeeklyInfo(weeklyList);

		if(DateUtils.isThisWeek(weeklyList.getCreateTime().getTime())){
			weeklyList.setType((byte)1);
			weeklyListDao.updateWeeklyList(weeklyList);
		}else{
			weeklyList.setType((byte)1);
			weeklyListDao.insert(weeklyList);
		}
		
		Todo t = todoDao.todoInfo(todo);
		if(t.getCtId()!=null){
			CycleTemplet cycleTemplet = new CycleTemplet();
			//cycleTemplet.setIsFinish((byte)0);
			cycleTempletDao.updateCycleTemplet(cycleTemplet);
		}
	}
}


