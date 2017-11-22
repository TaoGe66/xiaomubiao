package com.qixin.teamwork.controller.record;

import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.record.model.Reply;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.record.svc.ReplySvc;
import org.framework.iInterface.logs.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 行动跟踪回复服务接口
 * @author: xiehuilin
 * @date:2017/10/25 16:59
 * @version V0.0.7
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/reply")
public class ReplyConterlloer {

    @Autowired
    private LogWriter log;
    @Autowired
    private ReplySvc replySvc;
    @Autowired
    private EventItemSvc eventItemSvc;
    /**
     *@Description:保存行动跟踪回复记录
     *@author:xiehuilin
     *@param:request
     *@param:reply 回复实体
     *@return:java.util.Map<java.lang.String,java.lang.Object>
     *@date:2017/10/25 17:03
     *@version V 0.0.7
     */
    @RequestMapping(path ="/save",method = RequestMethod.POST)
    public Map<String,Object> save(HttpServletRequest request, Reply reply){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg="error";
        if(reply.getTrId() !=null){
            //根据行动id获取任务详情
            EventItem item=new EventItem();
            item.setEiId(reply.getEiId());
            EventItem itemInfo = eventItemSvc.getItemInfo(item);
            reply.setCreateBy(reply.getUserId());
            reply.seteId(itemInfo.geteId());
            replySvc.insert(reply);
            msg="success";
        }
        log.saveSystemLog(request,"", "",beginDate, "", "保存跟踪回复记录", 0);
        resMap.put("msg", msg);
        return resMap;
    }
}
