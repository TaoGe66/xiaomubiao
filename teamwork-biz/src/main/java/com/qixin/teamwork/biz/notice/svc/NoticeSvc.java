package com.qixin.teamwork.biz.notice.svc;

import com.qixin.teamwork.biz.notice.model.Notice;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/6 16:19
 * version:V0.0.1
 */
public interface NoticeSvc {
    /** 
    * 根据轻企id获取通知列表
    * @author xiehuilin    
    * @param  
    * @return  
    * @version V0.0.1
    * @date 2017/9/6 16:21
    */
    List<Notice> listGetNotice(Notice notice);
    /**
    *  根据通知id获取通知详情
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/6 16:22
    */
    Notice getNoticeInfo(Notice notice);
    
    /**
     *  新增通知
     * @author zyting
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/6 16:22
     */
    void insert(Notice notice);
}
