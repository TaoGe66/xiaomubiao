package com.qixin.teamwork.biz.group.svc.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.group.dao.FriendGroupDao;
import com.qixin.teamwork.biz.group.model.FriendGroup;
import com.qixin.teamwork.biz.group.svc.FriendGroupSvc;


/**
 * 我的好友实现层
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:04:59
 * @version V1.0
 */
@Service("friendGroupSvc")
public class FriendGroupSvcImpl implements FriendGroupSvc {

	@Autowired
	private FriendGroupDao friendGroupDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private WxuserSvc wxuserSvc;
	@Resource
	private EventItemDao eventItemDao;

	@Override
	public Map<String, Object> insert(FriendGroup group) {
		Map<String,Object> map=new HashMap<String, Object>();
		String msg="success";
		String errorCode="";
		if(!group.getUserId().equals(group.getfId())){
			StringBuilder builder=new StringBuilder();
			builder.append("1,").append("2");
			group.setStates(builder.toString());
			List<FriendGroup> friendGroups = friendGroupDao.getFriendnexus(group);
			if(friendGroups.size()>0){
				for(FriendGroup fg:friendGroups){
					if(fg.getState()==1){
						errorCode="对方已是您好友";
						msg="error";
						break;
					}else if(fg.getState()==2){
						errorCode="已是好友关系,等待确认";
						msg="error";
						break;
					}
				}
			}else{
				group.setState(new Byte("2"));
				group.setResState(new Byte("0"));
				group.setCreateBy(group.getUserId());
				friendGroupDao.insert(group);
				User user=new User();
				user.setUserId(group.getUserId());
				User userInfo = wxuserSvc.getUserInfo(user);
				StringBuilder sb=new StringBuilder();
				sb.append(userInfo.getUserName()).append("请求添加你为好友");
				News news=new News();
				news.setSendId(group.getfId());
				news.setnType(new Byte("0"));
				news.setIsRed(new Byte("0"));
				news.setCreateBy(group.getUserId());
				news.setnTitle("添加好友");
				news.setnTime(new Timestamp((new Date()).getTime()));
				news.setnContent(sb.toString());
				newsDao.insert(news);
				msg=group.getfId()>0?msg:"error";
			}
		}else{
			errorCode="好友不能是自己";
			msg="error";
		}
		map.put("msg",msg);
		map.put("errorCode",errorCode);
		return map;
	}

	@Override
	public List<FriendGroup> getFriendGroupById(FriendGroup friendGroup) {
		return friendGroupDao.getFriendGroupById(friendGroup);
	}

	@Override
	public List<FriendGroup> getGroupByUserId(FriendGroup friendGroup) {
		 List<FriendGroup> l = friendGroupDao.getGroupByUserId(friendGroup);
		 friendGroup.setState((byte)1);
		 for(int i=0;i<l.size();i++){
			 friendGroup.setgId(l.get(i).getgId());;
			l.get(i).setCount(friendGroupDao.getFriendGroupById(friendGroup).size());
		 }
		return l;
	}
	/**
	 *  同意：新增好友记录
	 *  拒绝：更新申请状态
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/6/22 17:36
	 */
	@Override
	public  Map<String,Object> update(FriendGroup friendGroup) {
		Map<String,Object> map=new HashMap<String, Object>();
		String msg="success";
		String errorCode="";
		StringBuilder builder=new StringBuilder();
		builder.append("1");
		friendGroup.setStates(builder.toString());
		List<FriendGroup> friendGroups = friendGroupDao.getFriendnexus(friendGroup);
		if(friendGroups.size()>0){
			for(FriendGroup fg:friendGroups){
				if(fg.getState()==1){
					errorCode="对方已同意您的好友请求!";
					msg="error";
					map.put("msg",msg);
					map.put("errorCode",errorCode);
					return map;
				}
			}
		}else{
			friendGroup.setState(friendGroup.getIsAgree());
			friendGroup.setCreateBy(friendGroup.getfId());
			friendGroup.setUpdateTime(StrUtils.getNowTimestamp());
			friendGroupDao.update(friendGroup);

			if(friendGroup.getIsAgree()==1){
				friendGroup.setResState(new Byte("1"));
				friendGroupDao.insert(friendGroup);
			}
			String title=friendGroup.getIsAgree()==1?"好友同意":"好友拒绝";
			User user=new User();
			user.setUserId(friendGroup.getUserId());
			User userInfo = wxuserSvc.getUserInfo(user);
			StringBuilder sb=new StringBuilder();
			String Content=friendGroup.getIsAgree()==1? sb.append(userInfo.getUserName())
					.append("同意你的好友请求").toString(): sb.append(userInfo.getUserName()) .append("拒绝你的好友请求").toString();

			News news=new News();
			news.setSendId(friendGroup.getfId());
			news.setnType(new Byte("0"));
			news.setIsRed(new Byte("0"));
			news.setnTime(new Timestamp((new Date()).getTime()));
			news.setCreateBy(friendGroup.getUserId());
			news.setnTitle(title);
			news.setnContent(Content);
			newsDao.insert(news);
			friendGroupDao.updateUnused(friendGroup);
		}

		map.put("msg",msg);
		map.put("errorCode",errorCode);
		return map;
	}

	/**
	 * 修改我的好友分组关系
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 上午10:24:12
	 * @version V0.0.1
	 * @param friendGroup
	 */
	@Override
	public void updateFriendGroup(FriendGroup friendGroup) {
		friendGroupDao.updateFriendGroup(friendGroup);
	}

	@Override
	public List<FriendGroup> getFriendsList(FriendGroup friendGroup) {
		return friendGroupDao.getFriendsList(friendGroup);
	}

	@Override
	public void savedelFriends(FriendGroup friendGroup) {
		Long userId = friendGroup.getUserId();
		Long fId = friendGroup.getfId();
		friendGroupDao.delFriends(friendGroup);

		friendGroup.setUserId(fId);
		friendGroup.setfId(userId);
		friendGroupDao.delFriends(friendGroup);
		
		List<EventItem> l =eventItemDao.getDelEventStatus(friendGroup);
		EventItem eventItem = new EventItem();
		for(int i=0;i<l.size();i++){
			eventItem.setEiId(l.get(i).getEiId());
			eventItem.setState((byte)5);
			eventItemDao.updateEventItem(eventItem);
		}
	}

	@Override
	public List<FriendGroup> getLatelyFriendList(FriendGroup friendGroup) {
		return friendGroupDao.getLatelyFriendList(friendGroup);
	}

	/**
	 * 修改好友最新联系时间
	 * @author wuchao
	 * @date 2017年8月11日
	 * @time 上午10:53:49
	 * @version V0.0.1
	 * @param friendGroup
	 */
	@Override
	public void updateUnused(FriendGroup friendGroup) {
		friendGroupDao.updateUnused(friendGroup);
	}

	@Override
	public void updateInfo(FriendGroup friendGroup) {
		friendGroupDao.update(friendGroup);
	}
}
