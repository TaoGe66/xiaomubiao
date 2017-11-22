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

import com.qixin.teamwork.biz.enterprise.model.Dynamic;
import com.qixin.teamwork.biz.enterprise.svc.DynamicSvc;
import com.qixin.teamwork.biz.light.model.LightInteract;
import com.qixin.teamwork.biz.light.svc.LightInteractSvc;

/**
 * 动态互动接口api接口
 * Created by
 * Author:zyting
 * Date:2017/9/8 10:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/interact")
public class InteractController {
	
	 @Autowired
	 private LogWriter logWriter;
	 @Autowired
	 private LightInteractSvc lightInteractSvc;
	 @Autowired
	 private DynamicSvc dynamicSvc;
	 /**
	     * 添加动态互动（点赞，评论）
	     * @author zyting
	     * @param [request, follow]
	     * @return java.util.Map<java.lang.String,java.lang.Object>
	     * @version V0.0.1
	     * @date 2017/9/8 11:45
	     */
	    @RequestMapping(path ="/saveInteract",method = RequestMethod.POST)
	    public Map<String,Object> saveInteract(HttpServletRequest request,LightInteract lightInteract){
	        Map<String,Object> reslutMap=new HashMap<String, Object>();
	        String msg="error";
	        if(lightInteract.getCreateBy()!=null && lightInteract.getLedId()!=null){
	        	Dynamic dynamic = new Dynamic();
	        	dynamic.setId(lightInteract.getLedId());
	        	dynamic = dynamicSvc.dynamic(dynamic);
	        	lightInteract.seteId(dynamic.geteId());
	        	lightInteract.setEiId(dynamic.getEiId());
	        	lightInteract.setLeId(dynamic.getLeId());
	        	lightInteract.setUserId(dynamic.getUserId());
	        	lightInteractSvc.saveInteract(lightInteract);
	        	msg="success";
	        }
	        reslutMap.put("msg",msg);
	        return  reslutMap;
	    }
	    

	/**
	 *  修改互动内容
	 * @author wuchao
	 * @date 2017年9月15日
	 * @time 下午2:29:33
	 * @version V0.0.3
	 * @param request
	 * @param lightInteract
	 * @return
	 */
	@RequestMapping(path = "/updateInteract", method = RequestMethod.POST)
	public Map<String, Object> updateInteract(HttpServletRequest request, LightInteract lightInteract) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String msg = "error";
		if (lightInteract.getId() != null && lightInteract.getIsValid() != null) {
			lightInteractSvc.updateInteract(lightInteract);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		return reslutMap;

	}
	
	/**
	 * 将轻企下动态消息置为已读
	 * @author wuchao
	 * @date 2017年9月15日
	 * @time 下午8:21:24
	 * @version V0.0.3
	 * @param request
	 * @param lightInteract
	 * @return
	 */
	@RequestMapping(path = "/updateRead", method = RequestMethod.POST)
	public Map<String, Object> updateRead(HttpServletRequest request, LightInteract lightInteract) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String msg = "error";
		if (lightInteract.getLeId() != null) {
			lightInteract.setIsRead((byte)1);
			lightInteractSvc.updateRead(lightInteract);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		return reslutMap;

	}

}
