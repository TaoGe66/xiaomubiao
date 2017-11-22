package com.qixin.teamwork.biz.memo.svc;

import com.qixin.teamwork.biz.memo.model.Memo;

/**
 * 备忘录接口
 *@author wuchao
 *@data 2017年10月24日
 *@version V0.0.5
 */
public interface MemoSvc {

	/**
	 * 保存备忘录
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 */
	public void saveMemo(Memo memo);
	
	/**
	 * 备忘录详情
	 *@author wuchao
	 *@data 2017年10月24日
	 *@version V0.0.5
	 * @param memo
	 * @return
	 */
	public Memo infoMemo(Memo memo);
}
