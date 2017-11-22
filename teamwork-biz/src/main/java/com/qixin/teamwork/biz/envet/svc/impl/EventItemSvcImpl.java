package com.qixin.teamwork.biz.envet.svc.impl;
import javax.annotation.Resource;

import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyListDao;
import com.qixin.teamwork.biz.awardPenalty.dao.AwardPenaltyRuleDao;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;

import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.svc.WeeklyListSvc;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.base.EventMap;
import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.LightWeeklyDao;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.enterprise.dao.DynamicDao;
import com.qixin.teamwork.biz.enterprise.model.Dynamic;
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
import com.qixin.teamwork.biz.record.dao.TrackRecordDao;
import com.qixin.teamwork.biz.todo.dao.TodoDao;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.wx.Templet;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
 
/**
 *  事项实现
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:22:22
 * @version V0.0.1
 */
@Service("eventItemSvc")
public class EventItemSvcImpl implements EventItemSvc{

	@Resource
	private EventItemDao eventItemDao;
	@Resource
	private TodoDao todoDao;
	@Resource
	private EventDao eventDao;
	@Resource
	private NewsDao newsDao;
	@Resource
	private WxuserDao userDao;
	@Resource
	private JoinEventDao joinEventDao;
	@Autowired
	private AwardPenaltyRuleDao awardPenaltyRuleDao;
	@Autowired
	private AwardPenaltyListDao awardPenaltyListDao;
	@Resource
	private CycleTempletDao cycleTempletDao;
	@Resource
	private FriendGroupDao friendGroupDao;
	@Resource
	private TrackRecordDao trackRecordDao;
	@Resource
	private LightWeeklyDao weeklyDao;
	@Resource
	private DynamicDao dynamicDao;
	@Autowired
	private WeeklyListSvc weeklyListSvc;
	@Autowired
	private WeeklyListDao weeklyListDao;
	
	/**
	 * 保存事项(单次)
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:23:15
	 * @version V0.0.1
	 * @param eventItem
	 */
	@Override
	public void saveEventItem(EventItem eventItem) {
		saveDuty(eventItem);
	}


	/**
	 * 修改事项
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:23:15
	 * @version V0.0.1
	 * @param  eventItem
	 */
	@Override
	public void updateEventItem(EventItem eventItem) {
		eventItemDao.updateEventItem(eventItem);
	}


	/**
	 * 获取事件下的事项列表
	 * @author xiehuilin
	 * @param  eventItem 事项实体
	 * @return  事项列表
	 * @version V0.0.1
	 * @date 2017/6/12 14:35
	 */
	@Override
	public List<EventItem> listEventItem(EventItem eventItem){
		return  eventItemDao.listEventItem(eventItem);
	}

	/**
	 * 保存待办事项信息保存事项(单次)
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 上午11:48:29
	 * @version V0.0.1
	 * @param eventItem
	 */
	public void saveDuty(EventItem eventItem) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		Event ei = new Event();
		ei.seteId(eventItem.geteId());
		Event envet = eventDao.getEnvetInfo(ei);
		Todo todo = new Todo();
		Timestamp timeOne = ToDoRemindUtils.getToDoRemindTime(eventItem.getFinishTime(), eventItem.getFrequency());
		todo.setRemindTime(timeOne);
		todo.setFinishTime(eventItem.getFinishTime());
		// eventItem.setStartTime(timeOne);
		Calendar c = Calendar.getInstance();
		// 创建新行动，判断待计划待办和待计划消息是否存在，存在则置为无效
		shutTodoNews(eventItem, present_time);

		todo.setUserId(eventItem.getDutyId());
		todo.setCreateBy(eventItem.getCreateBy());
		eventItem.setState((byte) 0);

		if (eventItem.getFrequency()==1) {
			todo.setIsSend((byte) 1);
		}else{
			todo.setIsSend((byte) 0);
		}
		// 保存事项

		eventItem.setIsAccept((byte) 1);
		eventItem.setIsReward((byte) 0);
		eventItemDao.saveEventItem(eventItem);
		long now_time = c.getTime().getTime();
		long start_time = timeOne.getTime();
		if (now_time > start_time || now_time == start_time) {
			todo.setIsSend((byte) 1);
			User user = new User();
			user.setUserId(eventItem.getDutyId());
			User use = userDao.getUserInfo(user);
			News news = new News();
			news.setnTitle("行动名称：" + eventItem.getEiDesc());
			news.setnTime(timeOne);
			news.setCreateBy(eventItem.getCreateBy());
			news.setCreateTime(present_time);
			news.setIsValid((byte) 1);
			news.setSendId(eventItem.getCreateBy());
			news.setnType((byte) 1);
			news.setnContent(EventMap.newsMap.get("4"));
			news.setnName(envet.getName());
			news.setnMold((byte) 7);
			news.seteId(eventItem.geteId());
			news.setEiId(eventItem.getEiId());
			newsDao.insert(news);
			if (eventItem.getFrequency()!=1) {
				if (!StrUtils.isEmpty(use.getOpenId())) {
					Templet.mould(eventItem.getEiDesc(), envet.getName(), EventMap.itemStateMap.get("0"),
							"时间：" + StrUtils.getDateHour(eventItem.getFinishTime()), use.getOpenId());
				}
			}
		} 

		todo.setPushTime(eventItem.getFinishTime());
		todo.settExplained(EventMap.desMap.get("0"));
		todo.settState((byte) 16);
		todo.settName(EventMap.itemStateMap.get("0"));
		long day = org.framework.utils.StrUtils.numDateBetween(eventItem.getFinishTime().toString(),
				eventItem.getStartTime().toString());
		if (day > 1 && eventItem.getStartTime().getTime() < ToDoRemindUtils.getTimeCheck(2)) {
			// 添加明天的行踪待办
			Timestamp pTime = new Timestamp(ToDoRemindUtils.getTimeCheck(4));
			saveTrack(envet, eventItem, pTime);

		} else if (day >= 1 && eventItem.getStartTime().getTime() > ToDoRemindUtils.getTimeCheck(2)) {
			// 添加开始时间的行踪待办
			Timestamp pTime = ToDoRemindUtils.getWeeHoursTime(eventItem.getStartTime());// 推送时间
			saveTrack(envet, eventItem, pTime);
		}
		if (!todo.getUserId().equals(eventItem.getCreateBy())) {
			// 消息推送
			saveNewsPush(eventItem, envet);
			// 好友关系更新
			FriendGroup friendGroup = new FriendGroup();
			friendGroup.setUserId(eventItem.getCreateBy());
			friendGroup.setfId(eventItem.getDutyId());
			friendGroupDao.updateUnused(friendGroup);
		}
		// 添加周报内容
		if (envet.getLeId() != null) {
			saveWeekly(eventItem, envet);
		}

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
		join.setFrequency(eventItem.getFrequency());
		join.setIsSign((byte) 0);
		join.setEiId(eventItem.getEiId());
		join.setCreateBy(eventItem.getCreateBy());
		JoinEvent jo = joinEventDao.itemJoinInfo(join);
		if (jo == null) {
			joinEventDao.insert(join);
		}
	}
	
	/**
	 * 修改事项状态
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午6:02:13
	 * @version V0.0.1
	 * @param eventItem
	 */
	@Override
	public void updateItemState(EventItem eventItem) {
        EventItem info = eventItemDao.getByEiIdInfo(eventItem);
        if (eventItem.getState()==5) {
        	eventItem.setDutyId(eventItem.getCreateBy());
        	eventItem.setIsAccept((byte)1);
		}
       if(info.getState()!=5) {
		   eventItemDao.updateEventItem(eventItem);
		   //保存跟踪记录
		   TrackRecord trackRecord=new TrackRecord();
		   trackRecord.setContent(eventItem.getRemark());
		   trackRecord.setCreateBy(eventItem.getCreateBy());
		   trackRecord.seteId(eventItem.geteId());
		   trackRecord.setEiId(eventItem.getEiId());
		   trackRecord.setTrType(new Byte("1"));
		   trackRecordDao.insert(trackRecord);

	   }
        Event event=new Event();
        event.seteId(eventItem.geteId());
        Event eventInfo=eventDao.getEnvetInfo(event);
		if(eventItem.getState()==4&&(info.getIsReward()==0)){
			//判断是否惩罚
			AwardPenaltyRule penaltyRule=new AwardPenaltyRule();
			penaltyRule.seteId(eventItem.geteId());
			penaltyRule.setRpCategory((byte)0);
			AwardPenaltyRule apr=awardPenaltyRuleDao.infoAward(penaltyRule);
			//当惩罚内容存在，进行对滞后完成名单添加
			if (apr !=null) {
				//add  by xiehuilin 20170911 生成奖惩行动
                StringBuilder builder=new StringBuilder().append("由于进度滞后完成,惩罚").append(apr.getRpMoney()).append("元");
                //Long eiId = createItemAndTodo(info, eventItem.getCreateBy(), eventInfo, builder.toString(), new Byte("0"));
                //add  by xiehuilin 20170911 生成消息推送
               pushNews(eventItem,info,EventMap.itemStateMap.get("0"),builder.toString(),new Byte("1"),new Byte("6"));
                AwardPenaltyList awardPenaltyList=new AwardPenaltyList();
                awardPenaltyList.setAprId(apr.getId());
                awardPenaltyList.setCreateBy(eventItem.getDutyId());
                awardPenaltyList.setRplReason("滞后完成");
                awardPenaltyList.setRplMoney(apr.getRpMoney());
                awardPenaltyList.setTaskTime(new Timestamp(new Date().getTime()));
                awardPenaltyList.seteId(eventItem.geteId());
                awardPenaltyList.setEiId(eventItem.getEiId());
                awardPenaltyList.setUserId(eventItem.getDutyId());
                awardPenaltyListDao.save(awardPenaltyList);
			}
			
		}else if (eventItem.getState()==5) {
            //pushNews(eventItem, info,"行动名称："+info.getEiDesc(),EventMap.itemStateMap.get("5"),new Byte("2"),new Byte("2"));
			//终止行动更新行动记录、更新清单、更新待办、更新参与记录、更新消息表
            updateEventItemRecord(eventItem);
        }
		if (eventInfo.getState()!=2 && eventInfo.getState()!=6 && eventInfo.getState()!=7 && eventInfo.getState()!=8 ) {
			Todo todo=new Todo();
			todo.setEiId(eventItem.getEiId());
			todo.setStateStr("16,1,11,0,17");
			todo.setIsFinish((byte)0);
			todo.setIsInvalid((byte)0);
			Todo to=todoDao.todoInfo(todo);
			if (eventItem.getState()==5) {
				todo.setIsInvalid((byte)1);
			}else{
				todo.setIsInvalid((byte)0);
			}
			todo.setUserId(to.getUserId());
			todo.setId(to.getId());
			todo.setIsFinish((byte)1);
			todo.setFinishTime(new Timestamp(new Date().getTime()) );
			//add by xiehuilin 20170814 根据用户id和事件id获取行动待跟踪记录待办 start
			Todo rTodo=new Todo();
			rTodo.settState(new Byte("10"));
			rTodo.settSubjType(new Byte("1"));
			rTodo.seteId(todo.geteId());
			rTodo.setEiId(todo.getEiId());
			rTodo.setUserId(todo.getUserId());
			rTodo.setIsFinish(new Byte("0"));
			Todo newTodoInfo = todoDao.getTodoInfoRecord(rTodo);
			if(null!=newTodoInfo){
				//删除行动待跟踪任务
				newTodoInfo.setIsValid(new Byte("0"));
				todoDao.updateTodo(newTodoInfo);
			}
			//add by xiehuilin 20170814 根据用户id和事件id获取行动待跟踪记录待办 end
			//删除待办任务
			todo.settState(new Byte("0"));
			todoDao.updateTodo(todo);
		}
		//add  by xiehuilin 20171027 根据完成时间区别是新增周报记录还是更新周报记录  start
        if(eventInfo.getLeId()!=null){
			//add by xiehuilin 20171011 奖惩行动不属于清单
			if(info.getIsReward()==0){
                LightWeekly weekly=new LightWeekly();
                weekly.setLeId(eventInfo.getLeId());
                weekly.setEiId(eventItem.getEiId());
                weekly.setUserId(info.getDutyId());
                LightWeekly weeklyInfo = weeklyDao.getWeeklyInfo(weekly);
				if(DateUtils.isThisWeek(weeklyInfo.getStrTime())){
					weeklyInfo.setIsFinish(new Byte("1"));
				}else {
					LightWeekly kw=new LightWeekly();
					BeanUtils.copyProperties(weeklyInfo,kw);
					kw.setCreateBy(eventItem.getCreateBy());
					kw.setStartTime(StrUtils.getNowTimestamp());
					kw.setEndTime(StrUtils.getNowTimestamp());
					kw.setIsFinish(new Byte("1"));
					weeklyDao.saveWeekly(kw);
				}
				weeklyInfo.setEndTime(StrUtils.getNowTimestamp());
                weeklyDao.updateWeekly(weeklyInfo);
			}
		//add  by xiehuilin 20171027 根据完成时间区别是新增周报记录还是更新周报记录  end

            //add by xiehuilin 20171027添加行动动态  start
            String title="";
            Long executeId=info.getDutyId();
            if (eventItem.getState()==5) {
            	executeId=info.getCreateBy();
            	title="异常终止了一项行动";
			}else if(eventItem.getState()==3){
				title="正常完成了一项行动";
			}else if(eventItem.getState()==4){
				title="滞后完成了一项行动";
			}else if(eventItem.getState()==7){
				title="提前完成了一项行动";
			}
				Dynamic dynamic=new Dynamic();
				dynamic.seteId(eventInfo.geteId());
				dynamic.setEiId(info.getEiId());
				dynamic.setUserId(executeId);
				dynamic.setLeId(eventInfo.getLeId());
				dynamic.setTitle(title);
				dynamic.setContent(title);
				dynamic.setCreateBy(executeId);
				dynamic.setIsValid((byte)1);
				dynamic.setState((byte)1);
				dynamicDao.saveDynamic(dynamic);
			//add by xiehuilin 20171027添加行动动态  end

            //eidt  by xiehuilin 行动终止不产生清单生成工作清单
			if(eventItem.getState()!=5){
				WeeklyList weeklyList=new WeeklyList();
				weeklyList.seteId(eventInfo.geteId());
				weeklyList.setLeId(eventInfo.getLeId());
				weeklyList.setUserId(eventItem.getDutyId());
				weeklyList.setCreateBy(eventItem.getDutyId());
				weeklyList.setEiId(eventItem.getEiId());
				weeklyListSvc.createWeekly(weeklyList,0);
			}
        }
		/** add by xiehuilin 20171027 奖惩行动支付完成更新奖惩名单
        if(null!=info.getIsReward()&&info.getIsReward()==1){
            // add by xiehuilin 20170911 根据行动id和奖惩列表获取奖惩名单详情
            AwardPenaltyList awardPenaltyList=new AwardPenaltyList();
            awardPenaltyList.setEiId(info.getEiId());
            awardPenaltyList.setRpCategory(info.getRpCategory());
            AwardPenaltyList awardPenaltyListInfo = awardPenaltyListDao.getAwardPenaltyListInfo(awardPenaltyList);
            // add by xiehuilin 20170911 更新奖惩名单记录
            awardPenaltyList.setIsFinish(new Byte("1"));
            awardPenaltyListDao.update(awardPenaltyList);
        }
	  **/
	}

    /**
	 * 事项详情
	 * @author wuchao
	 * @date 2017年6月15日
	 * @time 下午4:37:06
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	@Override
	public EventItem getItemInfo(EventItem eventItem) {
		return eventItemDao.getItemInfo(eventItem);
	}


	/**
	 * 校验事项完成时间
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午5:03:09
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	@Override
	public EventItem checkTime(EventItem eventItem) {
		return eventItemDao.checkTime(eventItem);
	}


	/**
	 * 获取事件下的事项列表
	 * @author wuchao
	 * @date 2017年6月22日
	 * @time 下午4:14:38
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	@Override
	public List<EventItem> listItem(EventItem eventItem) {
		return eventItemDao.listItem(eventItem);
	}
	/**
	 *  评分
	 *   1:更新事项记录
	 *   1.1：评价最低分和最高分生成奖惩记录
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/7/6 13:49
	 */
	@Override
	public void updateEventItemCreateAwardpenalty(EventItem eventItem) {
		eventItemDao.updateEventItem(eventItem);
		if(1==eventItem.getRank()||5==eventItem.getRank()){
            AwardPenaltyRule penaltyRule=new AwardPenaltyRule();
			penaltyRule.seteId(eventItem.geteId());
			Byte category=(1==eventItem.getRank()?new Byte("1"):new Byte("2"));
			penaltyRule.setRpCategory(category);
            AwardPenaltyRule reslut= awardPenaltyRuleDao.infoAward(penaltyRule);
            String reason=1==eventItem.getRank()?"主管评价一星":"主管评价五星";
			String context=1==eventItem.getRank()?"惩罚":"奖励";
            EventItem itemInfo = eventItemDao.getItemInfo(eventItem);
            if(null!=reslut){

                Event event=new Event();
                event.seteId(eventItem.geteId());
                Event envetInfo = eventDao.getEnvetInfo(event);
                StringBuilder builder=new StringBuilder().append("由于").append(reason).append(",").append(context).append(reslut.getRpMoney()).append("元");
                Long beneficiary=eventItem.getCreateBy();
                if(eventItem.getRank()==5){
					beneficiary=itemInfo.getDutyId();
                    itemInfo.setDutyId(eventItem.getCreateBy());


                }
                Byte rpType=eventItem.getRank()==5?new Byte("2"):new Byte("1");
                //add  by xiehuilin 20170911 生成奖惩行动
                //Long eiId = createItemAndTodo(itemInfo, beneficiary, envetInfo, builder.toString(), rpType);
                //eventItem.setEiId(eiId);
                //add  by xiehuilin 20170911 生成消息推送
             	//pushNews(eventItem, itemInfo,EventMap.itemStateMap.get("0"),builder.toString(),new Byte("1"),new Byte("6"));
				//add  by xiehuilin 20170929 生成奖惩名单 start
				AwardPenaltyList penaltyList=new AwardPenaltyList();
				penaltyList.setAprId(reslut.getId());
				penaltyList.setCreateBy(eventItem.getCreateBy());
				penaltyList.setUserId(eventItem.getDutyId());
				penaltyList.seteId(eventItem.geteId());
				penaltyList.setEiId(eventItem.getEiId());
				penaltyList.setRplReason(reason);
				penaltyList.setTaskTime(itemInfo.getFinishTime());
				penaltyList.setRplMoney(reslut.getRpMoney());
				awardPenaltyListDao.save(penaltyList);



            }
        }

	}


	/**
	 * 重新编辑进度内容(单次)
	 * @author wuchao
	 * @date 2017年7月27日
	 * @time 上午11:05:05
	 * @version V0.0.1
	 * @param eventItem
	 */
	@Override
	public void updateItem(EventItem eventItem) {
		EventItem itmeInfo=eventItemDao.getItemInfo(eventItem);
		Event ei=new Event();
		ei.seteId(itmeInfo.geteId());
		Event envet= eventDao.getEnvetInfo(ei);
		Timestamp timeOne=ToDoRemindUtils.getToDoRemindTime(itmeInfo.getFinishTime(),eventItem.getFrequency());
		eventItem.setEiId(itmeInfo.getEiId());
		User user=new User();
        user.setUserId(eventItem.getCreateBy());
        User use=userDao.getUserInfo(user);
        Calendar c = Calendar.getInstance();
        long now_time=c.getTime().getTime();
		long start_time=timeOne.getTime();
		eventItem.setIsAccept((byte) 1);
		eventItemDao.updateEventItem(eventItem);
		Todo todo = new Todo();
		if (eventItem.getFrequency()==1) {
			todo.setIsSend((byte)1);
		}else{
			todo.setIsSend((byte)0);
		}
		todo.setEiId(itmeInfo.getEiId());
		todo.settState((byte) 16);
		todo.setIsInvalid((byte) 0);
		Todo tod = todoDao.todoInfo(todo);
		todo.setId(tod.getId());
		todo.setFinishTime(eventItem.getFinishTime());
		todo.setRemindTime(timeOne);
		todo.setPushTime(eventItem.getFinishTime());
		if (now_time > start_time || now_time == start_time && eventItem.getFrequency() !=1) {
			if (!StrUtils.isEmpty(use.getOpenId())) {
				Templet.mould(eventItem.getEiDesc(), envet.getName(), EventMap.itemStateMap.get("0"),
						"时间：" + StrUtils.getDateHour(eventItem.getFinishTime()), use.getOpenId());
			}
			todo.setIsSend((byte) 1);
		}
		todoDao.updateTodo(todo);
	}

	
	
	
	/**
	 * 事项详情
	 * @author wuchao
	 * @date 2017年7月27日
	 * @time 上午11:19:34
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	@Override
	public EventItem getActionItem(EventItem eventItem) {
		EventItem itmeInfo=eventItemDao.getItemInfo(eventItem);
		User user=new User();
        user.setUserId(itmeInfo.getDutyId());
        User use=userDao.getUserInfo(user);
        itmeInfo.setHeadUrl(use.getHeadUrl());
        itmeInfo.setUserName(use.getUserName());
		return itmeInfo;
	}

	@Override
	public List<EventItem> listUnfinishedEventItem(EventItem eventItem) {
		return eventItemDao.listUnfinishedEventItem(eventItem);
	}

	@Override
	public List<EventItem> listEventItemByeIdAndDutyId(EventItem eventItem) {
		return eventItemDao.listEventItemByeIdAndDutyId(eventItem);
	}

	@Override
	public List<EventItem> getDelEventStatus(FriendGroup friendGroup) {
		return eventItemDao.getDelEventStatus(friendGroup);
	}


	/**
	  * 保存周期行动
	  * @author wuchao
	  * @date 2017年8月9日
	  * @time 上午11:47:05
	  * @version V0.0.1
	  * @param eventItem
	  */
	@Override
	public Integer saveCycItem(EventItem eventItem) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		Event ei = new Event();
		ei.seteId(eventItem.geteId());
		Event envet = eventDao.getEnvetInfo(ei);
		if (envet.getEndTime()==null) {
			envet.setEndTime(ToDoRemindUtils.getLagAge(envet.getStartTime(), 10));
		}
		Todo todo = new Todo();
		Integer succeed = 0;
		// 创建新行动，判断待计划待办和待计划消息是否存在，存在则置为无效
		shutTodoNews(eventItem, present_time);
		todo.seteId(eventItem.geteId());
		todo.setUserId(eventItem.getDutyId());
		todo.setCreateBy(eventItem.getCreateBy());
		todo.settSubjType((byte) 1);
		eventItem.setState((byte) 0);
		// 保存事项
		if (eventItem.getCycleFreq() == 0) {
			// 每天
			succeed = checkPeriodDay(eventItem, todo, envet);
		} else {
			// 选择性周几
			succeed = checkRevolution(eventItem, todo, envet, succeed);
		}
		if (!todo.getUserId().equals(eventItem.getCreateBy())) {
			// 好友关系更新
			FriendGroup friendGroup = new FriendGroup();
			friendGroup.setUserId(eventItem.getCreateBy());
			friendGroup.setfId(eventItem.getDutyId());
			friendGroupDao.updateUnused(friendGroup);
		}
		return succeed;
	}
	
	/**
	 * 创建新行动，判断待计划待办和待计划消息是否存在，存在则置为无效
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 上午11:54:34
	 * @version V0.0.1
	 * @param eventItem
	 * @param present_time
	 */
	public void shutTodoNews(EventItem eventItem,Timestamp present_time){
		Todo to=new Todo();
		to.seteId(eventItem.geteId());
		Todo td=todoDao.infoTodo(to);
		if (td !=null) {
			//将待办事件改成已完成
			if (td.getPushTime().getTime()>present_time.getTime()) {
				td.setIsValid((byte)0);
				td.setFinishTime(present_time);
			}else{
				td.setFinishTime(present_time);
				td.setIsFinish((byte)1);
			}
			todoDao.updateTodo(td);
			News ne=new News();
			ne.setSendId(td.getUserId());
			ne.seteId(td.geteId());
			ne.setnMold((byte)4);
			newsDao.delete(ne);
		}
	}
	/**
	 * 保存待办行踪
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午3:23:18
	 * @version V0.0.1
	 */
	public void saveTrack(Event envet,EventItem eventItem,Timestamp pTime){
		Todo toItem=new Todo();
		toItem.setUserId(eventItem.getCreateBy());
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
	 * 周期性每天校验添加
	 * @author wuchao
	 * @date 2017年8月10日
	 * @time 上午10:02:41
	 * @version V0.0.1
	 * @param eventItem
	 * @param cycleTemplet
	 */
	public Integer checkPeriodDay(EventItem eventItem, Todo todo, Event envet) {
		Integer succeed = 0;
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		Long event_Start = envet.getStartTime().getTime();
		Long event_end = envet.getEndTime().getTime();
		int templet = 0;
		CycleTemplet cycleTemplet = new CycleTemplet();
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		if (event_Start < present_time.getTime()) {
			int day = ToDoRemindUtils.getYTDWeeks(new Date());
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
				if (startTime >= event_Start && startTime <= event_end && startTime >= present_time.getTime()) {
					if (templet == 0) {
						cycleTemplet = saveTemplet(eventItem);
						eventItem.setIsShow((byte)1);
						templet++;
						succeed++;
					}else{
						eventItem.setIsShow((byte)0);
					}
					eventItem.setCtId(cycleTemplet.getId());
					savePeriodDay(eventItem, todo, envet);
				}
			}
			// 事项周期模板保存
		}
		// 判断项目的开始时间大于当前时间
		if (present_time.getTime() <= event_Start) {
			int day = ToDoRemindUtils.getYTDWeeks(envet.getStartTime());
			for (int i = day; i < 8; i++) {
				// 对应日期的零点零分
				long dayTime = ToDoRemindUtils.getDayWeeksYTD(i, envet.getStartTime()).getTime();
				 dayTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(dayTime)).getTime();
				// 进度的完成时间
				long finish = dayTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = dayTime + eventItem.getCircleStartTime();
				eventItem.setFinishTime(new Timestamp(finish));
				eventItem.setStartTime(new Timestamp(startTime));
				// 项目开始时间大于进度开始时间进行添加周期进度
				if (startTime >= event_Start && startTime <= event_end) {
					if (templet == 0) {
						cycleTemplet = saveTemplet(eventItem);
						eventItem.setIsShow((byte)1);
						templet++;
						succeed++;
					}else{
						eventItem.setIsShow((byte)0);
					}
					eventItem.setCtId(cycleTemplet.getId());
					savePeriodDay(eventItem, todo, envet);
				}
				long next_Start = ToDoRemindUtils.getNowWeekMonday(envet.getStartTime()).getTime();
				long next_item = ToDoRemindUtils.getWeeHoursTime(new Timestamp(next_Start)).getTime()
						+ eventItem.getCircleStartTime();
				// 项目开始时间是周末并且项目开始时间大于行动开始时间并且项目结束时间大于下周一的行动开始时间，进行添加行动
				if (startTime < event_Start && day == 7 && next_item < event_end) {
					envet.setStartTime(new Timestamp(next_Start));
					for (int j = 1; j <8; j++) {
						// 对应日期的零点零分
						long next_Time = ToDoRemindUtils.getWeeHoursTime(new Timestamp(next_item)).getTime()+eventItem.getCircleFinishTime();
						next_Time = ToDoRemindUtils.getDayWeeksYTD(j, new Timestamp(next_Time)).getTime();
						next_Time = ToDoRemindUtils.getWeeHoursTime(new Timestamp(next_Time)).getTime();
						// 进度的完成时间
						long next_finish = next_Time + eventItem.getCircleFinishTime();
						// 进度的开始时间
						long next_startTime = next_Time + eventItem.getCircleStartTime();
						eventItem.setFinishTime(new Timestamp(next_finish));
						eventItem.setStartTime(new Timestamp(next_startTime));
						if (next_startTime <= event_end) {
							if (templet == 0) {
								cycleTemplet = saveTemplet(eventItem);
								eventItem.setIsShow((byte)1);
								templet++;
								succeed++;
							}else{
								eventItem.setIsShow((byte)0);
							}
							eventItem.setCtId(cycleTemplet.getId());
							savePeriodDay(eventItem, todo, envet);
						}
					}
				}
			}
		}
		return succeed;
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
		CycleTemplet templet=new CycleTemplet();
		templet.setId(eventItem.getCtId());
		long startTime=ToDoRemindUtils.getNowWeekMonday(eventItem.getStartTime()).getTime();
		
		templet.setNextStartTime(ToDoRemindUtils.getWeeHoursTime(new Timestamp(startTime)));
		//判断事件的结束时间是否大于行动周期的下个周一，如果不大于，则不修改行动周期模板
		//if (envet.getEndTime().getTime()>startTime) {
			cycleTempletDao.updateCycleTemplet(templet);
		//}
		Calendar c = Calendar.getInstance();
		eventItem.setIsAccept((byte) 1);
		eventItem.setCtId(eventItem.getCtId());
		eventItem.setIsReward((byte) 0);
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
		join.setFrequency(envet.getFrequency());
		join.setIsSign((byte) 0);
		join.setEiId(eventItem.getEiId());
		join.setCreateBy(eventItem.getCreateBy());
		JoinEvent jo = joinEventDao.itemJoinInfo(join);
		if (jo == null) {
			joinEventDao.insert(join);
		}
		// 添加周报内容
		if (envet.getLeId() != null) {
			saveWeekly(eventItem, envet);
		}
		
	}

	/**
	 * 周期性选择校验添加  选择性周几
	 * @author wuchao
	 * @date 2017年8月10日
	 * @time 下午5:38:10
	 * @version V0.0.1
	 * @param eventItem
	 * @param cycleTemplet
	 * @param todo
	 * @param envet
	 */
	public Integer checkRevolution(EventItem eventItem, Todo todo, Event envet,Integer succeed) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		Long event_Start = envet.getStartTime().getTime();
		Long event_end = envet.getEndTime().getTime();
		int add_count = 0;
		int templet=0;
		CycleTemplet cycleTemplet=new CycleTemplet();
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
				if (a >= day && startTime >= present_time.getTime()) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					if (startTime >= event_Start && startTime <= event_end && startTime >= present_time.getTime()) {
						if (templet==0) {
							cycleTemplet=saveTemplet(eventItem);
							eventItem.setIsShow((byte)1);
							templet++;
							succeed++;
						}else{
							eventItem.setIsShow((byte)0);
						}
						add_count++;
						eventItem.setCtId(cycleTemplet.getId());
						savePeriodDay(eventItem, todo, envet);
					}
				}
			}
			if (add_count==0) {
				long next_start=ToDoRemindUtils
						.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getNowWeekMonday(new Timestamp(event_Start)).getTime())).getTime()+eventItem.getCircleStartTime();
				if (next_start<event_end) {
					envet.setStartTime(new Timestamp(next_start));
						if (succeed==0) {
							succeed=checkRevolutionWeek(eventItem, todo, envet,succeed);
						}
				}
			}
		}else if (event_Start > present_time.getTime() && event_end>present_time.getTime()){
			int day = ToDoRemindUtils.getYTDWeeks(new Date());
			String cy = eventItem.getCycle();
			String[] cyList = cy.split(",");
			for (int i = 0; i < cyList.length; i++) {
				String cyDay = EventMap.itemDate.get(cyList[i]);
				int a = Integer.parseInt(cyDay);
				// 对应日期的零点零分
				long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getDayWeeksYTD(a, envet.getStartTime()).getTime()))
						.getTime();
				// 进度的完成时间
				long finish = aTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = aTime + eventItem.getCircleStartTime();
				if (a >= day && startTime >= present_time.getTime()) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					if (startTime >= event_Start && startTime <= event_end) {
						if (templet==0) {
							cycleTemplet=saveTemplet(eventItem);
							eventItem.setIsShow((byte)1);
							templet++;
							succeed++;
						}else{
							eventItem.setIsShow((byte)0);
						}
						add_count++;
						eventItem.setCtId(cycleTemplet.getId());
						savePeriodDay(eventItem, todo, envet);
					}
				}
			}
			if (add_count==0) {
				long next_start=ToDoRemindUtils
						.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getNowWeekMonday(new Timestamp(event_Start)).getTime())).getTime()+eventItem.getCircleStartTime();
				if (next_start<event_end) {
					envet.setStartTime(new Timestamp(next_start));
						if (succeed==0) {
							succeed=checkRevolutionWeek(eventItem, todo, envet,succeed);
						}
				}
			}
		
		}
		
		return succeed;
	}
	
	/**
	 * 周期性选择校验添加  选择性周几
	 * @author wuchao
	 * @date 2017年8月18日
	 * @time 下午1:00:40
	 * @version V0.0.1
	 * @param eventItem
	 * @param todo
	 * @param envet
	 * @param succeed
	 * @return
	 */
	public Integer checkRevolutionWeek(EventItem eventItem, Todo todo, Event envet,Integer succeed) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		int add_count = 0;
		int templet=0;
		CycleTemplet cycleTemplet=new CycleTemplet();
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		if (envet.getStartTime().getTime() > present_time.getTime() && envet.getEndTime().getTime() > present_time.getTime()) {
			Long event_Start = envet.getStartTime().getTime();
			Long event_end = envet.getEndTime().getTime();
			String cy = eventItem.getCycle();
			String[] cyList = cy.split(",");
			for (int i = 0; i < cyList.length; i++) {
				String cyDay = EventMap.itemDate.get(cyList[i]);
				int a = Integer.parseInt(cyDay);
				// 对应日期的零点零分
				long aTime = ToDoRemindUtils
						.getWeeHoursTime(
								new Timestamp(ToDoRemindUtils.getDayWeeksYTD(a, new Timestamp(event_Start)).getTime()))
						.getTime();
				int event_Start_unknown = ToDoRemindUtils.getYTDWeeks(new Timestamp(event_Start));
				// 进度的完成时间
				long finish = aTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = aTime + eventItem.getCircleStartTime();
				if (a >= event_Start_unknown && startTime >= event_Start && startTime < event_end) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					if (templet == 0) {
						cycleTemplet = saveTemplet(eventItem);
						eventItem.setIsShow((byte)1);
						templet++;
						succeed++;
					}else{
						eventItem.setIsShow((byte)0);
					}
					add_count++;
					eventItem.setCtId(cycleTemplet.getId());
					savePeriodDay(eventItem, todo, envet);
				}
			}
			if (add_count==0) {
				long next_start=ToDoRemindUtils
						.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getNowWeekMonday(new Timestamp(event_Start)).getTime())).getTime()+eventItem.getCircleStartTime();
				if (next_start<event_end) {
					envet.setStartTime(new Timestamp(next_start));
					succeed=checkRevolutiontwo(eventItem, todo, envet,succeed);
				}
			}
		}
		
		
		return succeed;
	}
	/**
	 * 周期性选择校验添加  选择性周几
	 * @author wuchao
	 * @date 2017年8月18日
	 * @time 下午1:00:49
	 * @version V0.0.1
	 * @param eventItem
	 * @param todo
	 * @param envet
	 * @param succeed
	 * @return
	 */
	public Integer checkRevolutiontwo(EventItem eventItem, Todo todo, Event envet,Integer succeed) {
		Timestamp present_time = new Timestamp((new Date()).getTime());// 当前时间
		// 项目开始时间
		int templet=0;
		CycleTemplet cycleTemplet=new CycleTemplet();
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		if (envet.getStartTime().getTime() > present_time.getTime() && envet.getEndTime().getTime() > present_time.getTime()) {
			Long event_Start = envet.getStartTime().getTime();
			Long event_end = envet.getEndTime().getTime();
			String cy = eventItem.getCycle();
			String[] cyList = cy.split(",");
			for (int i = 0; i < cyList.length; i++) {
				String cyDay = EventMap.itemDate.get(cyList[i]);
				int a = Integer.parseInt(cyDay);
				// 对应日期的零点零分
				long aTime = ToDoRemindUtils
						.getWeeHoursTime(
								new Timestamp(ToDoRemindUtils.getDayWeeksYTD(a, new Timestamp(event_Start)).getTime()))
						.getTime();
				int event_Start_unknown = ToDoRemindUtils.getYTDWeeks(new Timestamp(event_Start));
				// 进度的完成时间
				long finish = aTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = aTime + eventItem.getCircleStartTime();
				if (a >= event_Start_unknown && startTime >= event_Start && startTime < event_end) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					if (templet == 0) {
						cycleTemplet = saveTemplet(eventItem);
						eventItem.setIsShow((byte)1);
						templet++;
						succeed++;
					}else{
						eventItem.setIsShow((byte)0);
					}
					eventItem.setCtId(cycleTemplet.getId());
					savePeriodDay(eventItem, todo, envet);
				}
			}
		}
		
		
		return succeed;
	}
	
	/**
	 * 校验待办跟踪是否存在，如果存在则置为无效
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 上午10:31:31
	 * @version V0.0.1
	 * @param itmeInfo
	 */
	public void  tailAfter(EventItem itmeInfo){
		Todo tdoInfo=new Todo();
		tdoInfo.setEiId(itmeInfo.getEiId());
		tdoInfo.settState((byte)10);
		tdoInfo.setIsInvalid((byte)0);
		tdoInfo.setIsValid((byte)1);
		Todo tdInfo=todoDao.todoInfo(tdoInfo);
		if (tdInfo!=null) {
			Todo tInfo=new Todo();
			tInfo.setId(tdInfo.getId());
			tInfo.setIsValid((byte)0);
			todoDao.updateTodo(tInfo);
		}
	}
	 /**
	  * 重新编辑进度内容(周期)
	  * @author wuchao
	  * @date 2017年8月11日
	  * @time 上午10:48:27
	  * @version V0.0.1
	  * @param eventItem
	  * @return
	  */
	@Override
	public  Integer updateCycItem(EventItem eventItem) {
		Integer succeed=0;
		//EventItem itmeInfo=eventItemDao.getItemInfo(eventItem);
		eventItemDao.updateTempletState(eventItem);
		List<EventItem> itemList=eventItemDao.listItemInfo(eventItem);
		for (int i = 0; i < itemList.size(); i++) {
			EventItem item = itemList.get(i);
			Timestamp time=ToDoRemindUtils.getToDoRemindTime(item.getStartTime(), item.getFrequency());
			Todo todo=new Todo();
			todo.setEiId(item.getEiId());
			todo.settState((byte)16);
			Todo td=todoDao.todoInfo(todo);
			td.setRemindTime(time);
			todoDao.updateCycTodo(td);
		}
		CycleTemplet cycleTemplet=new CycleTemplet();
		cycleTemplet.setCycleContent(eventItem.getEiDesc());
		cycleTemplet.setId(eventItem.getCtId());
		cycleTempletDao.updateCycleTemplet(cycleTemplet);
		return succeed;
	}
	
	/**
	 * 将大于当前时间的此周期进度置为无效
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 下午1:42:32
	 * @version V0.0.1
	 */
	public void updateItemValid(EventItem eventItem){
		eventItem.setStartTime(ToDoRemindUtils.getWeeHoursTime(eventItem.getStartTime()));
		List<EventItem> itemList=eventItemDao.listItemInfo(eventItem);
		for (int i = 0; i < itemList.size(); i++) {
			EventItem item=itemList.get(i);
			Event ei=new Event();
			ei.seteId(eventItem.geteId());
			Event envet= eventDao.getEnvetInfo(ei);
			if (envet.getLeId() != null) {
				deleteWeekly(eventItem);
			}
			//添加周报内容
			if (envet.getLeId() != null) {
				deleteWeekly(eventItem);
				saveWeekly(eventItem, envet);
			}
			//将行动置为无效
			EventItem eItem=new EventItem();
			eItem.setEiId(item.getEiId());
			eItem.setIsValid((byte)0);
			eventItemDao.updateEventItem(eItem);
			//删除相关进度系统消息
			News ne=new News();
			ne.setEiId(item.getEiId());
			newsDao.delete(ne);
			//将行动相关的事件责任人置为无效
			JoinEvent joinEvent=new JoinEvent();
			joinEvent.setEiId(item.getEiId());
			JoinEvent join=joinEventDao.getJoinEventInfo(joinEvent);
			if (join !=null) {
				join.setIsValid((byte)0);
				joinEventDao.updateJoinEvent(join);
			}
			//将行动对应的待办置为无效
			Todo to=new Todo();
			to.setEiId(item.getEiId());
			List<Todo> tdList=todoDao.listEventItemTodo(to);
			if (tdList.size()>0) {
				//将待办进度置为无效
				for (int j = 0; j < tdList.size(); j++) {
					Todo todoInfo=tdList.get(j);
					todoInfo.setIsValid((byte)0);
					todoDao.updateTodo(todoInfo);
				}
			}
		}
	}


	/**
	 * 进度列表
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 下午2:16:02
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	@Override
	public List<EventItem> listItemInfo(EventItem eventItem) {
		return eventItemDao.listItemInfo(eventItem);
	}

	/**
	 * 根据模板id获取事项的最大id
	 * @author wuchao
	 * @date 2017年8月12日
	 * @time 下午1:54:30
	 * @version V0.0.1
	 * @return
	 */
	@Override
	public EventItem getMAXItem(EventItem eventItem) {
		return eventItemDao.getMAXItem(eventItem);
	}
	
	/**
	 * 消息推送
	 * @author wuchao
	 * @date 2017年8月12日
	 * @time 下午5:53:56
	 * @version V0.0.1
	 * @param eventItem
	 * @param envet
	 */
	public void saveNewsPush(EventItem eventItem,Event envet){
		Timestamp present_time=new Timestamp((new Date()).getTime());//当前时间
		User user=new User();
        user.setUserId(eventItem.getCreateBy());
        User use=userDao.getUserInfo(user);
        User user_duty=new User();
        user_duty.setUserId(eventItem.getDutyId());
        User duty=userDao.getUserInfo(user_duty);
		News news =new News();
		news.setnTitle("行动名称："+eventItem.getEiDesc());
		news.setnTime(present_time);
		news.setCreateBy(eventItem.getCreateBy());
		news.setCreateTime(present_time);
		news.setIsValid((byte)1);
		news.setSendId(eventItem.getDutyId());
		news.setnType((byte)1);
		if (eventItem.getIsCycle()==1) {
			news.setnContent(use.getUserName()+EventMap.newsMap.get("6"));
		}else{
			news.setnContent(use.getUserName()+EventMap.newsMap.get("5"));
		}
		news.setnName(envet.getName());
		news.setnMold((byte)7);
		news.seteId(eventItem.geteId());
		news.setEiId(eventItem.getEiId());
		newsDao.insert(news);
		if (!StrUtils.isEmpty(duty.getOpenId())) {
			Templet.mould(use.getUserName()+"委托您一项行动",envet.getName(), EventMap.itemStateMap.get("1"),"时间："+StrUtils.getDateHour(eventItem.getFinishTime()), duty.getOpenId());
		}
	}
	
	/**
	 * 周期性每天校验添加(编辑)
	 * @author wuchao
	 * @date 2017年8月10日
	 * @time 上午10:02:41
	 * @version V0.0.1
	 * @param eventItem
	 * @param cycleTemplet
	 */
/*	public Integer checkCyclePeriodDay(EventItem eventItem,Todo todo,Event envet){
		Integer succeed=0;
		int ct=0;
		//项目开始时间
		Long event_start=envet.getStartTime().getTime();
		Long event_end=envet.getEndTime().getTime();
		//项目开始时间小于当前时间and进度开始时间大于当前时间
			int day=ToDoRemindUtils.getYTDWeeks(eventItem.getStartTime());
			for (int i = day; i < 8; i++) {
			//对应日期的零点零分
			long dayTime=	ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(i).getTime())).getTime();
			//进度的完成时间
			long finish=dayTime	+eventItem.getCircleFinishTime();
			//进度的开始时间
			long startTime=	dayTime+eventItem.getCircleStartTime();
			eventItem.setFinishTime(new Timestamp(finish));
			eventItem.setStartTime(new Timestamp(startTime));
			if (event_start<=startTime && startTime<=event_end ) {
				if (ct==0) {
					//更新事项周期模板
					saveTemplet( eventItem);
					succeed++;
					ct++;
				}
				savePeriodDay(eventItem, todo, envet);
			}
		}
			return succeed;	
	}*/
	/**
	 * 周期选择校验添加(编辑)
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 上午11:12:01
	 * @version V0.0.1
	 * @param eventItem
	 * @param cycleTemplet
	 * @param todo
	 * @param envet
	 */
	/*public Integer checkCycleRevolution(EventItem eventItem, Todo todo, Event envet) {
		Integer succeed=0;
		// 项目开始时间
		int ct=0;
		Long event_start=envet.getStartTime().getTime();
		Long event_end = envet.getEndTime().getTime();
		int add_count = 0;
		// 项目开始时间小于当前时间and进度开始时间大于当前时间
		int day = ToDoRemindUtils.getYTDWeeks(eventItem.getStartTime());
		long item_start= ToDoRemindUtils.getWeeHoursTime(eventItem.getStartTime()).getTime();
		String cy = eventItem.getCycle();
		String[] cyList = cy.split(",");
		for (int i = 0; i < cyList.length; i++) {
			String cyDay = EventMap.itemDate.get(cyList[i]);
			int a = Integer.parseInt(cyDay);
			long week_time=ToDoRemindUtils.getDayWeeksYTD(a, eventItem.getStartTime()).getTime();
			// 对应日期的零点零分
			long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(week_time)).getTime();
			// 进度的完成时间
			long finish = aTime + eventItem.getCircleFinishTime();
			// 进度的开始时间
			long startTime = aTime + eventItem.getCircleStartTime();
			eventItem.setFinishTime(new Timestamp(finish));
			eventItem.setStartTime(new Timestamp(startTime));
			if (event_start<=startTime &&a >= day && startTime <= event_end && startTime>item_start) {
				savePeriodDay(eventItem, todo, envet);
				if (ct == 0) {
					// 更新事项周期模板
					saveTemplet(eventItem);
					succeed++;
					ct++;
				}
				add_count++;
			}
		}
		if (add_count == 0) {
			long weekDate=ToDoRemindUtils.getNowWeekMonday(eventItem.getStartTime()).getTime();
			for (int j = 0; j < cyList.length; j++) {
				String cycleDay = EventMap.itemDate.get(cyList[j]);
				int b = Integer.parseInt(cycleDay);
				// 对应日期的零点零分
				long weekTime=ToDoRemindUtils.getDayWeeksYTD(b,new Timestamp(weekDate)).getTime()+eventItem.getCircleStartTime();
				long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(weekTime)).getTime();
				// 进度的完成时间
				long finish = aTime + eventItem.getCircleFinishTime();
				// 进度的开始时间
				long startTime = aTime + eventItem.getCircleStartTime();
				if (event_start<=startTime &&startTime < event_end) {
					eventItem.setFinishTime(new Timestamp(finish));
					eventItem.setStartTime(new Timestamp(startTime));
					savePeriodDay(eventItem, todo, envet);
					if (ct==0) {
						//更新事项周期模板
						saveTemplet( eventItem);
						succeed++;
						ct++;
					}
				}
			}
		}
		return succeed;
	}*/
	
	/**
	 * 保存模板内容
	 * @author wuchao
	 * @date 2017年8月14日
	 * @time 上午11:21:05
	 * @version V0.0.1
	 * @param eventItem
	 * @return
	 */
	public CycleTemplet saveTemplet(EventItem eventItem){
		CycleTemplet cycleTemplet=new CycleTemplet();
		cycleTemplet.setCycleState(eventItem.getCycleState());
		cycleTemplet.setCycleFreq(eventItem.getCycleFreq());
		cycleTemplet.setCycle(eventItem.getCycle());
		cycleTemplet.setCreateBy(eventItem.getCreateBy());
		cycleTemplet.setSatrtLength(eventItem.getCircleStartTime());
		cycleTemplet.setUntilLength(eventItem.getCircleFinishTime());
		cycleTemplet.setDutyId(eventItem.getDutyId());
		cycleTemplet.setCycleContent(eventItem.getEiDesc());
		long time=ToDoRemindUtils.getNowWeekMonday(eventItem.getStartTime()).getTime()+eventItem.getCircleStartTime();
		cycleTemplet.setNextStartTime(ToDoRemindUtils.getWeeHoursTime(new Timestamp(time)));
		if (eventItem.getCtId()!=null) {
		cycleTemplet.setId(eventItem.getCtId());
		cycleTempletDao.updateCycleTemplet(cycleTemplet);
		}else{
			cycleTempletDao.saveCycleTemplet(cycleTemplet);
		}
		return cycleTemplet;
	}


	/**
	 * 修改周期模板状态
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午6:02:13
	 * @version V0.0.1
	 * @param eventItem
	 */
	@Override
	public void updateCycleState(EventItem eventItem) {
		CycleTemplet cycleTemplet=new CycleTemplet();
		cycleTemplet.setId(eventItem.getCtId());
		cycleTemplet.setCycleState(eventItem.getCycleState());
		cycleTempletDao.updateCycleTemplet(cycleTemplet);
		eventItem.setCtId(eventItem.getCtId());
		eventItemDao.updateTempletState(eventItem);
	}


	/**
	 * 根据周期模板id更新行动的状态(激活、冻结)
	 * @author wuchao
	 * @date 2017年8月15日
	 * @time 下午8:03:55
	 * @version V0.0.1
	 * @param eventItem
	 */
	@Override
	public void updateTempletState(EventItem eventItem) {
		eventItemDao.updateTempletState(eventItem);
	}

	/**
	* 周期行动根据事件查询比当前事项开始时间小的行动
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/16 13:32
	*/
	@Override
	public List<EventItem> getCycleByTimeLessThan(EventItem eventItem) {
		return eventItemDao.getCycleByTimeLessThan(eventItem);
	}
	/**
	 * 周期性行动更新行动编辑限权
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/16 13:30
	 */
	@Override
	public void updateCycleEdit(EventItem eventItem) {
		//根据事项id获取行动记录
        EventItem info = eventItemDao.getByEiIdInfo(eventItem);
        //更新当前行动状态,将可编辑权限置为无效
        eventItem.setIsCycleEdit(new Byte("0"));
        eventItemDao.updateEventItem(eventItem);
        //根据当前行动开始时间查询比当前时间小的周期性行动记录，并置为无编辑权限
        List<EventItem> eventItems = eventItemDao.getCycleByTimeLessThan(info);
        if(null!=eventItems&&!eventItems.isEmpty()){
            for(EventItem ei:eventItems){
                ei.setIsCycleEdit(new Byte("0"));
                eventItemDao.updateEventItem(ei);
            }
        }
        //根据当前行动开始时间和周期id查询比当前时间大的第一条行动记录,并置为可编辑权限
        EventItem cycleByTime = eventItemDao.getCycleByTime(info);
        if(null!=cycleByTime){
            cycleByTime.setIsCycleEdit(new Byte("1"));
            eventItemDao.updateEventItem(cycleByTime);
        }


    }

	@Override
	public EventItem getByEiIdInfo(EventItem eventItem) {
		return eventItemDao.getByEiIdInfo(eventItem);
	}


	@Override
	public EventItem getTemWinInfo(EventItem eventItem) {
		return eventItemDao.getTemWinInfo(eventItem);
	}

	@Override
	public List<EventItem> listEventItemJoinUser(EventItem eventItem) {
		return eventItemDao.listEventItemJoinUser(eventItem);
	}


	 /**
	   * 健康度
	   * @author wuchao
	   * @date 2017年8月23日
	   * @time 上午10:52:38
	   * @version V0.0.2
	   * @param eventItem
	   * @return
	   */
	@Override
	public List<EventItem> listhealth(EventItem eventItem) {
		return eventItemDao.listhealth(eventItem);
	}


	@Override
	public List<EventItem> listUserName(EventItem eventItem) {
		return eventItemDao.listUserName(eventItem);
	}
	
	/**
	 * 添加周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午6:16:33
	 * @version V0.0.3
	 * @param event
	 */
	public void saveWeekly(EventItem eventItem,Event event){
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
	
	/**
	 * 将对应周报内容置为无效
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 上午10:16:24
	 * @version V0.0.3
	 * @param eventItem
	 */
	public void deleteWeekly(EventItem eventItem){
		 LightWeekly lightWeekly=new LightWeekly();
		  lightWeekly.seteId(eventItem.getEiId());
		  lightWeekly.setIsFinish((byte)0);
		  lightWeekly.setIsValid((byte)0);
		weeklyDao.deleteWeekly(lightWeekly);
	}

    /**
    * 生成系统消息和公众号消息推送
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/11 13:42
	 */
    private void pushNews(EventItem eventItem, EventItem info,String title,String desc,Byte type,Byte mold) {
        //添加消息提醒
        Event ei=new Event();
        ei.seteId(info.geteId());
        Event envet= eventDao.getEnvetInfo(ei);
        News news =new News();
        news.setnTitle(title);
        news.setnTime(new Timestamp((new Date()).getTime()) );
        news.setCreateBy(eventItem.getCreateBy());
        news.setCreateTime(new Timestamp((new Date()).getTime()) );
        news.setIsValid((byte)1);
        news.setSendId(info.getDutyId());
        news.setnType(type);
        news.setnContent(desc);
        news.setnMold(mold);
        news.setnName(envet.getName());
        news.seteId(eventItem.geteId());
        news.setEiId(eventItem.getEiId());
        //如果进度责任人和进度创建人是同一人，不进行系统消息和公众号消息

		 if (!eventItem.getCreateBy().equals(info.getDutyId())) {
            newsDao.insert(news);
            //公众号提醒
           /*  add  by xiehuilin 20171027 行动标记完成不推送公众号消息 User user=new User();
            user.setUserId(info.getDutyId());
            User use=userDao.getUserInfo(user);
            if (!StrUtils.isEmpty(use.getOpenId())) {
                Templet.mould(info.getEiDesc(), envet.getName(),desc, StrUtils.getDate(info.getFinishTime()),use.getOpenId());
            }*/
        }
    }


    /**
    * 滞后完成、一星评价、五星评价生成待办记录和行动记录
    * @author xiehuilin
    * @param info 行动实体
    * @param eventInfo 项目实体
    * @param str 描述
    * @param rpCategory 奖惩类型 0 罚 1 奖
    * @return
    * @version V0.0.1
    * @date 2017/9/11 14:10

    private Long createItemAndTodo(EventItem info,Long beneficiary, Event eventInfo, String str,Byte rpCategory) {

        EventItem  item=new EventItem();
        item.setDutyId(info.getDutyId());
        item.setEiDesc(str);
        item.seteId(eventInfo.geteId());
        item.setStartTime(StrUtils.getNowTimestamp());
        item.setCreateTime(StrUtils.getNowTimestamp());
        item.setCreateBy(info.getCreateBy());
        item.setState(new Byte("0"));
        item.setIsReward(new Byte("1"));
        item.setIsRange(new Byte("1"));
        item.setIsInvalid(new Byte("0"));
        item.setIsAccept(new Byte("1"));
        item.setIsCycle(info.getIsCycle());
        item.setRpCategory(rpCategory);
        item.setBeneficiary(beneficiary);
        if(info.getIsCycle()==1){
            item.setCtId(item.getCtId());
            item.setCycle(info.getCycle());
        }
        //当前时间是否大于项目开始时间  是 奖惩行动往后推迟5天 否 项目结束时间
        String s = StrUtils.addDay(new Date(), 5, org.framework.utils.Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN);
        Timestamp timestamp = eventInfo.getEndTime().compareTo(new Date()) < 0 ? Timestamp.valueOf(s) : eventInfo.getEndTime();
        item.setFinishTime(timestamp);
        eventItemDao.saveEventItem(item);
        //add  by xiehuilin 20170911 生成奖惩行动  end

        //add  by xiehuilin 20170911 生成奖惩待办 start
        Todo newTodo= new Todo();
        newTodo.setUserId(item.getDutyId());
        newTodo.seteId(eventInfo.geteId());
        newTodo.setEiId(item.getEiId());
        newTodo.settState(new Byte("17"));
        newTodo.setName("奖惩处理");
        newTodo.settExplained(str);
        newTodo.setCreateBy(item.getCreateBy());
        newTodo.settSubjType(new Byte("1"));
        newTodo.setIsIgnore(new Byte("0"));
        newTodo.setIsBlock(new Byte("0"));
        newTodo.setIsSend(new Byte("1"));
        newTodo.setPushTime(StrUtils.getNowTimestamp());
        newTodo.setRemindTime(StrUtils.getNowTimestamp());
        todoDao.saveTodo(newTodo);
        return item.getEiId();
    }
	 */

    /**
	   * 周报评星
	   * @author wuchao
	   * @date 2017年9月12日
	   * @time 下午6:07:53
	   * @version V0.0.3
	   * @param eventItem
	   * @return
	   */
	@Override
	public List<EventItem> listTswkRank(EventItem eventItem) {
		return eventItemDao.listTswkRank(eventItem);
	}

	@Override
	public List<EventItem> listLightEventItem(EventItem eventItem) {
		return eventItemDao.listLightEventItem(eventItem);
	}

	@Override
	public List<EventItem> listLightEventItemJoinUser(EventItem eventItem) {
		return eventItemDao.listLightEventItemJoinUser(eventItem);
	}
	/**
	 *@Description: 删除行动
	 *              更新行动记录、
	 *              将该行动下的所有待办更新为无效
	 *              将该行动下的所有更新清单记录无效
	 *              将该行动下的所有参与记录更新无效
	 *              将消息表中未推送的更新为无效
	 *@author:xiehuilin
	 *@param:request
	 *@param:event
	 *@return:java.util.Map<java.lang.String,java.lang.Object>
	 *@date:2017/10/25 14:18
	 *@version V 0.0.7
	 */
	@Override
	public void delEventItem(EventItem eventItem) {
		//周期行动,根据周期id获取该周期下的所有行动列表
		if(null!=eventItem.getCtId()){
			EventItem queryEventItem=new EventItem();
			queryEventItem.setCtId(eventItem.getCtId());
			List<EventItem> eventItems = eventItemDao.listItemInfo(queryEventItem);
			if(eventItems.isEmpty()){
				for(EventItem reslutEventItem:eventItems){
					updateEventItemRecord(reslutEventItem);
				}
			}
		}else {
			updateEventItemRecord(eventItem);
		}

	}
	/**
	 *@Description: 删除行动更新行动相关记录
	 *@author:xiehuilin
	 *@param:eventItem
	 *@return:void
	 *@date:2017/10/26 18:16
	 *@version V 0.0.7
	 */
	private void updateEventItemRecord(EventItem eventItem) {
		eventItem.setIsValid(new Byte("0"));
		//更新行动记录
		eventItemDao.updateEventItem(eventItem);
		Todo todo=new Todo();
		todo.setEiId(eventItem.getEiId());
		todoDao.updateBatch(todo);
		//根据行动id批量更新待办记录
		Event event=new Event();
		event.seteId(eventItem.geteId());
		Event envetInfo = eventDao.infoEnvet(event);
		if(null!=envetInfo.getLeId()) {
			WeeklyList weeklyList = new WeeklyList();
			weeklyList.setEiId(eventItem.getEiId());
			List<WeeklyList> weeklyLists = weeklyListDao.listWeeklistByEid(weeklyList);
			if (!weeklyLists.isEmpty()) {
				//根据行动id批量更新清单记录
				weeklyListDao.updateWeeklyListBatch(weeklyList);
			}
		}
		//批量更新参与人记录
		JoinEvent joinEvent=new JoinEvent();
		joinEvent.setEiId(eventItem.getEiId());
		joinEvent.setIsValid(new Byte("0"));
		joinEventDao.updateBacth(joinEvent);
		//根据行动id获取未推送的消息记录
		News news=new News();
		news.setEiId(eventItem.getEiId());
		List<News> reslutNews = newsDao.listGetNotPush(news);
		if(!reslutNews.isEmpty()){
			for(News  n:reslutNews){
				n.setIsValid(new Byte("0"));
				//将消息表中未推送的更新为无效
				newsDao.update(n);
			}
		}
	}


	/**
	  * 激活周期行动
	  *@author wuchao
	  *@data 2017年10月26日
	  *@version V0.0.5
	  * @param eventItem
	  */
	@Override
	public void updateActivateCyc(EventItem eventItem) {
		CycleTemplet cycleTemplet=new CycleTemplet();
		Event ei = new Event();
		ei.seteId(eventItem.geteId());
		Event envet = eventDao.getEnvetInfo(ei);
		//下周一零点零分
		long next_Start = ToDoRemindUtils.getNowWeekMonday(envet.getStartTime()).getTime();
		next_Start = ToDoRemindUtils.getWeeHoursTime(new Timestamp(next_Start)).getTime();
		// 本周周一对应日期的零点零分
		long aTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(ToDoRemindUtils.getWeeksYTD(1).getTime()))
				.getTime();
		if (envet.getStartTime().getTime()<aTime) {
			eventItem.setStartTime(new Timestamp(aTime));
			//本周内是否有此模板周期行动
			List<EventItem> itemList=eventItemDao.listItemInfo(eventItem);
			if (itemList.size()==0) {
				EventItem item= eventItemDao.getMAXItem(eventItem);
				EventItem itemInfo= eventItemDao.getItemInfo(item);
				Todo todo = new Todo();
				Integer succeed = 0;
				todo.seteId(itemInfo.geteId());
				todo.setUserId(itemInfo.getDutyId());
				todo.setCreateBy(itemInfo.getCreateBy());
				todo.settSubjType((byte) 1);
				eventItem.setState((byte) 0);
				// 保存事项
				if (eventItem.getCycleFreq() == 0) {
					// 每天
					succeed = meltPeriodDay(itemInfo, todo, envet);
				} else {
					// 选择性周几
					succeed = meltRevolution(itemInfo, todo, envet, succeed);
				}
				cycleTemplet.setNextStartTime(new Timestamp(next_Start));
			}
		}
		cycleTemplet.setId(eventItem.getCtId());
		cycleTemplet.setCycleState((byte)0);
		cycleTempletDao.updateCycleTemplet(cycleTemplet);
		eventItem.setCycleState((byte)0);
		eventItemDao.updateTempletState(eventItem);
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
	public Integer meltRevolution(EventItem eventItem, Todo todo, Event envet,Integer succeed) {
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
		
		return succeed;
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
	public Integer meltPeriodDay(EventItem eventItem, Todo todo, Event envet) {
		Integer succeed = 0;
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
		return succeed;
	}


	 /**
	  * 获取目标下未冻结的周期模板id
	  *@author wuchao
	  *@data 2017年10月27日
	  *@version V0.0.5
	  * @param eventItem
	  * @return
	  */
	@Override
	public List<EventItem> listEventCycThaw(EventItem eventItem) {
		return eventItemDao.listEventCycThaw(eventItem);
	}


	 /**
	  * 周期行动是否有显示
	  *@author wuchao
	  *@data 2017年11月1日
	  *@version V0.0.5
	  * @param eventItem
	  * @return
	  */
	@Override
	public EventItem isShowItem(EventItem eventItem) {
		return eventItemDao.isShowItem(eventItem);
	}


	

}
