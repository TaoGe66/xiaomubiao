package com.qixin.teamwork.biz.comment.svc.impl;

import com.qixin.teamwork.biz.comment.dao.CommentDao;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.comment.svc.CommentSvc;
import com.qixin.teamwork.biz.dto.CommentDto;
import com.qixin.teamwork.biz.envet.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 10:28
 * version:V0.0.1
 */
@Service("commentSvc")
public class CommentSvcImpl implements CommentSvc {

    @Autowired
    private CommentDao commentDao;


    /**
     * 保存用户评论记录
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/6/13 14:00
     */
    public Map<String ,Object> insert(Comment comment){
        commentDao.insert(comment);
        Map<String,Object> map=new HashMap<String, Object>();
        String msg=comment.getId()>0?"success":"error";
        map.put("msg",msg);
        return map;
    }
    /**
     *  根据事件id获取事件下的评论列表
     * @author xiehuilin
     * @param  comment 评论
     * @return List<Comment>  事件评论列表
     * @version V0.0.1
     * @date 2017/6/13 10:45
     */
    @Override
    public List<CommentDto> listCommentByEid(Comment comment) {
        return commentDao.listCommentByEid(comment);
    }
}
