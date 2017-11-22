package com.qixin.teamwork.biz.record.svc.impl;

import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import com.qixin.teamwork.biz.census.dao.WeeklyListDao;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.svc.WeeklyListSvc;
import com.qixin.teamwork.biz.enterprise.model.Dynamic;
import com.qixin.teamwork.biz.enterprise.svc.DynamicSvc;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.record.dao.ReplyDao;
import com.qixin.teamwork.biz.record.dao.TrackRecordDao;
import com.qixin.teamwork.biz.record.model.Reply;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.svc.TodoSvc;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/8/9 11:38
 * version:V0.0.1
 */
@Service("trackRecordSvc")
public class TrackRecordSvcImpl  implements TrackRecordSvc {
    @Autowired
    private TrackRecordDao trackRecordDao;
    @Autowired
    private EventItemSvc eventItemSvc;
    @Autowired
    private TodoSvc todoSvc;
    @Autowired
    private EventSvc eventSvc;
    @Autowired
    private DynamicSvc dynamicSvc;
    @Autowired
    private WeeklyListSvc weeklyListSvc;
    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private  WeeklyListDao weeklyListDao;
    /**
    * 1.保存行动跟踪记录
    * 2.查询该项目下该行动的待办记录
    * 3.更新该行动待办记录
    * 4.生成下一条行动记录待办
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 16:03
    */
    @Override
    public void insert(TrackRecord record) {
        /* Todo todo=new Todo();
        todo.seteId(record.geteId());
        todo.setEiId(record.getEiId());
        todo.setUserId(record.getCreateBy());
        todo.settSubjType(new Byte("1"));
        todo.settState(new Byte("10"));
        todo.setIsFinish(new Byte("0"));
        Todo relust= todoSvc.getTodoInfoRecord(todo);
        if(null!=relust){
            Timestamp pushTime = relust.getPushTime();
            Timestamp finishTime = relust.getFinishTime();
            //推送时间
            Date pDate = DateUtils.getTimestampToDate(pushTime, Constant.DEFAULT_DATE_FORMAT_PATTERN);
            //当前时间
            Date cDate=DateUtils.getTimestampToDate(new Timestamp(new Date().getTime()), Constant.DEFAULT_DATE_FORMAT_PATTERN);
            //行动截至时间,按天算
            Date  eDate=DateUtils.getTimestampToDate(finishTime, Constant.DEFAULT_DATE_FORMAT_PATTERN);
            //行动截止时间,减一天后大于当前时间生成行动待办否则不生成
            eDate= ToDoRemindUtils.getToDoRemindTime(new Timestamp(eDate.getTime()), -24 * 60);
            //add by xiehuilin 20170728 推送时间小于等于当前时间
            if(pDate.compareTo(cDate)<=0){
                relust.setIsFinish(new Byte("1"));
                relust.setFinishTime(StrUtils.getNowTimestamp());
                todoSvc.updateTodo(relust);
                //生成新的行动待办
                if(eDate.compareTo(pDate)>0){
                    relust.setPushTime(ToDoRemindUtils.getToDoRemindTime(StrUtils.getNowTimestamp(),24*60));
                    relust.setFinishTime(finishTime);
                    todoSvc.saveTodo(relust);
                }
            }
        }*/

       /* EventItem eventItem=new EventItem();
        eventItem.setEiId(record.getEiId());
        EventItem info = eventItemSvc.getByEiIdInfo(eventItem);
        List<TrackRecord> trackRecords = trackRecordDao.listTrackRecord(record);
        if(null==trackRecords||trackRecords.size()<=0&&info.getIsCycle()==1){
            eventItem.setIsCycleEdit(new Byte("0"));
            eventItemSvc.updateCycleEdit(eventItem);
        }*/
        Event event=new Event();
        event.seteId(record.geteId());
        Event envetInfo = eventSvc.getEnvetInfo(event);
        record.setTrType(new Byte("0"));
        trackRecordDao.insert(record);
        if(envetInfo.getLeId()!=null){
            //add by xiehuilin 20170914 项目完成生成动态记录
            Dynamic dynamic=new Dynamic();
            dynamic.seteId(record.geteId());
            dynamic.setLeId(envetInfo.getLeId());
            dynamic.setUserId(record.getCreateBy());
            StringBuilder builder=new StringBuilder().append("跟踪了一项行动");
            dynamic.setTitle(builder.toString());
            dynamic.setCreateBy(record.getCreateBy());
            dynamic.setState(new Byte("3"));
            dynamic.setEiId(record.getEiId());
            dynamic.setContent(record.getContent());
            dynamicSvc.saveDynamic(dynamic);
            //生成工作清单
            WeeklyList weeklyList=new WeeklyList();
            weeklyList.setTrContent(record.getContent());
            weeklyList.seteId(envetInfo.geteId());
            weeklyList.setLeId(envetInfo.getLeId());
            weeklyList.setUserId(record.getCreateBy());
            weeklyList.setCreateBy(record.getCreateBy());
            weeklyList.setEiId(record.getEiId());
            weeklyList.setTrackRecordId(record.getId());
            weeklyListSvc.createWeekly(weeklyList,1);
        }

    }

    @Override
    public List<TrackRecord> listTrackRecord(TrackRecord record) {
        return trackRecordDao.listTrackRecord(record);
    }

	@Override
	public void insertTrackRecord(TrackRecord record) {
		trackRecordDao.insert(record);
	}
    /**
    * 将项目下用户行动跟踪记录未阅的记录更新为已阅
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/15 15:50
    */
    @Override
    public void updateTrackRecord(TrackRecord record) {
        trackRecordDao.updateTrackRecord(record);
    }

    /**
     * 根据周期模板id查询周期跟踪列表
     *@author wuchao
     *@data 2017年10月26日
     *@version V0.0.5
     * @param record
     * @return
     */
	@Override
	public List<TrackRecord> listCycRecord(TrackRecord record) {
		return trackRecordDao.listCycRecord(record);
	}

    /**
     *@Description:跟踪撤销
     * 1:五分钟内有效
     * 2：撤销跟踪回复同时失效
     * 3：校验是否是轻企项目
     *   是:将该行动下的跟踪置为无效
     *   否：略过
     *@author:xiehuilin
     *@param:request
     *@param:record
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/27 11:46
     *@version V 0.0.7
     */
    @Override
    public void updateRevokeTrackRecord(TrackRecord record) {
        //更新跟踪记录
        record.setIsValid(new Byte("0"));
        trackRecordDao.updateTrackRecord(record);
        //批量更新回复信息
        Reply reply=new Reply();
        reply.setTrId(record.getId());
        reply.setIsValid(new Byte("0"));
        replyDao.updateBacth(reply);
        //校验是否是轻企项目
        Event event=new Event();
        event.seteId(record.geteId());
        Event envetInfo = eventSvc.getEnvetInfo(event);
        if(null!=envetInfo.getLeId()){
        //根据行动id和清单类型
         WeeklyList weeklyList=new WeeklyList();
         weeklyList.setEiId(record.getEiId());
         weeklyList.setType(new Byte("2"));
         List<WeeklyList> weeklyLists = weeklyListSvc.listWeeklistByEid(weeklyList);
         //更新当前撤销跟踪的记录
         if(!weeklyLists.isEmpty()&&weeklyLists.size()>=2){
             weeklyListDao.updateWeeklyList(weeklyList);
         }else{
         //更新当前撤销的跟踪记录及事件事项记录
             weeklyList.setIsValid(new Byte("0"));
            weeklyListDao.updateWeeklyListBatch(weeklyList);
         }
        }
    }
}

