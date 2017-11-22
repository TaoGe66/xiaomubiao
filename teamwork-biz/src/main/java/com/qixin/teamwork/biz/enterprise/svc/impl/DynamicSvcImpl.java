package com.qixin.teamwork.biz.enterprise.svc.impl;

import java.util.List;   

import javax.annotation.Resource;

import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.enterprise.dao.DynamicDao;
import com.qixin.teamwork.biz.enterprise.model.Dynamic;
import com.qixin.teamwork.biz.enterprise.svc.DynamicSvc;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.follow.dao.FollowDao;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.light.model.LightInteract;
import com.qixin.teamwork.biz.light.dao.LightInteractDao;

@Service("dynamicSvc")
public class DynamicSvcImpl implements DynamicSvc {

	@Resource 
	private DynamicDao dynamicDao;
	@Resource
	private LightInteractDao interactDao;
	@Resource
	private FollowDao followDao;
	@Resource
	private EventItemDao eventItemDao;
	@Resource
	private EventDao eventDao;
	
	@Override
	public List<Dynamic> dynamicList(Dynamic dynamic) {
		List<Dynamic> l = dynamicDao.dynamicListOne(dynamic);
		for(int i=0;i<l.size();i++){
			l.get(i).setCreateTimeStr(DateUtils.getDateDetailDay(l.get(i).getCreateTime().toString())); 
			LightInteract lightInteract = new LightInteract();
			lightInteract.setLedId(l.get(i).getId());
			//评论
			lightInteract.setType((byte)0);
			List<LightInteract> light = interactDao.listInteract(lightInteract);
			l.get(i).setInteractList(light);
			//点赞
			lightInteract.setType((byte)1);
			l.get(i).setLaudCount(interactDao.listInteract(lightInteract).size());
			//是否点赞
			lightInteract.setCreateBy(dynamic.getUserId());
			if(interactDao.listInteract(lightInteract).size()==0){
				l.get(i).setIsLaud((byte)0);
			}else{
				l.get(i).setIsLaud((byte)1);
			}
			
			l.get(i).setCreateTimeStr(DateUtils.getDateDetailDay(l.get(i).getCreateTime().toString()));
			if(l.get(i).getFinishTime()!=null){
				l.get(i).setFinishTimeStr(DateUtils.getDateDetailDay(l.get(i).getFinishTime().toString()));
			}
			if(l.get(i).getfTime()!=null){
				l.get(i).setfTimeStr(DateUtils.getDateDetailDay(l.get(i).getfTime().toString()));
			}
			if(l.get(i).getDelegateTime()!=null){
				l.get(i).setDelegateTimeStr(DateUtils.getDateDetailDay(l.get(i).getDelegateTime().toString()));
			}
			if(l.get(i).getReceTime()!=null){
				l.get(i).setReceTimeStr(DateUtils.getDateDetailDay(l.get(i).getReceTime().toString()));	
			}
			if(l.get(i).getEnEndTime()!=null){
				l.get(i).setEnEndTimeStr(DateUtils.getDateDetailDay(l.get(i).getEnEndTime().toString()));
			}
			if(l.get(i).getEnFinishTime()!=null){
				l.get(i).setEnFinishTimeStr(DateUtils.getDateDetailDay(l.get(i).getEnFinishTime().toString()));
			}
			
		}
		
		return l;
	}


	/**
	 * 轻企动态详情
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午1:53:40
	 * @version V0.0.3
	 * @param dynamic
	 * @return
	 */
	@Override
	public Dynamic infoDynamic(Dynamic dynamic) {
		//动态详情
		Dynamic dynamicInfo=dynamicDao.infoDynamic(dynamic);
		dynamicInfo.setCreateTimeStr(DateUtils.getDateDetailDay(dynamicInfo.getCreateTime().toString()));
		if (dynamicInfo.getReceTime() !=null) {
			dynamicInfo.setReceTimeStr(DateUtils.getDateDetailDay(dynamicInfo.getReceTime().toString()));
		}
		if (dynamicInfo.getRejecTime() !=null) {
			dynamicInfo.setRejecTimeStr(DateUtils.getDateDetailDay(dynamicInfo.getRejecTime().toString()));
		}
		if (dynamicInfo.getDelegateTime() !=null) {
			dynamicInfo.setDelegateTimeStr(DateUtils.getDateDetailDay(dynamicInfo.getDelegateTime().toString()));
		}
		Event event=new Event();
		event.seteId(dynamicInfo.geteId());
		//事件详情
		Event eventInfo=eventDao.infoEnvet(event);
		eventInfo.setStrFinishTime(DateUtils.getDateDetailDay(eventInfo.getFinishTime().toString()));
		eventInfo.setStrEndTime(DateUtils.getDateDetailDay(eventInfo.getEndTime().toString()));
		dynamicInfo.setEvent(eventInfo);
		if (dynamicInfo.getEiId() !=null) {
			EventItem eventItem=new EventItem();
			eventItem.setEiId(dynamicInfo.getEiId());
			//行动详情
			EventItem item=eventItemDao.getItemInfo(eventItem);
			item.setStrFinishTime(DateUtils.getDateDetailDay(item.getFinishTime().toString()));
			if (item.getfTimestamp()!=null) {
				item.setStrFtimestamp(DateUtils.getDateDetailDay(item.getfTimestamp().toString()));
			}
			dynamicInfo.setItem(item);
		}
		
		LightInteract lt=new LightInteract();
		lt.setType((byte)1);
		lt.setLedId(dynamicInfo.getId());
		//点赞列表
		List<LightInteract> ltList=interactDao.listInteract(lt);
		StringBuffer laudName=new StringBuffer("");;
		if (ltList.size()>0) {
			for (int i = 0; i < ltList.size(); i++) {
				LightInteract lit=ltList.get(i);
				if (StrUtils.isEmpty(laudName.toString())) {
					laudName.append(lit.getUserName());
				}else{
					laudName.append(","+lit.getUserName());
				}
			}
		}
		dynamicInfo.setLaudName(laudName.toString());
		dynamicInfo.setLaudCount(ltList.size());
		lt.setType((byte)0);
		//评论列表
		List<LightInteract> interactList=interactDao.listInteract(lt);
		dynamicInfo.setInteractList(interactList);
		lt.setCreateBy(dynamic.getUserId());
		lt.setType((byte)1);
		//自己是否点赞
		LightInteract lightIt=interactDao.lightInteractInfo(lt);
		if(lightIt !=null && lightIt.getIsValid()==1){
			dynamicInfo.setDotLaud(lightIt.getId());
			dynamicInfo.setIsLaud((byte)1);
		}else if(lightIt !=null && lightIt.getIsValid()==0){
			dynamicInfo.setDotLaud(lightIt.getId());
			dynamicInfo.setIsLaud((byte)0);
		}else{
			dynamicInfo.setIsLaud((byte)0);
		}
		
		return dynamicInfo;
	}


	/**
	 * 保存动态信息
	 * @author wuchao
	 * @date 2017年9月14日
	 * @time 下午1:48:50
	 * @version V0.0.3
	 * @param dynamic
	 */
	@Override
	public void saveDynamic(Dynamic dynamic) {
		dynamicDao.saveDynamic(dynamic);
	}


	@Override
	public Dynamic dynamic(Dynamic dynamic) {
		return dynamicDao.infoDynamic(dynamic);
	}
}
