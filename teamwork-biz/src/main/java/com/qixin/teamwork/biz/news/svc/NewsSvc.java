package com.qixin.teamwork.biz.news.svc;

import java.util.List;
import java.util.Map;

import com.qixin.teamwork.biz.news.model.News;

/**
 * 消息接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */
public interface NewsSvc {
	
	/** 
	* 保存消息记录
	* @author xiehuilin    
	* @param  news 消息实体
	* @version V0.0.1
	* @date 2017/6/14 14:28
	*/
	void insert(News news);
	
	/**
	 * 用户获取消息
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 */
	public List<News> getNewsByUserid(News news);
	
	public void update(News news);

	News getNewsInfo(News news);
	
	/**
	 * 初始化消息
	 * @author wuchao
	 * @date 2017年10月11日
	 * @time 下午9:13:32
	 * @version V0.0.3
	 */
	void  initializtionNews();

	/**
	 *@Description: 根据项目id或行动id获取未推送消息记录
	 *@author:xiehuilin
	 *@param:news
	 *@return:java.util.List<com.qixin.teamwork.biz.news.model.News>
	 *@date:2017/10/26 17:52
	 *@version V 0.0.7
	 */
	public  List<News> listGetNotPush(News news);
}
