package com.qixin.teamwork.biz.record.dao;

import com.qixin.teamwork.biz.record.model.Reply;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Description: {此类功能描述}
 * @author: xiehuilin
 * @date:2017/10/25 16:40
 * @version V0.0.7
 */
@Repository
public class ReplyDao extends BaseDao {
    /**
     *@Description:{方法的功能/动作描述}
     *@author:xiehuilin
     *@param:reply
     *@return:void
     *@date:2017/10/25 16:41
     *@version V 0.0.7
     */
    public  void  insert(Reply reply){
        this.insert("com.qixin.teamwork.biz.record.dao.ReplyDao.insert",reply);
    }
    /**
     *@Description:根据跟踪id批量更新回复记录
     *@author:xiehuilin
     *@param:reply
     *@return:void
     *@date:2017/10/27 12:16
     *@version V 0.0.7
     */
    public  void  updateBacth(Reply reply){
        this.update("com.qixin.teamwork.biz.record.dao.ReplyDao.updateBacth",reply);
    }
    /**
     *@Description:根据跟踪记录id获取留言列表
     *@author:xiehuilin
     *@param:reply
     *@return:java.util.List<com.qixin.teamwork.biz.record.model.Reply>
     *@date:2017/10/31 15:35
     *@version V 0.0.7
     */
    public List<Reply> listGetReply(Reply reply){
        return  this.queryForList("com.qixin.teamwork.biz.record.dao.ReplyDao.listGetReply",reply);
    }
}
