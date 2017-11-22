package com.qixin.teamwork.biz.record.dao;

import com.qixin.teamwork.biz.record.model.TrackRecord;
import org.framework.iInterface.dao.BaseDao;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/8/9 11:38
 * version:V0.0.1
 */
@Repository
public class TrackRecordDao extends BaseDao {
    /**
    * 保存跟踪记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 11:47
    */
    public  void  insert(TrackRecord record){
        this.insert("com.qixin.teamwork.biz.record.dao.TrackRecordDao.insert",record);
    }
    /**
    * 获取事项下的跟踪记录列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 11:49
    */
    public List<TrackRecord> listTrackRecord(TrackRecord record){
      return  this.queryForList("com.qixin.teamwork.biz.record.dao.TrackRecordDao.listTrackRecord",record);
    }
    /**
    * 将项目下用户行动跟踪记录未阅的记录更新为已阅
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/15 15:43
    */
    public  void updateTrackRecord(TrackRecord record){
        this.update("com.qixin.teamwork.biz.record.dao.TrackRecordDao.updateTrackRecord",record);
    }
    
    /**
     * 根据周期模板id查询周期跟踪列表
     *@author wuchao
     *@data 2017年10月26日
     *@version V0.0.5
     * @param record
     * @return
     */
	public List<TrackRecord> listCycRecord(TrackRecord record) {
		return queryForList("com.qixin.teamwork.biz.record.dao.TrackRecordDao.listCycRecord", record);
	}

	

}
