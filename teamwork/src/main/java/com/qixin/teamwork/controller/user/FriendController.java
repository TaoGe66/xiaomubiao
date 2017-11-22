package com.qixin.teamwork.controller.user;

import java.util.*; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.DesUtils;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.svc.FriendGroupSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;



/**
 * 我的好友接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	private FriendGroupSvc friendGroupSvc;
	@Autowired
	private WxuserSvc wxuserSvc;
	@Resource
	private LogWriter log;
	@Autowired
	private JoinEventSvc joinEventSvc;
	@Autowired
	private EventItemSvc eventItemSvc;
	/**
	 * 获取我的分组好友列表
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
     * userId
	 */
	@RequestMapping(path="/getFriendGroupById",method = RequestMethod.GET)
 	public Map<String,Object> getFriendGroupById(HttpServletRequest request,FriendGroup friendGroup,PaginationParameter paper){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		friendGroup.setPaper(paper);
		if(friendGroup.getUserId()!=null){
			reslutMap.put("groupFriend", friendGroupSvc.getFriendGroupById(friendGroup));
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "获取我的分组好友列表", 0);
		return reslutMap;
	}
	

	/**
	 * 获取我的分组
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
     * userId
	 */
	@RequestMapping(path="/getGroupByUserId",method = RequestMethod.GET)
 	public Map<String,Object> getGroupByUserId(HttpServletRequest request,FriendGroup friendGroup){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(friendGroup.getUserId()!=null){
			reslutMap.put("group", friendGroupSvc.getGroupByUserId(friendGroup));
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "获取我的分组好友列表", 0);
		return reslutMap;
	}
	

	/**
	 * 更新好友状态
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
     * state 0 不同意、1 同意\r\n ,2 申请中
     * fg_id
	 */
	@RequestMapping(path="/updateFriend",method = RequestMethod.POST)
 	public Map<String,Object> updateFriend(HttpServletRequest request,FriendGroup friendGroup){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(friendGroup.getFgId()!=null){
			friendGroupSvc.update(friendGroup);
			
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "更新好友状态", 0);
		return reslutMap;
	}
	/**
	 *  保存好友记录
	 * @author xiehuilin
	 * @param request
	 * @param friendGroup
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @version V0.0.1
	 * @date 2017/6/14 15:20
	 */
	@RequestMapping(path="/saveFriend",method = RequestMethod.POST)
	 public Map<String,Object> saveFriend(HttpServletRequest request,FriendGroup friendGroup){
		 Date beginDate=new Date();
		 Map<String,Object> reslutMap=new HashMap<String, Object>();
		 
		 if(StrUtils.isNotEmpty(friendGroup.getFriendType())){
			 DesUtils u = new DesUtils();
			 friendGroup.setfId(Long.parseLong(u.decrypt(friendGroup.getFriendType()).toString())); 
		 }
		 if(friendGroup.getfId()!=null&&friendGroup.getUserId()!=null){
			 return  friendGroupSvc.insert(friendGroup);
		 }
		 reslutMap.put("msg", "error");
		 log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "保存好友记录", 0);
		 return reslutMap;
	 }

	/**
	* 添加好友列表
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 16:37
	*/
    @RequestMapping(path="/getFriendsList",method = RequestMethod.GET)
	 public  Map<String,Object> getFriendsList(HttpServletRequest request,FriendGroup friendGroup,PaginationParameter parameter){
		 Date beginDate=new Date();
		 Map<String,Object> reslutMap=new HashMap<String, Object>();
        List<FriendGroup> friendsList=new ArrayList<FriendGroup>();
        List<FriendGroup> relsutList=new ArrayList<FriendGroup>();
        String msg="error";
		 if(friendGroup.getUserId()!=null){
		 	friendGroup.setPaper(parameter);

             //根据用户id获取用信息
             User user=new User();
             user.setUserId(friendGroup.getUserId());
             User userInfo = wxuserSvc.getUserInfo(user);
             friendGroup.setUserName(userInfo.getUserName());
			 friendsList= friendGroupSvc.getFriendsList(friendGroup);
             /*if(friendsList.size()>0){
            	 for(FriendGroup fg:friendsList){
            		 if(!fg.getUserName().equals(userInfo.getUserName())){
            			 relsutList.add(fg);
            		 }
            	 }
             }*/
             msg="success";

         }
        reslutMap.put("friendsList", friendsList);
		 reslutMap.put("msg", msg);
		 log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "添加好友列表", 0);
		 return reslutMap;
	 }
    
    /**
	* 删除好友关系
	* @author zyting
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 16:37
	*/
    @RequestMapping(path="/delFriends",method = RequestMethod.POST)
	 public  Map<String,Object> delFriends(HttpServletRequest request,FriendGroup friendGroup){
    	 Date beginDate=new Date();
		 Map<String,Object> reslutMap=new HashMap<String, Object>();
		 String msg="error";
    	
		 if(friendGroup.getUserId()!=null && friendGroup.getfId()!=null){
			 friendGroupSvc.savedelFriends(friendGroup);
			 msg="success";
		 }
		 
    	 reslutMap.put("msg", msg);
		 log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "添加好友列表", 0);
		 return reslutMap;
    }
    
    
    
    /**
	* 判断好友关系是否存在项目
	* @author zyting
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 16:37
	*/
    @RequestMapping(path="/getDelStatus",method = RequestMethod.GET)
	 public  Map<String,Object> getDelStatus(HttpServletRequest request,FriendGroup friendGroup){
    	 Date beginDate=new Date();
		 Map<String,Object> reslutMap=new HashMap<String, Object>();
		 String msg="error";
    	
		 if(friendGroup.getUserId()!=null && friendGroup.getfId()!=null){
			 msg="success";
			 if(joinEventSvc.getDelStatus(friendGroup).size()==0){
				 if(eventItemSvc.getDelEventStatus(friendGroup).size()==0){
					 msg="sizebest";
				 }
			 };
		 }
		 
    	 reslutMap.put("msg", msg);
		 log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "判断好友关系是否存在项目", 0);
		 return reslutMap;
    }
    
    /**
   	* 获取最近好友列表
   	* @author zyting
   	* @param
   	* @return
   	* @version V0.0.1
   	* @date 2017/08/07 16:51
   	*/
     @RequestMapping(path="/getLatelyFriendList",method = RequestMethod.GET)
   	 public  Map<String,Object> getLatelyFriendList(HttpServletRequest request,FriendGroup friendGroup){
       	 Date beginDate=new Date();
   		 Map<String,Object> reslutMap=new HashMap<String, Object>();
   		 String msg="error";
   		 
   		 if(friendGroup.getUserId()!=null){
   		    reslutMap.put("friendsList", friendGroupSvc.getLatelyFriendList(friendGroup));
   		    msg="success";
   		 }
       	
   		 reslutMap.put("msg", msg);
		 log.saveSystemLog(request,beginDate,friendGroup.getUserId()+"", "获取最近好友列表", 0);
		 return reslutMap;
    }
    
     /**
    	* 获取最近好友列表
    	* @author zyting
    	* @param
    	* @return
    	* @version V0.0.1
    	* @date 2017/08/07 16:51
    	*/
      @RequestMapping(path="/updateFriendInfo",method = RequestMethod.GET)
    	 public  Map<String,Object> updateFriendInfo(HttpServletRequest request,FriendGroup friendGroup){
        	 Date beginDate=new Date();
    		 Map<String,Object> reslutMap=new HashMap<String, Object>();
    		 String msg="error";
    		 if(friendGroup.getFgId()!=null){
    			 friendGroupSvc.updateInfo(friendGroup);
    			 msg="success";
    		 }
    		 reslutMap.put("msg", msg);
    		 return reslutMap;
      }
}
