package com.qixin.teamwork.controller.enterprise;

import java.util.Date; 
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.light.svc.LightAuthSvc;

/**
 * 轻企认证、年审
 * @author wuchao
 * @date 2017年9月13日
 * @time 上午9:44:59
 * @version V0.0.3
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private LogWriter logWriter;
	@Autowired
	private LightAuthSvc authSvc;

	/**
	 * 保存认证信息
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午9:49:57
	 * @version V0.0.3
	 * @param request
	 * @param lightAuth
	 * @return
	 */
	@RequestMapping(path = "/saveAuth", method = RequestMethod.POST)
	public Map<String, Object> saveAuth(HttpServletRequest request,@RequestBody LightAuth lightAuth) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String errorCode = "";
		String msg = !StrUtils.isEmpty(lightAuth.getLeId()) ? "success" : "error";
		if ("success".equals(msg)) {
			authSvc.saveAuth(lightAuth);
			reslutMap.put("item", lightAuth);
		}
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		return reslutMap;

	}
	
	/**
	 * 修改认证信息
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 下午3:30:57
	 * @version V0.0.3
	 * @param request
	 * @param lightAuth
	 * @return
	 */
	@RequestMapping(path = "/updateAuth", method = RequestMethod.POST)
	public Map<String, Object> updateAuth(HttpServletRequest request,@RequestBody LightAuth lightAuth) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String errorCode = "";
		String msg = !StrUtils.isEmpty(lightAuth.getId()) ? "success" : "error";
		if ("success".equals(msg)) {
			authSvc.updateAuth(lightAuth);
		}
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		return reslutMap;

	}
	
	/**
	 * 认证详情
	 * @author wuchao
	 * @date 2017年9月13日
	 * @time 上午9:54:20
	 * @version V0.0.3
	 * @param request
	 * @param lightAuth
	 * @return
	 */
	@RequestMapping(path = "/infoAuth", method = RequestMethod.GET)
	public Map<String, Object> infoWeekly(HttpServletRequest request, LightAuth lightAuth) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		 Date beginDate=new Date();
		String msg = "error";
		if (lightAuth.getLeId() != null) {
			//认证详情
			LightAuth item=authSvc.infoAuth(lightAuth);
			reslutMap.put("item", item);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		 logWriter.saveSystemLog(request,"", "",beginDate, "", "认证详情", 0);
		return reslutMap;

	}
}
