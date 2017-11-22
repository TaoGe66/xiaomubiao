package com.qixin.teamwork.biz.light.dao;

import java.util.List; 

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.light.model.LightInteract;

/**
 * 动态互动Dao
 * @author wuchao
 * @date 2017年9月7日
 * @time 下午2:17:12
 * @version V0.0.3
 */
@Repository
public class LightInteractDao extends BaseDao {

	/**
	 * 用户轻企动态互动内容列表
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午2:35:28
	 * @version V0.0.3
	 * @param lightUpvote
	 * @return
	 */
	public List<LightInteract> listUpvote(LightInteract lightUpvote) {
		return queryForList("com.qixin.teamwork.biz.light.dao.LightInteractDao.listUpvote", lightUpvote);
	}

	/**
	 * 添加互动内容
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午3:39:55
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void saveInteract(LightInteract lightInteract) {
		insert("com.qixin.teamwork.biz.light.dao.LightInteractDao.insert", lightInteract);
	}

	/**
	 * 修改互动内容
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午2:11:51
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void updateInteract(LightInteract lightInteract) {
		update("com.qixin.teamwork.biz.light.dao.LightInteractDao.updateInteract", lightInteract);
	}

	/**
	 * 根据动态id查询动态互动列表(包括自己)
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午2:55:42
	 * @version V0.0.3
	 * @param lightInteract
	 * @return
	 */
	public List<LightInteract> listInteract(LightInteract lightInteract) {
		return queryForList("com.qixin.teamwork.biz.light.dao.LightInteractDao.listInteract", lightInteract);
	}

	/**
	 * 根据轻企id修改动态是否查看
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午3:21:48
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void updateRead(LightInteract lightInteract) {
		update("com.qixin.teamwork.biz.light.dao.LightInteractDao.updateRead", lightInteract);
	}
	
	public LightInteract lightInteractInfo(LightInteract lightInteract){
		return(LightInteract) queryForObject("com.qixin.teamwork.biz.light.dao.LightInteractDao.lightInteractInfo", lightInteract);
	}

}
