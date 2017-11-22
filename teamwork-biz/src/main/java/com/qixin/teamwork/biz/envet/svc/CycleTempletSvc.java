package com.qixin.teamwork.biz.envet.svc;

import java.util.List;

import com.qixin.teamwork.biz.envet.model.CycleTemplet;

/**
 * 事项周期模板接口
 * @author wuchao
 * @date 2017年8月9日
 * @time 下午4:03:23
 * @version V0.0.1
 */
public interface CycleTempletSvc {

	/**
	 * 保存事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:05:22
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	public void saveCycleTemplet(CycleTemplet cycleTemplet);
	
	/**
	 * 修改事项周期模板
	 * @author wuchao
	 * @date 2017年8月9日
	 * @time 下午4:30:43
	 * @version V0.0.1
	 * @param cycleTemplet
	 */
	public void updateCycleTemplet(CycleTemplet cycleTemplet);


	/**
	 * 根据id获取周期模板详情
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/8/10 16:38
	 */
	  CycleTemplet getCtempletInfo(CycleTemplet cycleTemplet);
	  
	  /**
	   * 定时器处理周期行动
	   * @author wuchao
	   * @date 2017年8月12日
	   * @time 上午11:58:06
	   * @version V0.0.1
	   * @return
	   */
	  public void  taskCtemplet();
	  
	  /**
	   * 查询所有有效的模板id
	   * @author wuchao
	   * @date 2017年8月12日
	   * @time 下午12:11:51
	   * @version V0.0.1
	   * @param cycleTemplet
	   * @return
	   */
	  List<CycleTemplet> listCtemplet(CycleTemplet cycleTemplet);
	  
	  
	  
}
