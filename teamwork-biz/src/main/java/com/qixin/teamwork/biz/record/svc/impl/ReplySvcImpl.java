package com.qixin.teamwork.biz.record.svc.impl;

import com.qixin.teamwork.biz.record.dao.ReplyDao;
import com.qixin.teamwork.biz.record.model.Reply;
import com.qixin.teamwork.biz.record.svc.ReplySvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *行动跟踪回复服务
 * @Description:
 * @author: xiehuilin
 * @date:2017/10/25 16:38
 * @version V0.0.7
 */
@Service("replySvc")
public class ReplySvcImpl implements ReplySvc{
    @Autowired
    private ReplyDao replyDao;
    @Override
    public void insert(Reply reply) {
        replyDao.insert(reply);
    }

    @Override
    public void updateBacth(Reply reply) {
        replyDao.updateBacth(reply);
    }

    @Override
    public List<Reply> listGetReply(Reply reply) {
        return replyDao.listGetReply(reply);
    }
}
