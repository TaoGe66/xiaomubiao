package com.qixin.teamwork.biz.notice.dao;

import com.qixin.teamwork.biz.notice.model.Notice;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/6 16:25
 * version:V0.0.1
 */
@Repository
public class NoticeDao extends BaseDao {
    /**
     * 根据轻企id获取通知列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/6 16:21
     */
    public List<Notice> listGetNotice(Notice notice) {
        return this.queryForList("com.qixin.teamwork.biz.notice.dao.NoticeDao.listGetNotice",notice);
    }
    /**
     *  根据通知id获取通知详情
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/6 16:22
     */
    public Notice getNoticeInfo(Notice notice) {
        return (Notice) this.queryForObject("com.qixin.teamwork.biz.notice.dao.NoticeDao.getNoticeInfo",notice);
    }
    
    public void insert(Notice notice){
    	this.insert("com.qixin.teamwork.biz.notice.dao.NoticeDao.insert", notice);
    }


}
