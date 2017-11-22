package com.qixin.teamwork.biz.follow.svc;

import com.qixin.teamwork.biz.follow.model.Follow;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.List;
import java.util.Map;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 13:49
 * version:V0.0.1
 */
public interface FollowSvc {
    /**
    *  根据轻企id和用户id获取关注我成员列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/7 15:39
    */
    List<Follow>  listGetToMyFollow(Follow follow);

    /**
    * 更新关注状态
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/7 15:40
    */
    void update(Follow follow);

    /**
    *  根据轻企id和用户id获取我关注的成员列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/8 10:27
    */
    List<Follow> listGetMyToFollow(Follow follow);

    /**
     * 根据轻企id、用户id、项目创建人或项目责任id获取用户是否关注项目创建人或项目责任人
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/13 10:38
     */
    Follow  getFollowInfo(Follow follow);
    /**
     * 保存关注关系
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/15 16:28
     */
    void  insert(Follow follow);

    /**
     * 根据用户id和轻企id获取是否有新关注
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/15 16:28
     */
    Follow  getNowFollow(Follow follow);

    /**
     * 根据轻企id和用户id更新是否已阅状态
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.3
     * @date 2017/9/15 16:28
     */
     void  updateRead(Follow follow);
}
