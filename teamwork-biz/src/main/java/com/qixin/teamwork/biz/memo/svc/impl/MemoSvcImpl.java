package com.qixin.teamwork.biz.memo.svc.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.memo.dao.MemoDao;
import com.qixin.teamwork.biz.memo.model.Memo;
import com.qixin.teamwork.biz.memo.svc.MemoSvc;

/**
 * 备忘录实现层
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
@Service("memoSvc")
public class MemoSvcImpl implements MemoSvc{

	@Resource
	private MemoDao memoDao;
	/**
	 * 保存备忘录
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 */
	@Override
	public void saveMemo(Memo memo) {
		memoDao.saveMemo(memo);
	}

	/**
	 * 备忘录详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 * @return
	 */
	@Override
	public Memo infoMemo(Memo memo) {
		return memoDao.infoMemo(memo);
	}

}
