package com.qixin.teamwork.biz.enterpriseMember.dao;

import java.util.List;

import org.framework.iInterface.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.qixin.teamwork.biz.enterpriseMember.model.Member;


/**
 * 轻企人员DAO层
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:08:50
 * @version V0.0.1
 */
@Repository("memberDao")
public class MemberDao extends BaseDao{

	public void insert(Member member){
		 this.insert("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.insert",member);
	}
	
	/**
	 * 我的轻企列表
	 * @author wuchao
	 * @date 2017年9月6日
	 * @time 下午6:30:41
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
	public List<Member> listMember(Member member){
		return queryForList("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.listMember", member);
	}

	/**
	 * 根据轻企id获取轻企成员列表
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/9/8 10:24
	 */
	public List<Member> listGetMeber(Member member) {
		return this.queryForList("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.listGetMeber",member);
	}
	
    /**
	 * 是否能删除成员
	 * @author zyting
	 * @date 2017年9月8日
	 * @time 下午3:23:08
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
	public List<Member> isMemberDel(Member member){
		return this.queryForList("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.isMemberDel",member);
	}
	
	public void updateMemberDel(Member member){
        update("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.updateMemberDel", member);
	}
	
	public void updateMember(Member member){
        update("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.updateMember", member);
	}

	/**
	 * 轻企成员信息
	 * @author wuchao
	 * @date 2017年9月20日
	 * @time 上午10:28:52
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
	public Member infoMember(Member member) {
		return (Member) queryForObject("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.infoMember", member);
	}

	/**
	 * 根据轻企id获取轻企成员不包含自己
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/9/8 10:24
	 */
	public List<Member> listGetMemberExceptMy(Member member) {
		return this.queryForList("com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao.listGetMemberExceptMy",member);
	}


}
