package com.qixin.teamwork.controller.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.DesUtils;
import org.framework.utils.Function;
import org.framework.utils.StrUtils;
import org.framework.utils.sms.SmsSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.payment.utils.other.WeiXinUtil;
import com.qixin.payment.utils.other.wx.JsonUtil;
import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.svc.FriendGroupSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.model.Validated;
import com.qixin.teamwork.biz.user.svc.ValidatedSvc;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;


/**
 * 登录接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/userwx")
public class UserController {

	@Autowired
	private WxuserSvc wxuserSvc;
	@Autowired
	private ValidatedSvc validatedSvc;
	@Resource
	private LogWriter log;
	@Autowired
	private FriendGroupSvc friendGroupSvc;

	/**
	 * 发送手机验证码
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * 1.1生成验证码
	 * 1.2判断是否注册登录
	 *     1.1.1已注册
	 *     1.1.2  60秒内不能重发
	 *     1.1.3用户没注册过、发送验证码
	 * 1.3 发送验证码	
	 */
 	@RequestMapping(path="/getValidCode",method = RequestMethod.GET)
 	public Map<String,Object> getValidCodebyPhone(HttpServletRequest request,@RequestParam String tel){
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		Date beginDate=new Date();
		String errorCode="";
		String msg="success";
		//1.1生成验证码
		String code= Function.createValidationCode(true);
		Validated reslutCode=null; 
		//1.2判断是否注册登录
		if(StrUtils.isNotEmpty(tel)){
			reslutCode= this.validatedSvc.getCodeByTel(tel);
			//1.1.1已注册
			if(null!=reslutCode&&!"".equals(reslutCode)){
				//1.1.2  60秒内不能重发
				long sendTime = reslutCode.getUpdateTime().getTime();
				long newTime = System.currentTimeMillis();
				long s = (newTime - sendTime) / 1000; // 相差秒数
			    if (s <= 60) {
					errorCode=APIErrorMap.errorMap.get("1");
					msg="error";
					reslutMap.put("errorCode", errorCode);
					reslutMap.put("msg", msg);
//					log.saveSystemLog(request,userId,nice,beginDate, "", "获取短信验证码", 0);
					return reslutMap;
				}
				reslutCode.setValidated(code);
				reslutCode.setCreateTime(new Timestamp(System.currentTimeMillis()));
				reslutCode.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			  	this.validatedSvc.updateCode(reslutCode);
			  //1.1.3用户没注册过、发送验证码
			}else{
				reslutCode=new Validated();
				reslutCode.setValidated(code);
				reslutCode.setTel(tel);
				reslutCode.setCreateTime(new Timestamp(System.currentTimeMillis()));
				reslutMap.put("errorCode", errorCode);
				reslutMap.put("msg", msg);
				this.validatedSvc.insertCode(reslutCode);
				
			}
		}else{
			msg="error";
			errorCode=APIErrorMap.errorMap.get("0");
		}
		//1.3 发送验证码	
		SmsSendUtils.sendRegUserSMS(tel,code);	
		reslutMap.put("phoneCode", code);
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		log.saveSystemLog(request,beginDate,tel, "发送手机验证码"+code, 0);
		return reslutMap;
	}
 	
 	
 	/**
	 * 手机端登录
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * 1  手机端关联
	 * 2   正常登录
	 */
	@RequestMapping(path="/webLogin",method=RequestMethod.GET)
    public Map<String,Object> webLogin(HttpServletRequest request,HttpServletResponse response,User user,String oId){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String errorCode="";
		String msg="success";
		User loginUser = new User();
		if(StrUtils.isNotEmpty(oId)){
			//1  手机端关联
			loginUser = wxuserSvc.relevanceWx(user,oId);
		}else{
			//2   正常登录
			loginUser = wxuserSvc.registerUser(user);
		}
		reslutMap.put("user", loginUser);
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		log.saveSystemLog(request,beginDate,user.getTel(), "微信手机端登陆", 0);
		return reslutMap;
	}

 	/**
	 * 获取用户资料
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
	@RequestMapping(path="/getUserInfoById",method = RequestMethod.GET)
 	public Map<String,Object> getUserInfoById(HttpServletRequest request,User user){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(StrUtils.isNotEmpty(user.getTypeUserId())){
			 DesUtils u = new DesUtils();
			user.setUserId(Long.parseLong(u.decrypt(user.getTypeUserId()).toString())); 
		}
		if(user.getUserId() !=null){
			user = wxuserSvc.getUserInfo(user);
			msg="success";
			reslutMap.put("user", user);
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,user.getUserId()+"", "获取用户资料", 0);
		return reslutMap;
	}
	
	
	/**
	 * 获取好友资料
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
	@RequestMapping(path="/getFriendInfoById",method = RequestMethod.GET)
 	public Map<String,Object> getFriendInfoById(HttpServletRequest request,Long friendId,Long userId){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(friendId!=null && userId!=null){
			User user = new User();
			user.setUserId(friendId);
			user = wxuserSvc.getUserInfo(user);
				FriendGroup friendGroup = new FriendGroup();
				friendGroup.setUserId(userId);
				friendGroup.setfId(friendId);
				friendGroup.setState((byte) 1);
				FriendGroup a  = friendGroupSvc.getFriendGroupById(friendGroup).get(0);
				user.setGroupName(a.getName());
				user.setFgId(a.getFgId());
			msg="success";
			reslutMap.put("user", user);
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,userId+"", "获取用户资料", 0);
		return reslutMap;
	}
	
	/**
	 * 修改用户资料
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
	@RequestMapping(path="/updateUserInfo",method = RequestMethod.POST)
 	public Map<String,Object> updateUserInfo(HttpServletRequest request,User user){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(user.getUserId()!=null){
			wxuserSvc.updateUser(user);
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,user.getUserId()+"", "修改用户资料", 0);
		return reslutMap;
	}
	
	
	/**
 	 * 根据手机号获取验证码
 	 * @param request
 	 * @param tel
 	 * @return
 	 */
 	@RequestMapping(path = "/getCode",method = RequestMethod.GET)
    public  Map<String,Object> getCode(HttpServletRequest request,String tel){
	    Map<String, Object>  reslutMap=new HashMap<String, Object>();
    	String msg   = "error";
		Validated codeByTel = validatedSvc.getCodeByTel(tel);
		if(null!=codeByTel){
			long sendTime = codeByTel.getUpdateTime().getTime();
			long newTime = System.currentTimeMillis();
			long s = (newTime - sendTime) / 1000; // 相差秒数
			msg = "success";
		/*	if(s>60){
				msg = "overtime";
			}*/
		}
		
		reslutMap.put("code", codeByTel);
	   reslutMap.put("msg",msg);
       return  reslutMap;
    }
	
 	/**
 	 * 是否关注公众号 
 	 * @param request
 	 * @param userId
 	 * @return
 	 */
 	@RequestMapping(path = "/userbin",method = RequestMethod.GET)
    public  Map<String,Object> userbin(HttpServletRequest request,Long userId){
	    Map<String, Object>  reslutMap=new HashMap<String, Object>();
    	String msg   = "error";
    	if(userId!=null){
    		User user = new User();
    		user.setUserId(userId);
    		user = wxuserSvc.getUserInfo(user);
    		if(user.getOpenId()!=null){
    			//获取token
    			reslutMap.put("oId",user.getOpenId());
    			String access_token = WeiXinUtil.getAccessToken();
    			
    			String info_str = WeiXinUtil.getXUserInfo(access_token, user.getOpenId());
    			Long scrsub = JsonUtil.getJsonValueLong(info_str,"subscribe");
    			reslutMap.put("info_str",info_str);
    			reslutMap.put("scrsub",scrsub);
    			msg  = "success";
    		}else{
    			reslutMap.put("scrsub",0);
    		}
    		
    	}
    	
	  reslutMap.put("msg",msg);
      return  reslutMap;
 	}
 	
 	
 	  @RequestMapping(path={"/getAll"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
 	  public Map<String, Object> getAll(HttpServletRequest request)
 	  {
 	    Map reslutMap = new HashMap();
 	    String msg = "error";
 	    List<User> userAll = this.wxuserSvc.getUserAll();
 	    if ((userAll != null) && (userAll.size() > 0)) {
 	      for (User user : userAll) {
 	        this.wxuserSvc.updateUser(user);
 	      }
 	    }

 	    reslutMap.put("msg", msg);
 	    return reslutMap;
 	  }
 	  
 	  /**
 	   * 任务参与人列表
 	   *@author wuchao
 	   *@data 2017年11月2日
 	   *@version V0.0.5
 	   * @param request
 	   * @param userId
 	   * @return
 	   */
 	 @RequestMapping(path = "/partakeEvent",method = RequestMethod.GET)
     public  Map<String,Object> partakeEvent(HttpServletRequest request,User user){
 	    Map<String, Object>  reslutMap=new HashMap<String, Object>();
     	String msg   = "error";
     	if( !StrUtils.isEmpty(user.getUserIdStr())){
     	List<User> userList = new ArrayList<User>();
     		String UserIdStr=user.getUserIdStr();
        	String[] usList = UserIdStr.split(",");
        	for (int i = 0; i < usList.length; i++) {
        		User us=new User();
        		us.setUserId( Long.valueOf(usList[i]));
        		User ur = wxuserSvc.getUserInfo(us);
        		userList.add(ur);
        	}
        	msg  = "success";
        	 reslutMap.put("list",userList);
     	}
     	
 	  reslutMap.put("msg",msg);
       return  reslutMap;
  	}
}
