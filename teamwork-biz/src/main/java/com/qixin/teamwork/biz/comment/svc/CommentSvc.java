package com.qixin.teamwork.biz.comment.svc;

import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.dto.CommentDto;
import com.qixin.teamwork.biz.envet.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 10:27
 * version:V0.0.1
 */
public interface CommentSvc {
    /**
     * 保存用户评论记录
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/6/13 14:00
     */
      Map<String,Object> insert(Comment comment);

    /**
     *  根据事件id获取事件下的评论列表
     * @author xiehuilin
     * @param  comment 评论
     * @return List<Comment>  事件评论列表
     * @version V0.0.1
     * @date 2017/6/13 10:45
     */
    List<CommentDto>  listCommentByEid(Comment comment);
}
