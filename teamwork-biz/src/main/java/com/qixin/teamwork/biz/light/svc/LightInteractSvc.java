package com.qixin.teamwork.biz.light.svc;

import java.util.List;

import com.qixin.teamwork.biz.light.model.LightInteract; 


/**
 * 动态互动接口
 * @author wuchao
 * @date 2017年9月7日
 * @time 下午2:17:34
 * @version V0.0.3
 */
public interface LightInteractSvc {

	/**
	 * 用户轻企动态互动内容列表
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午2:22:39
	 * @version V0.0.3
	 * @param lightUpvote
	 * @return
	 */
	public List<LightInteract> listUpvote(LightInteract lightInteract);
	
	/**
	 * 添加互动内容
	 * @author wuchao
	 * @date 2017年9月7日
	 * @time 下午3:38:45
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void saveInteract(LightInteract lightInteract);
	
	/**
	 * 修改互动内容
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午2:10:42
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void updateInteract(LightInteract lightInteract);
	
	/**
	 * 根据动态id查询动态互动列表
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午2:53:40
	 * @version V0.0.3
	 * @param lightInteract
	 * @return
	 */
	public List<LightInteract> listInteract(LightInteract lightInteract);
	

	/**
	 * 根据轻企id修改动态是否查看
	 * @author wuchao
	 * @date 2017年9月11日
	 * @time 下午3:19:01
	 * @version V0.0.3
	 * @param lightInteract
	 */
	public void updateRead(LightInteract lightInteract);
}
