package com.qixin.teamwork.biz.notice.svc.impl;

import com.qixin.teamwork.biz.notice.dao.NoticeDao;
import com.qixin.teamwork.biz.notice.model.Notice;
import com.qixin.teamwork.biz.notice.svc.NoticeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/6 16:22
 * version:V0.0.1
 */
@Service("noticeSvc")
public class NoticeSvcImpl implements NoticeSvc {
    @Autowired
    private NoticeDao noticeDao;
    /**
     * 根据轻企id获取通知列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/6 16:21
     */
    @Override
    public List<Notice> listGetNotice(Notice notice) {
        return noticeDao.listGetNotice(notice);
    }
    /**
     *  根据通知id获取通知详情
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/6 16:22
     */
    @Override
    public Notice getNoticeInfo(Notice notice) {
        return noticeDao.getNoticeInfo(notice);
    }
    
	@Override
	public void insert(Notice notice) {
		noticeDao.insert(notice);
	}
}
