package com.qixin.teamwork.biz.todo.svc.impl;

import java.util.List;

import javax.annotation.Resource;

import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.todo.model.Todo;

import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.todo.dao.TodoHistoryDao;
import com.qixin.teamwork.biz.todo.model.TodoHistory;
import com.qixin.teamwork.biz.todo.svc.TodoHistorySvc;

/**
 * 待办历史记录实现
 * @author wuchao
 * @date 2017年6月12日
 * @time 下午2:16:08
 * @version V0.0.1
 */
@Service("todoHistorySvc")
public class TodoHistorySvcImpl implements TodoHistorySvc{

	@Resource
	private TodoHistoryDao todoHistoryDao;
	@Autowired
	private EventItemDao eventItemDao;
	/**
	 * 保存待办历史纪录
	 * @author wuchao
	 * @date 2017年6月12日
	 * @time 下午2:19:14
	 * @version V0.0.1
	 * @param todoHistory
	 */
	@Override
	public void saveTodoHistory(TodoHistory todoHistory) {
		todoHistoryDao.saveTodoHistory(todoHistory);
	}

	@Override
	public Integer toDayNumberList(Todo todo) {
		return todoHistoryDao.toDayNumberList(todo);
	}

	@Override
	public List<TodoHistory> toDayHisList(Todo todo) {
		List<TodoHistory> l = todoHistoryDao.toDayHisList(todo);
		for(int i=0;i<l.size();i++){
			if(l.get(i).gettSubjType()==1){
				EventItem eventItem = new EventItem();
				eventItem.seteId(l.get(i).geteId());
				eventItem.setEiId(l.get(i).getEiId());
				eventItem = eventItemDao.listEventItem(eventItem).get(0);
				l.get(i).setEiDesc(eventItem.getEiDesc());
				l.get(i).setEiStartTime(StrUtils.getWeekDay(eventItem.getStartTime()));
				l.get(i).setFinishTimeStr(DateUtils.getDateDetailDay(eventItem.getFinishTime().toString()));
			}
			 l.get(i).setEndTimeStr(DateUtils.getDateDetailDay(l.get(i).getEndTime().toString()));
			 l.get(i).setPushTimeStr( DateUtils.getDateDetailDay(l.get(i).getPushTime().toString()));
		}
		return l;
	}
}
