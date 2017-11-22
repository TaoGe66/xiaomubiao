package com.qixin.teamwork.biz.news.svc.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.framework.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.base.EventMap;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.news.svc.NewsSvc;
import com.qixin.teamwork.biz.user.svc.ValidatedSvc;

/**
 * 消息实现层
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:04:59
 * @version V1.0
 */
@Service("newsSvc")
public class NewsSvcImpl implements NewsSvc {

	 @Autowired
	 private NewsDao newsDao;
	 @Resource
		private EventItemDao eventItemDao;
	 @Resource
		private EventDao envetDao;
	/**
	 * 保存消息记录
	 * @author xiehuilin
	 * @param news
	 * @return void
	 * @version V0.0.1
	 * @date 2017/6/14 14:29
	 */
	@Override
	public void insert(News news) {

		newsDao.insert(news); ;
	}

	/**
	 * 用户获取消息
	 * @author zyting
	 * @date 2017年6月12日
	 * @time 上午10:05:07
	 * @version V1.0
	 */
	@Override
	public List<News> getNewsByUserid(News news) {
		List<News> l = newsDao.getNewsByUserid(news);
		for(int i=0;i<l.size();i++){
			l.get(i).setnTimeStr(DateUtils.getDateDetail(l.get(i).getnTime().toString()));	
		}
		return l;
	}

	@Override
	public void update(News news) {
		newsDao.update(news);
	}

	@Override
	public News getNewsInfo(News news) {
		return newsDao.getNewsInfo(news);
	}

	@Override
	public void initializtionNews() {
		//进度待完成
		List<News> newsList=newsDao.getNewsList((byte)6);
		if (newsList.size()>0) {
			for (int i = 0; i < newsList.size(); i++) {
				News news=newsList.get(i);
				EventItem eventItem=new EventItem();
				eventItem.setEiId(news.getEiId());
				EventItem info = eventItemDao.getByEiIdInfo(eventItem);
				System.out.println(news.getnId());
				if (info!=null) {
					news.setnContent(EventMap.newsMap.get("4"));
					news.setnTitle("行动名称："+info.getEiDesc());
					newsDao.update(news);
					
				}
			}
		}
		//项目待计划
		List<News> newsList4=newsDao.getNewsList((byte)4);
		if (newsList4.size()>0) {
			for (int i = 0; i < newsList4.size(); i++) {
				News news=newsList4.get(i);
				
				Event event=new Event();
				event.seteId(news.geteId());
				Event info=envetDao.infoEnvet(event);
				if (info !=null) {
					if (info.getType()==0) {
						news.setnContent(EventMap.newsMap.get("0"));
					}else if (info.getType()==1) {
						news.setnContent(EventMap.newsMap.get("1"));
					}
					news.setnTitle("项目名称："+info.getName());
					System.out.println(news.geteId());
					newsDao.update(news);
				}
				
			}
		}
		//项目待关闭
		List<News> newsList3=newsDao.getNewsList((byte)3);
		if (newsList3.size()>0) {
			for (int i = 0; i < newsList3.size(); i++) {
				News news=newsList3.get(i);
				
				Event event=new Event();
				event.seteId(news.geteId());
				Event info=envetDao.infoEnvet(event);
				if (info !=null) {
					if (info.getType()==0) {
						news.setnContent(EventMap.newsMap.get("2"));
					}else if (info.getType()==1) {
						news.setnContent(EventMap.newsMap.get("3"));
					}
					news.setnTitle("项目名称："+info.getName());
					newsDao.update(news);
				}
				
			}
		}
		//异常终止
			List<News> newsList2=newsDao.getNewsList((byte)2);
			if (newsList2.size()>0) {
				for (int i = 0; i < newsList2.size(); i++) {
					News news=newsList2.get(i);
					EventItem eventItem=new EventItem();
					eventItem.setEiId(news.getEiId());
					EventItem info = eventItemDao.getByEiIdInfo(eventItem);
					System.out.println(news.getnId());
					if (info!=null) {
						News ne=new News();
						ne.setnId(news.getnId());
						ne.setnContent(EventMap.newsMap.get("9"));
						ne.setnTitle("行动名称："+info.getEiDesc());
						ne.setnType((byte)2);
						newsDao.update(ne);
						
					}
				}
			}
		
			//项目关闭
			List<News> newsList10=newsDao.getNewsList((byte)10);
			if (newsList10.size()>0) {
				for (int i = 0; i < newsList10.size(); i++) {
					News news=newsList10.get(i);
					
					Event event=new Event();
					event.seteId(news.geteId());
					Event info=envetDao.infoEnvet(event);
					if (info !=null) {
						News ne=new News();
						ne.setnId(news.getnId());
						ne.setnType((byte)2);
						ne.setnTitle("项目名称："+info.getName());
						newsDao.update(ne);
					}
					
				}
			}
		
		
		
		
	}

	@Override
	public List<News> listGetNotPush(News news) {
		return newsDao.listGetNotPush(news);
	}
}
