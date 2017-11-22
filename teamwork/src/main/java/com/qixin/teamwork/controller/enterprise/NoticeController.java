package com.qixin.teamwork.controller.enterprise;

import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.notice.model.Notice;
import com.qixin.teamwork.biz.notice.svc.NoticeSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 轻企通知服务Api
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 11:21
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeSvc noticeSvc;
    @Autowired
    private LogWriter logWriter;

    /**
    * 根据轻企id获取通知列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/7 11:42
    */
    @RequestMapping(path ="/listGetNotice",method = RequestMethod.GET)
    public Map<String,Object> listGetNotice(HttpServletRequest request,Notice notice,PaginationParameter parameter){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg=!StrUtils.isEmpty(notice.getLeId())?"success":"error";
        String errorCode=!StrUtils.isEmpty(notice.getLeId())?"": APIErrorMap.errorMap.get("17");
        List<Notice> notices=new ArrayList<Notice>();
        if(!StrUtils.isEmpty(notice.getLeId())){
            notice.setPaper(parameter);
            notices= noticeSvc.listGetNotice(notice);
            for(int x=0;x<notices.size();x++){
                notices.get(x).setPushTime(DateUtils.getDateDetail(DateUtils.getTimestampToString(  notices.get(x).getCreateTime(), Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN)));
            }
            resMap.put("noticeList",notices);
        }
        resMap.put("msg",msg);
        resMap.put("errorCode",errorCode);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "轻企获取通知列表", notices.size());
        return  resMap;
    }
    /**
    * 根据通知di获取详情
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/7 11:43
    */
    @RequestMapping(path ="/getNoticeInfo",method = RequestMethod.GET)
    public Map<String,Object> getNoticeInfo(HttpServletRequest request,Notice notice){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg=!StrUtils.isEmpty(notice.getLeId())?"success":"error";
        String errorCode=!StrUtils.isEmpty(notice.getLeId())?"": APIErrorMap.errorMap.get("17");
        if(!StrUtils.isEmpty(notice.getLeId())){
            resMap.put("noticet",noticeSvc.getNoticeInfo(notice));
        }
        resMap.put("msg",msg);
        resMap.put("errorCode",errorCode);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取详情",0);
        return  resMap;
    }
    
    /**
     * 保存通知
     * @author zyting
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 11:43
     */
     @RequestMapping(path ="/saveNoticeInfo",method = RequestMethod.POST)
     public Map<String,Object> saveNoticeInfo(HttpServletRequest request,Notice notice){
         Map<String,Object> resMap=new HashMap<String, Object>();
         Date beginDate=new Date();
         String msg=!StrUtils.isEmpty(notice.getLeId())?"success":"error";
         if(!StrUtils.isEmpty(notice.getLeId())){
            noticeSvc.insert(notice);;
         }
         resMap.put("msg",msg);
         logWriter.saveSystemLog(request,"", "",beginDate, "", "保存通知",0);
         return  resMap;
     }
}
