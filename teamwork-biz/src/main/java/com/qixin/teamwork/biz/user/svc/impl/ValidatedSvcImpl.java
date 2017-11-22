package com.qixin.teamwork.biz.user.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.user.dao.ValidatedDao;
import com.qixin.teamwork.biz.user.model.UserOpenid;
import com.qixin.teamwork.biz.user.model.Validated;
import com.qixin.teamwork.biz.user.svc.ValidatedSvc;

/**
 * 验证码实现层
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:04:59
 * @version V1.0
 */
@Service("validatedSvc")
public class ValidatedSvcImpl implements ValidatedSvc {

	@Autowired 
	ValidatedDao validatedDao;

	@Override
	public Validated getCodeByTel(String tel) {
		Validated validated = new Validated();
		validated.setTel(tel);
		return validatedDao.getValidated(validated);
	}

	@Override
	public void updateCode(Validated validated) {
		validatedDao.updateValidated(validated);
	}

	@Override
	public void insertCode(Validated validated) {
		validatedDao.insertValidated(validated);
	}

	@Override
	public UserOpenid getUserOpenid(UserOpenid userOpenid) {
		return validatedDao.getUserOpenid(userOpenid);
	}

	@Override
	public void insertUserOpenid(UserOpenid userOpenid) {
		validatedDao.insertUserOpenid(userOpenid);
	}

}
