package com.qixin.teamwork.biz.census.svc;

import java.util.List;

import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.envet.model.Event;

/**
 * 周工作清单接口
 * @author wuchao
 * @date 2017年9月21日
 * @time 下午2:51:08
 * @version V0.0.3
 */
public interface WeeklyListSvc {

	/**
	 * 清单列表
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午2:53:50
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	public List<WeeklyList> listWeeklyList(WeeklyList weeklyList);
	
	/**
	 * 修改清单信息
	 * @author wuchao
	 * @date 2017年9月21日
	 * @time 下午2:54:38
	 * @version V0.0.3
	 * @param weeklyList
	 */
	public void updateWeeklyList(WeeklyList weeklyList);
	
	/**
	 * 是否生成周报
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午9:13:47
	 * @version V0.0.3
	 * @param weeklyList
	 * @return
	 */
	public WeeklyList isMakeWeekly(WeeklyList weeklyList);
	
	/**
	 * 保存清单信息
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList
	 */
	public void saveWeekly(WeeklyList weeklyList);
	/**
	 * 生成周工作清单记录
	 * @author xiehuilin
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList 清单实体
	 * @param type 类型: 0 行动标记完成  1 行动根据
	 */
	void createWeekly(WeeklyList weeklyList,Integer type );
	/**
	 * 根据id获取清单详情
	 * @author xiehuilin
	 * @date 2017年9月26日
	 * @time 上午10:20:35
	 * @version V0.0.3
	 * @param weeklyList 清单实体
	 */
	WeeklyList  getWeeklyInfo(WeeklyList weeklyList);
	/**
	 *@Description:根据项目id获取清单记录
	 *@author:xiehuilin
	 *@param:weeklyList
	 *@return:java.util.List<com.qixin.teamwork.biz.census.model.WeeklyList>
	 *@date:2017/10/25 15:07
	 *@version V 0.0.7
	 */
	List<WeeklyList>  listWeeklistByEid(WeeklyList weeklyList);

	/**
	 *@Description:根据行动id或项目id更新清单记录
	 *@author:xiehuilin
	 *@param:weeklyList
	 *@return:void
	 *@date:2017/10/26 15:30
	 *@version V 0.0.7
	 */
	void updateWeeklyListBatch(WeeklyList weeklyList);
}
