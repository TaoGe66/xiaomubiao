package com.qixin.teamwork.controller.enterprise;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.enterpriseMember.model.Member;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;

/**
 * 动态互动接口api接口
 * Created by
 * Author:zyting
 * Date:2017/9/8 10:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/member")
public class MemberController {

	 @Autowired
	 private LogWriter logWriter;
	 @Autowired
	 private MemberSvc memberSvc;
	 /**
	     * 判断成员能否删除
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/isMemberDel",method = RequestMethod.GET)
	    public Map<String,Object> isMemberDel(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getUserId()!=null && member.getLeId() !=null){
	        	if(memberSvc.isMemberDel(member)){
	        		 reslutMap.put("isDel",1);
	        	}else{
	        		 reslutMap.put("isDel",0);
	        	}
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;

	    }
	    
	    /**
	     * 删除成员
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/delMember",method = RequestMethod.GET)
	    public Map<String,Object> delMember(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	         if(member.getUserId()!=null){
	        	 memberSvc.updateMemberDel(member);
	        	 msg="success";
	         }
	        reslutMap.put("msg",msg);
	        return  reslutMap;

	    }
	    
	    /**
	     * 成员列表
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/memberList",method = RequestMethod.GET)
	    public Map<String,Object> memberList(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getLeId()!=null){
	        	member.setLemState((byte)1);
	        	reslutMap.put("memberList",memberSvc.listGetMeber(member));
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }
	    
	    /**
	     * 查询申请人列表  (管理员)
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/memberListAll",method = RequestMethod.GET)
	    public Map<String,Object> memberListAll(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getLeId()!=null){
	        	reslutMap.put("memberList",memberSvc.listGetMeber(member));
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }  
	    
	    /**
	     * 更新申请状态
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/updateMember",method = RequestMethod.GET)
	    public Map<String,Object> updateMember(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getId()!=null){
	        	memberSvc.updateMember(member);
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }  
	    
	    
	    /**
	     * 取消管理员
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45 
	     */
	    @RequestMapping(path ="/updateMemberOne",method = RequestMethod.GET)
	    public Map<String,Object> updateMemberOne(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getId()!=null){
	        	memberSvc.updateMemberOne(member);
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }
	    
	    /**
	     * 分享轻企二维码详情
	     * @author wuchao
	     * @date 2017年9月20日
	     * @time 上午10:38:46
	     * @version V0.0.3
	     * @param request
	     * @param member
	     * @return
	     */
	    @RequestMapping(path ="/shareLigth",method = RequestMethod.GET)
	    public Map<String,Object> shareLigth(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getLeId()!=null && member.getUserId()!=null){
	        	Member info=memberSvc.infoMember(member);
	        	reslutMap.put("member",info);
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }  
	    
	    /**
	     * 添加轻企成员
	     * @author zyting
	     * @date 2017年9月20日
	     * @time 上午10:38:46
	     * @version V0.0.3
	     * @param request
	     * @param member
	     * @return
	     */
	    @RequestMapping(path ="/insertMem",method = RequestMethod.POST)
	    public Map<String,Object> insertMem(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getUserId()!=null){
	        	member.setCreateBy(member.getUserId());
	        	memberSvc.insert(member);
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }  
	    
	    /**
	     * 查询轻企成员
	     * @author zyting
	     * @date 2017年9月20日
	     * @time 上午10:38:46
	     * @version V0.0.3
	     * @param request
	     * @param member
	     * @return
	     */
	    @RequestMapping(path ="/getMeber",method = RequestMethod.GET)
	    public Map<String,Object> getMeber(HttpServletRequest request,Member member){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(member.getUserId()!=null && member.getLeId()!=null){
	        	reslutMap.put("getMeber",memberSvc.listGetMeber(member));
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }  
	    
}
