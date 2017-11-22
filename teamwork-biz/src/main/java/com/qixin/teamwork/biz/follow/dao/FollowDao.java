package com.qixin.teamwork.biz.follow.dao;

import com.qixin.teamwork.biz.follow.model.Follow; 
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 16:02
 * version:V0.0.1
 */
@Repository
public class FollowDao extends BaseDao {
    /**
     *  根据轻企id和用户id获取关注我成员列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:39
     */
    public List<Follow> listGetToMyFollow(Follow follow) {
        return this.queryForList("com.qixin.teamwork.biz.follow.dao.FollowDao.listGetToMyFollow",follow);
    }

    /**
     * 更新关注状态
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:40
     */

    public void update(Follow follow) {
        this.update("com.qixin.teamwork.biz.follow.dao.FollowDao.update",follow);
    }
    
    /**
     * 更新关注状态
     * @author zyting
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:40
     */
    public void updateDel(Follow follow) {
        this.update("com.qixin.teamwork.biz.follow.dao.FollowDao.updateDel",follow);
    }
    

    public  List<Follow>  listGetMyToFollow(Follow follow){
        return this.queryForList("com.qixin.teamwork.biz.follow.dao.FollowDao.listGetMyToFollow",follow);
    }
    /** 
    * 根据轻企id、用户id、项目创建人或项目责任id获取用户是否关注项目创建人或项目责任人
    * @author xiehuilin    
    * @param  
    * @return  
    * @version V0.0.3
    * @date 2017/9/13 10:38
    */
    public  Follow  getFollowInfo(Follow follow){
        return (Follow) this.queryForObject("com.qixin.teamwork.biz.follow.dao.FollowDao.getFollowInfo",follow);

    }
    /**
    * 保存关注关系
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.3
    * @date 2017/9/15 16:28
    */
    public  void  insert(Follow follow){
        this.insert("com.qixin.teamwork.biz.follow.dao.FollowDao.insert",follow);
    }

    /**
     * 根据轻企id和用户id更新是否已阅状态
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/15 16:28
     */
    public void  updateRead(Follow follow){
        this.update("com.qixin.teamwork.biz.follow.dao.FollowDao.updateRead",follow);
    }

    /**
     * 根据用户id和轻企id获取是否有新关注
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/15 16:28
     */
    public Follow  getNowFollow(Follow follow){
        return (Follow) this.queryForObject("com.qixin.teamwork.biz.follow.dao.FollowDao.getNowFollow",follow);
    }

}
