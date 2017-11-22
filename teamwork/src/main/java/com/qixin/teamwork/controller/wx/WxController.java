package com.qixin.teamwork.controller.wx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qixin.payment.utils.other.WeiXinUtil;
import com.qixin.payment.utils.other.wx.Sha1Util;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.CommConstants;
import org.framework.utils.StrUtils;
import org.framework.utils.img.FileUtil;
import org.framework.utils.pool.PoolFactory;
import org.framework.utils.thread.DownloadImage;
import org.framework.utils.thread.DownloadUrlHeadPicThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.qixin.payment.utils.config.WeiXinMpConfig;
import com.qixin.payment.utils.other.wx.JsonUtil;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.model.UserOpenid;
import com.qixin.teamwork.biz.user.svc.ValidatedSvc;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/wxtool")
public class WxController {

	 @Autowired
	 private LogWriter log;
	 @Autowired
	 private WxuserSvc wxuserSvc;
	 @Autowired
	 private ValidatedSvc validatedSvc;
	
	/**
	 * 微信内部登录
	 * @author zyting
	 * @param request
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 */
	@RequestMapping(path = "wx_web_login",method = RequestMethod.GET)
	public Map<String, String> wxWebLogin(HttpServletRequest request,String signCode) throws UnsupportedEncodingException{
		Map<String, String> reslutMap = new HashMap<String,String>();
		Date beginDate=new Date();
		String url = CommConstants.TEAM_WEB+"wxtool/login_submit";
		//记录哪端登录  1 : 手机端登录     2: PC端登录
		String code = "1";
		if(signCode=="2"){
			 code = "2";
		}
		//返回错误信息 
		reslutMap.put("msg","success");
		reslutMap.put("redirect_uri",url);
		reslutMap.put("app_id",WeiXinMpConfig.APP_ID);
		reslutMap.put("pay_state",code);
		log.saveSystemLog(request,beginDate,code, "微信手机端登录", 0);
		return reslutMap;
	}
	
	

	/**
	 * 获取微信返回信息  
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(path = "login_submit",method = RequestMethod.GET)
	public ModelAndView loginSubmit(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException{
		/**
	    1 第一步：用户同意授权，获取code
		2 第二步：通过code换取网页授权access_token
		3 第三步：刷新access_token（如果需要）
		4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
		5 附：检验授权凭证（access_token）是否有效
		*/
		String code = request.getParameter("code");
		String pay_code = request.getParameter("state");   //pay_code  1:微信内部登录   2:pc用的sign（扫码）
		String token_str = WeiXinUtil.getResContent(code);
		String access_token = JsonUtil.getJsonValue(token_str, "access_token");
		String openID = JsonUtil.getJsonValue(token_str, "openid");
		String resUser = WeiXinUtil.getResUser(access_token, openID);
		String nickname = JsonUtil.getJsonValue(resUser, "nickname"); 
		String headimgurl = JsonUtil.getJsonValue(resUser, "headimgurl");
		User user = new User();
		user.setOpenId(openID);
		user = wxuserSvc.getUserInfo(user);
	
		//微信内部登录 直接获取用户信息,链接到网站
		//网页登录 扫二维码 进入获取授权信息页面 获取用户信息  用户点击授权成功 变为已登录状态
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String login_page = "2";//1:pc用的sign（扫码）,2:web微信内部登录
	/*	if(!pay_code.equals("1")){
			PcSign pcSignBySign = pcSignSvc.getPcSignBySign(pay_code);  
			Timestamp ts = new Timestamp(new Date().getTime());
			pcSignBySign.setLoginTime(ts);
			pcSignBySign.setOpenId(openID);
			pcSignBySign.setState(1);
			pcSignSvc.updatePcSign(pcSignBySign);
			login_page="1"; //来记录跳转页面
		}*/
		//已注册跳转首页
		if(null!=user){
			//用户登录信息 
			modelMap.put("userId", user.getUserId());
			modelMap.put("nice", user.getUserName());
			modelMap.put("headUrl", user.getHeadUrl());
			modelMap.put("tel", user.getTel());
			
			modelMap.put("login_state", 1);
		//未注册跳转关联手机号
		}else{
			
			modelMap.put("login_state", 2);
			// 文件保存路径
			Properties props = new Properties(); 
			try {
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("activity.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String savePath = props.getProperty("uploadImage");
			byte[] imgbyte=DownloadImage.download(headimgurl);
			String imageType = FileUtil.getFileSuffix(imgbyte);//图片类型
			String fileName=openID+"."+imageType;
			//将用户头像下载到本地
		    PoolFactory.getTaskPool().addTask(new DownloadUrlHeadPicThread(headimgurl, savePath, openID));
		    UserOpenid userOpenid = new UserOpenid();
		    userOpenid.setOpenId(openID);
		    UserOpenid suserOpenId = validatedSvc.getUserOpenid(userOpenid);
		    //用户已关联
		    if(null!=suserOpenId){
		    	modelMap.put("oId", suserOpenId.getId());
		    	modelMap.put("headUrl", suserOpenId.getWxLogo());
		    	modelMap.put("nice", suserOpenId.getWxNice());
		    //用户未关联
		    }else{
		    	userOpenid.setOpenId(openID);
		    	userOpenid.setWxLogo(fileName);
		    	userOpenid.setWxNice(nickname);
		    	validatedSvc.insertUserOpenid(userOpenid);
		    	modelMap.put("oId", userOpenid.getId());
		    	modelMap.put("headUrl", fileName);
		    	modelMap.put("nice", nickname);
		    }
		}
		modelMap.put("goHref", CommConstants.TEAMWORK_VIEW_WEB);
		modelMap.put("login_page", login_page);
		model.addAllAttributes(modelMap);
		ModelAndView mv = new ModelAndView("loginskip");
		return mv;
	}



	/**
	* 功能：获取微信分享数据签名等
	* @author xiehuilin
	* @param  requestMap 参数map
	* @version V0.0.1
	* @date 2017/6/15 14:51
	*/
	@RequestMapping(path = "wx_parameter",method = RequestMethod.POST)
	public Map<String, Object> wxParameter(HttpServletRequest request,@RequestBody Map<String, Object> requestMap){
		Map<String, Object>  share=new HashMap<String, Object>();
		Date beginDate=new Date();
		String requestPage = "";
		if(requestMap!=null){
			requestPage = requestMap.get("requestPage").toString();
			String url_parameter = requestMap.get("pageMessage").toString();
			// 时间戳,当前时间的毫秒数 (必填参数)
			String timeStamp = Sha1Util.getTimeStamp();
			// 随机字符串 ,随机生成 (必填参数)
			String nonceStr = Sha1Util.getNonceStr();
			String access_token = WeiXinUtil.getAccessToken();
			String ticket = WeiXinUtil.getjsTicket(access_token);
			String weixinUrl = CommConstants.TEAMWORK_VIEW_WEB+requestPage+".html"+url_parameter;
			String signature = null;
			try {
				signature = WeiXinUtil.getSignature(ticket,timeStamp,nonceStr,weixinUrl);
			} catch (IOException e) {
				share.put("msg","error");
				e.printStackTrace();
			}
			//返回错误信息
			share.put("msg","success");
			share.put("signature", signature);
			share.put("timeStamp", timeStamp);
			share.put("nonceStr", nonceStr);
			share.put("appId", WeiXinMpConfig.APP_ID);
		}else{
			share.put("msg","error");
		}
		log.saveSystemLog(request,beginDate,requestPage, "获取微信分享数据签名", 0);
		return share;
	}
	
	
}
