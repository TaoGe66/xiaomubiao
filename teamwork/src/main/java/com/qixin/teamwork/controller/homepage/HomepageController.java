package com.qixin.teamwork.controller.homepage;

import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.PaginationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.EventType;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.envet.svc.EventTypeSvc;
import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.model.TodoHistory;
import com.qixin.teamwork.biz.todo.svc.TodoHistorySvc;
import com.qixin.teamwork.biz.todo.svc.TodoSvc;


/**
 * 首页
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/homepage")
public class HomepageController {

	@Resource
	private LogWriter log;
	@Autowired
	private EventSvc eventSvc;
	@Autowired
	private TodoSvc todoSvc;
	@Autowired
	private TodoHistorySvc todoHistorySvc;
	@Autowired
	private WxuserSvc wxuserSvc;
	@Autowired
	private EventTypeSvc eventTypeSvc;
	@Autowired
	private EventItemSvc eventItemSvc;
	/**
	 * 获取今日完成任务
	 * @author zyting
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 13:31
	 */
	@RequestMapping(path="/toDayHisList",method = RequestMethod.GET)
 	public Map<String,Object> toDayHisList(HttpServletRequest request,Todo todo,PaginationParameter page){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		todo.setPaper(page);
		if(todo.getUserId()!=null){
			msg="success";
			reslutMap.put("pageInfo", todoHistorySvc.toDayHisList(todo));
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getUserId()+"", "获取今日完成任务", 0);
		return reslutMap;
	}
	
	/**
	 * 待办事项
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
	@RequestMapping(path="/tobeEventList",method = RequestMethod.GET)
 	public Map<String,Object> tobeEventList(HttpServletRequest request,Todo todo){//,PaginationParameter page
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="error";
		
		if(todo.getUserId()!=null){
			msg="success";
			List<TodoHistory> dayNumberList = todoHistorySvc.toDayHisList(todo);
		//	Integer dayn=null!=dayNumberList&&dayNumberList.size()>0?dayNumberList.size():0;
	//		todo.setPaper(page);
			User user=new User();
			user.setUserId(todo.getUserId());
			List<Todo> list =todoSvc.tobeEventList(todo);
		//	List<Todo> listAll =todoSvc.listTodoZingAll(todo);
			reslutMap.put("dayNumberList",dayNumberList);
			reslutMap.put("pageInfo", list);
			//今日事件
		//	reslutMap.put("totalNum",listAll.size());
			//完成事件
		//	reslutMap.put("todayNum",dayn);
		//	reslutMap.put("user",wxuserSvc.getUserInfo(user));
		}
		
		reslutMap.put("msg", msg);
		return reslutMap;
	}

	/**
	 * 热点发现
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * userId  用户id
	 */
	@RequestMapping(path="/findEvent",method = RequestMethod.GET)
 	public Map<String,Object> findEvent(HttpServletRequest request,Event event,PaginationParameter page){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="success";
		List<Event> list = new ArrayList<Event>();
		event.setPaper(page);
		if(event.getUserId()!=null){
			list =eventSvc.findEvenList(event,event.getUserId());
		}else{
			event.setState((byte) 0);
			event.setPubRange((byte) 1);
			event.setTypeStr("1,2");
			list = eventSvc.getEventList(event);
		}
		reslutMap.put("pageInfo", list);
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,event.getUserId()+"", "热点发现", 0);
		return reslutMap;
	}
	
	/**
	 * 今日事件(进行中)
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * userId  用户id
	 */
	@RequestMapping(path="/tadayEvent",method = RequestMethod.GET)
 	public Map<String,Object> tadayEvent(HttpServletRequest request,Todo todo,PaginationParameter page){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String  msg="error";
		//todo.setPaper(page);
		if(todo.getUserId()!=null){
			msg="success";
			reslutMap.put("pageInfo", todoSvc.tadayEvent(todo));
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getUserId()+"", "今日事件", 0);
		return reslutMap;
	}
	
	/**
	 * 关闭事件
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * 事件id   eId
	 * userId
	 */
	@RequestMapping(path="/todoCloseEvent",method = RequestMethod.POST)
 	public Map<String,Object> todoCloseEvent(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="wrong";
	    if(todo.geteId()!=null && todo.getId()!=null){
	    //	String tinme = todo.getFinishTimeStr()+":00";
	    	Event event = new Event();
	    	event.seteId(todo.geteId());
	    	event.setStateStr("0,1,3,4,5");
	    	if(eventSvc.getEnvetInfo(event)!=null){
	    		Todo todo1 = new Todo();
	    		todo1.setId(todo.getId());
	    		todo1.setIsFinish((byte)0);
	    		if(todoSvc.getTodoInfo(todo1)!=null){
	    			todo.setFinishTime(new Timestamp(System.currentTimeMillis()));
					todo.setIsFinish(new Byte("1"));
			    	todoSvc.updateTodoCloseEvent(todo);
			    	msg="success";
	    		}
	    		
	    	};
	    	
	    }else{
	    	msg="error";
	    }
		reslutMap.put("msg", msg);
		//log.saveSystemLog(request,beginDate,todo.getId()+"", "关闭事件", 0);
		return reslutMap;
	}





	/**
	 * 接受、拒绝
	 * @author xiehuilin
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return

	@RequestMapping(path="/acceptOrRefuse",method = RequestMethod.POST)
	public Map<String,Object>  acceptOrRefuse(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="wrong";
		if(todo.getEiId()!=null && todo.getId()!=null && todo.geteId()!=null){
			Event event = new Event();
	    	event.seteId(todo.geteId());
	    	event.setStateStr("0,1,3,4,5");
			if(eventSvc.getEnvetInfo(event)!=null){
				Todo todo1 = new Todo();
	    		todo1.setId(todo.getId());
	    		todo1.setIsFinish((byte)0);
	    		todo1.setIsInvalid((byte)0);
	    		if(todoSvc.getTodoInfo(todo1)!=null){
	    			EventItem eventItem = new EventItem();
	    			eventItem.setEiId(todo.getEiId());
	    			//eventItem.setStateStr("1");
                    EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
                    if(itemInfo!=null){
	    				//单次的
						if(itemInfo.getIsCycle()==0){
	    				todoSvc.updateAcceptOrRefuse(todo);
	    				 msg="success";
						}
						//周期的
						if(itemInfo.getIsCycle()==1){
						return  todoSvc.updateCycleAcceptOrRefuse(todo);
						}
	    			}
	    		}
			}

		}else{
			msg="error";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "接受、拒绝", 0);
		return reslutMap;
	}
	 */
	/**
	 * 完成事件
	 * @author xiehuilin
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * 事件id   eId
	 * userId
	 */
	@RequestMapping(path="/closeEvent",method = RequestMethod.POST)
	public Map<String,Object> CloseEvent(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object>();
		String msg="success";
		if(todo.geteId()!=null){
			todo.setFinishTime(new Timestamp(System.currentTimeMillis()));
			todo.setIsFinish(new Byte("1"));
			todoSvc.updateTodoCloseEvent(todo);
		}else{
			msg="error";
		}
		reslutMap.put("msg", msg);
	//	log.saveSystemLog(request,beginDate,todo.getId()+"", "关闭事件", 0);
		return reslutMap;
	}
	
	
	/**
	 * 完成事项
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * id  待办任务id
	 */
	@RequestMapping(path="/todoClose",method = RequestMethod.POST)
 	public Map<String,Object> todoClose(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="wrong";
	    if(todo.getId()!=null){
	    	Todo todo1 = new Todo();
    		todo1.setId(todo.getId());
    		todo1.setIsFinish((byte)0);
    		if(todoSvc.getTodoInfo(todo1)!=null){
				todo.setFinishTime(DateUtils.StrToDate(todo.getFinishTimeStr()));//new Timestamp(System.currentTimeMillis())
				todo.setIsFinish(new Byte("0"));
				EventItem eventItem = new EventItem();
				eventItem.setEiId(todo.getEiId());
				eventItem =	eventItemSvc.getItemInfo(eventItem);
				Date eDate =DateUtils.getTimestampToDate(eventItem.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
				Date tDate =DateUtils.getTimestampToDate(todo.getFinishTime(), Constant.DEFAULT_DATE_FORMAT_PATTERN);
				if (eDate.compareTo(tDate)>0){
					eventItem.setState((byte)7);
				}else if(eDate.compareTo(tDate)<0){
					eventItem.setState((byte)4);
				}else if(eDate.compareTo(tDate)==0){
					eventItem.setState((byte)3);
				}
				EventItem e = new EventItem();
				e.setEiId(todo.getEiId());
				e.setState(eventItem.getState());
				e.setfTime(todo.getFinishTimeStr());
				e.seteId(eventItem.geteId());
				e.setfTimestamp(todo.getFinishTime());
				e.setDutyId(eventItem.getDutyId());
				e.setCreateBy(eventItem.getCreateBy());
				eventItemSvc.updateItemState(e);

		    	//todoSvc.updateTodoClose(todo);
		    	msg="success";
    		}
	    }else{
	    	msg="error";	
	    }
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "完成事项", 0);
		return reslutMap;
	}
	
	/**
	 * 终止事项
	 * @param request
	 * @param todo
	 * @return
	 */
	@RequestMapping(path="/closeEventItem",method = RequestMethod.POST)
 	public Map<String,Object> closeEventItem(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
	      if( todo.getId()!=null){
			  todo.setFinishTime(new Timestamp(System.currentTimeMillis()));
			  todo.setIsFinish(new Byte("1"));
	    	  todoSvc.updateCloseEventItem(todo);
	    	  msg="success";
	      }
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "终止事项", 0);
		return reslutMap;
	}
	

	/**
	 * 去评论
	 * @param request
	 * @param todo
	 * @return
	 */
	@RequestMapping(path="/commentTo",method = RequestMethod.GET)
 	public Map<String,Object> commentTo(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
	      if( todo.getId()!=null){
	    	  reslutMap.put("retinfo", todoSvc.commentTo(todo));
	    	  msg="success";
	      }
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "去评论", 0);
		return reslutMap;
	}
	
	/**
	 * 去查看
	 * @param request
	 * @param todo
	 * @return
	 */
	@RequestMapping(path="/todoDelAddHis",method = RequestMethod.POST)
 	public Map<String,Object> todoDelAddHis(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
	      if( todo.getId()!=null){
	    	//  todo.settState((byte)8);
	    	  todoSvc.updateTodoDelAddHis(todo);
	    	  msg="success";
	      }
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "去查看", 0);
		return reslutMap;
	}
	
	
	/**
	 * 类型
	 * @param request
	 * @param eventType
	 * @return
	 */
	@RequestMapping(path="/eventType",method = RequestMethod.GET)
 	public Map<String,Object> eventType(HttpServletRequest request,EventType eventType){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="success";
		eventTypeSvc.listEventType(eventType);
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,"", "", 0);
		return reslutMap;
	}
	
	/**
	 * 校验接受或拒绝的待办任务是否有效
	 * @author wuchao
	 * @date 2017年7月31日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return
	 */
	@RequestMapping(path="/checkTodo",method = RequestMethod.GET)
 	public Map<String,Object> checkTodo(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		int hint=0;//提示
	      if( todo.getId()!=null){
	    		todo.setIsInvalid((byte)0);
		    	Todo td=todoSvc.todoInfo(todo);
		    	if (td==null) {
		    		hint=1;
				}
	    	  msg="success";
	      }
	     reslutMap.put("hint", hint);
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "去查看", 0);
		return reslutMap;
	}
	
	/**
	 * 已失效
	 * @author zyting
	 * @date 2017年7月31日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return
	 */
	@RequestMapping(path="/isInvalidTodo",method = RequestMethod.GET)
 	public Map<String,Object> isInvalidTodo(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		if(todo.getUserId()!=null){
			reslutMap.put("pageInfo", todoSvc.isInvalidTodo(todo));
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "去查看", 0);
		return reslutMap;
	}
	

	/**
	 * 行动跟踪卡片
	 * @author zyting
	 * @date 2017年8月8日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return
	 * 	 是否忽略 
	 */
	@RequestMapping(path="/todoTrack",method = RequestMethod.POST)
 	public Map<String,Object> todoTrack(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
	
		if(todo.getId()!=null){
			todoSvc.updateTodoTrack(todo);
			msg="success";
		}
		
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.getId()+"", "行动跟踪卡片", 0);
		return reslutMap;
	}
	
	/**
	 * 判断项目是否有行动未完成
	 * @author zyting
	 * @date 2017年8月10日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return   eId
	 */
	@RequestMapping(path="/getUndoneItem",method = RequestMethod.GET)
 	public Map<String,Object> getUndoneItem(HttpServletRequest request,EventItem eventItem){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		if(eventItem.geteId()!=null){
			eventItem.setStateStr("0,1,2");
			reslutMap.put("itemSize", eventItemSvc.listEventItem(eventItem).size());
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,eventItem.geteId()+"", "判断项目是否有行动未完成", 0);
		return reslutMap;
	}
	
	/**
	 * 首页委托弹框详情
	 * @author zyting
	 * @date 2017年8月17日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return   eId
	 */
	@RequestMapping(path="/getTemWinInfo",method = RequestMethod.GET)
 	public Map<String,Object> getTemWinInfo(HttpServletRequest request,EventItem eventItem){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		if(eventItem.getEiId()!=null){
			reslutMap.put("itemInfo",eventItemSvc.getTemWinInfo(eventItem));
			msg="success";
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,eventItem.geteId()+"", "首页委托弹框详情", 0);
		return reslutMap;
	}
	
	
	/**
	 * 点击开始处理
	 * @author zyting
	 * @date 2017年8月17日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return   eId eiId userId id
	 */
	@RequestMapping(path="/updateSixteen",method = RequestMethod.GET)
	public Map<String,Object> updateSixteen(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		if(todo.getId()!=null && todo.getEiId()!=null){
			todoSvc.updateSixteen(todo);
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.geteId()+"", "点击开始处理", 0);
		return reslutMap;
	}
	
	/**
	 * 已完成-->标记未完成
	 * @author zyting
	 * @date 2017年8月17日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return   Id EiId
	 */
	@RequestMapping(path="/makeNotCompleted",method = RequestMethod.POST)
	public Map<String,Object> makeNotCompleted(HttpServletRequest request,Todo todo){
		Date beginDate=new Date();
		Map<String,Object> reslutMap=new HashMap<String, Object	>();
		String msg="error";
		if(todo.getId()!=null && todo.getEiId()!=null){
			todoSvc.updateNotCompleted(todo);
		}
		reslutMap.put("msg", msg);
		log.saveSystemLog(request,beginDate,todo.geteId()+"", "标记未完成", 0);
		return reslutMap;
	}
	
}
