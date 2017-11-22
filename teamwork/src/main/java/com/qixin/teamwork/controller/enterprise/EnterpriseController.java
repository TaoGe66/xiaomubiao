package com.qixin.teamwork.controller.enterprise;

import java.util.Date;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterprise.svc.EnterpriseSvc;
import com.qixin.teamwork.biz.enterpriseMember.model.Member;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.follow.svc.FollowSvc;


/** 
 * 轻企
 * Created by
 * Author:zyting
 * Date:2017/6/12 14:01
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
    private LogWriter logWriter;
	@Resource
	private EnterpriseSvc enterpriseSvc;
	@Autowired
	private MemberSvc memberSvc;
	@Autowired
	private JoinEventSvc joinEventSvc;
	@Autowired
	private FollowSvc followSvc;

    /**
     * 创建轻企
     * @author zyting
     * @param  event 事件实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
	  @RequestMapping(path ="/insert",method = RequestMethod.POST)
	  public Map<String,Object> insertEnterprise(HttpServletRequest request, Enterprise enterprise){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        enterpriseSvc.insertEnterprise(enterprise);
	        resMap.put("id",enterprise.getLeId());
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "创建轻企", 0);
	        return resMap;

	    }
	  

    /**
     * 编辑轻企
     * @author zyting
     * @param  event 事件实体
     * @return map 集合
     * @version V0.0.1
     * @date 2017/6/13 18:00
     */
	  @RequestMapping(path ="/updateEnterprise",method = RequestMethod.POST)
	  public Map<String,Object> updateEnterprise(HttpServletRequest request, Enterprise enterprise){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(enterprise.getLeId()!=null){
	        	enterpriseSvc.updateEnterprise(enterprise);
	        	msg="success";
	        }
	        resMap.put("msg",msg);
	        return resMap;

	    }
	  
	/**
	 * 我的轻企列表
	 * @author zyting
     * @date 2017年9月6日
     * @time 下午4:08:50
	 * @param enterprise
	 * @return
	 */
	  @RequestMapping(path ="/enterpriseMyList",method = RequestMethod.GET)
	  public Map<String,Object> enterpriseMyList(HttpServletRequest request,Enterprise enterprise){
		    Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(enterprise.getUserId()!=null){
	        	msg="success";
	        	resMap.put("enList",enterpriseSvc.enterpriseMyList(enterprise));
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "我的轻企列表", 0);
	        return resMap;

	  }

	  /**
	   * 个人中心我的轻企列表
	   * @author wuchao
	   * @date 2017年9月12日
	   * @time 下午2:47:46
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	  @RequestMapping(path ="/personalLight",method = RequestMethod.GET)
	  public Map<String,Object> personalLight(HttpServletRequest request,Member member){
		    Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(member.getUserId()!=null){
	        	List<Member> list=memberSvc.listMember(member);
	        	msg="success";
	        	resMap.put("list",list);
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "个人中心我的轻企列表", 0);
	        return resMap;

	  }
	  
	  /**
	   *  根据用户id和轻企id查询用户轻企的项目
	   * @author wuchao
	   * @date 2017年9月13日
	   * @time 下午4:46:41
	   * @version V0.0.3
	   * @param request
	   * @param joinEvent
	   * @return
	   */
	  @RequestMapping(path ="/listProject",method = RequestMethod.GET)
	  public Map<String,Object> listProject(HttpServletRequest request,JoinEvent joinEvent){
		    Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(joinEvent.getUserId()!=null && joinEvent.getLeId() !=null){
	        	joinEvent.setStrEventState("0,1,3");
	        	//joinEvent.setStrEventType("0,1");
	        	List<JoinEvent> list=joinEventSvc.listProject(joinEvent);
	        	msg="success";
	        	resMap.put("list",list);
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", " 根据用户id和轻企id查询用户轻企的项目", 0);
	        return resMap;

	  }
	  
	  /**
	   * 轻企我关注的用户
	   * @author wuchao
	   * @date 2017年9月18日
	   * @time 下午1:34:07
	   * @version V0.0.3
	   * @param request
	   * @param follow
	   * @return
	   */
	  @RequestMapping(path ="/listFollowUser",method = RequestMethod.GET)
	  public Map<String,Object> listProject(HttpServletRequest request,Follow follow){
		    Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(follow.getCreateBy()!=null && follow.getLeId() !=null){
	        	follow.setStrState("1");
	        	 List<Follow> follows = followSvc.listGetMyToFollow(follow);
	        	msg="success";
	        	resMap.put("follows",follows);
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", " 根据用户id和轻企id查询用户轻企的项目", 0);
	        return resMap;

	  }
	  /**
	   * 修改轻企成员身份
	   * @author wuchao
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	  @RequestMapping(path ="/updateLightRole",method = RequestMethod.POST)
	  public Map<String,Object> updateLightRole(HttpServletRequest request, Member member){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(member.getId()!=null && member.getRoleType() !=null){
	        	memberSvc.updateMemberRole(member);
	        	msg="success";
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "修改轻企成员身份", 0);
	        return resMap;

	    }
	  
	  /**
	   * 轻企详情
	   * @author zyting
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	  @RequestMapping(path ="/enterpriseInfo",method = RequestMethod.GET)
	  public Map<String,Object> enterpriseInfo(HttpServletRequest request, Enterprise enterprise){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(enterprise.getLeId()!=null){
	        	resMap.put("enterpriseInfo",enterpriseSvc.enterpriseInfo(enterprise));
	        	msg="success";
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "轻企详情", 0);
	        return resMap;

	    }
	  
	  /**
	   * 查询简称和全程有没有重复
	   * @author zyting
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	  @RequestMapping(path ="/selectEnterprise",method = RequestMethod.POST)
	  public Map<String,Object> selectEnterprise(HttpServletRequest request, Enterprise enterprise){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(enterprise.getName()!=null || enterprise.getFullName()!=null){
	        	Enterprise e = new Enterprise();
	        	e.setName(enterprise.getName());
	        	e.setLeId(enterprise.getLeId());
	        	List<Enterprise> l = enterpriseSvc.selectEnterprise(e);
	        	e.setName(null);
	        	e.setFullName(enterprise.getFullName());
	        	List<Enterprise> le = enterpriseSvc.selectEnterprise(e);
	        	int isSave =1;
	        	if(l.size()!=0){
	        		isSave =2;  //简称有值
	        	}
	        	if(le.size()!=0){
	        		isSave =3;  //全程有值
	        	}
	        	resMap.put("isSave",isSave);
	        	msg="success";
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "查询简称和全程有没有重复", 0);
	        return resMap;

	    }
	  
	  
	  
	  /**
	   * 解散轻企
	   * @author zyting
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	  @RequestMapping(path ="/isEnterpriseDel",method = RequestMethod.POST)
	  public Map<String,Object> isEnterpriseDel(HttpServletRequest request, Enterprise enterprise){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(enterprise.getLeId()!=null){
	        	enterpriseSvc.updateisEnterpriseDel(enterprise);
	        }
	        resMap.put("msg",msg);
	        logWriter.saveSystemLog(request,"", "",beginDate, "", "解散轻企", 0);
	        return resMap;

	    }
	  
}
