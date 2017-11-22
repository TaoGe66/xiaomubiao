package com.qixin.teamwork.biz.comment.dao;

import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.dto.CommentDto;
import com.qixin.teamwork.biz.envet.model.Event;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 评论dao
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 10:28
 * version:V0.0.1
 */
@Repository
public class CommentDao extends BaseDao {
    /**
    * 保存用户评论记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/6/13 14:00
    */
    public  void insert(Comment comment){
        this.insert("com.qixin.teamwork.biz.comment.dao.CommentDao.insert",comment);
    }


    /**
     *  根据事件id获取事件下的评论列表
     * @author xiehuilin
     * @param  comment 事件
     * @return List<Comment>  事件评论列表
     * @version V0.0.1
     * @date 2017/6/13 10:45
     */
    public List<CommentDto> listCommentByEid(Comment comment) {
        return this.queryForList("com.qixin.teamwork.biz.comment.dao.CommentDao.listCommentByEid",comment);
    }
    
    /**
     *  根据事件id获取事件下的评论列表
     * @author xiehuilin
     * @param  comment 事件
     * @return List<Comment>  事件评论列表
     * @version V0.0.1
     * @date 2017/6/13 10:45
     */
    public List<CommentDto> listCoyEid(Comment comment) {
        return this.queryForList("com.qixin.teamwork.biz.comment.dao.CommentDao.listCoyEid",comment);
    }

}
