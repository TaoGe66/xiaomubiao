package com.qixin.teamwork.biz.todo.svc;

import java.util.List;
import java.util.Map;

import com.qixin.teamwork.biz.record.model.TrackRecord;
import com.qixin.teamwork.biz.todo.model.Todo;

/**
 * 待办任务接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:39:22
 * @version V0.0.1
 */
public interface TodoSvc {

	/**
	 * 保存待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:43:45
	 * @version V0.0.1
	 * @param todo
	 */
	public void saveTodo(Todo todo);
	
	/**
	 * 修改待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:44:31
	 * @version V0.0.1
	 * @param todo
	 */
	public void updateTodo(Todo todo);
	
	/**
	 * 查询待办任务列表
	 * @author wuchao
	 * @date 2017年6月13日
	 * @time 下午3:27:30
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public List<Todo>  listTodo(Todo todo);
	
	/**
	 * 删除待办任务
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:50:39
	 * @version V0.0.1
	 * @param todo
	 */
	public void deleteTodo(Todo todo);
	
	/**
	 * 待办事项详情
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午11:05:03
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public Todo  todoInfo(Todo todo);
	
	/**
	 * 定时待办任务列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 下午5:36:45
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public List<Todo>  taskListTodo(Todo todo);
	
	/**
	 * 关闭事件
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * id  待办任务id
	 */
	public void updateTodoCloseEvent(Todo todo);
	
	/**
	 * 完成事项  -- 已废弃
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 * id  待办任务id
	 */
	public void updateTodoClose(Todo todo);
	/**
	*  待办任务接受、拒绝
	* @author xiehuilin
	* @param  todo
	* @return
	* @version V0.0.1
	* @date 2017/6/15 10:51
	*/
	void updateAcceptOrRefuse(Todo todo);

	/**
	 * 待办事项
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 * @return
	 */
    public List<Todo> tobeEventList(Todo todo);
    
    /**
	 * 今日事件
	 * @description
	 * @author zyting
	 * @param todo 待办实体
	 * @return Todo  待办实体
	 * @version V0.0.1
	 * @date 2017/6/15 11:20
	 */
    public List<Todo> tadayEvent(Todo todo);

	/**
	 * 获取待办数量
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 11:50
	 */
	List<Todo> tobeNumber(Todo todo);
	
	/**
	 * 终止事项
	 * @author zyting
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 11:50
	 */
	void updateCloseEventItem(Todo todo);
	
	public List<Todo> listTodoZing(Todo todo);

	/**
	 * 根据事件id查询待办事件信息(根据事件id查询待办计划)
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 上午10:57:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public Todo infoTodo(Todo todo);

	/**
	 * 待办事项列表
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 下午3:16:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public List<Todo> listItemTodo(Todo todo);
	
	public Todo updateTodoDelAddHis(Todo todo);
	
	public Todo commentTo(Todo todo);
	
	/**
	 * 待办事件信息
	 * @author zyting
	 * @date 2017年6月27日
	 * @time 上午10:57:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public  Todo getTodoInfo(Todo todo);
	
	/**
	 * 首页今日全部待办
	 * @author zyting
	 * @date 2017年7月20日
	 * @param todo
	 * @return
	 */
	public List<Todo> listTodoZingAll(Todo todo);
	
	/**
	 * 已失效
	 * @author zyting
	 * @date 2017年7月20日
	 * @param todo
	 * @return
	 */
	public List<Todo> isInvalidTodo(Todo todo);
	

	/**
	 * 行动跟踪卡片
	 * @author zyting
	 * @date 2017年8月8日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return
	 */
	void updateTodoTrack(Todo todo);


	/**
	 * 获取用户行动待办记录
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/9 16:29
	 */
	  Todo getTodoInfoRecord(Todo todo);
	/**
	* 周期性行动接受拒绝
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/10 11:24
	*/
	  Map<String,Object> updateCycleAcceptOrRefuse (Todo todo);
	  
	  
	  List<Todo> listEventItemTodo(Todo todo);
	  
	  /**
	 * 修改待办任务责任人
	 * @author zyting
	 * @date 2017年8月14日
	 * @time 下午1:48:01
	 * @version V0.0.1
	 * @param todo
	 */
	  void updateUserTodo(Todo todo);

	/**
	 * 获取所有行动待接受列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/14 13:58
	 */
	List<Todo> listItemWait(Todo todo);
	/**
	* 行动超时更新
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/14 14:11
	*/
	void  updateItemWait(Todo todo);

	/** 
	* 根据行动id、状态、主题获取行动委派时间
	* @author xiehuilin    
	* @param  
	* @return  
	* @version V0.0.3
	* @date 2017/9/14 15:44
	*/
	Todo getByEidTodoInfo(Todo todo);
	
	/**
	 * 点击开始处理
	 * @author zyting
	 * @date 2017年8月17日
	 * @time 上午9:46:22
	 * @version V0.0.1
	 * @param request
	 * @param todo
	 * @return   eId
	 */
	void updateSixteen(Todo todo);
	
	/**
	 * 通过周期模板id修改待办提醒时间
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param todo
	 */
	public void updateCycTodo(Todo todo);

	/**
	 *@Description:根据行动id或项目id批量更新待办记录
	 *@author:xiehuilin
	 *@param:todo
	 *@return:void
	 *@date:2017/10/26 15:42
	 *@version V 0.0.7
	 */
	 void updateBatch(Todo todo);
	 
	 
	 void updateNotCompleted(Todo todo);
}
