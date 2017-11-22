package com.qixin.teamwork.biz.census.svc.impl;

import java.sql.Timestamp;  
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.LightWeeklyDao;
import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.svc.LightWeeklySvc;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.model.EventItem;

/**
 * 周报实现层
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午4:12:47
 * @version V0.0.3
 */
@Service("lightWeeklySvc")
public class LightWeeklySvcImpl implements LightWeeklySvc{

	@Resource
	private LightWeeklyDao weeklyDao;
	@Resource
	private JoinEventDao joinEventDao;
	@Resource
	private EventDao eventDao;
	@Resource
	private EventItemDao itemDao;
	@Autowired
	private WeeklyListDao weeklyListDao;
	
	/**
	 * 保存周报内容
	 */
	@Override
	public void saveWeekly(LightWeekly lightWeekly) {
		weeklyDao.saveWeekly(lightWeekly);
	}

	/**
	 * 修改周报信息
	 */
	@Override
	public void updateWeekly(LightWeekly lightWeekly) {
		weeklyDao.updateWeekly(lightWeekly);
	}

	/**
	 * 查询周报内容
	 */
	@Override
	public LightWeekly infoWeekly(LightWeekly lightWeekly) {
		LightWeekly light=new LightWeekly();
		//根据日期获取其下周一的零点零分
		Long sTime=ToDoRemindUtils.getNowWeekMonday(lightWeekly.getStartTime()).getTime();
		sTime=ToDoRemindUtils.getWeeHoursTime(new Timestamp(sTime)).getTime();
		
		Long monday=ToDoRemindUtils.getDayWeeksYTD(1, lightWeekly.getStartTime()).getTime();
		monday=ToDoRemindUtils.getWeeHoursTime(new Timestamp(monday)).getTime();
		
		int finishItem=0;//已完成的项目
		int finishAction=0;//已完成的行动
		int advanceItem=0;//项目提前完成数      
		int normalItem=0;//项目正常完成数       
		int lagItem=0;//项目滞后完成数          
		int  unusualItem=0;//项目异常数       
		int advanceAction=0;//行动提前完成数    
		int normalAction=0;//	正常完成的行动  
		int lagAction=0;//行动完成数          
		int unusualAction=0;//行动异常数
		int bestNumber=0;//五星好评数量
		int worstNumber=0;//一星评价数量
		int totalAssess=0;//周评价总数
		//周报清单
		WeeklyList weeklyInfo=new WeeklyList();
		weeklyInfo.setUserId(lightWeekly.getUserId());
		weeklyInfo.setLeId(lightWeekly.getLeId());
		weeklyInfo.setStartTime(new Timestamp(sTime));
		weeklyInfo.setMondayTime(new Timestamp(monday));
		weeklyInfo.setIsRecord((byte)1);
		List<WeeklyList> listWeek=weeklyListDao.listWeeklyList(weeklyInfo);
		if (listWeek.size()>0) {
			light.setListWeeklyList(listWeek);
		}
		
		lightWeekly.setStartTime(new Timestamp(sTime));
		lightWeekly.setIsFinish((byte)0);
		lightWeekly.seteId(1l);
		lightWeekly.setUserId(lightWeekly.getUserId());
		List<LightWeekly> weeklyList=weeklyDao.listWeekly(lightWeekly);//未完成项目
		lightWeekly.seteId(null);
		List<LightWeekly> weekActionList=weeklyDao.listFinishAction(lightWeekly);//未完成行动
		lightWeekly.seteId(1l);
		lightWeekly.setIsFinish((byte)1);
		lightWeekly.setMondayTime(new Timestamp(monday));
		List<LightWeekly> finishItemList=weeklyDao.listFinishItem(lightWeekly);//已完成的项目
		List<LightWeekly> finishActionList=weeklyDao.listFinishAction(lightWeekly);//已完成的行动
		finishItem=finishItemList.size();
		int totalItem=weeklyList.size()+finishItem;//全部项目
		finishAction=finishActionList.size();
		int totalAction=weekActionList.size()+finishAction;//全部行动
		if (finishItemList.size()>0) {
			for (int i = 0; i < finishItemList.size(); i++) {
				LightWeekly weekly=	finishItemList.get(i);
				if (weekly.getStateItem()==7) {
					advanceItem	++;//项目提前完成数
				}else if (weekly.getStateItem()==2) {
					normalItem++;//项目正常完成数    
				}else if (weekly.getStateItem()==8){
					lagItem++;//项目滞后完成数     
				}else if (weekly.getStateItem()==6){
					unusualItem++;//项目异常数     
				}
			}
		}
		if (finishActionList.size()>0) {
			for (int i = 0; i < finishActionList.size(); i++) {
				LightWeekly  actionWeek=finishActionList.get(i);
				if (actionWeek.getStateAction()==7) {
					advanceAction++;//行动提前完成数
				}else if (actionWeek.getStateAction()==3) {
					normalAction++;//行动正常完成数    
				}else if (actionWeek.getStateAction()==4){
					lagAction++;//行动滞后完成数     
				}else if (actionWeek.getStateAction()==5){
					unusualAction++;//行动异常数     
				}
				
			}
		}
		EventItem eventItem= new EventItem();
		eventItem.setStartTime(new Timestamp(sTime));
		eventItem.setUpdateTime(new Timestamp(monday));
		eventItem.setDutyId(lightWeekly.getUserId());
		eventItem.setLeId(lightWeekly.getLeId());
		List<EventItem>itemList=itemDao.listTswkRank(eventItem);
		totalAssess=itemList.size();
		if (itemList.size()>0) {
			for (int i = 0; i < itemList.size(); i++) {
				EventItem item=itemList.get(i);
				//评价情况
				if (item.getRank()==1) {
					worstNumber++;
				}else if (item.getRank()==5){
					bestNumber++;
				}
			}
		}
		light.setTotalAssess(totalAssess);
		light.setTotalItem(totalItem);
		light.setTotalAction(totalAction);
		light.setFinishItem(finishItem);
		light.setFinishAction(finishAction);
		light.setBestNumber(bestNumber);
		light.setWorstNumber(worstNumber);
		light.setAdvanceItem(advanceItem);
		light.setAdvanceAction(advanceAction);
		light.setNormalItem(normalItem);
		light.setNormalAction(normalAction);
		light.setLagItem(lagItem);
		light.setLagAction(lagAction);
		light.setUnusualItem(unusualItem);
		light.setUnusualAction(unusualAction);
		return light;
	}

	/**
	 * 周报内容列表
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午1:39:57
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	@Override
	public List<LightWeekly> listWeekly(LightWeekly lightWeekly) {
		return weeklyDao.listWeekly(lightWeekly);
	}

	/**
	 * 已完成的项目
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:57:39
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	@Override
	public List<LightWeekly> listFinishItem(LightWeekly lightWeekly) {
		return weeklyDao.listFinishItem(lightWeekly);
	}

	/**
	 * 已完成的行动
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:57:44
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	@Override
	public List<LightWeekly> listFinishAction(LightWeekly lightWeekly) {
		return weeklyDao.listFinishAction(lightWeekly);
	}

	@Override
	public LightWeekly getWeeklyInfo(LightWeekly weekly) {
		return weeklyDao.getWeeklyInfo(weekly);
	}
}
