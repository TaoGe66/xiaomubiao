package com.qixin.teamwork.biz.todo.dao;

import com.qixin.teamwork.biz.todo.model.Todo;
import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.todo.model.TodoHistory;

import java.util.List;

/**
 * 待办历史记录dao
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午2:16:21
 * @version V0.0.1
 */
@Repository
public class TodoHistoryDao extends BaseDao{

	/**
	 * 保存待办历史纪录
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午2:19:14
	 * @version V0.0.1
	 * @param todoHistory
	 */
	public void saveTodoHistory(TodoHistory todoHistory) {
		insert("com.qixin.teamwork.biz.todo.dao.TodoHistoryDao.insert", todoHistory);
	}
	/**
	* 获取今日完成任务数量
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/22 13:31
	*/
	public Integer  toDayNumberList(Todo todo){
        List list = this.queryForList("com.qixin.teamwork.biz.todo.dao.TodoHistoryDao.toDayNumberList", todo);
        return null!=list?list.size():0;
	}
	
	public List<TodoHistory>  toDayHisList(Todo todo){
		 return  this.queryForList("com.qixin.teamwork.biz.todo.dao.TodoHistoryDao.toDayNumberList", todo);
	}
}
