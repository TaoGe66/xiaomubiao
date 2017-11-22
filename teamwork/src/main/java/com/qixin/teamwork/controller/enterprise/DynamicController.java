package com.qixin.teamwork.controller.enterprise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.PaginationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.enterprise.model.Dynamic;
import com.qixin.teamwork.biz.enterprise.svc.DynamicSvc;
import com.qixin.teamwork.biz.light.model.LightInteract;
import com.qixin.teamwork.biz.light.svc.LightInteractSvc;
import com.qixin.teamwork.biz.notice.model.Notice;
import com.qixin.teamwork.biz.notice.svc.NoticeSvc;

/**
 * 动态接口api接口
 * Created by
 * Author:zyting
 * Date:2017/9/8 10:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/dynamic")
public class DynamicController {
	@Autowired
	private LogWriter logWriter;
	@Autowired
	private DynamicSvc dynamicSvc;
	@Autowired
	private NoticeSvc noticeSvc;
	@Autowired
	private LightInteractSvc lightInteractSvc;

	/**
	 * 获取我的轻企动态
	 * 
	 * @author zyting
	 * @param [request,
	 *            follow]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @version V0.0.1
	 * @date 2017/9/8 11:45
	 */
	@RequestMapping(path = "/dynamicList", method = RequestMethod.GET)
	public Map<String, Object> dynamicList(HttpServletRequest request, Dynamic dynamic,PaginationParameter page) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String msg = "error";
		if (dynamic.getUserId() != null && dynamic.getLeId() != null) {
			// 通知
			Notice notice = new Notice();
			PaginationParameter parameter = new PaginationParameter();
			parameter.setNumPerPage(2);
			notice.setPaper(parameter);
			notice.setLeId(dynamic.getLeId());
			reslutMap.put("noticeList", noticeSvc.listGetNotice(notice));
			// 消息
			LightInteract lightInteract = new LightInteract();
			lightInteract.setLeId(dynamic.getLeId());
			lightInteract.setUserId(dynamic.getUserId());
			lightInteract.setIsRead((byte)0);
			int con = lightInteractSvc.listUpvote(lightInteract).size();
			reslutMap.put("lighCount", con);

			dynamic.setPaper(page);
			reslutMap.put("dynamicList", dynamicSvc.dynamicList(dynamic));
			msg = "success";
		}
		reslutMap.put("msg", msg);
		return reslutMap;

	}
	    
	/**
	 * 轻企动态互动列表
	 * 
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 下午2:28:58
	 * @version V0.0.3
	 * @param request
	 * @param lightInteract
	 * @return
	 */
	@RequestMapping(path = "/interactList", method = RequestMethod.GET)
	public Map<String, Object> dynamicList(HttpServletRequest request, LightInteract lightInteract) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String msg = "error";
		if (lightInteract.getUserId() != null && lightInteract.getLeId() != null) {
			List<LightInteract> interactList = lightInteractSvc.listUpvote(lightInteract);
			reslutMap.put("list", interactList);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		return reslutMap;

	}

	/**
	 * 轻企动态详情
	 * 
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 下午2:40:17
	 * @version V0.0.3
	 * @param request
	 * @param dynamic
	 * @return
	 */
	@RequestMapping(path = "/dynamicInfo", method = RequestMethod.GET)
	public Map<String, Object> dynamicInfo(HttpServletRequest request, Dynamic dynamic) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String msg = "error";
		if (dynamic.getUserId() != null && dynamic.getId() !=null) {
			Dynamic dynamicInfo = dynamicSvc.infoDynamic(dynamic);
			reslutMap.put("item", dynamicInfo);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		return reslutMap;

	}

}
