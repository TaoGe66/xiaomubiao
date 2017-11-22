package com.qixin.teamwork.biz.census.svc.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.WeeklyRecordDao;
import com.qixin.teamwork.biz.census.model.WeeklyRecord;
import com.qixin.teamwork.biz.census.svc.WeeklyRecordSvc;

/**
 * 周报清单记录实现层
 *@author wuchao
 *@data 2017年10月30日
 *@version V0.0.5
 */
@Service("weeklyRecordSvc")
public class WeeklyRecordSvcImpl implements WeeklyRecordSvc{

	@Resource
	private WeeklyRecordDao weeklyRecordDao; 
	
	/**
	 * 保存周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	@Override
	public void saveWeeklyRecord(WeeklyRecord weeklyRecord) {
		weeklyRecordDao.saveWeeklyRecord(weeklyRecord);
	}

	/**
	 * 删除周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	@Override
	public void deleteWeeklyRecord(WeeklyRecord weeklyRecord) {
		// 根据日期获取其下周一的零点零分
		Long sTime = ToDoRemindUtils.getNowWeekMonday(weeklyRecord.getStartTime()).getTime();
		sTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(sTime)).getTime();

		Long monday = ToDoRemindUtils.getDayWeeksYTD(1, weeklyRecord.getStartTime()).getTime();
		monday = ToDoRemindUtils.getWeeHoursTime(new Timestamp(monday)).getTime();
		weeklyRecord.setStartTime(new Timestamp(sTime));
		weeklyRecord.setMondayTime(new Timestamp(monday));
		weeklyRecordDao.deleteWeeklyRecord(weeklyRecord);
	}

	/**
	 * 周报清单列表
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 * @return
	 */
	@Override
	public List<WeeklyRecord> listWeeklyRecord(WeeklyRecord weeklyRecord) {
		// 根据日期获取其下周一的零点零分
		Long sTime = ToDoRemindUtils.getNowWeekMonday(weeklyRecord.getStartTime()).getTime();
		sTime = ToDoRemindUtils.getWeeHoursTime(new Timestamp(sTime)).getTime();

		Long monday = ToDoRemindUtils.getDayWeeksYTD(1, weeklyRecord.getStartTime()).getTime();
		monday = ToDoRemindUtils.getWeeHoursTime(new Timestamp(monday)).getTime();
		weeklyRecord.setStartTime(new Timestamp(sTime));
		weeklyRecord.setMondayTime(new Timestamp(monday));
		return weeklyRecordDao.listWeeklyRecord(weeklyRecord);
	}

}
