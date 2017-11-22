package com.qixin.teamwork.biz.memo.svc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.memo.dao.MemoDao;
import com.qixin.teamwork.biz.memo.dao.MemoListDao;
import com.qixin.teamwork.biz.memo.model.Memo;
import com.qixin.teamwork.biz.memo.model.MemoList;
import com.qixin.teamwork.biz.memo.svc.MemoListSvc;
/**
 * 备忘录列表实现层
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
@Service("memoListSvc")
public class MemoListSvcImpl implements MemoListSvc{

	@Resource
	private MemoListDao memoListDao;
	@Resource
	private MemoDao memoDao;
	
	/**
	 * 保存备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	@Override
	public void saveMemoList(MemoList memoList) {
		Memo memo=new Memo();
		memo.setUserId(memoList.getUserId());
		Memo m=memoDao.infoMemo(memo);
		if (m==null) {
			memo.setCreateBy(memoList.getUserId());
			memo.setmName("所有");
			memoDao.saveMemo(memo);
			memoList.setmId(memo.getId());
		}else{
			memoList.setmId(m.getId());
		}
		if (memoList.getId() !=null) {
			memoListDao.updateMemoList(memoList);		
		}else{
			memoListDao.saveMemoList(memoList);	
		}
	}

	/**
	 * 修改备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	@Override
	public void updateMemoList(MemoList memoList) {
		memoListDao.updateMemoList(memoList);		
	}

	/**
	 * 备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @return
	 */
	@Override
	public List<MemoList> listMemoList(MemoList memoList) {
		return memoListDao.listMemoList(memoList);
	}

	/**
	 * 备忘录详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @return
	 */
	@Override
	public MemoList infoMemoList(MemoList memoList) {
		return memoListDao.infoMemoList(memoList);
	}

}
