package com.qixin.teamwork.biz.memo.svc;

import java.util.List;

import com.qixin.teamwork.biz.memo.model.MemoList;

/**
 * 备忘录列表接口
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
public interface MemoListSvc {

	/**
	 * 保存备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	public void saveMemoList(MemoList memoList);
	
	/**
	 * 修改备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memoList
	 */
	public void updateMemoList(MemoList memoList);
	
	/**
	 * 备忘录列表
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @return
	 */
	public List<MemoList> listMemoList(MemoList memoList);
	
	/**
	 * 备忘录列表详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @return
	 */
	public MemoList infoMemoList(MemoList memoList);
}
