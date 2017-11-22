package com.qixin.teamwork.biz.todo.svc;

import java.util.List;

import com.qixin.teamwork.biz.todo.model.Todo;
import com.qixin.teamwork.biz.todo.model.TodoHistory;

/**
 * 待办历史记录接口
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午2:15:38
 * @version V0.0.1
 */
public interface TodoHistorySvc {

	/**
	 * 保存待办历史纪录
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午2:17:11
	 * @version V0.0.1
	 * @param todoHistory
	 */
	public void saveTodoHistory(TodoHistory todoHistory);

	/**
	 * 获取今日完成任务数量
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 13:31
	 */
	public Integer  toDayNumberList(Todo todo);
	
	/**
	 * 获取今日完成任务
	 * @author zyting
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 13:31
	 */
	public List<TodoHistory> toDayHisList(Todo todo);
}
