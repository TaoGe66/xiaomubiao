package com.qixin.teamwork.biz.census.svc;

import java.util.List;

import com.qixin.teamwork.biz.census.model.WeeklyRecord;

/**
 * 周报清单记录接口层
 *@author wuchao
 *@data 2017年10月30日
 *@version V0.0.5
 */
public interface WeeklyRecordSvc {
	
	/**
	 * 保存周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	public void saveWeeklyRecord(WeeklyRecord weeklyRecord);
	
	/**
	 * 删除周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	public void deleteWeeklyRecord(WeeklyRecord weeklyRecord);
	
	/**
	 * 周报清单列表
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 * @return
	 */
	public List<WeeklyRecord> listWeeklyRecord(WeeklyRecord weeklyRecord);

}
