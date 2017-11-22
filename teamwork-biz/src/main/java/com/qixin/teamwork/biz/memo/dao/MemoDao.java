package com.qixin.teamwork.biz.memo.dao;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.memo.model.Memo;

/**
 * 备忘录DAO
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
@Repository
public class MemoDao extends BaseDao{

	/**
	 *  保存备忘录
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 */
	public void saveMemo(Memo memo) {
		insert("com.qixin.teamwork.biz.memo.dao.MemoDao.insert", memo);
		
	}
	
	/**
	 * 备忘录详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 * @return
	 */
	public Memo infoMemo(Memo memo) {
		return (Memo) queryForObject("com.qixin.teamwork.biz.memo.dao.MemoDao.infoMemo", memo);
	}

}
