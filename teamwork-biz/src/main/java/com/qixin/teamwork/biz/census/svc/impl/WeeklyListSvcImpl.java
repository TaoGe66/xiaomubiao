package com.qixin.teamwork.biz.census.svc.impl;

import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.svc.WeeklyListSvc;
import org.framework.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("weeklyListSvc")
public class WeeklyListSvcImpl implements WeeklyListSvc{

	@Resource
	private WeeklyListDao weeklyListDao;
	
	/**
	 * 清单列表
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午2:53:50
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	@Override
	public List<WeeklyList> listWeeklyList(WeeklyList weeklyList) {
		//根据日期获取其下周一的零点零分
		Long sTime=ToDoRemindUtils.getNowWeekMonday(weeklyList.getStartTime()).getTime();
		sTime=ToDoRemindUtils.getWeeHoursTime(new Timestamp(sTime)).getTime();
				
		Long monday=ToDoRemindUtils.getDayWeeksYTD(1, weeklyList.getStartTime()).getTime();
		monday=ToDoRemindUtils.getWeeHoursTime(new Timestamp(monday)).getTime();
		weeklyList.setStartTime(new Timestamp(sTime));
		weeklyList.setMondayTime(new Timestamp(monday));
		return weeklyListDao.listWeeklyList(weeklyList);
	}

	/**
	 * 修改清单信息
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午2:54:38
	 * @version V0.0.3
	 * @param weeklyList
	 */
	@Override
	public void updateWeeklyList(WeeklyList weeklyList) {
		weeklyListDao.updateWeeklyList(weeklyList);
	}

	/**
	 * 是否生成周报
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午9:13:47
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	@Override
	public WeeklyList isMakeWeekly(WeeklyList weeklyList) {
		Long sTime=ToDoRemindUtils.getNowWeekMonday(weeklyList.getWeeklyDate()).getTime();
		sTime=ToDoRemindUtils.getWeeHoursTime(new Timestamp(sTime)).getTime();
				
		Long monday=ToDoRemindUtils.getDayWeeksYTD(1, weeklyList.getWeeklyDate()).getTime();
		monday=ToDoRemindUtils.getWeeHoursTime(new Timestamp(monday)).getTime();
		weeklyList.setStartTime(new Timestamp(sTime));
		weeklyList.setMondayTime(new Timestamp(monday));
		weeklyList.setIsWeeklyPsuh((byte)1);
		return weeklyListDao.isMakeWeekly(weeklyList);
	}

	/**
	 * 保存清单信息
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList
	 */
	@Override
	public void saveWeekly(WeeklyList weeklyList) {
		weeklyListDao.insert(weeklyList);		
	}
	/**
	 * 生成周工作清单记录
	 *  行动标记完成:
	 *  	本周是否有行动：
	 *  		有:新增行动记录
	 *  	    否：新增项目记录、行动记录
	 *  行动跟踪
	 *  	本周是否已有跟踪记录
	 *        有:新增跟踪记录
	 *        否：新增项目记录、行动记录、跟踪记录
	 * @author xiehuilin
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList 清单实体
	 * @param type 类型: 0 行动标记完成  1 行动跟踪
	 */
	@Override
	public void createWeekly(WeeklyList weeklyList ,Integer type) {
		//根据行动id获取本周行动是否存在
		weeklyList.setStartTime(StrUtils.getNowTimestamp());
		WeeklyList weeklyInfo = weeklyListDao.getWeeklyInfo(weeklyList);
		//行动实体
		WeeklyList itemWeekly=new WeeklyList();
		itemWeekly.setIsRecord(new Byte("0"));
		itemWeekly.setLeId(weeklyList.getLeId());
		itemWeekly.seteId(weeklyList.geteId());
		itemWeekly.setUserId(weeklyList.getUserId());
		itemWeekly.setCreateBy(weeklyList.getCreateBy());
		itemWeekly.setWeeklyDate(StrUtils.getNowTimestamp());
		itemWeekly.setEiId(weeklyList.getEiId());

		//项目清单实体
		WeeklyList proWeeklyList=new WeeklyList();
		proWeeklyList.setIsRecord(new Byte("0"));
		proWeeklyList.setLeId(weeklyList.getLeId());
		proWeeklyList.seteId(weeklyList.geteId());
		proWeeklyList.setUserId(weeklyList.getUserId());
		proWeeklyList.setCreateBy(weeklyList.getCreateBy());
		proWeeklyList.setWeeklyDate(StrUtils.getNowTimestamp());
		proWeeklyList.setType(new Byte("0"));
		//本周项目是否存在
		WeeklyList pw=new WeeklyList();
		pw.seteId(weeklyList.geteId());
		pw.setStartTime(StrUtils.getNowTimestamp());
		WeeklyList pWeeklyInfo=weeklyListDao.getWeeklyInfo(pw);
		//跟踪清单实体
		WeeklyList tarckWeekly=new WeeklyList();
	   if(type==0&&weeklyInfo==null){
	   	  itemWeekly.setType(new Byte("4"));
		   //新增项目记录
		   if(pWeeklyInfo==null){
		    weeklyListDao.insert(proWeeklyList);
		   }
		   //新增行动记录

		  WeeklyList weeklyList1=new WeeklyList();
		   BeanUtils.copyProperties(itemWeekly,weeklyList1);
		   weeklyList1.setType(new Byte("1"));
		  weeklyListDao.insert(weeklyList1);
		   weeklyListDao.insert(itemWeekly);
	   }else if(type==0&&weeklyInfo!=null){
		   itemWeekly.setType(new Byte("4"));
		   //新增行动记录
		   weeklyListDao.insert(itemWeekly);
	   }

		if(type==1&&weeklyInfo==null){
			itemWeekly.setType(new Byte("1"));
			weeklyListDao.insert(itemWeekly);
			if(pWeeklyInfo==null){
				weeklyListDao.insert(proWeeklyList);
			}
			org.springframework.beans.BeanUtils.copyProperties(itemWeekly,tarckWeekly);
			tarckWeekly.setType(new Byte("2"));
			tarckWeekly.setTrackRecordId(weeklyList.getTrackRecordId());
			weeklyListDao.insert(tarckWeekly);
		}else if(type==1&&weeklyInfo!=null){
			org.springframework.beans.BeanUtils.copyProperties(itemWeekly,tarckWeekly);
			tarckWeekly.setTrackRecordId(weeklyList.getTrackRecordId());
			tarckWeekly.setTrContent(weeklyList.getTrContent());
			tarckWeekly.setType(new Byte("2"));
			weeklyListDao.insert(tarckWeekly);
		}

	}

	/**
	 * 根据id获取清单详情
	 * @author xiehuilin
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList 清单实体
	 */
   public 	WeeklyList  getWeeklyInfo(WeeklyList weeklyList){
	return  weeklyListDao.getWeeklyInfo(weeklyList);
	}

	@Override
	public List<WeeklyList> listWeeklistByEid(WeeklyList weeklyList) {
		return  weeklyListDao.listWeeklistByEid(weeklyList);
	}

	@Override
	public void updateWeeklyListBatch(WeeklyList weeklyList) {
		weeklyListDao.updateWeeklyListBatch(weeklyList);
	}
}
