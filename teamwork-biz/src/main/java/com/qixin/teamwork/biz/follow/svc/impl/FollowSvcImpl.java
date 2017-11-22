package com.qixin.teamwork.biz.follow.svc.impl;

import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterprise.svc.EnterpriseSvc;
import com.qixin.teamwork.biz.follow.dao.FollowDao;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.follow.svc.FollowSvc;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.news.svc.NewsSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 13:50
 * version:V0.0.1
 */
@Service("followSvc")
public class FollowSvcImpl implements FollowSvc {
    @Autowired
    private FollowDao followDao;
    @Autowired
    private WxuserSvc wxuserSvc;
    @Autowired
    private NewsSvc newsSvc;
    @Autowired
    private EnterpriseSvc enterpriseSvc;
    /**
     *  根据轻企id和用户id获取关注我成员列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:39
     */
    @Override
    public List<Follow> listGetToMyFollow(Follow follow) {
        return followDao.listGetToMyFollow(follow);
    }

    /**
     * 更新关注状态
     *   为用户生成消息通知
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:40
     */

    @Override
    public void update(Follow follow) {
        //获取用户信息
        User user=new User();
        user.setUserId(follow.getCreateBy());
        User userInfo = wxuserSvc.getUserInfo(user);
        //生成消息信息
        News news=new News();
        news.setCreateBy(follow.getCreateBy());
        news.setSendId(follow.getUserId());
        news.setIsRed(new Byte("0"));
        news.setnType(new Byte("2"));
        news.setnTime(StrUtils.getNowTimestamp());
        StringBuilder builder=new StringBuilder();
        switch (follow.getState()){
            case 0:
                builder.append(userInfo.getUserName()).append("拒绝您的关注");
                break;
            case 1:
                builder.append("您已关注").append(userInfo.getUserName()).append("，可以在轻企查看他（她）的动态");
                break;
            case 3:
                builder.append(userInfo.getUserName()).append("已取消对您的关注");
                break;
        }
        news.setnContent(builder.toString());
        //根据轻企id获取轻企详情,联调从前端取值
        Enterprise info = enterpriseSvc.getEnterpriseInfo(follow.getLeId());
        news.setnTitle("轻企名称："+info.getName());
        news.setLeId(follow.getLeId());
        //保存消息记录
        newsSvc.insert(news);
        //更新关注状态
        followDao.update(follow);

    }
    /**
     *  根据轻企id和用户id获取我关注的成员列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/8 10:27
     */
    @Override
    public List<Follow> listGetMyToFollow(Follow follow) {
        return followDao.listGetMyToFollow(follow);
    }


    /**
     * 根据轻企id、用户id、项目创建人或项目责任id获取用户是否关注项目创建人或项目责任人
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/13 10:38
     */
    @Override
    public Follow  getFollowInfo(Follow follow){
        return  followDao.getFollowInfo(follow);
    }

    @Override
    public void insert(Follow follow) {
        followDao.insert(follow);
    }

    @Override
    public void updateRead(Follow follow) {
        followDao.updateRead(follow);
    }

    @Override
    public Follow getNowFollow(Follow follow) {
        return followDao.getNowFollow(follow);
    }
}
