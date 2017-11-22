package com.qixin.teamwork.biz.census.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.census.model.WeeklyRecord;

/**
 * 周报清单记录DAO
 *@author wuchao
 *@data 2017年10月30日
 *@version V0.0.5
 */
@Repository
public class WeeklyRecordDao extends BaseDao{

	/**
	 * 保存周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	public void saveWeeklyRecord(WeeklyRecord weeklyRecord) {
		insert("com.qixin.teamwork.biz.census.dao.WeeklyRecordDao.insert", weeklyRecord);
	}

	/**
	 * 删除周报清单
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 */
	public void deleteWeeklyRecord(WeeklyRecord weeklyRecord) {
		delete("com.qixin.teamwork.biz.census.dao.WeeklyRecordDao.delete", weeklyRecord);
	}

	/**
	 * 周报清单列表
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param weeklyRecord
	 * @return
	 */
	public List<WeeklyRecord> listWeeklyRecord(WeeklyRecord weeklyRecord) {
		return queryForList("com.qixin.teamwork.biz.census.dao.WeeklyRecordDao.listWeeklyRecord", weeklyRecord);
	}

}
