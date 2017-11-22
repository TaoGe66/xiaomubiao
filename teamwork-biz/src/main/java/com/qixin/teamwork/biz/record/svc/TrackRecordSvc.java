package com.qixin.teamwork.biz.record.svc;

import com.qixin.teamwork.biz.record.model.TrackRecord;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/8/9 11:32
 * version:V0.0.1
 */
public interface TrackRecordSvc {
    /**
    *  保存跟踪记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 11:36
    */
    void  insert(TrackRecord record);
    /**
    * 获取事项跟踪记录列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 11:37
    */
    List<TrackRecord> listTrackRecord(TrackRecord record);

    void insertTrackRecord(TrackRecord record);

    /**
     * 将项目下用户行动跟踪记录未阅的记录更新为已阅
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/8/15 15:43
     */
    void updateTrackRecord(TrackRecord record);
    
    /**
     * 根据周期模板id查询周期跟踪列表
     *@author wuchao
     *@data 2017年10月26日
     *@version V0.0.5
     * @param record
     * @return
     */
    public List<TrackRecord> listCycRecord(TrackRecord record);
    /**
     *@Description:撤销跟踪记录
     *@author:xiehuilin
     *@param:record
     *@return:void
     *@date:2017/10/27 11:55
     *@version V 0.0.7
     */
    void updateRevokeTrackRecord(TrackRecord record);
}
