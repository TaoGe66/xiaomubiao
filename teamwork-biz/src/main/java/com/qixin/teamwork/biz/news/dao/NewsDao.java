package com.qixin.teamwork.biz.news.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.news.model.News;

/**
 * 消息dao
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:33
 * @version V1.0
 */

@Repository
public class NewsDao extends BaseDao{
	/**
	*  保存消息记录
	* @author xiehuilin
	* @param
	* @return
	* @version V0.0.1
	* @date 2017/6/14 14:25
	*/
	public  void insert(News news){
		this.insert("com.qixin.teamwork.biz.news.dao.NewsDao.insert",news);
	}

	public List<News> getNewsByUserid(News news){
		return queryForList("com.qixin.teamwork.biz.news.dao.NewsDao.getNewsByUserid", news);
	}

    public void update(News news){
    	update("com.qixin.teamwork.biz.news.dao.NewsDao.update", news);
    }
	
    /**
     * 删除系统消息
     * @author wuchao
     * @date 2017年7月13日
     * @time 下午2:09:09
     * @version V0.0.1
     * @param news
     */
    public void delete(News news){
    	delete("com.qixin.teamwork.biz.news.dao.NewsDao.delete",news);
    }

    /**
     * 查询消息详情
     * @author wuchao
     * @date 2017年7月19日
     * @time 下午1:30:03
     * @version V0.0.1
     * @param news
     * @return
     */
    public News getNewsInfo(News news){
		return (News) queryForObject("com.qixin.teamwork.biz.news.dao.NewsDao.getNewsInfo", news);
	}

    /**
     * 初始化消息
     * @author wuchao
     * @date 2017年10月11日
     * @time 下午9:22:25
     * @version V0.0.3
     * @param nMold
     * @return
     */
	public List<News> getNewsList(Byte nMold) {
		return queryForList("com.qixin.teamwork.biz.news.dao.NewsDao.getNewsList", nMold);	
	}
	/**
	 *@Description: 根据项目id或行动id获取未推送消息记录
	 *@author:xiehuilin
	 *@param:news
	 *@return:java.util.List<com.qixin.teamwork.biz.news.model.News>
	 *@date:2017/10/26 17:52
	 *@version V 0.0.7
	 */
	public  List<News> listGetNotPush(News news){
		return  this.queryForList("com.qixin.teamwork.biz.news.dao.NewsDao.listGetNotPush",news);
	}
}
