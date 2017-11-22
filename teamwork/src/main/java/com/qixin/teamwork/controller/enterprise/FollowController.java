package com.qixin.teamwork.controller.enterprise;

import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.follow.svc.FollowSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 轻企关注服务接口api
 * Created by
 * Author:xiehuilin
 * Date:2017/9/7 18:39
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowSvc followSvc;
    @Autowired
    private LogWriter logWriter;

    /**
     *  根据轻企id和用户id获取关注我成员列表
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/7 15:39
     */
    @RequestMapping(path ="/listGetFollow",method = RequestMethod.GET)
    public Map<String,Object> listGetFollow(Follow follow, HttpServletRequest request,PaginationParameter parameter){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=!StrUtils.isEmpty(follow.getLeId())&&!StrUtils.isEmpty(follow.getUserId())?"success":"error";
        String errorCode="";
        Date beginDate=new Date();
        List<Follow> follows=new ArrayList<Follow>();
        if("success".equals(msg)){
            follow.setPaper(parameter);
            follows = followSvc.listGetToMyFollow(follow);
            reslutMap.put("follows",follows);
            //更新消息记录为已读
            if(null!=follow.getNewFollow()&&follow.getNewFollow()==1){
            	follow.setIsRed(new Byte("1"));
            	followSvc.updateRead(follow);
            }

        }
        reslutMap.put("msg",msg);
        reslutMap.put("errorCode",errorCode);
        logWriter.saveSystemLog(request,String.valueOf(follow.getUserId()), "",beginDate, "", "轻企获取通知列表", follows.size());
        return  reslutMap;
    }
    /**
    * 根据id更新关注状态
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/8 9:38
    */
    @RequestMapping(path ="/update",method = RequestMethod.POST)
    public  Map<String,Object> update(Follow follow, HttpServletRequest request){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg=!StrUtils.isEmpty(follow.getId())?"success":"error";
        if("success".equals(msg)){
            followSvc.update(follow);
        }
        reslutMap.put("msg",msg);
        reslutMap.put("errorCode",errorCode);
        return  reslutMap;

    }

    /**
     * 保存关注关系记录
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/8 9:38
     */
    @RequestMapping(path ="/insert",method = RequestMethod.POST)
    public  Map<String,Object> insert(Follow follow, HttpServletRequest request){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg=!StrUtils.isEmpty(follow.getUserId())||!StrUtils.isEmpty(follow.getLeId())?"success":"error";
        if("success".equals(msg)){
            followSvc.insert(follow);
        }
        reslutMap.put("msg",msg);
        reslutMap.put("errorCode",errorCode);
        return  reslutMap;

    }


}
