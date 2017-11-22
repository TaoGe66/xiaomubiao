package com.qixin.teamwork.biz.memo.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.memo.model.MemoList;

/**
 * 备忘录列表DAO
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
@Repository
public class MemoListDao extends BaseDao{

	/**
	 *  保存备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	public void saveMemoList(MemoList memoList) {
		insert("com.qixin.teamwork.biz.memo.dao.MemoListDao.insert", memoList);
	}

	/**
	 * 修改备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	public void updateMemoList(MemoList memoList) {
		update("com.qixin.teamwork.biz.memo.dao.MemoListDao.update", memoList);
	}

	/**
	 * 备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 * @return
	 */
	public List<MemoList> listMemoList(MemoList memoList) {
		return queryForList("com.qixin.teamwork.biz.memo.dao.MemoListDao.listMemoList", memoList);
	}

	/**
	 * 备忘录详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 * @return
	 */
	public MemoList infoMemoList(MemoList memoList) {
		return (MemoList) queryForObject("com.qixin.teamwork.biz.memo.dao.MemoListDao.infoMemoList", memoList);
	}

}
