package com.qixin.teamwork.biz.todo.dao;

import java.util.List;

import com.qixin.teamwork.biz.base.ToDoRemindUtils;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.todo.model.Todo;

/**
 * 待办任务dao
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午1:38:43
 * @version V0.0.1
 */
@Repository
public class TodoDao extends BaseDao{

	/**
	 * 保存待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:47:57
	 * @version V0.0.1
	 * @param todo
	 */
	public void saveTodo(Todo todo) {
		insert("com.qixin.teamwork.biz.todo.dao.TodoDao.insert", todo);
	}

	/**
	 * 修改待办任务
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午1:48:01
	 * @version V0.0.1
	 * @param todo
	 */
	public void updateTodo(Todo todo) {
		update("com.qixin.teamwork.biz.todo.dao.TodoDao.update", todo);
	}
	/**
	 * 修改待办任务责任人
	 * @author zyting
	 * @date 2017年8月14日
	 * @time 下午1:48:01
	 * @version V0.0.1
	 * @param todo
	 */
	public void updateUserTodo(Todo todo) {
		update("com.qixin.teamwork.biz.todo.dao.TodoDao.updateUserTodo", todo);
	}
	public List<Todo> listTodo(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listTodo", todo);
	}
	
	public List<Todo> listTodoZing(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listTodoZing", todo);
	}
	/**
	 * 首页今日全部待办
	 * @author zyting
	 * @date 2017年7月20日
	 * @param todo
	 * @return
	 */
	public List<Todo> listTodoZingAll(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listTodoZingAll", todo);
	}
	
	/**
	 * 删除待办任务
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:51:48
	 * @version V0.0.1
	 * @param todo
	 */
	public void deleteTodo(Todo todo) {
		delete("com.qixin.teamwork.biz.todo.dao.TodoDao.deleteTodo", todo);
	}

	/**
	 * 待办事项详情
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午11:23:55
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public Todo todoInfo(Todo todo) {
		return (Todo) queryForObject("com.qixin.teamwork.biz.todo.dao.TodoDao.todoInfo", todo);
	}

	/**
	 * 定时待办任务列表
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 下午7:56:12
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public List<Todo> taskListTodo(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.taskListTodo", todo);
	}

	/**
	 * @description
	 * @author xiehuilin
	 * @param todo 待办实体
	 * @return Todo  待办实体
	 * @version V0.0.1
	 * @date 2017/6/15 11:20
	 */
	public  Todo getTodoInfo(Todo todo){
		return (Todo) this.queryForObject("com.qixin.teamwork.biz.todo.dao.TodoDao.todoInfo",todo);
	}

	/**
	 * 今日事件
	 * @description
	 * @author zyting
	 * @param todo 待办实体
	 * @return Todo  待办实体
	 * @version V0.0.1
	 * @date 2017/6/15 11:20
	 */
	public List<Todo> tadayEvent(Todo todo){
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.tadayEvent", todo);
	}
	/**
	* 获取待办数量
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 11:50
	*/
	public  List<Todo> tobeNumber(Todo todo){
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.tobeNumber", todo);
	}

	/**
	 * 根据事件id查询待办事件信息(根据事件id查询待办计划)
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 上午10:59:13
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public Todo infoTodo(Todo todo) {
		return (Todo) queryForObject("com.qixin.teamwork.biz.todo.dao.TodoDao.infoTodo", todo);
	}

	/**
	 * 待办事项列表
	 * @author wuchao
	 * @date 2017年6月27日
	 * @time 下午3:19:10
	 * @version V0.0.1
	 * @param todo
	 * @return
	 */
	public List<Todo> listItemTodo(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listItemTodo", todo);
	}
	
	/**
	 * 已失效
	 * @author zyting
	 * @date 2017年7月20日
	 * @param todo
	 * @return
	 */
	public List<Todo> isInvalidTodo(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.isInvalidTodo", todo);
	}

	/**
	* 获取用户行动待办记录
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/9 16:29
	*/
	public  Todo getTodoInfoRecord(Todo todo){
		return (Todo) queryForObject("com.qixin.teamwork.biz.todo.dao.TodoDao.getTodoInfoRecord", todo);
	}
	/**
	* 获取用户行动待办记录
	* @author zyting
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/9 16:29
	*/
	public  List<Todo> getTodoRecordList(Todo todo){
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.getTodoRecordList", todo);
	}
	
	public List<Todo> listEventItemTodo(Todo todo) {
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listEventItemTodo", todo);
	}
	/**
	* 获取所有行动待接受列表
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/8/14 13:58
	*/
	public List<Todo> listItemWait(Todo todo){
		return queryForList("com.qixin.teamwork.biz.todo.dao.TodoDao.listItemWait", todo);
	}

	/**
	 * 根据行动id、状态、主题获取行动委派时间
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.3
	 * @date 2017/9/14 15:43
	 */
	public  Todo getByEidTodoInfo(Todo todo){
		return (Todo) queryForObject("com.qixin.teamwork.biz.todo.dao.TodoDao.getByEidTodoInfo", todo);
	}

	/**
	 * 修改待办提醒时间
	 *@author wuchao
	 *@data 2017年10月25日
	 *@version V0.0.5
	 * @param todo
	 */
	public void updateCycTodo(Todo todo) {
		update("com.qixin.teamwork.biz.todo.dao.TodoDao.updateCycTodo", todo);
	}
	/**
	 *@Description:根据行动id或项目id批量更新待办记录
	 *@author:xiehuilin
	 *@param:todo
	 *@return:void
	 *@date:2017/10/26 15:42
	 *@version V 0.0.7
	 */
	public  void updateBatch(Todo todo){
		this.update("com.qixin.teamwork.biz.todo.dao.TodoDao.updateBatch", todo);
	}
}
