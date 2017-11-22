package com.qixin.teamwork.controller.record;

import com.qixin.teamwork.biz.envet.model.EventItem; 
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.record.model.Reply;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.ReplySvc;
import com.qixin.teamwork.biz.record.svc.TrackRecordSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事项跟踪记录服务api
 * Created by
 * Author:xiehuilin
 * Date:2017/8/9 15:55
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/track")
public class TrackRecordConterlloer {
    @Resource
    private LogWriter log;
    @Autowired
    private TrackRecordSvc trackRecordSvc;
    @Autowired
    private EventItemSvc eventItemSvc;
    @Autowired
    private ReplySvc replySvc;
    /**
    * 保存事项跟踪记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/8/9 15:56
    */
    @RequestMapping(path ="/save",method = RequestMethod.POST)
    public Map<String,Object> updateItem(HttpServletRequest request, TrackRecord record){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(record.getEiId() !=null){
            //根据行动id获取任务详情
            EventItem item=new EventItem();
            item.setEiId(record.getEiId());
            EventItem itemInfo = eventItemSvc.getItemInfo(item);
            record.seteId(itemInfo.geteId());
            trackRecordSvc.insert(record);
            msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "保存事项跟踪记录", 0);
        resMap.put("msg", msg);
        return resMap;
    }

    /**
     *@Description:跟踪撤销
     * 1:十分钟内有效
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
    @RequestMapping(path ="/revokeTrackRecord",method = RequestMethod.POST)
    public  Map<String,Object> revokeTrackRecord(HttpServletRequest request,TrackRecord record){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg=StrUtils.isNull(record.getId());
               msg=StrUtils.isNull(record.getEiId());
        if(!StrUtils.isEmpty(record.getId())&&!StrUtils.isEmpty(record.getId())){
            //根据行动id获取行动详情
            EventItem eventItem=new EventItem();
            eventItem.setEiId(record.getEiId());
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            record.seteId(itemInfo.geteId());
            trackRecordSvc.updateRevokeTrackRecord(record);
        }
        resMap.put("msg",msg);
        log.saveSystemLog(request,"", "",beginDate, "", "行动详情撤销跟踪记录", 0);
        return  resMap;
    }


    /**
     *@Description:根据行动id获取跟踪记录
     *@author:xiehuilin
     *@param:request
     *@param:record
     *@param:parameter
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/11/2 14:56
     *@version V 0.0.7
     */
    @RequestMapping(path ="/trackRecordList",method = RequestMethod.GET)
    public  Map<String,Object> trackRecordList(HttpServletRequest request, TrackRecord record, PaginationParameter parameter){
            Integer isEventDuty = 0;
            Date beginDate=new Date();
            Map<String,Object> reslutMap=new HashMap<String, Object>();
        List<TrackRecord> trackRecords=null;
            String msg=StrUtils.isNull(record.getEiId());
            if(!StrUtils.isEmpty(record.getEiId())){
                record.setPaper(parameter);
                trackRecords = trackRecordSvc.listTrackRecord(record);
                //获取跟踪记录留言列表
                if(!trackRecords.isEmpty()){
                    for(TrackRecord tr:trackRecords){
                        tr.setStrCreateTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(tr.getCreateTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                        Reply reply=new Reply();
                        reply.setTrId(tr.getId());
                        List<Reply> replies = replySvc.listGetReply(reply);
                        if(!replies.isEmpty()){
                            for (Reply rp:replies){
                                rp.setStrCreteTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(rp.getCreateTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
                            }
                        }
                        tr.setReplyList(replies);
                    }
                }

            }
        reslutMap.put("msg",msg);
        reslutMap.put("trackRecords",trackRecords);
        log.saveSystemLog(request,"", "",beginDate, "", "获取行动跟踪列表", 0);
        return  reslutMap;
    }
    
    /**
     * 编辑时校验是否有跟踪
     *@author wuchao
     *@data 2017年11月3日
     *@version V0.0.5
     * @param request
     * @param record
     * @return
     */
    @RequestMapping(path="/checkFollow",method = RequestMethod.GET)
   	public Map<String,Object> checkEvent(HttpServletRequest request, TrackRecord record){
  		Date beginDate=new Date();
  		Map<String,Object> reslutMap=new HashMap<String, Object>();
  		String msg="error";
  		int count=0;//0是无  1是有
  		if(record.getCtId() !=null ){
  			List<TrackRecord> recordList=trackRecordSvc.listCycRecord(record);
  			if (recordList.size()>0) {
  				 count=1;
			}
  		}else{
  			List<TrackRecord> rdList=trackRecordSvc.listTrackRecord(record);
  			if (rdList.size()>0) {
  				count=1;
			}
  		}
  		reslutMap.put("count", count);
  		reslutMap.put("msg", msg);
  		log.saveSystemLog(request,beginDate,"", "编辑时校验是否有跟踪", 0);
  		return reslutMap;
  	}

}
