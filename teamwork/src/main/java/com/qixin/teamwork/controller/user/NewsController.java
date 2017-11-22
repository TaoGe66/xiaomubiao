package com.qixin.teamwork.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.PaginationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.news.svc.NewsSvc;


/**
 * 我的好友接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsSvc newsSvc;
	@Resource
	private LogWriter log;
	

	/**
	 * 用户获取消息
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
     * sendId  查询用户
     * nType   1  事件
	 */
	@RequestMapping(path="/getNewsByUserid",method = RequestMethod.GET)
 	public Map<String,Object> getNewsByUserid(HttpServletRequest request,News news,PaginationParameter paper){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		news.setPaper(paper);
		if(news.getSendId()!=null){
			reslutMap.put("newsList", newsSvc.getNewsByUserid(news));
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,news.getSendId()+"", "用户获取消息", 0);
		return reslutMap;
	}

	/**
	* 获取提示未读数量
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/7/17 16:14
	*/
	@RequestMapping(path="/getPromptCount",method = RequestMethod.GET)
	public Map<String,Object> getPromptCount(HttpServletRequest request,News news){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(news.getSendId()!=null){
			reslutMap.put("promptCount", newsSvc.getNewsByUserid(news).size());
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,news.getSendId()+"", "获取提示未读数量", 0);
		return reslutMap;
	}






	@RequestMapping(path="/updateNew",method = RequestMethod.POST)
 	public Map<String,Object> updateNew(HttpServletRequest request,News news){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		if(news.getSendId()!=null){
			 newsSvc.update(news);
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,news.getSendId()+"", "", 0);
		return reslutMap;
	}
	
	/**
	 * 初始化消息
	 * @author zyting
     * @date 2017年6月12日
     * @time 上午10:05:33
     * @version V1.0
     * sendId  查询用户
     * nType   1  事件
	 */
	@RequestMapping(path="/initializtionNews",method = RequestMethod.GET)
 	public Map<String,Object> initializtionNews(HttpServletRequest request,News news){
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		newsSvc.initializtionNews();
		return reslutMap;
	}

}
