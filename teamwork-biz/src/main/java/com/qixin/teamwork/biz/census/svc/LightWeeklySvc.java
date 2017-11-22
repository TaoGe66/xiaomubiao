package com.qixin.teamwork.biz.census.svc;

import java.util.List;

import com.qixin.teamwork.biz.census.model.LightWeekly;

/**
 * 周报接口
 * @author wuchao
 * @date 2017年9月6日
 * @time 下午4:10:45
 * @version V0.0.3
 */
public interface LightWeeklySvc {

	/**
	 * 保存周报信息
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午4:19:40
	 * @version V0.0.3
	 * @param lightWeekly
	 */
	public void saveWeekly(LightWeekly lightWeekly);
	
	/**
	 * 修改周报内容
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午4:19:57
	 * @version V0.0.3
	 * @param lightWeekly
	 */
	public void updateWeekly(LightWeekly lightWeekly);
	
	/**
	 * 周报内容
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 上午10:36:29
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public LightWeekly infoWeekly(LightWeekly lightWeekly);
	
	/**
	 * 周报内容列表
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午12:02:07
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listWeekly(LightWeekly lightWeekly);
	
	/**
	 * 已完成的项目
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:28:27
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listFinishItem(LightWeekly lightWeekly);

	/**
	 * 已完成的行动
	 * @author wuchao
	 * @date 2017年9月12日
	 * @time 上午9:28:52
	 * @version V0.0.3
	 * @param lightWeekly
	 * @return
	 */
	public List<LightWeekly> listFinishAction(LightWeekly lightWeekly);

	/**
	 * 根据轻企id和行动id获取周报详情
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/9/11 17:04
	 */
	 LightWeekly getWeeklyInfo(LightWeekly weekly);
}
