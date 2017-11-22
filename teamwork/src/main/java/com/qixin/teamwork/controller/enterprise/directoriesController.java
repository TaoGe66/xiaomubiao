package com.qixin.teamwork.controller.enterprise;

import com.qixin.teamwork.biz.enterpriseMember.model.Member;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.follow.svc.FollowSvc;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 轻企通讯录接口api接口
 * Created by
 * Author:xiehuilin
 * Date:2017/9/8 10:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/direct")
public class directoriesController {
    @Autowired
    private LogWriter logWriter;
    @Autowired
    private FollowSvc followSvc;
    @Autowired
    private MemberSvc memberSvc;

    /**
     * 获取我的通讯录
     * @author xiehuilin
     * @param [request, follow]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version V0.0.1
     * @date 2017/9/8 11:45
     */
    @RequestMapping(path ="/listGetDir",method = RequestMethod.GET)
    public Map<String,Object> listGetDir(HttpServletRequest request, Follow follow){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=!StrUtils.isEmpty(follow.getLeId())&&!StrUtils.isEmpty(follow.getUserId())?"success":"error";
        String errorCode="";
        Date beginDate=new Date();
        if("success".equals(msg)){
            //是否有新关注
          Integer newFollow= followSvc.getNowFollow(follow)!=null?1:0;
            switch (follow.getType()){
                //获取关注我的成员列表
                case 0:
                    follow.setStrState("1");
                    reslutMap.put("listToMyFollow",followSvc.listGetToMyFollow(follow));
                    break;
                //获取我关注的成员列表、已接受的
                case 1:
                    follow.setStrState("1");
                    follow.setCreateBy(follow.getUserId());
                    follow.setLemState(new Byte("1"));
                    follow.setUserId(null);
                    reslutMap.put("listMyToFollow",followSvc.listGetMyToFollow(follow));
                    break;
                //获取轻企成员
                default:
                    //轻企成员列表
                	Member m = new Member();
                	m.setLeId(follow.getLeId());
                	m.setUserId(follow.getUserId());
                    List<Member> members = memberSvc.listGetMemberExceptMy(m);

                    //获取关注我的成员列表
                    follow.setStrState("1");
                    reslutMap.put("listToMyFollow",followSvc.listGetToMyFollow(follow));

                    //获取我关注的成员列表，已接受、等待确认
                    follow.setStrState("1,2");
                    follow.setCreateBy(follow.getUserId());
                    follow.setUserId(null);
                    follow.setLemState(new Byte("1"));
                    List<Follow> follows = followSvc.listGetMyToFollow(follow);
                    List<Follow> listMyToFollow=new ArrayList<Follow>();
                    if(!members.isEmpty()){
                        for (int x=0;x<members.size();x++){
                            Member member = members.get(x);
                            if(!follows.isEmpty()){
                                for(Follow f:follows){
                                    if(member.getUserId().equals(f.getUserId())){
                                        member.setState(f.getState());
                                      // follows.remove(f);
                                       break;
                                    }
                                }
                            }
                        }
                    }
                    if(!follows.isEmpty()){
                        for(Follow f:follows){
                            if(f.getState()!=2){
                                listMyToFollow.add(f);
                            }
                        }
                    }
                    reslutMap.put("memberList",members);
                    reslutMap.put("listMyToFollow",listMyToFollow);
                    reslutMap.put("follows",follows);
                    break;
            }

            reslutMap.put("newFollow",newFollow);

        }
        reslutMap.put("msg",msg);
        reslutMap.put("errorCode",errorCode);

        logWriter.saveSystemLog(request,"", "",beginDate, "", "通讯录", 0);
        return  reslutMap;

    }

    /**
     * 添加管理员列表
     * @author wuchao
     * @date 2017年9月18日
     * @time 下午8:45:14
     * @version V0.0.3
     * @param request
     * @param follow
     * @return
     */
    @RequestMapping(path ="/listManage",method = RequestMethod.GET)
    public Map<String,Object> listManage(HttpServletRequest request, Follow follow){
        Map<String,Object> reslutMap=new HashMap<String, Object>();
        String msg=!StrUtils.isEmpty(follow.getLeId())&&!StrUtils.isEmpty(follow.getUserId())?"success":"error";
        String errorCode="";
        Date beginDate=new Date();
        if("success".equals(msg)){
                //获取关注我的成员列表
                    follow.setStrState("1");
                    reslutMap.put("listToMyFollow",followSvc.listGetToMyFollow(follow));
                //获取我关注的成员列表、已接受的
                    follow.setStrState("1");
                    follow.setCreateBy(follow.getUserId());
                    follow.setUserId(null);
                    reslutMap.put("listMyToFollow",followSvc.listGetMyToFollow(follow));
                //获取轻企成员
                    //轻企成员列表
                	Member m = new Member();
                	m.setLeId(follow.getLeId());
                	m.setLemState((byte)1);
                    List<Member> members = memberSvc.listGetMeber(m);
                    reslutMap.put("memberList",members);
            }


        reslutMap.put("msg",msg);
        reslutMap.put("errorCode",errorCode);
        return  reslutMap;

    }

}
