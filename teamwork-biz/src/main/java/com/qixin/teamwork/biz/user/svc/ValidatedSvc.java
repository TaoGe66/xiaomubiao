package com.qixin.teamwork.biz.user.svc;

import com.qixin.teamwork.biz.user.model.UserOpenid;
import com.qixin.teamwork.biz.user.model.Validated;

/**
 * 验证码接口
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:05:07
 * @version V1.0
 */
public interface ValidatedSvc {

	public Validated getCodeByTel(String tel);
	public void updateCode(Validated validated);
	public void insertCode(Validated validated);
	public UserOpenid getUserOpenid(UserOpenid userOpenid);
	public void insertUserOpenid(UserOpenid userOpenid);
}
