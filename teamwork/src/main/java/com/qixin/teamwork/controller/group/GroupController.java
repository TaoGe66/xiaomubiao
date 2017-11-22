package com.qixin.teamwork.controller.group;

import java.util.Date; 
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.StrUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.model.Group;
import com.qixin.teamwork.biz.group.svc.FriendGroupSvc;
import com.qixin.teamwork.biz.group.svc.GroupSvc;

/**
 * 好友组控制层
 * @author wuchao
 * @date 2017年6月14日
 * @time 下午8:40:02
 * @version V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/group")
public class GroupController {

	@Resource
	private LogWriter log;
	@Resource
	private GroupSvc groupSvc;
	@Resource
	private FriendGroupSvc friendGroupSvc;
	
	/**
	 * 新增好友组
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 下午8:41:57
	 * @version V0.0.1
	 * @param request
	 * @param eventItem
	 * @return
	 */
	 @RequestMapping(path ="/saveGroup",method = RequestMethod.POST)
	    public Map<String,Object> saveGroup(HttpServletRequest request,@RequestBody Group group){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(group.getCreateBy() !=null && !StrUtils.isEmpty(group.getName())){
	        	groupSvc.saveGroup(group);
	        	msg="success";
	        	resMap.put("group",group);
	        }
	        resMap.put("msg", msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "新增好友组", 0);
	        return resMap;
	    }
	 
	 /**
	  * 修改我的好友分组关系
	  * @author wuchao
	  * @date 2017年6月14日
	  * @time 下午8:50:44
	  * @version V0.0.1
	  * @param request
	  * @param friendGroup
	  * @return
	  */
	 @RequestMapping(path ="/updateFriend",method = RequestMethod.POST)
	    public Map<String,Object> updateFriend(HttpServletRequest request,@RequestBody FriendGroup friendGroup){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(friendGroup.getfId() !=null && friendGroup.getgId() !=null){
	        	friendGroupSvc.updateFriendGroup(friendGroup);
	        	msg="success";
	        }
	        resMap.put("msg", msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "修改我的好友分组关系", 0);
	        return resMap;
	    }
	 
	/**
	 * 根据用户id和组名称查询组是否存在
	 * @author wuchao
	 * @date 2017年6月19日
	 * @time 下午1:44:51
	 * @version V0.0.1
	 * @param request
	 * @param group
	 * @return
	 */
	 @RequestMapping(path="/checkGroupInfo",method = RequestMethod.GET)
	 public Map<String,Object> checkEvent(HttpServletRequest request, Group group){
	  Date beginDate=new Date();
	  Map<String,Object> reslutMap=new HashMap<String, Object>();
	  String msg="error";
	  int count=0;
	  if(group.getCreateBy() !=null &&  !StrUtils.isEmpty(group.getName()) ){
		  Group g=groupSvc.infoGroup(group);
	  	if (g !=null) {
	  		count=1;
		}
	  	msg="success";
	  }
	  reslutMap.put("count", count);
	  reslutMap.put("msg", msg);
	  log.saveSystemLog(request,beginDate,group.getCreateBy()+""+group.getName(), "根据用户id和组名称查询组是否存在", 0);
	  return reslutMap;
	 }
}
