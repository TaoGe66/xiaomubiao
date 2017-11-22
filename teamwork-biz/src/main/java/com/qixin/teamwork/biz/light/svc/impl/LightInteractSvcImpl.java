package com.qixin.teamwork.biz.light.svc.impl;

import java.util.List; 

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.light.dao.LightInteractDao;
import com.qixin.teamwork.biz.light.model.LightInteract;
import com.qixin.teamwork.biz.light.svc.LightInteractSvc;

/**
 * 动态互动实现层
 * @author wuchao
 * @date 2017年9月7日
 * @time 下午2:18:17
 * @version V0.0.3
 */
@Service("interactSvc")
public class LightInteractSvcImpl implements LightInteractSvc{

	@Resource
	private LightInteractDao interactDao;

	/**
	 * 用户轻企动态互动内容列表
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午2:22:39
	 * @version V0.0.3
	 * @param lightUpvote
	 * @return
	 */
	@Override
	public List<LightInteract> listUpvote(LightInteract lightInteract) {
		return interactDao.listUpvote(lightInteract);
	}

	/**
	 * 添加互动内容
	 * @author zyting
	 * @date 2017年9月7日
	 * @time 下午3:38:45
	 * @version V0.0.3
	 * @param lightInteract
	 */
	@Override
	public void saveInteract(LightInteract lightInteract) {
		//点赞
		if(lightInteract.getType()==1){
			LightInteract l =	interactDao.lightInteractInfo(lightInteract);
			if(l!=null){
				lightInteract.setId(l.getId());
				interactDao.updateInteract(lightInteract);
			}else{
				interactDao.saveInteract(lightInteract);
			}
		//评论
		}else{
			interactDao.saveInteract(lightInteract);
		}
		
	}

	@Override
	public void updateInteract(LightInteract lightInteract) {
		interactDao.updateInteract(lightInteract);
	}

	/**
	 * 根据动态id查询动态互动列表
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午2:53:40
	 * @version V0.0.3
	 * @param lightInteract
	 * @return
	 */
	@Override
	public List<LightInteract> listInteract(LightInteract lightInteract) {
		return interactDao.listInteract(lightInteract);
	}

	/**
	 * 根据轻企id修改动态是否查看
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午3:19:01
	 * @version V0.0.3
	 * @param lightInteract
	 */
	@Override
	public void updateRead(LightInteract lightInteract) {
		interactDao.updateRead(lightInteract);
	}
}
