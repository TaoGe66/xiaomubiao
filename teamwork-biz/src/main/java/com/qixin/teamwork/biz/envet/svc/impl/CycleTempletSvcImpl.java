package com.qixin.teamwork.biz.envet.svc.impl;

import java.sql.Timestamp; 
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.base.EventMap;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.LightWeeklyDao;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.envet.dao.CycleTempletDao;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.model.CycleTemplet;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.CycleTempletSvc;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.todo.dao.TodoDao;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;

/**
 * 事项周期模板实现层
 * @author wuchao
 * @date 2017年8月9日
 * @time 下午4:08:57
 * @version V0.0.1
 */
@Service("cycleTempletSvc")
public class CycleTempletSvcImpl implements CycleTempletSvc{

	@Resource
	private CycleTempletDao cycleTempletDao;
	@Resource
	private EventItemDao eventItemDao;
	@Resource
	private EventDao eventDao;
	@Resource
	private NewsDao newsDao;
	@Resource
	private JoinEventDao joinEventDao;
	@Resource
	private TodoDao todoDao;
	@Resource
	private WxuserDao userDao;
	@Resource
	private LightWeeklyDao weeklyDao;
	
	/**
	 * 保存事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:14:22
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	@Override
	public void saveCycleTemplet(CycleTemplet cycleTemplet) {
		cycleTempletDao.saveCycleTemplet(cycleTemplet);
	}

	/**
	 * 修改事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:30:43
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	@Override
	public void updateCycleTemplet(CycleTemplet cycleTemplet) {
		cycleTempletDao.updateCycleTemplet(cycleTemplet);
	}

	@Override
	public CycleTemplet getCtempletInfo(CycleTemplet cycleTemplet) {
		return cycleTempletDao.getCtempletInfo(cycleTemplet);
	}

	/**
	   * 定时器处理周期行动
	   * @author wuchao
	   * @date 2017年8月12日
	   * @time 上午11:58:06
	   * @version V0.0.1
	   * @return
	   */
	@Override
	public void taskCtemplet() {
		Timestamp present_time=new Timestamp((new Date()).getTime());//当前时间
		CycleTemplet cycleTemplet=new CycleTemplet();
		cycleTemplet.setNextStartTime(new Timestamp(ToDoRemindUtils.getTimeCheck(1)));
		cycleTemplet.setCycleState((byte)0);
		List<CycleTemplet> templetList=cycleTempletDao.listCtemplet(cycleTemplet);
		if (templetList.size()>0) {
			System.out.println("---------"+templetList.size());
			for (int i = 0; i < templetList.size(); i++) {
				CycleTemplet templet=templetList.get(i);
				EventItem eventItem=new EventItem();
				eventItem.setCtId(templet.getId());
				EventItem item= eventItemDao.getMAXItem(eventItem);
				Event event= new Event();
				event.seteId(item.geteId());
				event.setStateStr("0,1,3");
				Event eventInfo=eventDao.infoEnvet(event);
				CycleTemplet templetInfo=cycleTempletDao.getCtempletInfo(templet);
				EventItem itemInfo= eventItemDao.getItemInfo(item);
				if (eventInfo !=null &&(eventInfo.getEndTime().getTime()>present_time.getTime())) {
					if (item.getCycleFreq()==0) {
						//每天
						everyDay(eventInfo,templetInfo,itemInfo);
					}else{
						//选择周期
						choose(eventInfo,templetInfo,itemInfo);
					}
				}
			}
			
		}
	}

	 /**
	   * 查询所有有效的模板id
	   * @author wuchao
	   * @date 2017年8月12日
	   * @time 下午12:11:51
	   * @version V0.0.1
	   * @param cycleTemplet
	   * @return
	   */
	@Override
	public List<CycleTemplet> listCtemplet(CycleTemplet cycleTemplet) {
		return cycleTempletDao.listCtemplet(cycleTemplet);
	}
	
	/**
	 * 周期是每天
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 下午1:43:34
	 * @version V0.0.1
	 * @param event
	 * @param templet
	 */
	public void everyDay(Event event,CycleTemplet templet,EventItem item){
		//当前日期的零分零秒
		for (int i = 1; i < 8; i++) {
			// 对应日期的零点零分
			long dayTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(i).getTime())).getTime();
			// 进度的完成时间
			long finish = dayTime + templet.getUntilLength();
			// 进度的开始时间
			long startTime = dayTime + templet.getSatrtLength();
			item.setFinishTime(new Timestamp(finish));
			item.setStartTime(new Timestamp(startTime));
			if ( startTime < event.getEndTime().getTime()) {
				savePeriodDay(item,templet,event);
			}
		}
		long weekStartTime=ToDoRemindUtils.getNowWeekMonday(new Date()).getTime();
		
		templet.setNextStartTime(ToDoRemindUtils.getWeeHoursTime(new Timestamp(weekStartTime)));
		//判断事件的结束时间是否大于行动周期的下个周一，如果不大于，则不修改行动周期模板
		//if (event.getEndTime().getTime()>weekStartTime) {
			cycleTempletDao.updateCycleTemplet(templet);
		//}
	}
	
	//周期选择保存
	public void choose(Event event,CycleTemplet templet,EventItem item){
		//当前日期的零分零秒
		String cy = templet.getCycle();
		String[] cyList = cy.split(",");
		for (int i = 0; i < cyList.length; i++) {
			String cyDay = EventMap.itemDate.get(cyList[i]);
			int a = Integer.parseInt(cyDay);
			// 对应日期的零点零分
			long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(a).getTime()))
					.getTime();
				// 进度的完成时间
				long finish = aTime + templet.getUntilLength();
				// 进度的开始时间
				long startTime = aTime + templet.getSatrtLength();
				item.setFinishTime(new Timestamp(finish));
				item.setStartTime(new Timestamp(startTime));
				if ( startTime < event.getEndTime().getTime()) {
					savePeriodDay(item,templet,event);
				}
		}
		long weekStartTime=ToDoRemindUtils.getNowWeekMonday(new Date()).getTime()+templet.getSatrtLength();
		templet.setNextStartTime(ToDoRemindUtils.getWeeHoursTime(new Timestamp(weekStartTime)));
		//判断事件的结束时间是否大于行动周期的下个周一，如果不大于，则不修改行动周期模板
		//if (event.getEndTime().getTime()>weekStartTime) {
			cycleTempletDao.updateCycleTemplet(templet);
		//}
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
	public void savePeriodDay(EventItem eventItem,CycleTemplet cycleTemplet,Event envet){
		Timestamp present_time=new Timestamp((new Date()).getTime());//当前时间
		Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(eventItem.getFinishTime(),envet.getFrequency());
		Todo todo=new Todo();
		todo.setUserId(eventItem.getDutyId());
		todo.setCreateBy(eventItem.getCreateBy());
		eventItem.setIsAccept((byte) 1);
		eventItem.setState((byte) 0);
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
		todo.setIsSend((byte) 1);
		User user = new User();
		user.setUserId(eventItem.getDutyId());
		News news = new News();
		news.setnTitle("行动名称："+eventItem.getEiDesc());
		news.setnTime(timeOne);
		todo.setIsSend((byte) 0);
		news.setCreateBy(eventItem.getCreateBy());
		news.setCreateTime(present_time);
		news.setIsValid((byte) 1);
		news.setSendId(eventItem.getDutyId());
		news.setnType((byte) 1);
		news.setnContent(EventMap.newsMap.get("4"));
		news.setnName(envet.getName());
		news.setnMold((byte)6);
		news.seteId(eventItem.geteId());
		news.setEiId(eventItem.getEiId());
		if (envet.getLeId() !=null) {
			news.setLeId(envet.getLeId());
		}
		newsDao.insert(news);

		todo.setRemindTime(timeOne);
		todo.setFinishTime(eventItem.getFinishTime());
		todo.setPushTime(eventItem.getFinishTime());
		todo.settExplained(EventMap.desMap.get("0"));
		todo.settState((byte)16);
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
		join.setFrequency(envet.getFrequency());
		join.setIsSign((byte) 0);
		join.setEiId(eventItem.getEiId());
		join.setCreateBy(eventItem.getCreateBy());
		JoinEvent jo = joinEventDao.itemJoinInfo(join);
		if (jo == null) {
			joinEventDao.insert(join);
		}
		if (envet.getLeId() !=null) {
			saveWeekly(eventItem, envet);
		}
	}
	
	/**
	 * 添加周报内容
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 上午10:57:11
	 * @version V0.0.3
	 * @param eventItem
	 * @param event
	 */
	public void saveWeekly(EventItem eventItem,Event event){
		  LightWeekly lightWeekly=new LightWeekly();
		  lightWeekly.setUserId(eventItem.getDutyId());
		  lightWeekly.setLeId(event.getLeId());
		  lightWeekly.seteId(event.geteId());
		  lightWeekly.seteId(eventItem.getEiId());
		  lightWeekly.setIsFinish((byte)0);
		  lightWeekly.setCreateBy(eventItem.getCreateBy());
		  lightWeekly.setIsValid((byte)1);
		  lightWeekly.setStartTime(eventItem.getStartTime());
		weeklyDao.saveWeekly(lightWeekly);
	}
	

}
