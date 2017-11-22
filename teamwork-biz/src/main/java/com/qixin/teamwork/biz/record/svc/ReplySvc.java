package com.qixin.teamwork.biz.record.svc;

import com.qixin.teamwork.biz.record.model.Reply;

import java.util.List;

/**
 * 行动跟踪回复服务
 * @Description:
 * @author: xiehuilin
 * @date:2017/10/25 16:37
 * @version V0.0.7
 */
public interface ReplySvc {
    /**
     * 保存行动跟踪回复记录
     * @param reply
     */
    void  insert(Reply reply);
    /**
     *@Description:根据跟踪id批量更新回复记录
     *@author:xiehuilin
     *@param:reply
     *@return:void
     *@date:2017/10/27 12:16
     *@version V 0.0.7
     */
      void  updateBacth(Reply reply);
    /**
     *@Description:根据跟踪记录id获取留言列表
     *@author:xiehuilin
     *@param:reply
     *@return:java.util.List<com.qixin.teamwork.biz.record.model.Reply>
     *@date:2017/10/31 15:35
     *@version V 0.0.7
     */
     List<Reply> listGetReply(Reply reply);
}
